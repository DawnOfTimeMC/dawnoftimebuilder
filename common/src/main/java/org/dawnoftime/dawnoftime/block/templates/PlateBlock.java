package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.PLATE_SHAPES;

public class PlateBlock extends WaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    public PlateBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(PlateBlock.FACING, Direction.NORTH).setValue(PlateBlock.SHAPE, StairsShape.STRAIGHT));
    }

    public PlateBlock(Properties properties){
        this(properties, PLATE_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PlateBlock.FACING, PlateBlock.SHAPE);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        int index = (state.getValue(PlateBlock.FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch (state.getValue(PlateBlock.SHAPE)) {
            default -> {}
            case OUTER_RIGHT -> index += 3;
            case STRAIGHT -> index += 1;
            case INNER_LEFT -> index += 2;
            case INNER_RIGHT -> index += 5;
        }
        return index % 12;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context).setValue(PlateBlock.FACING, context.getHorizontalDirection());
        return state.setValue(PlateBlock.SHAPE, PlateBlock.getShapeProperty(state, context.getLevel(), context.getClickedPos()));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState, final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal() ? stateIn.setValue(PlateBlock.SHAPE, PlateBlock.getShapeProperty(stateIn, worldIn, currentPos)) : stateIn;
    }

    /**
     * Returns a plate shape property based on the surrounding plates from the given blockstate and position
     */
    private static StairsShape getShapeProperty(final BlockState state, final BlockGetter worldIn, final BlockPos pos) {
        final Direction direction = state.getValue(PlateBlock.FACING);

        BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
        if(PlateBlock.isBlockPlate(adjacentState)) {
            final Direction adjacentDirection = adjacentState.getValue(PlateBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(PlateBlock.FACING).getAxis() && PlateBlock.isDifferentPlate(state, worldIn, pos, adjacentDirection.getOpposite())) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
            }
        }

        adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
        if(PlateBlock.isBlockPlate(adjacentState)) {
            final Direction adjacentDirection = adjacentState.getValue(PlateBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(PlateBlock.FACING).getAxis() && PlateBlock.isDifferentPlate(state, worldIn, pos, adjacentDirection)) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean isDifferentPlate(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final Direction face) {
        final BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
        return !PlateBlock.isBlockPlate(adjacentState) || adjacentState.getValue(PlateBlock.FACING) != state.getValue(PlateBlock.FACING);
    }

    public static boolean isBlockPlate(final BlockState state) {
        return state.getBlock() instanceof PlateBlock;
    }

    @Override
    public @NotNull BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(PlateBlock.FACING, rot.rotate(state.getValue(PlateBlock.FACING)));
    }

    @Override
    public @NotNull BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        final Direction direction = state.getValue(PlateBlock.FACING);
        final StairsShape stairsshape = state.getValue(PlateBlock.SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if(direction.getAxis() == Direction.Axis.Z) {
                    return switch (stairsshape) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if(direction.getAxis() == Direction.Axis.X) {
                    return switch (stairsshape) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
            default:
                break;
        }

        return super.mirror(state, mirrorIn);
    }
}