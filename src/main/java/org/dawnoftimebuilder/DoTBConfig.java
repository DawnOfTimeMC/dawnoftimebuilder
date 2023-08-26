package org.dawnoftimebuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import net.minecraftforge.common.ForgeConfigSpec;

public class DoTBConfig {
	public final static List<CustomArmorItem> ARMORS_TO_SYNC = new ArrayList<>();

	public final static String FOOD_CATEGORY = "food_properties";
	public final static String ARMOR_MATERIAL_CATEGORY = "armor_properties";
	public final static String BLOCK_CATEGORY = "block_properties";
	public final static String WORLD_GENERATION_CATEGORY = "world_generation";
	public final static String ENTITY_CATEGORY = "entity_properties";

	// Plants
	public static ForgeConfigSpec.IntValue GRAPE_HUNGER;
	public static ForgeConfigSpec.DoubleValue GRAPE_SATURATION;
	public static ForgeConfigSpec.IntValue MAIZE_HUNGER;
	public static ForgeConfigSpec.DoubleValue MAIZE_SATURATION;
	public static ForgeConfigSpec.IntValue MULBERRY_HUNGER;
	public static ForgeConfigSpec.DoubleValue MULBERRY_SATURATION;

	// Armors
	public static ForgeConfigSpec.IntValue IRON_PLATE_DURABILITY;
	public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_FEET;
	public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_LEGS;
	public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_CHEST;
	public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_HELMET;
	public static ForgeConfigSpec.IntValue IRON_PLATE_ENCHANT;
	public static ForgeConfigSpec.DoubleValue IRON_PLATE_TOUGHNESS;
	public static ForgeConfigSpec.IntValue HOLY_DURABILITY;
	public static ForgeConfigSpec.IntValue HOLY_DEF_FEET;
	public static ForgeConfigSpec.IntValue HOLY_DEF_LEGS;
	public static ForgeConfigSpec.IntValue HOLY_DEF_CHEST;
	public static ForgeConfigSpec.IntValue HOLY_DEF_HELMET;
	public static ForgeConfigSpec.IntValue HOLY_ENCHANT;
	public static ForgeConfigSpec.DoubleValue HOLY_TOUGHNESS;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_DURABILITY;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_DEF_FEET;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_DEF_LEGS;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_DEF_CHEST;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_DEF_HELMET;
	public static ForgeConfigSpec.IntValue JAPANESE_LIGHT_ENCHANT;
	public static ForgeConfigSpec.DoubleValue JAPANESE_LIGHT_TOUGHNESS;
	public static ForgeConfigSpec.IntValue O_YOROI_DURABILITY;
	public static ForgeConfigSpec.IntValue O_YOROI_DEF_FEET;
	public static ForgeConfigSpec.IntValue O_YOROI_DEF_LEGS;
	public static ForgeConfigSpec.IntValue O_YOROI_DEF_CHEST;
	public static ForgeConfigSpec.IntValue O_YOROI_DEF_HELMET;
	public static ForgeConfigSpec.IntValue O_YOROI_ENCHANT;
	public static ForgeConfigSpec.DoubleValue O_YOROI_TOUGHNESS;
	public static ForgeConfigSpec.IntValue PHARAOH_DURABILITY;
	public static ForgeConfigSpec.IntValue PHARAOH_DEF_FEET;
	public static ForgeConfigSpec.IntValue PHARAOH_DEF_LEGS;
	public static ForgeConfigSpec.IntValue PHARAOH_DEF_CHEST;
	public static ForgeConfigSpec.IntValue PHARAOH_DEF_HELMET;
	public static ForgeConfigSpec.IntValue PHARAOH_ENCHANT;
	public static ForgeConfigSpec.DoubleValue PHARAOH_TOUGHNESS;
	public static ForgeConfigSpec.IntValue RAIJIN_DURABILITY;
	public static ForgeConfigSpec.IntValue RAIJIN_DEF_FEET;
	public static ForgeConfigSpec.IntValue RAIJIN_DEF_LEGS;
	public static ForgeConfigSpec.IntValue RAIJIN_DEF_CHEST;
	public static ForgeConfigSpec.IntValue RAIJIN_DEF_HELMET;
	public static ForgeConfigSpec.IntValue RAIJIN_ENCHANT;
	public static ForgeConfigSpec.DoubleValue RAIJIN_TOUGHNESS;

