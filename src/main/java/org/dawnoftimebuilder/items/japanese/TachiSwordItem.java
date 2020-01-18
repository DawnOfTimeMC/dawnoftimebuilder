package org.dawnoftimebuilder.items.japanese;

import net.minecraft.item.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class TachiSwordItem extends SwordItem {

	public TachiSwordItem() {
		super(ItemTier.DIAMOND, 3, -2.4F, new Properties());
		this.setRegistryName(MOD_ID, "tachi_sword");
		//this.setCreativeTab(DOTB_TAB);
	}
}
