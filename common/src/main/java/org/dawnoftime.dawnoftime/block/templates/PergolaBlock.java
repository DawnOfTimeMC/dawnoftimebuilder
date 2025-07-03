package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.PERGOLA_SHAPES;

public class PergolaBlock extends BeamBlock {

    public PergolaBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
    }

    public PergolaBlock(Properties properties){
        this(properties, PERGOLA_SHAPES);
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return state.getValue(AXIS_Y) ? BlockStatePropertiesAA.PillarConnection.SIX_PX : BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this)
            state = this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        return switch (context.getClickedFace().getAxis()) {
            case X -> state.setValue(AXIS_X, true);
            case Z -> state.setValue(AXIS_Z, true);
            default -> state.setValue(AXIS_Y, true);
        };
    }
}
