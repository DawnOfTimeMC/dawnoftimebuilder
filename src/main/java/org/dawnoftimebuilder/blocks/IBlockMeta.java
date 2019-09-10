package org.dawnoftimebuilder.blocks;

import net.minecraft.block.Block;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

public interface IBlockMeta {
	
	String getTranslationKey(int meta);
	
	Block getBlock();

	IEnumMetaVariants[] getVariants();
}
