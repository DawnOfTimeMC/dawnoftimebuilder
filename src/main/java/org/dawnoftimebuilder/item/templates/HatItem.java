package org.dawnoftimebuilder.item.templates;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class HatItem extends ItemDoTB {

    public HatItem() {
        super();
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }
}
