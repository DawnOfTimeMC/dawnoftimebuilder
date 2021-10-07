package org.dawnoftimebuilder.block.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.dawnoftimebuilder.block.IBlockCustomItem;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class MixedRoofSupportBlock extends SlabBlockDoTB implements IBlockCustomItem {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    private final Supplier<Block> roofSlabBlockSupplier;

    public MixedRoofSupportBlock(RegistryObject<Block> roofSlabBlockSupplier, Properties properties) {
        super(properties);
        this.roofSlabBlockSupplier = roofSlabBlockSupplier;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TYPE, SlabType.BOTTOM).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SHAPE);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState newState = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getClickedFace();
        newState = newState.setValue(TYPE, (direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockpos.getY() > 0.5D))) ? SlabType.BOTTOM : SlabType.TOP);
        return newState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(SHAPE, getShapeProperty(newState, context.getLevel(), pos));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Direction facing = hit.getDirection();
        ItemStack itemStack = player.getItemInHand(handIn);
        if(!player.isCrouching() && player.mayUseItemAt(pos, facing, itemStack) && facing.getAxis().isVertical() && !itemStack.isEmpty()){
            if(facing == Direction.UP){
                if (state.getValue(TYPE) == SlabType.BOTTOM && itemStack.getItem() == this.roofSlabBlockSupplier.get().asItem()) {
                    if(worldIn.setBlock(pos, state.setValue(TYPE, SlabType.DOUBLE), 11))  {
                        this.setPlacedBy(worldIn, pos, state, player, itemStack);
                        if (player instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
                        }
                        SoundType soundtype = this.getSoundType(state, worldIn, pos, player);
                        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        if(!player.isCreative())
                            itemStack.shrink(1);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }


    @Nullable
    @Override
    public Item getCustomItemBlock() {
        return new BlockItem(this, new Item.Properties().tab(DOTB_TAB)){
            @Override
            public ActionResultType place(BlockItemUseContext context) {
                Direction facing = context.getClickedFace();
                if((context.getPlayer() != null && context.getPlayer().isCrouching()) || !facing.getAxis().isVertical())
                    return super.place(context);

                PlayerEntity player = context.getPlayer();
                ItemStack itemStack = context.getItemInHand();
                World worldIn = context.getLevel();
                BlockPos pos = context.getClickedPos();
                if(!context.replacingClickedOnBlock()) pos = pos.relative(facing.getOpposite());
                if(player != null){
                    if(!player.mayUseItemAt(pos, facing, itemStack))
                        return super.place(context);
                }

                if (!itemStack.isEmpty()) {
                    BlockState state = worldIn.getBlockState(pos);
                    MixedRoofSupportBlock block = (MixedRoofSupportBlock) this.getBlock();
                    if (state.getBlock() == block.roofSlabBlockSupplier.get()) {
                        if(state.getValue(TYPE) == SlabType.TOP){
                            BlockState madeState = block.getStateForPlacement(context);
                            if(madeState == null)
                                return super.place(context);
                            madeState = madeState.setValue(TYPE, SlabType.DOUBLE);
                            if(worldIn.setBlock(pos, madeState.setValue(TYPE, SlabType.DOUBLE), 11))  {
                                this.getBlock().setPlacedBy(worldIn, pos, state, player, itemStack);
                                if (player instanceof ServerPlayerEntity) {
                                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
                                }
                                SoundType soundtype = block.getSoundType(state, worldIn, pos, player);
                                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                itemStack.shrink(1);
                                return ActionResultType.SUCCESS;
                            }
                        }
                    }
                }
                return super.place(context);
            }
        };
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal() ? stateIn.setValue(SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : stateIn;
    }

    /**
     * Returns a shape property based on the surrounding stairs from the given blockstate and position
     */
    private StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
        if (isSameBlock(adjacentState)) {
            if((state.getValue(TYPE) == SlabType.TOP) == (adjacentState.getValue(TYPE) == SlabType.TOP)){
                Direction adjacentDirection = adjacentState.getValue(FACING);
                if (adjacentDirection.getAxis() != state.getValue(FACING).getAxis() && isConnectableRoofSupport(state, worldIn, pos, adjacentDirection.getOpposite())) {
                    return (adjacentDirection == direction.getCounterClockWise()) ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
                }
            }
        }
        adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
        if (isSameBlock(adjacentState)) {
            if((state.getValue(TYPE) == SlabType.TOP) == (adjacentState.getValue(TYPE) == SlabType.TOP)) {
                Direction adjacentDirection = adjacentState.getValue(FACING);
                if (adjacentDirection.getAxis() != state.getValue(FACING).getAxis() && isConnectableRoofSupport(state, worldIn, pos, adjacentDirection)) {
                    return (adjacentDirection == direction.getCounterClockWise()) ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
                }
            }
        }
        return StairsShape.STRAIGHT;
    }

    private boolean isConnectableRoofSupport(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
        return !isSameBlock(adjacentState) || adjacentState.getValue(FACING) != state.getValue(FACING) || ((adjacentState.getValue(TYPE) == SlabType.TOP) != (state.getValue(TYPE) == SlabType.TOP));
    }

    public boolean isSameBlock(BlockState state) {
        return state.getBlock() == this;
    }

    @Override
    public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            if (!world.isClientSide()) {
                world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);
                world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        Direction direction = state.getValue(FACING);
        StairsShape stairsshape = state.getValue(SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        default:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.mirror(state, mirrorIn);
    }
}
