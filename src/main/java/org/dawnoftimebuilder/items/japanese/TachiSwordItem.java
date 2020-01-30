package org.dawnoftimebuilder.items.japanese;

import net.minecraft.item.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class TachiSwordItem extends SwordItem {

	public TachiSwordItem() {
		super(ItemTier.DIAMOND, 3, -2.4F, new Properties().group(DOTB_TAB));
		this.setRegistryName(MOD_ID, "tachi_sword");
	}
}
