package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

/**
 * @author seyro
 *
 */
public class FaucetBlock extends WaterJetBlock {

	public FaucetBlock(final Properties propertiesIn) {
		super(propertiesIn);
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {
		ActionResultType resultType = super.use(blockStateIn, worldIn, blockPosIn, playerEntityIn, handIn, blockRaytraceResultIn);

		if(resultType == ActionResultType.SUCCESS){
			final BlockPos below = blockPosIn.below();
			if (!blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED)) {
				if (worldIn.getBlockState(below).getBlock() instanceof AirBlock) {
					final BlockPos belowBlockPosOfBelow	= below.below();
					final BlockState state;

					if (worldIn.getBlockState(belowBlockPosOfBelow).getBlock() instanceof AirBlock) {
						 state = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get()
								.defaultBlockState()
								.setValue(BlockStateProperties.NORTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.NORTH_STATE)))
								.setValue(BlockStateProperties.EAST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.EAST_STATE)))
								.setValue(BlockStateProperties.SOUTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.SOUTH_STATE)))
								.setValue(BlockStateProperties.WEST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.WEST_STATE)));
					} else {
						state = DoTBBlocksRegistry.WATER_TRICKLE.get()
								.defaultBlockState()
								.setValue(BlockStateProperties.NORTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.NORTH_STATE)))
								.setValue(BlockStateProperties.EAST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.EAST_STATE)))
								.setValue(BlockStateProperties.SOUTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.SOUTH_STATE)))
								.setValue(BlockStateProperties.WEST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.WEST_STATE)))
								.setValue(DoTBBlockStateProperties.FLOOR, Block.canSupportCenter(worldIn, belowBlockPosOfBelow, Direction.UP));
					}
					worldIn.setBlock(below, state, 10);
				}
			} else {
				final BlockState bottomState = worldIn.getBlockState(below);
				if (bottomState.getBlock() instanceof WaterTrickleBlock || bottomState.getBlock() instanceof WaterMovingTrickleBlock) {
					worldIn.setBlock(below, Blocks.AIR.defaultBlockState(), 10);
				}
			}
		}
		return resultType;
	}
}
