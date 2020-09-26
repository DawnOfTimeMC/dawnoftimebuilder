package org.dawnoftimebuilder.enums;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public enum EArmorMaterial {

	/**
	 *
	 * name - Name of Armor (All Caps)
	 * durability  - How much damage the armor can take before breaking.
	 * reductionAmounts - An array of 4 numbers, each representing how many half shields an armour piece provides. [Helmet, legs, chest, boots]
	 * enchantability - Higher number means better enchantments. Wood 15, stone 5, iron 14, diamond 10, gold 22
	 * soundOnEquip - Sound played on equip.
	 * toughness - affects the amount of damage that is required to penetrate each armor point.  A percentage of damage let through. Diamond is .8F.
	 * */

	IRONPLATE("Iron Plate Armor", 239, new int[]{2,5,6,2}, 20, ItemArmor.ArmorMaterial.IRON.getSoundEvent(), 0.0F, Items.IRON_INGOT),
	JAPANESE_LIGHT_ARMOR("Japanese Light Armor", 239, new int[]{2,5,6,2}, 14, ItemArmor.ArmorMaterial.LEATHER.getSoundEvent(), 0.0F, Items.LEATHER),
	OYOROI("O-yoroi Armor", 339, new int[]{3,6,8,3}, 10, ItemArmor.ArmorMaterial.IRON.getSoundEvent(), 2.0F, ItemBlock.getItemFromBlock(Blocks.REDSTONE_BLOCK)),
	PHARAOH("Pharaoh Armor", 226, new int[]{3,6,8,3}, 37, ItemArmor.ArmorMaterial.GOLD.getSoundEvent(), 0.0F, Items.GOLD_INGOT),
	RAIJIN("Raijin Armor", 526, new int[]{4,8,10,4}, 26, ItemArmor.ArmorMaterial.DIAMOND.getSoundEvent(), 2.0F, Items.GOLD_INGOT);

	private final String name;
	private int durability;
	private int[] reductionPoints;
	private int enchantability;
	private final SoundEvent soundOnEquip;
	private float toughness;
	private final Item repairItem;

	EArmorMaterial(String name, int durability, int[] reductionPoints, int enchantability, SoundEvent soundOnEquip, float toughness, Item repairItem){
		this.name = name;
		this.durability = durability;
		this.reductionPoints = reductionPoints;
		this.enchantability = enchantability;
		this.soundOnEquip = soundOnEquip;
		this.toughness = toughness;
		this.repairItem = repairItem;
	}

	public ItemArmor.ArmorMaterial getArmorMaterial() {
		ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(name(), name, durability, reductionPoints, enchantability, soundOnEquip, toughness);
		if(armorMaterial != null) armorMaterial.setRepairItem(new ItemStack(this.repairItem));
		return armorMaterial;
	}

	public String getName(){
		return this.name;
	}

	public int getDurability(){
		return this.durability;
	}

	public int[] getReductionPoints(){
		return this.reductionPoints;
	}

	public int getEnchantability(){
		return this.enchantability;
	}

	public float getToughness(){
		return this.toughness;
	}

	public void loadConfig(int durability, int[] reductionPoints, int enchantability, float toughness){
		this.durability = durability;
		this.reductionPoints = reductionPoints;
		this.enchantability = enchantability;
		this.toughness = toughness;
	}
}
