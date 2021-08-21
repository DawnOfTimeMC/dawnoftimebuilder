package org.dawnoftimebuilder.items.templates;

import net.minecraft.item.Item;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class ItemDoTB extends Item {

    public ItemDoTB() {
        this(new Properties());
    }

    public ItemDoTB(Properties properties){
        super(properties.group(DOTB_TAB));
    }
}
