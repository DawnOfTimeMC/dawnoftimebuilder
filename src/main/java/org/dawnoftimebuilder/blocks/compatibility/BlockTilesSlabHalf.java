package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;

public class BlockTilesSlabHalf extends BlockTilesSlab{

	public BlockTilesSlabHalf(){
		super("slab_tiles");
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
