/**
 *
 */
package org.dawnoftimebuilder.block.german;

import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/**
 * @author seyro
 *
 */
public class WaterTrickleBlock extends BlockDoTB {
	public final static boolean isCorrectFloor(final BlockState belowBlockStateIn, final BlockPos belowBlockPosIn, final IWorld worldIn) {
		return !(belowBlockStateIn.getBlock() instanceof AirBlock) && !(belowBlockStateIn.getBlock() instanceof WaterTrickleBlock) && !(belowBlockStateIn.getBlock() instanceof WaterMovingTrickleBlock) && !(belowBlockStateIn.getBlock() instanceof WaterSourceTrickleBlock) && belowBlockStateIn.isFaceSturdy(worldIn, belowBlockPosIn, Direction.UP);
	}

	public final static boolean isCorrectSupport(final BlockState aboveBlockStateIn) {
		return aboveBlockStateIn.getBlock() instanceof FaucetBlock || aboveBlockStateIn.getBlock() instanceof WaterSourceTrickleBlock || aboveBlockStateIn.getBlock() instanceof WaterTrickleBlock || aboveBlockStateIn.getBlock() instanceof WaterMovingTrickleBlock;
	}

	/**
	 * @param propertiesIn
	 */
	public WaterTrickleBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.FLOOR, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.FLOOR);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		final World			level			= contextIn.getLevel();
		final BlockPos		bottomBlockPos	= contextIn.getClickedPos().below();
		final BlockState	bottomState		= level.getBlockState(bottomBlockPos);

		if (level.getBlockState(bottomBlockPos).getBlock() instanceof AirBlock) {
			return DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, false);
		}

		// Floor condition = the remaining to be tested of isCorrectFloor method
		return DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, !(bottomState.getBlock() instanceof WaterTrickleBlock) && !(bottomState.getBlock() instanceof WaterMovingTrickleBlock) && !(bottomState.getBlock() instanceof WaterSourceTrickleBlock) && bottomState.isFaceSturdy(level, bottomBlockPos, Direction.UP));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {

		if (Direction.DOWN.equals(directionIn)) {
			if (facingStateIn.getBlock() instanceof AirBlock) {
				stateIn = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, stateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, stateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, stateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, stateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR, false);
				worldIn.setBlock(currentPosIn, stateIn, 35);
			}
			else if (!(facingStateIn.getBlock() instanceof WaterTrickleBlock) && !(facingStateIn.getBlock() instanceof WaterMovingTrickleBlock) && facingStateIn.isFaceSturdy(worldIn, facingPosIn, Direction.UP)) {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, true);
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
		else if (Direction.UP.equals(directionIn)) {
			if (facingStateIn.getBlock() instanceof WaterTrickleBlock) {
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
			else if (facingStateIn.getBlock() instanceof AirBlock) {
				stateIn = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, stateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, stateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, stateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, stateIn.getValue(BlockStateProperties.WEST)).setValue(DoTBBlockStateProperties.FLOOR,
						facingStateIn.isFaceSturdy(worldIn, facingPosIn, Direction.UP));
				worldIn.setBlock(currentPosIn, stateIn, 10);

			}
		}

		return stateIn;
	}
}