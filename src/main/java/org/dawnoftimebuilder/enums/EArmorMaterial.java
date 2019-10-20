package org.dawnoftimebuilder.enums;

import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public enum EArmorMaterial {

    /**
     *
     * name - Name of Armor (All Caps)
     * durability  - How much damage the armor can take before breaking.
     * reductionAmounts - An array of 4 numbers, each representing how many half shields an armour piece provides. [Helmet, chest, legs, boots]
     * enchantability - Higher number means better enchantments. Wood 15, stone 5, iron 14, diamond 10, gold 22
     * soundOnEquip - Sound played on equip.
     * toughness - affects the amount of damage that is required to penetrate each armor point.  A percentage of damage let through. Diamond is .8F.
     * */

    /* Looking up the values for the vanilla armours will be beneficial here
    *
     */
    IRONPLATE("IronPlate Armor", 239, new int[]{2,6,5,2}, 22, ItemArmorMaterial.IRON.getSoundEvent(), 0.0F),
    DOMARU("DoMaru Armor", 239, new int[]{2,6,5,2}, 14, ItemArmor.ArmorMaterial.LEATHER.getSoundEvent(), 0.0F),
    OYOROI("Oyoroi Armor", 339, new int[]{3,8,6,3}, 10, ItemArmor.ArmorMaterial.IRON.getSoundEvent(), 2.0F),
    RAIJIN("Raijin Armor", 526, new int[]{6,16,12,6}, 22, ItemArmor.ArmorMaterial.DIAMOND.getSoundEvent(), 2.0F);

    private String name;
    private int durability;
    private int[] reductionPoints;
    private int enchantability;
    private SoundEvent soundOnEquip;
    private float toughness;


    EArmorMaterial(String name, int durability, int[] reductionPoints, int enchantability, SoundEvent soundOnEquip, float toughness){
        this.name = name;
        this.durability = durability;
        this.reductionPoints = reductionPoints;
        this.enchantability = enchantability;
        this.soundOnEquip = soundOnEquip;
        this.toughness = toughness;
    }

    public ItemArmor.ArmorMaterial getArmorMaterial() {
        return EnumHelper.addArmorMaterial(name(), name, durability, reductionPoints, enchantability, soundOnEquip, toughness);
    }
}
