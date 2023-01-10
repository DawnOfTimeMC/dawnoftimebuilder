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
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
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
public class WaterSourceTrickleBlock extends BlockDoTB {
	/**
	 * @param propertiesIn
	 */
	public WaterSourceTrickleBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.FLOOR, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.FLOOR);
	}

	//@Override
	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		final World			level			= contextIn.getLevel();
		final BlockPos		bottomBlockPos	= contextIn.getClickedPos().below();
		final BlockState	bottomState		= level.getBlockState(bottomBlockPos);

		if (WaterTrickleBlock.isCorrectFloor(bottomState, bottomBlockPos, level)) {
			return DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, true);
		}

		return DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, false);
	}

	@Override
	public void setPlacedBy(final World worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final LivingEntity livingEntityIn, final ItemStack itemStackIn) {
		super.setPlacedBy(worldIn, blockPosIn, blockStateIn, livingEntityIn, itemStackIn);

		final BlockPos belowBlockPos = blockPosIn.below();
		if (worldIn.getBlockState(belowBlockPos).getBlock() instanceof AirBlock) {
			final BlockPos		belowBlockPosOfBelow	= belowBlockPos.below();
			final BlockState	bottomBlockStateOfBelow	= worldIn.getBlockState(belowBlockPosOfBelow);

			if (bottomBlockStateOfBelow.getBlock() instanceof AirBlock) {
				final BlockState state = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST));

				worldIn.setBlock(belowBlockPos, state, 10);
			}
			else {
				final BlockState state = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, blockStateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, blockStateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, blockStateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, blockStateIn.getValue(BlockStateProperties.WEST))

						// TODO terminate floor condition (Seynax)
						.setValue(DoTBBlockStateProperties.FLOOR, /**!(bottomBlockStateOfBelow.getBlock() instanceof AirBlock) &&**/
								bottomBlockStateOfBelow.isFaceSturdy(worldIn, belowBlockPosOfBelow, Direction.UP));

				worldIn.setBlock(belowBlockPos, state, 10);
			}
		}
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {

		if (Direction.DOWN.equals(directionIn)) {
			if (WaterTrickleBlock.isCorrectFloor(stateIn, facingPosIn, worldIn)) {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, true);
			}
			else {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, false);

				if (facingStateIn.getBlock() instanceof WaterSourceTrickleBlock) {
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
				else if (facingStateIn.getBlock() instanceof AirBlock) {
					final BlockPos		belowBlockPosOfBelow	= facingPosIn.below();
					final BlockState	bottomBlockStateOfBelow	= worldIn.getBlockState(belowBlockPosOfBelow);

					if (bottomBlockStateOfBelow.getBlock() instanceof AirBlock) {
						final BlockState state = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, stateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, stateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, stateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, stateIn.getValue(BlockStateProperties.WEST));

						worldIn.setBlock(facingPosIn, state, 10);
					}
					else {
						final BlockState state = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, stateIn.getValue(BlockStateProperties.NORTH)).setValue(BlockStateProperties.EAST, stateIn.getValue(BlockStateProperties.EAST)).setValue(BlockStateProperties.SOUTH, stateIn.getValue(BlockStateProperties.SOUTH)).setValue(BlockStateProperties.WEST, stateIn.getValue(BlockStateProperties.WEST))

								.setValue(DoTBBlockStateProperties.FLOOR, /**!(bottomBlockStateOfBelow.getBlock() instanceof AirBlock) &&**/
										bottomBlockStateOfBelow.isFaceSturdy(worldIn, belowBlockPosOfBelow, Direction.UP));

						worldIn.setBlock(facingPosIn, state, 10);
					}
				}
			}
		}
		else if (Direction.UP.equals(directionIn) && facingStateIn.getBlock() instanceof WaterSourceTrickleBlock) {
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