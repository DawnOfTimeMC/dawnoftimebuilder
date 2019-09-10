package org.dawnoftimebuilder.items.global;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class DoTBItemSlabWrapper extends ItemSlab {

	@Deprecated
	public DoTBItemSlabWrapper(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}
}
