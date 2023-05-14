package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.general.WaterSourceTrickleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalLimitedConnection;

/**
 * @author Seynax
 */
public class FaucetBlock extends WaterSourceTrickleBlock {

	public FaucetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(DoTBBlockStateProperties.ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoTBBlockStateProperties.ACTIVATED);
	}

	@Override
	public boolean[] getWaterTrickleOutPut(BlockState currentState) {
		if(!currentState.getValue(DoTBBlockStateProperties.ACTIVATED)){
			return new boolean[]{
					currentState.getValue(DoTBBlockStateProperties.NORTH_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.EAST_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.SOUTH_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.WEST_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.CENTER_TRICKLE)};
		}
		return super.getWaterTrickleOutPut(currentState);
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {
		final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
		if (!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		}
		worldIn.setBlock(blockPosIn, blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED)), 10);
		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, IWorld worldIn,
			BlockPos currentPosIn, BlockPos facingPosIn)
	{
		BlockState state = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);

		switch(directionIn)
		{
		case NORTH:
			if(state.getValue(BlockStateProperties.NORTH) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case SOUTH:
			if(state.getValue(BlockStateProperties.SOUTH) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case EAST:
			if(state.getValue(BlockStateProperties.EAST) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case WEST:
			if(state.getValue(BlockStateProperties.WEST) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		}

		return state;
	}
}