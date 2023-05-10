package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.general.WaterSourceTrickleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

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
}