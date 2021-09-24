package org.dawnoftimebuilder.block.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
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
import org.dawnoftimebuilder.block.IBlockCustomItem;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class MixedRoofSupportBlock extends SlabBlockDoTB implements IBlockCustomItem {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    private final SlabBlock roofSlabBlock;

    public MixedRoofSupportBlock(SlabBlock roofSlabBlock, Properties properties) {
        super(properties);
        this.roofSlabBlock = roofSlabBlock;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(TYPE, SlabType.BOTTOM).with(SHAPE, StairsShape.STRAIGHT).with(WATERLOGGED, false));
    }

    public MixedRoofSupportBlock(SlabBlock roofSlabBlock, Block block) {
        this(roofSlabBlock, Block.Properties.from(block));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, SHAPE);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        BlockState newState = this.defaultBlockState().with(FACING, context.getPlacementHorizontalFacing());
        BlockPos pos = context.getPos();
        IFluidState ifluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getFace();
        newState = newState.with(TYPE, (direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double)blockpos.getY() > 0.5D))) ? SlabType.BOTTOM : SlabType.TOP);
        return newState.with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER).with(SHAPE, getShapeProperty(newState, context.getLevel(), pos));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Direction facing = hit.getFace();
        ItemStack itemStack = player.getHeldItem(handIn);
        if(!player.isSneaking() && player.canPlayerEdit(pos, facing, itemStack) && facing.getAxis().isVertical() && !itemStack.isEmpty()){
            if(facing == Direction.UP){
                if (state.get(TYPE) == SlabType.BOTTOM && itemStack.getItem() == roofSlabBlock.asItem()) {
                    if(worldIn.setBlockState(pos, state.with(TYPE, SlabType.DOUBLE), 11))  {
                        this.onBlockPlacedBy(worldIn, pos, state, player, itemStack);
                        if (player instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
                        }
                        SoundType soundtype = this.getSoundType(state, worldIn, pos, player);
                        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        if(!player.isCreative())
                            itemStack.shrink(1);
                        return true;
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }


    @Nullable
    @Override
    public Item getCustomItemBlock() {
        return new BlockItem(this, new Item.Properties().group(DOTB_TAB)){
            @Override
            public ActionResultType tryPlace(BlockItemUseContext context) {
                Direction facing = context.getFace();
                if(context.isPlacerSneaking() || facing != Direction.DOWN)
                    return super.place(context);

                PlayerEntity player = context.getPlayer();
                ItemStack itemStack = context.getItem();
                World worldIn = context.getLevel();
                BlockPos pos = context.getPos();
                if(!context.replacingClickedOnBlock()) pos = pos.offset(facing.getOpposite());
                if(player != null){
                    if(!player.canPlayerEdit(pos, facing, itemStack))
                        return super.place(context);
                }

                if (!itemStack.isEmpty()) {
                    BlockState state = worldIn.getBlockState(pos);
                    MixedRoofSupportBlock block = (MixedRoofSupportBlock) this.getBlock();
                    if (state.getBlock() == block.roofSlabBlock) {
                        if(state.get(TYPE) == SlabType.TOP){
                            BlockState madeState = block.getStateForPlacement(context);
                            if(madeState == null)
                                return super.place(context);
                            madeState = madeState.with(TYPE, SlabType.DOUBLE);
                            if(worldIn.setBlockState(pos, madeState.with(TYPE, SlabType.DOUBLE), 11))  {
                                this.onBlockPlaced(pos, worldIn, player, itemStack, madeState);
                                this.getBlock().onBlockPlacedBy(worldIn, pos, state, player, itemStack);
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
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal() ? stateIn.with(SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : stateIn;
    }

    /**
     * Returns a shape property based on the surrounding stairs from the given blockstate and position
     */
    private StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockState adjacentState = worldIn.getBlockState(pos.offset(direction));
        if (isSameBlock(adjacentState)) {
            if((state.get(TYPE) == SlabType.TOP) == (adjacentState.get(TYPE) == SlabType.TOP)){
                Direction adjacentDirection = adjacentState.get(FACING);
                if (adjacentDirection.getAxis() != state.get(FACING).getAxis() && isConnectableRoofSupport(state, worldIn, pos, adjacentDirection.getOpposite())) {
                    return (adjacentDirection == direction.rotateYCCW()) ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
                }
            }
        }
        adjacentState = worldIn.getBlockState(pos.offset(direction.getOpposite()));
        if (isSameBlock(adjacentState)) {
            if((state.get(TYPE) == SlabType.TOP) == (adjacentState.get(TYPE) == SlabType.TOP)) {
                Direction adjacentDirection = adjacentState.get(FACING);
                if (adjacentDirection.getAxis() != state.get(FACING).getAxis() && isConnectableRoofSupport(state, worldIn, pos, adjacentDirection)) {
                    return (adjacentDirection == direction.rotateYCCW()) ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
                }
            }
        }
        return StairsShape.STRAIGHT;
    }

    private boolean isConnectableRoofSupport(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        BlockState adjacentState = worldIn.getBlockState(pos.offset(face));
        return !isSameBlock(adjacentState) || adjacentState.get(FACING) != state.get(FACING) || ((adjacentState.get(TYPE) == SlabType.TOP) != (state.get(TYPE) == SlabType.TOP));
    }

    public boolean isSameBlock(BlockState state) {
        return state.getBlock() == this;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            return true;
        }else return false;
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return !state.get(BlockStateProperties.WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        Direction direction = state.get(FACING);
        StairsShape stairsshape = state.get(SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
                        default:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.mirror(state, mirrorIn);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
