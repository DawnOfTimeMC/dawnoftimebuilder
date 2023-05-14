package org.dawnoftimebuilder.block.templates;

import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class WaterJetBlock extends BlockDoTB {
	public WaterJetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(DoTBBlockStateProperties.ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoTBBlockStateProperties.ACTIVATED);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		BlockState state = super.getStateForPlacement(contextIn);

		BlockState belowState = contextIn.getLevel().getBlockState(contextIn.getClickedPos().below());
		if(belowState.getBlock() instanceof BasePoolBlock)
		{
			if(belowState.getValue(DoTBBlockStateProperties.LEVEL) >= ((BasePoolBlock) belowState.getBlock()).maxLevel-1)
			{
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, true);
			}
		}

		return state;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {
		final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
		if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		}

		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED));
		worldIn.setBlock(blockPosIn, blockStateIn, 10);

		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		if(facingStateIn.getBlock() instanceof BasePoolBlock)
		{
			return stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, facingStateIn.getValue(DoTBBlockStateProperties.LEVEL)
					>= ((BasePoolBlock) facingStateIn.getBlock()).maxLevel-1);
		}

		return stateIn;
	}
}