package org.dawnoftimebuilder.util;

import java.util.function.Supplier;

import org.dawnoftimebuilder.DoTBConfig;

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

public class DoTBMaterials {

	public enum ArmorMaterial implements IArmorMaterial {
		IRON_PLATE("iron_plate",
				DoTBConfig.IRON_PLATE_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_IRON,
				() -> Ingredient.of(Items.IRON_BLOCK.asItem())),
		HOLY("holy",
				DoTBConfig.HOLY_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_DIAMOND,
				() -> Ingredient.of(Blocks.GOLD_BLOCK.asItem())),
		JAPANESE_LIGHT(
				"japanese_light",
				DoTBConfig.JAPANESE_LIGHT_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_LEATHER,
				() -> Ingredient.of(Items.LEATHER)),
		O_YOROI("o_yoroi",
				DoTBConfig.O_YOROI_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_IRON,
				() -> Ingredient.of(Items.REDSTONE_BLOCK.asItem())),
		RAIJIN("raijin",
				DoTBConfig.RAIJIN_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_LEATHER,
				() -> Ingredient.of(Blocks.GOLD_BLOCK.asItem())),
		PHARAOH("pharaoh",
				DoTBConfig.PHARAOH_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_GOLD,
				() -> Ingredient.of(Items.GOLD_BLOCK.asItem())),
		ANUBIS("anubis",
				DoTBConfig.ANUBIS_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_GOLD,
				() -> Ingredient.of(Blocks.LAPIS_BLOCK.asItem())),
		CENTURION("centurion",
			   DoTBConfig.CENTURION_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_CHAIN,
				() -> Ingredient.of(Blocks.GOLD_BLOCK.asItem())),
		QUETZALCOATL("quetzalcoatl",
				DoTBConfig.QUETZALCOATL_ARMOR_CONFIG,
				SoundEvents.ARMOR_EQUIP_TURTLE,
				() -> Ingredient.of(Items.FEATHER));

		private static final int[] MAX_DAMAGE_ARRAY = {13, 15, 16, 11 };
		private final String name;
		private final DoTBConfig.ArmorConfig armorConfig;
		private final SoundEvent soundEvent;
		private final LazyValue<Ingredient> repairMaterial;

		/**
		 * @param nameIn                   Material name.
		 * @param config			       Armor config that contains all the parameters of the armor
		 *                                    (durability, armor, enchantability, toughness)
		 * @param equipSoundIn             Sound when equipped.
		 * @param repairMaterialSupplier   Ingredient used to repair the armor.
		 */
		ArmorMaterial(final String nameIn, DoTBConfig.ArmorConfig config, final SoundEvent equipSoundIn, final Supplier<Ingredient> repairMaterialSupplier) {
			this.name = nameIn;
			this.armorConfig = config;
			this.soundEvent = equipSoundIn;
			this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
		}

		@Override
		public int getDefenseForSlot(final EquipmentSlotType slotIn) {
			switch (slotIn) {
			case FEET:
				return this.armorConfig.feetDefSupplier().get();

			case LEGS:
				return this.armorConfig.legsDefSupplier().get();

			case CHEST:
				return this.armorConfig.chestDefSupplier().get();

			case HEAD:
				return this.armorConfig.helmetDefSupplier().get();

			default:
				return 0;
			}
		}

		@Override
		public int getEnchantmentValue() {
			return this.armorConfig.enchantabilitySupplier().get();
		}

		@Override
		public SoundEvent getEquipSound() {
			return this.soundEvent;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairMaterial.get();
		}

		@Override
		public int getDurabilityForSlot(final EquipmentSlotType slotIn) {
			return ArmorMaterial.MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.armorConfig.durabilitySupplier().get();
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public String getName() {
			return this.name;
		}

		@Override
		public float getToughness() {
			return this.armorConfig.toughnessSupplier().get();
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}
	}

	/*
	 * I keep this here in case we want to add some tools public enum ItemMaterial
	 * implements IItemTier { WOOD(0, 59, 2.0F, 0.0F, 15, () -> { return
	 * Ingredient.fromTag(ItemTags.PLANKS); }), STONE(1, 131, 4.0F, 1.0F, 5, () -> {
	 * return Ingredient.of(Blocks.COBBLESTONE); }), IRON(2, 250, 6.0F, 2.0F, 14, ()
	 * -> { return Ingredient.of(Items.IRON_INGOT); }), DIAMOND(3, 1561, 8.0F, 3.0F,
	 * 10, () -> { return Ingredient.of(Items.DIAMOND); }), GOLD(0, 32, 12.0F, 0.0F,
	 * 22, () -> { return Ingredient.of(Items.GOLD_INGOT); });
	 *
	 * private final int harvestLevel; private final int maxUses; private final
	 * float efficiency; private final float attackDamage; private final int
	 * enchantability; private final LazyLoadBase<Ingredient> repairMaterial;
	 *
	 * ItemMaterial(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float
	 * attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
	 * { this.harvestLevel = harvestLevelIn; this.maxUses = maxUsesIn;
	 * this.efficiency = efficiencyIn; this.attackDamage = attackDamageIn;
	 * this.enchantability = enchantabilityIn; this.repairMaterial = new
	 * LazyLoadBase<>(repairMaterialIn); }
	 *
	 * public int getMaxUses() { return this.maxUses; }
	 *
	 * public float getEfficiency() { return this.efficiency; }
	 *
	 * public float getAttackDamage() { return this.attackDamage; }
	 *
	 * public int getHarvestLevel() { return this.harvestLevel; }
	 *
	 * public int getEnchantability() { return this.enchantability; }
	 *
	 * public Ingredient getRepairMaterial() { return
	 * this.repairMaterial.getValue(); } }
	 *
	 */
}