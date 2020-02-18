package org.dawnoftimebuilder.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public interface IBlockPillar {

	/**
	 * @return The current pillar connection of the block
	 */
	static DoTBBlockStateProperties.PillarConnection getPillarConnection(IWorld worldIn, BlockPos pos){
		BlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if(block instanceof IBlockPillar) return ((IBlockPillar) block).getBlockPillarConnection(state);
		if(block.isIn(BlockTags.FENCES)) return DoTBBlockStateProperties.PillarConnection.FOUR_PX;
		if(block.isIn(BlockTags.WALLS)) return DoTBBlockStateProperties.PillarConnection.EIGHT_PX;
		return DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	/**
	 * @return The pillar connection for a given blockstate
	 */
	@Nonnull
	DoTBBlockStateProperties.PillarConnection getBlockPillarConnection(BlockState state);
}
