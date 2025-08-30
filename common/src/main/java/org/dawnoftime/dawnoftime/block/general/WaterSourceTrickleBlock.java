package org.dawnoftime.dawnoftime.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;

public class WaterSourceTrickleBlock extends WaterTrickleBlock {
    public WaterSourceTrickleBlock(Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BlockStateProperties.NORTH, false)
                .setValue(BlockStateProperties.EAST, false)
                .setValue(BlockStateProperties.SOUTH, false)
                .setValue(BlockStateProperties.WEST, false)
                .setValue(BlockStatePropertiesAA.CENTER, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                BlockStateProperties.NORTH,
                BlockStateProperties.EAST,
                BlockStateProperties.SOUTH,
                BlockStateProperties.WEST,
                BlockStatePropertiesAA.CENTER);
    }

    @Override
    public boolean[] getWaterTrickleOutPut(BlockState currentState) {
        boolean[] trickleOutput = super.getWaterTrickleOutPut(currentState);
        return new boolean[] {
                trickleOutput[0] || currentState.getValue(BlockStateProperties.NORTH),
                trickleOutput[1] || currentState.getValue(BlockStateProperties.EAST),
                trickleOutput[2] || currentState.getValue(BlockStateProperties.SOUTH),
                trickleOutput[3] || currentState.getValue(BlockStateProperties.WEST),
                trickleOutput[4] || currentState.getValue(BlockStatePropertiesAA.CENTER) };
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
        final Level level = contextIn.getLevel();
        final BlockPos pos = contextIn.getClickedPos();
        final BlockState currentState = level.getBlockState(pos);
        final Direction targetDirection = contextIn.getNearestLookingDirection();

        // If the current block is a WaterTrickle, we keep its state to add a new trickle in this block.
        BlockState outState = currentState.is(this) ? currentState : this.defaultBlockState();
        // We add a trickle for the target direction and the end type.
        return outState.setValue(getPropertyFromDirection(targetDirection), true);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if(state.is(this)) {
            Direction targetDirection = context.getNearestLookingDirection();
            // If the clicked block is a SourceWaterTrickle, and it doesn't have a trickle in the clicked direction, we replace it.
            if(!state.getValue(getPropertyFromDirection(targetDirection))) {
                return true;
            }
        }
        return super.canBeReplaced(state, context);
    }
}