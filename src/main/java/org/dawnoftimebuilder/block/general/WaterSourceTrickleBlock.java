package org.dawnoftimebuilder.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class WaterSourceTrickleBlock extends WaterTrickleBlock {

	public WaterSourceTrickleBlock(Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(BlockStateProperties.NORTH, false)
				.setValue(BlockStateProperties.EAST, false)
				.setValue(BlockStateProperties.SOUTH, false)
				.setValue(BlockStateProperties.WEST, false)
				.setValue(DoTBBlockStateProperties.CENTER, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(
				BlockStateProperties.NORTH,
				BlockStateProperties.EAST,
				BlockStateProperties.SOUTH,
				BlockStateProperties.WEST,
				DoTBBlockStateProperties.CENTER);
	}

	@Override
	public boolean[] getWaterTrickleOutPut(BlockState currentState) {
		boolean[] trickleOutput = super.getWaterTrickleOutPut(currentState);
		return new boolean[]{
				trickleOutput[0] || currentState.getValue(BlockStateProperties.NORTH),
				trickleOutput[1] || currentState.getValue(BlockStateProperties.EAST),
				trickleOutput[2] || currentState.getValue(BlockStateProperties.SOUTH),
				trickleOutput[3] || currentState.getValue(BlockStateProperties.WEST),
				trickleOutput[4] || currentState.getValue(DoTBBlockStateProperties.CENTER)};
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		final World level = contextIn.getLevel();
		final BlockPos pos = contextIn.getClickedPos();
		final BlockState currentState = level.getBlockState(pos);
		final Direction targetDirection = contextIn.getNearestLookingDirection();

		// If the current block is a WaterTrickle, we keep its state to add a new trickle in this block.
		BlockState outState = currentState.getBlock().is(this) ? currentState : this.defaultBlockState();
		// We add a trickle for the target direction and the end type.
		return outState.setValue(getPropertyFromDirection(targetDirection), true);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
		if(state.getBlock().is(this)){
			Direction targetDirection = context.getNearestLookingDirection();
			// If the clicked block is a SourceWaterTrickle, and it doesn't have a trickle in the clicked direction, we replace it.
			if(!state.getValue(getPropertyFromDirection(targetDirection))){
				return true;
			}
		}
		return super.canBeReplaced(state, context);
	}
}