package org.dawnoftimebuilder.block;

import net.minecraft.block.BlockState;

public interface IBlockChain {

	/**
	 * @param state Current state of this block.
	 * @param bottomOfChain True if the chain studied is above this block, false if the chain is under this block.
	 * @return True if the chain must connect to it as if it was part of the chain.
	 */
	default boolean canConnectToChain(BlockState state, boolean bottomOfChain){
		return bottomOfChain;
	}
}
