package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;

public class BlockTilesSlabDouble extends BlockTilesSlab {

	public BlockTilesSlabDouble(){
		super("slab_double_tiles");
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
