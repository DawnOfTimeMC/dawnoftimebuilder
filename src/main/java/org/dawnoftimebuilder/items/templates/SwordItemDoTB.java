package org.dawnoftimebuilder.items.templates;

import net.minecraft.item.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SwordItemDoTB extends SwordItem {
	public SwordItemDoTB(String name, IItemTier tier, int attackDamageIn, float attackSpeedIn) {
		super(tier, attackDamageIn, attackSpeedIn, new Properties().group(DOTB_TAB));
		this.setRegistryName(MOD_ID, name);
	}
}
