package org.dawnoftimebuilder.items.templates;

import net.minecraft.item.Item;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class ItemDoTB extends Item {

    public ItemDoTB(String name) {
        super(new Properties().group(DOTB_TAB));
        this.setRegistryName(MOD_ID, name);
    }
}
