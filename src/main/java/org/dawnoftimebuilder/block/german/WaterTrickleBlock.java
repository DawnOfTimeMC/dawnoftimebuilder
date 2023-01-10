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

	//@Override
	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		final World			level		= contextIn.getLevel();
		final BlockPos		blockPos	= contextIn.getClickedPos().below();
		final BlockState	bottomState	= level.getBlockState(blockPos);

		if (!(bottomState.getBlock() instanceof AirBlock) && Block.canSupportCenter(level, blockPos, Direction.UP)) {
			return DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, true);
		}

		return DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(DoTBBlockStateProperties.FLOOR, false);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {

		if (Direction.DOWN.equals(directionIn)) {
			if (!(facingStateIn.getBlock() instanceof AirBlock) && Block.canSupportCenter(worldIn, facingPosIn, Direction.UP)) {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, true);
			}
			else {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.FLOOR, false);
			}
		}

		return super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
	}
}