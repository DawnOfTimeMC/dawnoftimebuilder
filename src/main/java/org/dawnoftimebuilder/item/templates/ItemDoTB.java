package org.dawnoftimebuilder.item.templates;

import net.minecraft.item.Item;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class ItemDoTB extends Item {

    public ItemDoTB() {
        this(new Properties());
    }

    public ItemDoTB(Properties properties){
        super(properties.tab(DOTB_TAB));
    }
}
