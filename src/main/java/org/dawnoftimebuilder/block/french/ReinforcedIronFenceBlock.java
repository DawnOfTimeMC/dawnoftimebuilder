package org.dawnoftimebuilder.block.french;

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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalConnection;

import static net.minecraft.world.level.block.state.properties.StairsShape.OUTER_LEFT;

public class ReinforcedIronFenceBlock extends ColumnConnectibleBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    private static final VoxelShape[] SHAPES_BOTTOM = makeBottomShapes();
    private static final VoxelShape[] SHAPES = makeShapes();

    public ReinforcedIronFenceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SHAPE, StairsShape.STRAIGHT).setValue(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SHAPE);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        int index = (state.getValue(PlateBlock.FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch(state.getValue(PlateBlock.SHAPE)) {
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
        return state.getValue(VERTICAL_CONNECTION) == VerticalConnection.NONE || state.getValue(VERTICAL_CONNECTION) == VerticalConnection.ABOVE ? SHAPES_BOTTOM[index] : SHAPES[index];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : NW Outer <p/>
     * 1 : N Default <p/>
     * 2 : NW Inner <p/>
     * 3 : NE Outer <p/>
     * 4 : N Default <p/>
     * 5 : NE Inner <p/>
     * 6 : SE Outer <p/>
     * 7 : S Default <p/>
     * 8 : SE Inner <p/>
     * 9 : SW Outer <p/>
     * 10 : S Default <p/>
     * 11 : SW Inner <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_north_flat = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 6.0D);
        VoxelShape vs_east_flat = Block.box(10.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
        VoxelShape vs_south_flat = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 12.0D);
        VoxelShape vs_west_flat = Block.box(4.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
        VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vs_ne_corner = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
        VoxelShape vs_se_corner = Block.box(6.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 6.0D, 10.0D, 16.0D, 16.0D);
        return new VoxelShape[] {
                vs_nw_corner,
                vs_north_flat,
                Shapes.or(vs_north_flat, vs_west_flat, vs_nw_corner),
                vs_ne_corner,
                vs_east_flat,
                Shapes.or(vs_east_flat, vs_north_flat, vs_ne_corner),
                vs_se_corner,
                vs_south_flat,
                Shapes.or(vs_south_flat, vs_east_flat, vs_se_corner),
                vs_sw_corner,
                vs_west_flat,
                Shapes.or(vs_west_flat, vs_south_flat, vs_sw_corner) };
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : NW Outer <p/>
     * 1 : N Default <p/>
     * 2 : NW Inner <p/>
     * 3 : NE Outer <p/>
     * 4 : N Default <p/>
     * 5 : NE Inner <p/>
     * 6 : SE Outer <p/>
     * 7 : S Default <p/>
     * 8 : SE Inner <p/>
     * 9 : SW Outer <p/>
     * 10 : S Default <p/>
     * 11 : SW Inner <p/>
     */
    private static VoxelShape[] makeBottomShapes() {
        VoxelShape vs_north_flat = Shapes.or(Block.box(0.0D, 8.0D, 4.0D, 16.0D, 16.0D, 6.0D), Block.box(0.0D, 0.0D, 1.0D, 16.0D, 8.0D, 9.0D));
        VoxelShape vs_east_flat = Shapes.or(Block.box(10.0D, 8.0D, 0.0D, 12.0D, 16.0D, 16.0D), Block.box(7.0D, 0.0D, 0.0D, 15.0D, 8.0D, 16.0D));
        VoxelShape vs_south_flat = Shapes.or(Block.box(0.0D, 8.0D, 10.0D, 16.0D, 16.0D, 12.0D), Block.box(0.0D, 0.0D, 7.0D, 16.0D, 8.0D, 15.0D));
        VoxelShape vs_west_flat = Shapes.or(Block.box(4.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D), Block.box(1.0D, 0.0D, 0.0D, 9.0D, 8.0D, 16.0D));
        VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vs_ne_corner = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
        VoxelShape vs_se_corner = Block.box(6.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 6.0D, 10.0D, 16.0D, 16.0D);
        return new VoxelShape[] {
                vs_nw_corner,
                vs_north_flat,
                Shapes.or(vs_north_flat, vs_west_flat, vs_nw_corner),
                vs_ne_corner,
                vs_east_flat,
                Shapes.or(vs_east_flat, vs_north_flat, vs_ne_corner),
                vs_se_corner,
                vs_south_flat,
                Shapes.or(vs_south_flat, vs_east_flat, vs_se_corner),
                vs_sw_corner,
                vs_west_flat,
                Shapes.or(vs_west_flat, vs_south_flat, vs_sw_corner) };
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context).setValue(PlateBlock.FACING, context.getHorizontalDirection());
        return state.setValue(PlateBlock.SHAPE, getShapeProperty(state, context.getLevel(), context.getClickedPos()));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isHorizontal() ? stateIn.setValue(PlateBlock.SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : stateIn;
    }

    /**
     * Returns a shape property based on the surrounding blocks from the given blockstate and position
     */
    private static StairsShape getShapeProperty(final BlockState state, final BlockGetter worldIn, final BlockPos pos) {
        final Direction direction = state.getValue(PlateBlock.FACING);

        BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
        if(adjacentState.getBlock() instanceof ReinforcedIronFenceBlock) {
            final Direction adjacentDirection = adjacentState.getValue(PlateBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(PlateBlock.FACING).getAxis()) {
                return adjacentDirection == direction.getCounterClockWise() ? OUTER_LEFT : StairsShape.OUTER_RIGHT;
            }
        }

        adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
        if(adjacentState.getBlock() instanceof ReinforcedIronFenceBlock) {
            final Direction adjacentDirection = adjacentState.getValue(PlateBlock.FACING);
            if(adjacentDirection.getAxis() != state.getValue(PlateBlock.FACING).getAxis()) {
                return adjacentDirection == direction.getCounterClockWise() ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(PlateBlock.FACING, rot.rotate(state.getValue(PlateBlock.FACING)));
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        final Direction direction = state.getValue(PlateBlock.FACING);
        final StairsShape stairsshape = state.getValue(PlateBlock.SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                if(direction.getAxis() == Direction.Axis.Z) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, OUTER_LEFT);
                        default:
                            return state.rotate(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if(direction.getAxis() == Direction.Axis.X) {
                    switch(stairsshape) {
                        case INNER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(Rotation.CLOCKWISE_180).setValue(PlateBlock.SHAPE, OUTER_LEFT);
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
