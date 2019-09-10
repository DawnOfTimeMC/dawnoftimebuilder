package org.dawnoftimebuilder.items.japanese;

import net.minecraft.item.ItemSword;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class ItemTachiSword extends ItemSword {

	public ItemTachiSword() {
		super(ToolMaterial.DIAMOND);
		this.setRegistryName(MOD_ID, "tachi_sword");
		this.setTranslationKey(MOD_ID + ".tachi_sword");
		this.setCreativeTab(DOTB_TAB);
	}
}
