package org.dawnoftimebuilder.block.templates;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.block.IBlockCustomItem;

import javax.annotation.Nullable;

public class NoItemBlock extends BlockDoTB implements IBlockCustomItem {

	public NoItemBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public Item getCustomItemBlock() {
		return null;
	}
}
