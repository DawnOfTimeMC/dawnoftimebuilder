package org.dawnoftimebuilder.utils;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class DoTBMaterials {

	public enum ArmorMaterial implements IArmorMaterial{
		IRON_PLATE("iron_plate", 15, new int[]{2, 5, 6, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> {
			return Ingredient.fromItems(Items.IRON_INGOT);
		}),
		JAPANESE_LIGHT("japanese_light", 15, new int[]{1, 5, 6, 2}, 14, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, () -> {
			return Ingredient.fromItems(Items.LEATHER);
		}),
		O_YOROI("o_yoroi", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F, () -> {
			return Ingredient.fromItems(Blocks.REDSTONE_BLOCK.asItem());
		}),
		PHARAOH("pharaoh", 12, new int[]{3, 6, 8, 3}, 37, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, () -> {
			return Ingredient.fromItems(Items.GOLD_INGOT);
		}),
		RAIJIN("raijin", 45, new int[]{4, 8, 10, 4}, 26, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F, () -> {
			return Ingredient.fromItems(Blocks.GOLD_BLOCK.asItem());
		});

		private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
		private final String name;
		private final int maxDamageFactor;
		private final int[] damageReductionAmountArray;
		private final int enchantability;
		private final SoundEvent soundEvent;
		private final float toughness;
		private final LazyLoadBase<Ingredient> repairMaterial;

		/**
		 * @param nameIn Material name.
		 * @param maxDamageFactorIn Durability factor.
		 * @param damageReductionAmountsIn Durability of each armor part in following order : Feet, Legs, Chest, Helmet.
		 * @param enchantabilityIn Impact the change of getting powerful enchants.
		 * @param equipSoundIn Sound when equipped.
		 * @param toughness Toughness value (same for every parts).
		 * @param repairMaterialSupplier Ingredient used to repair the armor.
		 */
		ArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
			this.name = nameIn;
			this.maxDamageFactor = maxDamageFactorIn;
			this.damageReductionAmountArray = damageReductionAmountsIn;
			this.enchantability = enchantabilityIn;
			this.soundEvent = equipSoundIn;
			this.toughness = toughness;
			this.repairMaterial = new LazyLoadBase<>(repairMaterialSupplier);
		}

		public int getDurability(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
		}

		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return this.damageReductionAmountArray[slotIn.getIndex()];
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public SoundEvent getSoundEvent() {
			return this.soundEvent;
		}

		public Ingredient getRepairMaterial() {
			return this.repairMaterial.getValue();
		}

		@OnlyIn(Dist.CLIENT)
		public String getName() {
			return this.name;
		}

		public float getToughness() {
			return this.toughness;
		}
	}

	public enum ItemMaterial implements IItemTier {
		//TODO remove useless
		WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
			return Ingredient.fromTag(ItemTags.PLANKS);
		}),
		STONE(1, 131, 4.0F, 1.0F, 5, () -> {
			return Ingredient.fromItems(Blocks.COBBLESTONE);
		}),
		IRON(2, 250, 6.0F, 2.0F, 14, () -> {
			return Ingredient.fromItems(Items.IRON_INGOT);
		}),
		DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
			return Ingredient.fromItems(Items.DIAMOND);
		}),
		GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
			return Ingredient.fromItems(Items.GOLD_INGOT);
		});

		private final int harvestLevel;
		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final int enchantability;
		private final LazyLoadBase<Ingredient> repairMaterial;

		ItemMaterial(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
			this.harvestLevel = harvestLevelIn;
			this.maxUses = maxUsesIn;
			this.efficiency = efficiencyIn;
			this.attackDamage = attackDamageIn;
			this.enchantability = enchantabilityIn;
			this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
		}

		public int getMaxUses() {
			return this.maxUses;
		}

		public float getEfficiency() {
			return this.efficiency;
		}

		public float getAttackDamage() {
			return this.attackDamage;
		}

		public int getHarvestLevel() {
			return this.harvestLevel;
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public Ingredient getRepairMaterial() {
			return this.repairMaterial.getValue();
		}
	}
}
