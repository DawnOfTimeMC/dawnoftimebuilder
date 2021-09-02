package org.dawnoftimebuilder.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public interface IBlockPillar {

	/**
	 * Get the block pillar connection on the top side. Useful to connect blocks with elements of good size.
	 * @param worldIn World of the block.
	 * @param pos Position of the block
	 * @return PillarConnection of the Block toward the block above it.
	 */
	static DoTBBlockStateProperties.PillarConnection getPillarConnectionAbove(IWorld worldIn, BlockPos pos){
		BlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if(block instanceof IBlockPillar) return ((IBlockPillar) block).getBlockPillarConnectionAbove(state);
		if(block.isIn(BlockTags.FENCES)) return DoTBBlockStateProperties.PillarConnection.FOUR_PX;
		if(block.isIn(BlockTags.WALLS)) return DoTBBlockStateProperties.PillarConnection.EIGHT_PX;
		return DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	/**
	 * Get the block pillar connection on the down side. Useful to connect blocks with elements of good size.
	 * @param worldIn World of the block.
	 * @param pos Position of the block
	 * @return PillarConnection of the Block toward the block under it.
	 */
	static DoTBBlockStateProperties.PillarConnection getPillarConnectionUnder(IWorld worldIn, BlockPos pos){
		BlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if(block instanceof IBlockPillar) return ((IBlockPillar) block).getBlockPillarConnectionAbove(state);
		if(block.isIn(BlockTags.FENCES)) return DoTBBlockStateProperties.PillarConnection.FOUR_PX;
		if(block.isIn(BlockTags.WALLS)) return DoTBBlockStateProperties.PillarConnection.EIGHT_PX;
		return DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	/**
	 * @param state BlockState of the block.
	 * @return The pillar connection on the Top side for a given blockstate
	 */
	@Nonnull
	DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state);

	/**
	 * @param state BlockState of the block.
	 * @return The pillar connection on the Down side for a given blockstate.
	 * Default : return getBlockPillarConnectionAbove value.
	 */
	@Nonnull
	default DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionUnder(BlockState state){
		return this.getBlockPillarConnectionAbove(state);
	}
}
