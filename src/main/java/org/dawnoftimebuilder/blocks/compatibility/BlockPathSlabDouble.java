package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;

public class BlockPathSlabDouble extends BlockPathSlab{

	public BlockPathSlabDouble(){
		super("slab_double_path");
	}
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
	@Override
	public Block getBlock(){
		return this;
	}
}
