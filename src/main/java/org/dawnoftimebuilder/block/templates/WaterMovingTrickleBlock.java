/**
 *
 */
package org.dawnoftimebuilder.block.templates;

import java.util.Random;

import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

/**
 * @author seyro
 *
 */
public class WaterMovingTrickleBlock extends WaterTrickleBlock {
	/**
	 * @param propertiesIn
	 */
	public WaterMovingTrickleBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.FLOOR, false));
	}

	@Override
	public void tick(final BlockState blockStateIn, final ServerWorld serverWorldIn, final BlockPos blockPosIn, final Random randomIn) {

		if (!WaterTrickleBlock.isCorrectSupport(serverWorldIn.getBlockState(blockPosIn.above()))) {

			//if (randomIn.nextInt(0, 1) == 1) {
			serverWorldIn.setBlock(blockPosIn, Blocks.AIR.defaultBlockState(), 35);
			//}
		}
		else {
			if (!(serverWorldIn.getBlockState(blockPosIn.below()).getBlock() instanceof AirBlock)) {
				final BlockPos		bottomBlockPos		= blockPosIn.below();
				final BlockState	bottomBlockState	= serverWorldIn.getBlockState(bottomBlockPos);

				final BlockState	state				= DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR,
						!(bottomBlockState.getBlock() instanceof AirBlock) && bottomBlockState.isFaceSturdy(serverWorldIn, bottomBlockPos, Direction.UP));
				serverWorldIn.setBlock(blockPosIn, state, 35);

				return;
			}

			//if (randomIn.nextInt(0, 1) == 1) {
			BlockState state = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR, false);
			serverWorldIn.setBlock(blockPosIn, state, 35);

			final BlockPos		bottomBlockPos					= blockPosIn.below();
			final BlockPos		bottomOfBottomBlockPos			= bottomBlockPos.below();
			final BlockState	bottomBlockStateOfBottomBlock	= serverWorldIn.getBlockState(bottomOfBottomBlockPos);
			final boolean		bottomIsAboveAirBlock			= bottomBlockStateOfBottomBlock.getBlock() instanceof AirBlock;

			if (bottomIsAboveAirBlock) {
				state = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR, false);
			}
			else {
				state = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR,
						/**!bottomIsAboveAirBlock &&**/
						bottomBlockStateOfBottomBlock.isFaceSturdy(serverWorldIn, bottomOfBottomBlockPos, Direction.UP));
			}

			serverWorldIn.setBlock(bottomBlockPos, state, 35);
			//}
		}
	}

	//@Override
	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		return super.getStateForPlacement(contextIn);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		if (Direction.DOWN.equals(directionIn)) {
			if (!(facingStateIn.getBlock() instanceof AirBlock)) {
				stateIn = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, stateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, stateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, stateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, stateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR,
						facingStateIn.isFaceSturdy(worldIn, facingPosIn, Direction.UP));
				worldIn.setBlock(currentPosIn, stateIn, 35);
			}
			else {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, false);

				if (facingStateIn.getBlock() instanceof WaterTrickleBlock) {
					if (stateIn.getValue(BlockStateProperties.NORTH)) {
						facingStateIn = facingStateIn.setValue(BlockStateProperties.NORTH, true);
					}
					if (stateIn.getValue(BlockStateProperties.EAST)) {
						facingStateIn = facingStateIn.setValue(BlockStateProperties.EAST, true);
					}
					if (stateIn.getValue(BlockStateProperties.SOUTH)) {
						facingStateIn = facingStateIn.setValue(BlockStateProperties.SOUTH, true);
					}
					if (stateIn.getValue(BlockStateProperties.WEST)) {
						facingStateIn = facingStateIn.setValue(BlockStateProperties.WEST, true);
					}
				}
			}
		}
		else if (Direction.UP.equals(directionIn) && facingStateIn.getBlock() instanceof WaterTrickleBlock) {
			if (facingStateIn.getValue(BlockStateProperties.NORTH)) {
				stateIn = stateIn.setValue(BlockStateProperties.NORTH, true);
			}
			if (facingStateIn.getValue(BlockStateProperties.EAST)) {
				stateIn = stateIn.setValue(BlockStateProperties.EAST, true);
			}
			if (facingStateIn.getValue(BlockStateProperties.SOUTH)) {
				stateIn = stateIn.setValue(BlockStateProperties.SOUTH, true);
			}
			if (facingStateIn.getValue(BlockStateProperties.WEST)) {
				stateIn = stateIn.setValue(BlockStateProperties.WEST, true);
			}
		}

		return stateIn;
	}
}