	// General
	public static ForgeConfigSpec.IntValue DRYING_TIME_VARIATION;
	public static ForgeConfigSpec.IntValue CLIMBING_PLANT_GROWTH_CHANCE;
	public static ForgeConfigSpec.IntValue CLIMBING_PLANT_SPREAD_CHANCE;
	public static ForgeConfigSpec.IntValue STICK_BUNDLE_GROWTH_CHANCE;

	// Plants generation
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> CAMELLIA_GENERATION;
	public static ForgeConfigSpec.IntValue CAMELLIA_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> CYPRESS_GENERATION;
	public static ForgeConfigSpec.IntValue CYPRESS_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> RED_MAPLE_GENERATION;
	public static ForgeConfigSpec.IntValue RED_MAPLE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> BOXWOOD_BUSH_GENERATION;
	public static ForgeConfigSpec.IntValue BOXWOOD_BUSH_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> MULBERRY_GENERATION;
	public static ForgeConfigSpec.IntValue MULBERRY_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> RICE_GENERATION;
	public static ForgeConfigSpec.IntValue RICE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> WILD_GRAPE_GENERATION;
	public static ForgeConfigSpec.IntValue WILD_GRAPE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> WILD_MAIZE_GENERATION;
	public static ForgeConfigSpec.IntValue WILD_MAIZE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMELINA_GENERATION;
	public static ForgeConfigSpec.IntValue COMMELINA_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> GERANIUM_PINK_GENERATION;
	public static ForgeConfigSpec.IntValue GERANIUM_PINK_ROLLS;

	public static ForgeConfigSpec.IntValue SILKMOTH_SPAWN_CHANCE;
	public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_MAX_RANGE;
	public static ForgeConfigSpec.BooleanValue SILKMOTH_MUST_DIE;
	public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_CHANGE;
	public static ForgeConfigSpec.BooleanValue SILKMOTH_MUTE;
	public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_HEALTH;
	public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_ATTACK;
	public static ForgeConfigSpec.BooleanValue JAPANESE_DRAGON_MUTE;

