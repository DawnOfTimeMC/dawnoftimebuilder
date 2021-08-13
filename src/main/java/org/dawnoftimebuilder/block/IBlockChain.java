package org.dawnoftimebuilder.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import static org.dawnoftimebuilder.utils.DoTBBlockUtils.DoTBTags.CHAINS;

public interface IBlockChain extends IBlockPillar{

	/**
	 * @param state State of the block
	 * @param tryConnectUnder True if the connection will be made under this block, False is the connection will be above it.
	 * @return True if this block will chain-connect
	 */
	static boolean canBeChained(BlockState state, boolean tryConnectUnder){
		Block block = state.getBlock();
		if(CHAINS.contains(block)) return true;
		if(block instanceof IBlockChain) return tryConnectUnder ? ((IBlockChain) block).canConnectToChainUnder(state) : ((IBlockChain) block).canConnectToChainAbove(state);
		return false;
	}

	/**
	 * @param state Current state of this block.
	 * @return True if this block can connect to a chain above it.  Default : true.
	 */
	default boolean canConnectToChainAbove(BlockState state){
		return true;
	}

	/**
	 * @param state Current state of this block.
	 * @return True if this block can connect to a chain under it. Default : true.
	 */
	default boolean canConnectToChainUnder(BlockState state){
		return true;
	}

	@Override
	default DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
		return this.canConnectToChainAbove(state) ? DoTBBlockStateProperties.PillarConnection.FOUR_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	@Override
	default DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionUnder(BlockState state) {
		return this.canConnectToChainUnder(state) ? DoTBBlockStateProperties.PillarConnection.FOUR_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}
}
