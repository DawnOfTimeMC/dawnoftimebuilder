package org.dawnoftimebuilder.items.general;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class DoTBItemHat extends DoTBItem {

    public DoTBItemHat(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.HEAD;
    }
}
