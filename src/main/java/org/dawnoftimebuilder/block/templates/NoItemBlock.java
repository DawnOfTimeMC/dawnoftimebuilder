package org.dawnoftimebuilder.block.templates;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.block.ICustomBlockItem;

import javax.annotation.Nullable;

public class NoItemBlock extends BlockDoTB implements ICustomBlockItem {

	public NoItemBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public Item getCustomBlockItem() {
		return null;
	}
}
