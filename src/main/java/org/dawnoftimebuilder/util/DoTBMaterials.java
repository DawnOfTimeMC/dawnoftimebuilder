package org.dawnoftimebuilder.util;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.DoTBConfig;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DoTBMaterials {

	public enum ArmorMaterial implements IArmorMaterial{
		IRON_PLATE(
				"iron_plate",
				DoTBConfig.IRON_PLATE_DURABILITY.get(),
				new int[]{
						DoTBConfig.IRON_PLATE_DEF_FEET.get(),
						DoTBConfig.IRON_PLATE_DEF_LEGS.get(),
						DoTBConfig.IRON_PLATE_DEF_CHEST.get(),
						DoTBConfig.IRON_PLATE_DEF_HELMET.get()},
				DoTBConfig.IRON_PLATE_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_IRON,
				DoTBConfig.IRON_PLATE_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Items.IRON_INGOT)),
		HOLY(
				"iron_plate",
				DoTBConfig.HOLY_DURABILITY.get(),
				new int[]{
						DoTBConfig.HOLY_DEF_FEET.get(),
						DoTBConfig.HOLY_DEF_LEGS.get(),
						DoTBConfig.HOLY_DEF_CHEST.get(),
						DoTBConfig.HOLY_DEF_HELMET.get()},
				DoTBConfig.HOLY_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_DIAMOND,
				DoTBConfig.HOLY_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Blocks.GOLD_BLOCK.asItem())),
		JAPANESE_LIGHT(
				"japanese_light",
				DoTBConfig.JAPANESE_LIGHT_DURABILITY.get(),
				new int[]{
						DoTBConfig.JAPANESE_LIGHT_DEF_FEET.get(),
						DoTBConfig.JAPANESE_LIGHT_DEF_LEGS.get(),
						DoTBConfig.JAPANESE_LIGHT_DEF_CHEST.get(),
						DoTBConfig.JAPANESE_LIGHT_DEF_HELMET.get()},
				DoTBConfig.JAPANESE_LIGHT_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_LEATHER,
				DoTBConfig.JAPANESE_LIGHT_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Items.LEATHER)),
		O_YOROI(
				"o_yoroi",
				DoTBConfig.O_YOROI_DURABILITY.get(),
				new int[]{
						DoTBConfig.O_YOROI_DEF_FEET.get(),
						DoTBConfig.O_YOROI_DEF_LEGS.get(),
						DoTBConfig.O_YOROI_DEF_CHEST.get(),
						DoTBConfig.O_YOROI_DEF_HELMET.get()},
				DoTBConfig.O_YOROI_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_IRON,
				DoTBConfig.O_YOROI_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Items.REDSTONE_BLOCK.asItem())),
		PHARAOH(
				"pharaoh",
				DoTBConfig.PHARAOH_DURABILITY.get(),
				new int[]{
					DoTBConfig.PHARAOH_DEF_FEET.get(),
					DoTBConfig.PHARAOH_DEF_LEGS.get(),
					DoTBConfig.PHARAOH_DEF_CHEST.get(),
					DoTBConfig.PHARAOH_DEF_HELMET.get()},
				DoTBConfig.PHARAOH_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_GOLD,
				DoTBConfig.PHARAOH_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Items.GOLD_INGOT)),
		RAIJIN(
				"raijin",
				DoTBConfig.RAIJIN_DURABILITY.get(),
				new int[]{
					DoTBConfig.RAIJIN_DEF_FEET.get(),
					DoTBConfig.RAIJIN_DEF_LEGS.get(),
					DoTBConfig.RAIJIN_DEF_CHEST.get(),
					DoTBConfig.RAIJIN_DEF_HELMET.get()},
				DoTBConfig.RAIJIN_ENCHANT.get(),
				SoundEvents.ARMOR_EQUIP_LEATHER,
				DoTBConfig.RAIJIN_TOUGHNESS.get().floatValue(),
				() -> Ingredient.of(Blocks.GOLD_BLOCK.asItem()));

		private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
		private final String name;
		private final int maxDamageFactor;
		private final int[] damageReductionAmountArray;
		private final int enchantability;
		private final SoundEvent soundEvent;
		private final float toughness;
		private final LazyValue<Ingredient> repairMaterial;

		/**
		 * @param nameIn Material name.
		 * @param maxDamageFactorIn Durability factor.
		 * @param damageReductionAmountsIn Defense of each armor part in the following order : Feet, Legs, Chest, Helmet.
		 * @param enchantabilityIn Impact the chance of getting powerful enchants.
		 * @param equipSoundIn Sound when equipped.
		 * @param toughness Toughness value (same for every part).
		 * @param repairMaterialSupplier Ingredient used to repair the armor.
		 */
		ArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
			this.name = nameIn;
			this.maxDamageFactor = maxDamageFactorIn;
			this.damageReductionAmountArray = damageReductionAmountsIn;
			this.enchantability = enchantabilityIn;
			this.soundEvent = equipSoundIn;
			this.toughness = toughness;
			this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn) {
			return this.damageReductionAmountArray[slotIn.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return this.enchantability;
		}

		@Override
		public @NotNull SoundEvent getEquipSound() {
			return this.soundEvent;
		}

		@Override
		public @NotNull Ingredient getRepairIngredient() {
			return this.repairMaterial.get();
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public @NotNull String getName() {
			return this.name;
		}

		@Override
		public float getToughness() {
			return this.toughness;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}
	}

	/* I keep this here in case we want to add some tools
	public enum ItemMaterial implements IItemTier {
		WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
			return Ingredient.fromTag(ItemTags.PLANKS);
		}),
		STONE(1, 131, 4.0F, 1.0F, 5, () -> {
			return Ingredient.of(Blocks.COBBLESTONE);
		}),
		IRON(2, 250, 6.0F, 2.0F, 14, () -> {
			return Ingredient.of(Items.IRON_INGOT);
		}),
		DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
			return Ingredient.of(Items.DIAMOND);
		}),
		GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
			return Ingredient.of(Items.GOLD_INGOT);
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

	 */
}
