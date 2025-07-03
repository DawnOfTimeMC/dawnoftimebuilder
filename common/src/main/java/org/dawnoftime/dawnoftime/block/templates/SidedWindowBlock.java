package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SidedWindowBlock extends WaterloggedHorizontalBlock {
    private static final BooleanProperty UP = BlockStateProperties.UP;
    private static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;

    public SidedWindowBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(UP, false).setValue(ATTACHED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, ATTACHED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return this.defaultBlockState();
        }
        return state.setValue(UP, canConnectVertical(state, worldIn, pos)).setValue(ATTACHED, canConnectHorizontal(state, worldIn, pos));
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        boolean changeTOP = canConnectVertical(state, worldIn, pos);
        boolean changeSIDE = canConnectHorizontal(state, worldIn, pos);
        BlockState newState = state;
        if(changeTOP != state.getValue(UP))
            newState = newState.setValue(UP, changeTOP);
        if(changeSIDE != state.getValue(ATTACHED))
            newState = newState.setValue(ATTACHED, changeSIDE);
        if(changeTOP != state.getValue(UP) || changeSIDE != state.getValue(ATTACHED))
            worldIn.setBlock(pos, newState, 10);
    }

    private boolean canConnectVertical(BlockState state, Level worldIn, BlockPos pos) {
        if(isSameWindowAndSide(state, worldIn, pos.below())) {
            return !isSameWindowAndSide(state, worldIn, pos.above());
        }
        return false;
    }

    private boolean canConnectHorizontal(BlockState state, Level worldIn, BlockPos pos) {
        return isSameWindowAndSide(state, worldIn, pos.relative(state.getValue(FACING).getCounterClockWise()));
    }

    private boolean isSameWindowAndSide(BlockState state, Level worldIn, BlockPos pos) {
        BlockState otherState = worldIn.getBlockState(pos);
        if(otherState.getBlock() != this) {
            return false;
        }
        return otherState.getValue(FACING) == state.getValue(FACING);
    }
}
