package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public class PergolaBlock extends BeamBlock {
    private static final VoxelShape[] SHAPES = makeShapes();

    public PergolaBlock(Properties properties) {
        super(properties);
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : Axis X <p/>
     * 1 : Axis Z <p/>
     * 2 : Axis X + Z <p/>
     * 3 : Axis Y <p/>
     * 4 : Axis Y + Bottom <p/>
     * 5 : Axis Y + X <p/>
     * 6 : Axis Y + X + Bottom <p/>
     * 7 : Axis Y + Z <p/>
     * 8 : Axis Y + Z + Bottom <p/>
     * 9 : Axis Y + X + Z <p/>
     * 10 : Axis Y + X + Z + Bottom
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_axis_x = Block.box(0.0D, 5.0D, 6.0D, 16.0D, 11.0D, 10.0D);
        VoxelShape vs_axis_z = Block.box(6.0D, 5.0D, 0.0D, 10.0D, 11.0D, 16.0D);
        VoxelShape vs_axis_x_z = Shapes.or(vs_axis_x, vs_axis_z);
        VoxelShape vs_axis_y = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        VoxelShape vs_axis_y_bottom = Shapes.or(vs_axis_y, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D));
        return new VoxelShape[] {
                vs_axis_x,
                vs_axis_z,
                vs_axis_x_z,
                vs_axis_y,
                vs_axis_y_bottom,
                Shapes.or(vs_axis_y, vs_axis_x),
                Shapes.or(vs_axis_y_bottom, vs_axis_x),
                Shapes.or(vs_axis_y, vs_axis_z),
                Shapes.or(vs_axis_y_bottom, vs_axis_z),
                Shapes.or(vs_axis_y, vs_axis_x_z),
                Shapes.or(vs_axis_y_bottom, vs_axis_x_z)
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[getShapeIndex(state)];
    }

    @Nonnull
    @Override
    public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return state.getValue(AXIS_Y) ? DoTBBlockStateProperties.PillarConnection.SIX_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing == Direction.DOWN) {
            return stateIn.setValue(BOTTOM, this.isBeamBottom(stateIn, facingState) && stateIn.getValue(AXIS_Y) &&
                    !stateIn.getValue(CLIMBING_PLANT).hasNoPlant() && !this.canSustainClimbingPlant(facingState) &&
                    Block.canSupportCenter(worldIn, facingPos, Direction.UP));
        }
        return stateIn;
    }

    @Override
    public void placePlant(BlockState state, Level world, BlockPos pos, int option) {
        BlockPos posUnder = pos.below();
        BlockState stateUnder = world.getBlockState(posUnder);
        super.placePlant(state.setValue(BOTTOM, this.isBeamBottom(state, stateUnder) && state.getValue(AXIS_Y) &&
                !state.getValue(CLIMBING_PLANT).hasNoPlant() && !this.canSustainClimbingPlant(stateUnder) &&
                Block.canSupportCenter(world, posUnder, Direction.UP)), world, pos, option);
    }

    @Override
    public boolean canHavePlant(BlockState state) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this)
            state = this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        switch(context.getClickedFace().getAxis()) {
            case X:
                return state.setValue(AXIS_X, true);
            default:
            case Y:
                return state.setValue(AXIS_Y, true);
            case Z:
                return state.setValue(AXIS_Z, true);
        }
    }
}
