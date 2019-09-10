package org.dawnoftimebuilder.enums;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public enum EToolMaterial {

    /**
     *
     * name - Name of the ToolMaterial (Caps)
     * harvestLevel - 0 wood, 1 stone, 2 iron, 3 diamond
     * durability - Wood 59, stone, 131, iron 250, diamond 1561, gold 32
     * efficiency - hand 1.0F, wood, 2.0F, Stone 4.0F, Iron 6.0F, Diamond 8.0F, Gold 12.0F
     * damage - wood 0.0F, stone 1.0F, diamond 3.0F, gold 0.0F
     * enchantability - Wood 15, stone 5, iron 14, diamond 10, gold 22
     */

    TACHI(0, 500, 1.0F, 8.0F, 15);

    private int harvestLevel;
    private int durability;
    private float efficiency;
    private float damage;
    private int enchantability;

    EToolMaterial(int harvestLevel, int durability, float efficiency, float damage, int enchantability) {
        this.harvestLevel = harvestLevel;
        this.durability = durability;
        this.efficiency = efficiency;
        this.damage = damage;
        this.enchantability = enchantability;
    }

    public ItemArmor.ToolMaterial getToolMaterial() {
        return EnumHelper.addToolMaterial(name(), harvestLevel, durability, efficiency, damage, enchantability);
    }
}
