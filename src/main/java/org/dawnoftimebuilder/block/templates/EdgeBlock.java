package org.dawnoftimebuilder.block.templates;

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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EdgeBlock extends WaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    private static final VoxelShape[] SHAPES_TOP = EdgeBlock.makeShapes(false);
    private static final VoxelShape[] SHAPES_BOTTOM = EdgeBlock.makeShapes(true);

    public EdgeBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(EdgeBlock.FACING, Direction.NORTH)
                .setValue(EdgeBlock.HALF, Half.BOTTOM).setValue(EdgeBlock.SHAPE, StairsShape.STRAIGHT));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EdgeBlock.FACING, EdgeBlock.HALF, EdgeBlock.SHAPE);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos,
                               final CollisionContext context) {
        int index = (state.getValue(EdgeBlock.FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch(state.getValue(EdgeBlock.SHAPE)) {
            default:
            case OUTER_LEFT:
                break;
            case OUTER_RIGHT:
                index += 3;
                break;
            case STRAIGHT:
                index += 1;
                break;
            case INNER_LEFT:
                index += 2;
                break;
            case INNER_RIGHT:
                index += 5;
                break;
        }
        index %= 12;
        return state.getValue(EdgeBlock.HALF) == Half.BOTTOM ? EdgeBlock.SHAPES_BOTTOM[index]
                : EdgeBlock.SHAPES_TOP[index];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : NW Outer <p/>
     * 1 : NW Default <p/>
     * 2 : NW Inner <p/>
     * 3 : NE Outer <p/>
     * 4 : NE Default <p/>
     * 5 : NE Inner <p/>
     * 6 : SE Outer <p/>
     * 7 : SE Default <p/>
     * 8 : SE Inner <p/>
     * 9 : SW Outer <p/>
     * 10 : SW Default <p/>
     * 11 : SW Inner <p/>
     */
    private static VoxelShape[] makeShapes(final boolean bottom) {
        final VoxelShape vs_north_flat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        final VoxelShape vs_east_flat = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vs_south_flat = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vs_west_flat = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);
        final VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        final VoxelShape vs_ne_corner = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        final VoxelShape vs_se_corner = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        final VoxelShape[] vss =
                {
                        vs_nw_corner, vs_north_flat, Shapes.or(vs_north_flat, vs_sw_corner), vs_ne_corner, vs_east_flat,
                        Shapes.or(vs_east_flat, vs_nw_corner), vs_se_corner, vs_south_flat,
                        Shapes.or(vs_south_flat, vs_ne_corner), vs_sw_corner, vs_west_flat,
                        Shapes.or(vs_west_flat, vs_se_corner),
                };
        if(bottom) {
            return vss;
        }
        for(int i = 0; i < vss.length; i++) {
            vss[i] = vss[i].move(0.0D, 0.5D, 0.0D);
        }
        return vss;
    }

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
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState,
                                  final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
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

    private static boolean isDifferentEdge(final BlockState state, final LevelReader worldIn, final BlockPos pos,
                                           final Direction face) {
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
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
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
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
                                    StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(EdgeBlock.SHAPE,
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