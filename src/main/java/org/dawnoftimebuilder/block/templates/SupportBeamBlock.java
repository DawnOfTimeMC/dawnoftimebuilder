package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class SupportBeamBlock extends WaterloggedBlock {

    private static final EnumProperty<DoTBBlockStateProperties.PillarConnection> PILLAR_CONNECTION = DoTBBlockStateProperties.PILLAR_CONNECTION;
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final BooleanProperty SUBAXIS = DoTBBlockStateProperties.SUBAXIS;
    private static final VoxelShape[] SHAPES = makeShapes();

    public SupportBeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(SUBAXIS, false).setValue(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.NOTHING));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PILLAR_CONNECTION, HORIZONTAL_AXIS, SUBAXIS);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = 0;
        switch (state.getValue(PILLAR_CONNECTION)) {
            case FOUR_PX:
                index = 3;
                break;
            case EIGHT_PX:
                index = 6;
                break;
            case TEN_PX:
                index = 9;
                break;
            default:
        }
        if (state.getValue(SUBAXIS)) index += 2;
        else if (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.Z) index++;
        return SHAPES[index];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : Axis X <p/>
     * 1 : Axis Z <p/>
     * 2 : Axis X + Z <p/>
     * 3 : Axis X + 4px <p/>
     * 4 : Axis Z + 4px <p/>
     * 5 : Axis X + Z + 4px <p/>
     * 6 : Axis X + 8px <p/>
     * 7 : Axis Z + 8px <p/>
     * 8 : Axis X + Z + 8px <p/>
     * 9 : Axis X + 10px <p/>
     * 10 : Axis Z + 10px <p/>
     * 11 : Axis X + Z + 10px <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_axis_x = Shapes.or(vs, Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
        VoxelShape vs_axis_z = Shapes.or(vs, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
        VoxelShape vs_axis_x_z = Shapes.or(vs_axis_x, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
        VoxelShape vs_axis_4px = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vs_axis_8px = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        VoxelShape vs_axis_10px = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        return new VoxelShape[]{
                vs_axis_x,
                vs_axis_z,
                vs_axis_x_z,
                Shapes.or(vs_axis_x, vs_axis_4px),
                Shapes.or(vs_axis_z, vs_axis_4px),
                Shapes.or(vs_axis_x_z, vs_axis_4px),
                Shapes.or(vs_axis_x, vs_axis_8px),
                Shapes.or(vs_axis_z, vs_axis_8px),
                Shapes.or(vs_axis_x_z, vs_axis_8px),
                Shapes.or(vs_axis_x, vs_axis_10px),
                Shapes.or(vs_axis_z, vs_axis_10px),
                Shapes.or(vs_axis_x_z, vs_axis_10px)
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context).setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis());
        return this.getCurrentState(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return this.getCurrentState(stateIn, worldIn, currentPos);
    }

    private BlockState getCurrentState(BlockState stateIn, LevelAccessor worldIn, BlockPos currentPos) {
        if (stateIn.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) {
            if (canConnect(worldIn, currentPos, Direction.NORTH) || canConnect(worldIn, currentPos, Direction.SOUTH))
                stateIn = stateIn.setValue(SUBAXIS, true);
        } else {
            if (canConnect(worldIn, currentPos, Direction.EAST) || canConnect(worldIn, currentPos, Direction.WEST))
                stateIn = stateIn.setValue(SUBAXIS, true);
        }
        return stateIn.setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(worldIn, currentPos.below()));
    }

    private boolean canConnect(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos.relative(direction));
        return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction) || isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
    }

    private boolean isConnectibleBeam(BlockState state, Direction direction) {
        if (state.getBlock() instanceof BeamBlock)
            return (direction.getAxis() == Direction.Axis.X) ? state.getValue(BeamBlock.AXIS_X) : state.getValue(BeamBlock.AXIS_Z);
        else return false;
    }

    private boolean isConnectibleSupportBeam(BlockState state, Direction direction) {
        if (state.getBlock() instanceof SupportBeamBlock)
            return state.getValue(SupportBeamBlock.HORIZONTAL_AXIS) == direction.getAxis();
        else return false;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
            return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
        else return super.rotate(state, rot);
    }
}