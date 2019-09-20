package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;

public class BlockPathSlabHalf extends BlockPathSlab{

	public BlockPathSlabHalf(){
		super("slab_path");
	}
	
	
	@Override
	public boolean isDouble() {
		return false;
	}
	
	@Override
	public Block getBlock(){
		return this;
	}

}
