package org.dawnoftimebuilder.items.general;

import net.minecraft.item.Item;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBItem extends Item {

    public DoTBItem(String name) {
        super(new Properties().group(DOTB_TAB));
        this.setRegistryName(MOD_ID, name);
    }
}
