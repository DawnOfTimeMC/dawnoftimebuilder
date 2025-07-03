package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.EDGE_SHAPES;

public class EdgeBlock extends WaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    public EdgeBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(EdgeBlock.FACING, Direction.NORTH).setValue(EdgeBlock.HALF, Half.BOTTOM).setValue(EdgeBlock.SHAPE, StairsShape.STRAIGHT));
    }

    public EdgeBlock(Properties properties){
        this(properties, EDGE_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EdgeBlock.FACING, EdgeBlock.HALF, EdgeBlock.SHAPE);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        int index = (state.getValue(EdgeBlock.FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch (state.getValue(EdgeBlock.SHAPE)) {
            default -> {}
            case OUTER_RIGHT -> index += 3;
            case STRAIGHT -> index += 1;
            case INNER_LEFT -> index += 2;
            case INNER_RIGHT -> index += 5;
        }
        index %= 12;
        return state.getValue(EdgeBlock.HALF) == Half.BOTTOM ? index : index + 12;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        final Direction direction = context.getClickedFace();
        final BlockPos pos = context.getClickedPos();
        state = state.setValue(EdgeBlock.FACING, context.getHorizontalDirection()).setValue(EdgeBlock.HALF,
                direction != Direction.DOWN
                        && (direction == Direction.UP || (context.getClickLocation().y - pos.getY() <= 0.5D))
                        ? Half.BOTTOM
                        : Half.TOP);
        return state.setValue(EdgeBlock.SHAPE, EdgeBlock.getShapeProperty(state, context.getLevel(), pos));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState,
                                           final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal()
                ? stateIn.setValue(EdgeBlock.SHAPE, EdgeBlock.getShapeProperty(stateIn, worldIn, currentPos))
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    /**
     * Returns a stair shape property based on the surrounding stairs from the given blockstate and position
     */
    private static StairsShape getShapeProperty(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        final Direction direction = state.getValue(EdgeBlock.FACING);

        BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
        if(EdgeBlock.isBlockEdge(adjacentState)
                && state.getValue(EdgeBlock.HALF) == adjacentState.getValue(EdgeBlock.HALF)) {
            final Direction adjacentDirection = adjacentState.getValue(EdgeBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(EdgeBlock.FACING).getAxis()
                    && EdgeBlock.isDifferentEdge(state, worldIn, pos, adjacentDirection.getOpposite())) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.OUTER_LEFT
                        : StairsShape.OUTER_RIGHT;
            }
        }

        adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
        if(EdgeBlock.isBlockEdge(adjacentState)
                && state.getValue(EdgeBlock.HALF) == adjacentState.getValue(EdgeBlock.HALF)) {
            final Direction adjacentDirection = adjacentState.getValue(EdgeBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(EdgeBlock.FACING).getAxis()
                    && EdgeBlock.isDifferentEdge(state, worldIn, pos, adjacentDirection)) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.INNER_LEFT
                        : StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean isDifferentEdge(final BlockState state, final LevelReader worldIn, final BlockPos pos, final Direction face) {
        final BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
        return !EdgeBlock.isBlockEdge(adjacentState)
                || adjacentState.getValue(EdgeBlock.FACING) != state.getValue(EdgeBlock.FACING)
                || adjacentState.getValue(EdgeBlock.HALF) != state.getValue(EdgeBlock.HALF);
    }

    public static boolean isBlockEdge(final BlockState state) {
        return state.getBlock() instanceof EdgeBlock;
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(EdgeBlock.FACING, rot.rotate(state.getValue(EdgeBlock.FACING)));
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        final Direction direction = state.getValue(EdgeBlock.FACING);
        final StairsShape stairsshape = state.getValue(EdgeBlock.SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if(direction.getAxis() == Direction.Axis.Z) {
                    return switch (stairsshape) {
                        case INNER_LEFT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if(direction.getAxis() == Direction.Axis.X) {
                    return switch (stairsshape) {
                        case INNER_LEFT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
            default:
                break;
        }

        return super.mirror(state, mirrorIn);
    }
}