	public final static class Config {
		Config(final ForgeConfigSpec.Builder builderIn) {

			builderIn.comment(
					"----------------------------------------|| Food settings ||----------------------------------------")
					.push(DoTBConfig.FOOD_CATEGORY);
			builderIn.push("grape");
			DoTBConfig.GRAPE_HUNGER = builderIn.defineInRange("grapeHunger", 4, 1, 20);
			DoTBConfig.GRAPE_SATURATION = builderIn.defineInRange("grapeSaturation", 0.2, 0.1, 3.0);
			builderIn.pop();
			builderIn.push("maize");
			DoTBConfig.MAIZE_HUNGER = builderIn.defineInRange("maizeHunger", 6, 1, 20);
			DoTBConfig.MAIZE_SATURATION = builderIn.defineInRange("maizeSaturation", 1.0, 0.1, 3.0);
			builderIn.pop();
			builderIn.push("mulberry");
			DoTBConfig.MULBERRY_HUNGER = builderIn.defineInRange("mulberryHunger", 1, 1, 20);
			DoTBConfig.MULBERRY_SATURATION = builderIn.defineInRange("mulberrySaturation", 0.5, 0.1, 3.0);
			builderIn.pop();
			builderIn.pop();

			builderIn.comment(
					"----------------------------------------|| Armor settings ||---------------------------------------")
					.push(DoTBConfig.ARMOR_MATERIAL_CATEGORY);

			builderIn.push("iron_plate");
			DoTBConfig.IRON_PLATE_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("ironPlateDurabilityFactor", 15, 1, 1000);

			DoTBConfig.IRON_PLATE_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("ironPlateDefenseHelmet", 2, 1, 100);
			DoTBConfig.IRON_PLATE_DEF_CHEST = builderIn.comment("Chest damage reduction :")
					.defineInRange("ironPlateDefenseChest", 6, 1, 100);
			DoTBConfig.IRON_PLATE_DEF_LEGS = builderIn.comment("Legs damage reduction :")
					.defineInRange("ironPlateDefenseLegs", 5, 1, 100);
			DoTBConfig.IRON_PLATE_DEF_FEET = builderIn.comment("Feet damage reduction :")
					.defineInRange("ironPlateDefenseFeet", 2, 1, 100);
			DoTBConfig.IRON_PLATE_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("ironPlateEnchantability", 20, 1, 100);
			DoTBConfig.IRON_PLATE_TOUGHNESS = builderIn.comment("This armor's toughness :")
					.defineInRange("ironPlateToughness", 0.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.push("holy");
			DoTBConfig.HOLY_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("holyDurabilityFactor", 45, 1, 1000);
			DoTBConfig.HOLY_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("holyDefenseHelmet", 5, 1, 100);
			DoTBConfig.HOLY_DEF_CHEST = builderIn.comment("Chest damage reduction :").defineInRange("holyDefenseChest",
					10, 1, 100);
			DoTBConfig.HOLY_DEF_LEGS = builderIn.comment("Legs damage reduction :").defineInRange("holyDefenseLegs", 8,
					1, 100);
			DoTBConfig.HOLY_DEF_FEET = builderIn.comment("Feet damage reduction :").defineInRange("holyDefenseFeet", 5,
					1, 100);
			DoTBConfig.HOLY_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("holyEnchantability", 8, 1, 100);
			DoTBConfig.HOLY_TOUGHNESS = builderIn.comment("This armor's toughness :").defineInRange("holyToughness",
					4.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.push("japanese_light");
			DoTBConfig.JAPANESE_LIGHT_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("japaneseLightDurabilityFactor", 15, 1, 1000);
			DoTBConfig.JAPANESE_LIGHT_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("japaneseLightDefenseHelmet", 2, 1, 100);
			DoTBConfig.JAPANESE_LIGHT_DEF_CHEST = builderIn.comment("Chest damage reduction :")
					.defineInRange("japaneseLightDefenseChest", 6, 1, 100);
			DoTBConfig.JAPANESE_LIGHT_DEF_LEGS = builderIn.comment("Legs damage reduction :")
					.defineInRange("japaneseLightDefenseLegs", 5, 1, 100);
			DoTBConfig.JAPANESE_LIGHT_DEF_FEET = builderIn.comment("Feet damage reduction :")
					.defineInRange("japaneseLightDefenseFeet", 1, 1, 100);
			DoTBConfig.JAPANESE_LIGHT_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("japaneseLightEnchantability", 14, 1, 100);
			DoTBConfig.JAPANESE_LIGHT_TOUGHNESS = builderIn.comment("This armor's toughness :")
					.defineInRange("japaneseLightToughness", 0.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.push("o_yoroi");
			DoTBConfig.O_YOROI_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("oYoroiDurabilityFactor", 33, 1, 1000);
			DoTBConfig.O_YOROI_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("oYoroiDefenseHelmet", 3, 1, 100);
			DoTBConfig.O_YOROI_DEF_CHEST = builderIn.comment("Chest damage reduction :")
					.defineInRange("oYoroiDefenseChest", 8, 1, 100);
			DoTBConfig.O_YOROI_DEF_LEGS = builderIn.comment("Legs damage reduction :")
					.defineInRange("oYoroiDefenseLegs", 6, 1, 100);
			DoTBConfig.O_YOROI_DEF_FEET = builderIn.comment("Feet damage reduction :")
					.defineInRange("oYoroiDefenseFeet", 3, 1, 100);
			DoTBConfig.O_YOROI_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("oYoroiEnchantability", 10, 1, 100);
			DoTBConfig.O_YOROI_TOUGHNESS = builderIn.comment("This armor's toughness :")
					.defineInRange("oYoroiToughness", 2.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.push("pharaoh");
			DoTBConfig.PHARAOH_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("pharaohDurabilityFactor", 12, 1, 1000);
			DoTBConfig.PHARAOH_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("pharaohDefenseHelmet", 3, 1, 100);
			DoTBConfig.PHARAOH_DEF_CHEST = builderIn.comment("Chest damage reduction :")
					.defineInRange("pharaohDefenseChest", 8, 1, 100);
			DoTBConfig.PHARAOH_DEF_LEGS = builderIn.comment("Legs damage reduction :")
					.defineInRange("pharaohDefenseLegs", 6, 1, 100);
			DoTBConfig.PHARAOH_DEF_FEET = builderIn.comment("Feet damage reduction :")
					.defineInRange("pharaohDefenseFeet", 3, 1, 100);
			DoTBConfig.PHARAOH_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("pharaohEnchantability", 37, 1, 100);
			DoTBConfig.PHARAOH_TOUGHNESS = builderIn.comment("This armor's toughness :")
					.defineInRange("pharaohToughness", 0.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.push("raijin");
			DoTBConfig.RAIJIN_DURABILITY = builderIn.comment(
					"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
					.defineInRange("raijinDurabilityFactor", 45, 1, 1000);
			DoTBConfig.RAIJIN_DEF_HELMET = builderIn.comment("Helmet damage reduction :")
					.defineInRange("raijinDefenseHelmet", 4, 1, 100);
			DoTBConfig.RAIJIN_DEF_CHEST = builderIn.comment("Chest damage reduction :")
					.defineInRange("raijinDefenseChest", 10, 1, 100);
			DoTBConfig.RAIJIN_DEF_LEGS = builderIn.comment("Legs damage reduction :").defineInRange("raijinDefenseLegs",
					8, 1, 100);
			DoTBConfig.RAIJIN_DEF_FEET = builderIn.comment("Feet damage reduction :").defineInRange("raijinDefenseFeet",
					4, 1, 100);
			DoTBConfig.RAIJIN_ENCHANT = builderIn.comment("This armor's enchantability :")
					.defineInRange("raijinEnchantability", 26, 1, 100);
			DoTBConfig.RAIJIN_TOUGHNESS = builderIn.comment("This armor's toughness :").defineInRange("raijinToughness",
					2.0, 0.0, 100.0);
			builderIn.pop();
			builderIn.pop();

			builderIn.comment(
					"---------------------------------------|| Entity settings ||---------------------------------------")
					.push(DoTBConfig.ENTITY_CATEGORY);
			builderIn.push("silkmoth");
			DoTBConfig.SILKMOTH_SPAWN_CHANCE = builderIn.comment(
					"The probability to spawn a Silkmoth on a Mulberry each random tick is equal to 1/x, with x the following value :")
					.defineInRange("silkmothSpawnChance", 400, 10, 10000);
			DoTBConfig.SILKMOTH_ROTATION_MAX_RANGE = builderIn.defineInRange("silkmothRotationMaxRange", 2, 0, 10);
			DoTBConfig.SILKMOTH_MUST_DIE = builderIn.define("silkmothDiesAfterOneDay", true);
			DoTBConfig.SILKMOTH_ROTATION_CHANGE = builderIn.comment(
					"The probability to change the rotation point each tick is equal to 1/x, with x the following value :")
					.defineInRange("silkmothRotationChange", 400, 10, 10000);
			DoTBConfig.SILKMOTH_MUTE = builderIn.define("silkmothMute", false);
			builderIn.pop();
			builderIn.push("japanese_dragon");
			DoTBConfig.JAPANESE_DRAGON_HEALTH = builderIn.defineInRange("japaneseDragonDefaultMaxHealth", 60, 1, 10000);
			DoTBConfig.JAPANESE_DRAGON_ATTACK = builderIn.defineInRange("japaneseDragonDefaultAttack", 4, 1, 100);
			DoTBConfig.JAPANESE_DRAGON_MUTE = builderIn.define("japaneseDragonMute", false);
			builderIn.pop();
			builderIn.pop();

			builderIn.comment(
					"---------------------------------------|| Block settings ||----------------------------------------")
					.push(DoTBConfig.BLOCK_CATEGORY);
			builderIn.push("dryer");
			DoTBConfig.DRYING_TIME_VARIATION = builderIn.comment(
					"The drying time of an item is randomly set in an interval around the default time from the recipe. The following value defines the high bound of the interval in percents. IE, if you choose '20', the interval will be [ 83.3% , 120%]. If you chose '200', the interval will be [33.3% , 300%] :")
					.defineInRange("dryingTimeVariationRange", 30, 0, 100000);
			builderIn.pop();
			builderIn.push("climbing_plant");
			DoTBConfig.CLIMBING_PLANT_GROWTH_CHANCE = builderIn
					.comment("The probability to grow is equal to 1/x, with x the following value :")
					.defineInRange("climbingPlantGrowthChance", 16, 1, 200);
			DoTBConfig.CLIMBING_PLANT_SPREAD_CHANCE = builderIn.comment(
					"If the plant could have grown (see climbingPlantGrowthChance), it has a probability to spread to an adjacent block equal to 1/x, with x the following value :")
					.defineInRange("climbingPlantSpreadChance", 5, 1, 1000);
			builderIn.pop();
			builderIn.push("stick_bundle");
			DoTBConfig.STICK_BUNDLE_GROWTH_CHANCE = builderIn.comment(
					"Worms have a probability to grow on random tick equal to 1/x, with x the following value :")
					.defineInRange("stickBundleGrowthChance", 25, 1, 1000);
			builderIn.pop();
			builderIn.pop();

			builderIn.comment(
					"----------------------------------|| World generation settings ||----------------------------------")
					.push(DoTBConfig.WORLD_GENERATION_CATEGORY);
			builderIn.push("camellia");
			DoTBConfig.CAMELLIA_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.defineList("camelliaBiomes", Arrays.asList("minecraft:flower_forest", "minecraft:jungle",
							"minecraft:jungle_hills", "minecraft:jungle_edge"), obj -> true);
			DoTBConfig.CAMELLIA_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("camelliaRolls", 5, 1, 200);
			builderIn.pop();
			builderIn.push("cypress");
			DoTBConfig.CYPRESS_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("cypressBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:taiga", "minecraft:taiga_hills",
									"minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills",
									"minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills"),
							obj -> true);
			DoTBConfig.CYPRESS_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("cypressRolls", 15, 1, 200);
			builderIn.pop();
			builderIn.push("red_maple");
			DoTBConfig.RED_MAPLE_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("redMapleBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:taiga", "minecraft:taiga_hills",
									"minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills",
									"minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills"),
							obj -> true);
			DoTBConfig.RED_MAPLE_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("redMapleRolls", 5, 1, 200);
			builderIn.pop();
			builderIn.push("boxwood_bush");
			DoTBConfig.BOXWOOD_BUSH_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("boxwoodBushBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:forest", "minecraft:wooded_hills"),
							obj -> true);
			DoTBConfig.BOXWOOD_BUSH_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("boxwoodBushRolls", 15, 1, 200);
			builderIn.pop();
			builderIn.push("mulberry");
			DoTBConfig.MULBERRY_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("mulberryBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:taiga", "minecraft:taiga_hills",
									"minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills",
									"minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills"),
							obj -> true);
			DoTBConfig.MULBERRY_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("mulberryRolls", 64, 1, 200);
			builderIn.pop();
			builderIn.push("rice");
			DoTBConfig.RICE_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("riceBiomes", Arrays.asList("minecraft:swamp", "minecraft:river", "minecraft:swamp_hills"),
							obj -> true);
			DoTBConfig.RICE_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("riceRolls", 64, 1, 200);
			builderIn.pop();
			builderIn.push("wild_grape");
			DoTBConfig.WILD_GRAPE_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("wildGrapeBiomes", Arrays.asList("minecraft:flower_forest", "minecraft:tall_birch_forest",
							"minecraft:tall_birch_hills", "minecraft:birch_forest", "minecraft:birch_forest_hills"),
							obj -> true);
			DoTBConfig.WILD_GRAPE_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("wildGrapeRolls", 64, 1, 200);
			builderIn.pop();
			builderIn.push("wild_maize");
			DoTBConfig.WILD_MAIZE_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("wildMaizeBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:savanna", "minecraft:savanna_plateau",
									"minecraft:shattered_savanna", "minecraft:shattered_savanna_plateau"),
							obj -> true);
			DoTBConfig.WILD_MAIZE_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("wildMaizeRolls", 64, 1, 200);
			builderIn.pop();
			builderIn.push("commelina");
			DoTBConfig.COMMELINA_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("commelinaBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:swamp", "minecraft:swamp_hills"),
							obj -> true);
			DoTBConfig.COMMELINA_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("commelinaRolls", 64, 1, 200);
			builderIn.pop();
			builderIn.push("geranium_pink");
			DoTBConfig.GERANIUM_PINK_GENERATION = builderIn.comment(
					"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
					.define("geraniumPinkBiomes",
							Arrays.asList("minecraft:flower_forest", "minecraft:tall_birch_forest",
									"minecraft:tall_birch_hills", "minecraft:birch_forest",
									"minecraft:birch_forest_hills"),
							obj -> true);
			DoTBConfig.GERANIUM_PINK_ROLLS = builderIn.comment(
					"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
					.defineInRange("geraniumPinkRolls", 5, 1, 200);
			builderIn.pop();
			builderIn.pop();
		}
	}

	final static ForgeConfigSpec COMMON_SPEC;
	public final static Config COMMON_CONFIG;
	static {
		final Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);
		COMMON_SPEC = pair.getRight();
		COMMON_CONFIG = pair.getLeft();
	}
}
