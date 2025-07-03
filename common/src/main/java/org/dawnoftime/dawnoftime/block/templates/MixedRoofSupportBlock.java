package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class MixedRoofSupportBlock extends SlabBlockDoT {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    private final Supplier<Block> roofSlabBlockSupplier;

    public MixedRoofSupportBlock(final Supplier<Block> roofSlabBlockSupplier, final Properties properties) {
        super(properties);
        this.roofSlabBlockSupplier = roofSlabBlockSupplier;
        this.registerDefaultState(this.defaultBlockState().setValue(MixedRoofSupportBlock.FACING, Direction.NORTH)
                .setValue(SlabBlock.TYPE, SlabType.BOTTOM).setValue(MixedRoofSupportBlock.SHAPE, StairsShape.STRAIGHT)
                .setValue(SlabBlock.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MixedRoofSupportBlock.FACING, MixedRoofSupportBlock.SHAPE);
    }

    @Override
    public boolean canBeReplaced(final BlockState state, final BlockPlaceContext context) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockPos blockpos = context.getClickedPos();
        BlockState newState = this.defaultBlockState().setValue(MixedRoofSupportBlock.FACING,
                context.getHorizontalDirection());
        final BlockPos pos = context.getClickedPos();
        final FluidState fluidState = context.getLevel().getFluidState(blockpos);
        final Direction direction = context.getClickedFace();
        newState = newState.setValue(SlabBlock.TYPE,
                direction != Direction.DOWN
                        && (direction == Direction.UP || (context.getClickLocation().y - blockpos.getY() <= 0.5D))
                        ? SlabType.BOTTOM
                        : SlabType.TOP);
        return newState.setValue(SlabBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(MixedRoofSupportBlock.SHAPE, this.getShapeProperty(newState, context.getLevel(), pos));
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos,
                                 final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        final Direction facing = hit.getDirection();
        final ItemStack itemStack = player.getItemInHand(handIn);
        if(!player.isCrouching() && player.mayUseItemAt(pos, facing, itemStack) && facing.getAxis().isVertical()
                && !itemStack.isEmpty()) {
            if((facing == Direction.UP) && (state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM
                    && itemStack.getItem() == this.roofSlabBlockSupplier.get().asItem())) {
                if(worldIn.setBlock(pos, state.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 11)) {
                    this.setPlacedBy(worldIn, pos, state, player, itemStack);
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
                    }
                    final SoundType soundtype = this.getSoundType(state);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS,
                            (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if(!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    public static Item getBlockItem(MixedRoofSupportBlock block) {
        return new BlockItem(block, new Item.Properties()) {
            @Override
            public InteractionResult place(final BlockPlaceContext context) {
                final Direction facing = context.getClickedFace();
                if(context.getPlayer() != null && context.getPlayer().isCrouching() || !facing.getAxis().isVertical()) {
                    return super.place(context);
                }

                final Player player = context.getPlayer();
                final ItemStack itemStack = context.getItemInHand();
                final Level worldIn = context.getLevel();
                BlockPos pos = context.getClickedPos();
                if(!context.replacingClickedOnBlock()) {
                    pos = pos.relative(facing.getOpposite());
                }
                if((player != null) && !player.mayUseItemAt(pos, facing, itemStack)) {
                    return super.place(context);
                }

                if(!itemStack.isEmpty()) {
                    final BlockState state = worldIn.getBlockState(pos);
                    final MixedRoofSupportBlock block = (MixedRoofSupportBlock) this.getBlock();
                    if((state.getBlock() == block.roofSlabBlockSupplier.get())
                            && (state.getValue(SlabBlock.TYPE) == SlabType.TOP)) {
                        BlockState madeState = block.getStateForPlacement(context);
                        if(madeState == null) {
                            return super.place(context);
                        }
                        madeState = madeState.setValue(SlabBlock.TYPE, SlabType.DOUBLE);
                        if(worldIn.setBlock(pos, madeState.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 11)) {
                            this.getBlock().setPlacedBy(worldIn, pos, state, player, itemStack);
                            if(player instanceof ServerPlayer) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
                            }
                            final SoundType soundtype = block.getSoundType(state);
                            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS,
                                    (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                            itemStack.shrink(1);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                return super.place(context);
            }
        };
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState,
                                  final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal()
                ? stateIn.setValue(MixedRoofSupportBlock.SHAPE, this.getShapeProperty(stateIn, worldIn, currentPos))
                : stateIn;
    }

    /**
     * Returns a shape property based on the surrounding stairs from the given blockstate and position
     */
    private StairsShape getShapeProperty(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        final Direction direction = state.getValue(MixedRoofSupportBlock.FACING);
        BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
        if(this.isSameBlock(adjacentState) && (state
                .getValue(SlabBlock.TYPE) == SlabType.TOP == (adjacentState.getValue(SlabBlock.TYPE) == SlabType.TOP))) {
            final Direction adjacentDirection = adjacentState.getValue(MixedRoofSupportBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(MixedRoofSupportBlock.FACING).getAxis()
                    && this.isConnectableRoofSupport(state, worldIn, pos, adjacentDirection.getOpposite())) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.OUTER_LEFT
                        : StairsShape.OUTER_RIGHT;
            }
        }
        adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
        if(this.isSameBlock(adjacentState) && (state
                .getValue(SlabBlock.TYPE) == SlabType.TOP == (adjacentState.getValue(SlabBlock.TYPE) == SlabType.TOP))) {
            final Direction adjacentDirection = adjacentState.getValue(MixedRoofSupportBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(MixedRoofSupportBlock.FACING).getAxis()
                    && this.isConnectableRoofSupport(state, worldIn, pos, adjacentDirection)) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.INNER_LEFT
                        : StairsShape.INNER_RIGHT;
            }
        }
        return StairsShape.STRAIGHT;
    }

    private boolean isConnectableRoofSupport(final BlockState state, final LevelReader worldIn, final BlockPos pos,
                                             final Direction face) {
        final BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
        return !this.isSameBlock(adjacentState)
                || adjacentState.getValue(MixedRoofSupportBlock.FACING) != state.getValue(MixedRoofSupportBlock.FACING)
                || adjacentState
                .getValue(SlabBlock.TYPE) == SlabType.TOP != (state.getValue(SlabBlock.TYPE) == SlabType.TOP);
    }

    public boolean isSameBlock(final BlockState state) {
        return state.getBlock() == this;
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if(state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if(!world.isClientSide()) {
            world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);
            world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        }
        return true;
    }

    @Override
    public boolean canPlaceLiquid(final BlockGetter world, final BlockPos pos, final BlockState state,
                                  final Fluid fluid) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(MixedRoofSupportBlock.FACING, rot.rotate(state.getValue(MixedRoofSupportBlock.FACING)));
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        final Direction direction = state.getValue(MixedRoofSupportBlock.FACING);
        final StairsShape stairsshape = state.getValue(MixedRoofSupportBlock.SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if(direction.getAxis() == Direction.Axis.Z) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.OUTER_LEFT);
                        default:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if(direction.getAxis() == Direction.Axis.X) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
                                    StairsShape.OUTER_LEFT);
                        case STRAIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
            default:
                break;
        }

        return super.mirror(state, mirrorIn);
    }
}
