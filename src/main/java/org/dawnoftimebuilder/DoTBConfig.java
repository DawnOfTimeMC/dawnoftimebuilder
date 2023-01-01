package org.dawnoftimebuilder;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public class DoTBConfig
{

	private static final ForgeConfigSpec.Builder						COMMON_BUILDER				= new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec										COMMON_CONFIG;

	public static final String											FOOD_CATEGORY				= "food_properties";
	public static ForgeConfigSpec.IntValue								GRAPE_HUNGER;
	public static ForgeConfigSpec.DoubleValue							GRAPE_SATURATION;
	public static ForgeConfigSpec.IntValue								MAIZE_HUNGER;
	public static ForgeConfigSpec.DoubleValue							MAIZE_SATURATION;
	public static ForgeConfigSpec.IntValue								MULBERRY_HUNGER;
	public static ForgeConfigSpec.DoubleValue							MULBERRY_SATURATION;

	public static final String											ARMOR_MATERIAL_CATEGORY		= "armor_properties";
	public static ForgeConfigSpec.IntValue								IRON_PLATE_DURABILITY;
	public static ForgeConfigSpec.IntValue								IRON_PLATE_DEF_FEET;
	public static ForgeConfigSpec.IntValue								IRON_PLATE_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								IRON_PLATE_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								IRON_PLATE_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								IRON_PLATE_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							IRON_PLATE_TOUGHNESS;
	public static ForgeConfigSpec.IntValue								HOLY_DURABILITY;
	public static ForgeConfigSpec.IntValue								HOLY_DEF_FEET;
	public static ForgeConfigSpec.IntValue								HOLY_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								HOLY_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								HOLY_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								HOLY_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							HOLY_TOUGHNESS;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_DURABILITY;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_DEF_FEET;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								JAPANESE_LIGHT_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							JAPANESE_LIGHT_TOUGHNESS;
	public static ForgeConfigSpec.IntValue								O_YOROI_DURABILITY;
	public static ForgeConfigSpec.IntValue								O_YOROI_DEF_FEET;
	public static ForgeConfigSpec.IntValue								O_YOROI_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								O_YOROI_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								O_YOROI_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								O_YOROI_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							O_YOROI_TOUGHNESS;
	public static ForgeConfigSpec.IntValue								PHARAOH_DURABILITY;
	public static ForgeConfigSpec.IntValue								PHARAOH_DEF_FEET;
	public static ForgeConfigSpec.IntValue								PHARAOH_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								PHARAOH_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								PHARAOH_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								PHARAOH_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							PHARAOH_TOUGHNESS;
	public static ForgeConfigSpec.IntValue								RAIJIN_DURABILITY;
	public static ForgeConfigSpec.IntValue								RAIJIN_DEF_FEET;
	public static ForgeConfigSpec.IntValue								RAIJIN_DEF_LEGS;
	public static ForgeConfigSpec.IntValue								RAIJIN_DEF_CHEST;
	public static ForgeConfigSpec.IntValue								RAIJIN_DEF_HELMET;
	public static ForgeConfigSpec.IntValue								RAIJIN_ENCHANT;
	public static ForgeConfigSpec.DoubleValue							RAIJIN_TOUGHNESS;

	public static final String											BLOCK_CATEGORY				= "block_properties";
	public static ForgeConfigSpec.IntValue								DRYING_TIME_VARIATION;
	public static ForgeConfigSpec.IntValue								CLIMBING_PLANT_GROWTH_CHANCE;
	public static ForgeConfigSpec.IntValue								CLIMBING_PLANT_SPREAD_CHANCE;
	public static ForgeConfigSpec.IntValue								STICK_BUNDLE_GROWTH_CHANCE;

	public static final String											WORLD_GENERATION_CATEGORY	= "world_generation";
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	CAMELLIA_GENERATION;
	public static ForgeConfigSpec.IntValue								CAMELLIA_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	CYPRESS_GENERATION;
	public static ForgeConfigSpec.IntValue								CYPRESS_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	RED_MAPLE_GENERATION;
	public static ForgeConfigSpec.IntValue								RED_MAPLE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	MULBERRY_GENERATION;
	public static ForgeConfigSpec.IntValue								MULBERRY_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	RICE_GENERATION;
	public static ForgeConfigSpec.IntValue								RICE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	WILD_GRAPE_GENERATION;
	public static ForgeConfigSpec.IntValue								WILD_GRAPE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	WILD_MAIZE_GENERATION;
	public static ForgeConfigSpec.IntValue								WILD_MAIZE_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	COMMELINA_GENERATION;
	public static ForgeConfigSpec.IntValue								COMMELINA_ROLLS;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>>	GERANIUM_PINK_GENERATION;
	public static ForgeConfigSpec.IntValue								GERANIUM_PINK_ROLLS;

	public static final String											ENTITY_CATEGORY				= "entity_properties";
	public static ForgeConfigSpec.IntValue								SILKMOTH_SPAWN_CHANCE;
	public static ForgeConfigSpec.IntValue								SILKMOTH_ROTATION_MAX_RANGE;
	public static ForgeConfigSpec.BooleanValue							SILKMOTH_MUST_DIE;
	public static ForgeConfigSpec.IntValue								SILKMOTH_ROTATION_CHANGE;
	public static ForgeConfigSpec.BooleanValue							SILKMOTH_MUTE;
	public static ForgeConfigSpec.IntValue								JAPANESE_DRAGON_HEALTH;
	public static ForgeConfigSpec.IntValue								JAPANESE_DRAGON_ATTACK;
	public static ForgeConfigSpec.BooleanValue							JAPANESE_DRAGON_MUTE;

	static
	{
		DoTBConfig.COMMON_BUILDER.comment(
				"----------------------------------------|| Food settings ||----------------------------------------")
				.push(DoTBConfig.FOOD_CATEGORY);
		DoTBConfig.COMMON_BUILDER.push("grape");
		DoTBConfig.GRAPE_HUNGER		= DoTBConfig.COMMON_BUILDER.defineInRange("grapeHunger", 4, 1, 20);
		DoTBConfig.GRAPE_SATURATION	= DoTBConfig.COMMON_BUILDER.defineInRange("grapeSaturation", 0.2, 0.1, 3.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("maize");
		DoTBConfig.MAIZE_HUNGER		= DoTBConfig.COMMON_BUILDER.defineInRange("maizeHunger", 6, 1, 20);
		DoTBConfig.MAIZE_SATURATION	= DoTBConfig.COMMON_BUILDER.defineInRange("maizeSaturation", 1.0, 0.1, 3.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("mulberry");
		DoTBConfig.MULBERRY_HUNGER		= DoTBConfig.COMMON_BUILDER.defineInRange("mulberryHunger", 1, 1, 20);
		DoTBConfig.MULBERRY_SATURATION	= DoTBConfig.COMMON_BUILDER.defineInRange("mulberrySaturation", 0.5, 0.1, 3.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.pop();

		DoTBConfig.COMMON_BUILDER.comment(
				"----------------------------------------|| Armor settings ||---------------------------------------")
				.push(DoTBConfig.ARMOR_MATERIAL_CATEGORY);
		DoTBConfig.COMMON_BUILDER.push("iron_plate");
		DoTBConfig.IRON_PLATE_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("ironPlateDurabilityFactor", 15, 1, 1000);
		DoTBConfig.IRON_PLATE_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("ironPlateDefenseHelmet", 2, 1, 100);
		DoTBConfig.IRON_PLATE_DEF_CHEST		= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("ironPlateDefenseChest", 6, 1, 100);
		DoTBConfig.IRON_PLATE_DEF_LEGS		= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("ironPlateDefenseLegs", 5, 1, 100);
		DoTBConfig.IRON_PLATE_DEF_FEET		= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("ironPlateDefenseFeet", 2, 1, 100);
		DoTBConfig.IRON_PLATE_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("ironPlateEnchantability", 20, 1, 100);
		DoTBConfig.IRON_PLATE_TOUGHNESS		= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("ironPlateToughness", 0.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("holy");
		DoTBConfig.HOLY_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("holyDurabilityFactor", 45, 1, 1000);
		DoTBConfig.HOLY_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("holyDefenseHelmet", 5, 1, 100);
		DoTBConfig.HOLY_DEF_CHEST	= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("holyDefenseChest", 10, 1, 100);
		DoTBConfig.HOLY_DEF_LEGS	= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("holyDefenseLegs", 8, 1, 100);
		DoTBConfig.HOLY_DEF_FEET	= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("holyDefenseFeet", 5, 1, 100);
		DoTBConfig.HOLY_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("holyEnchantability", 8, 1, 100);
		DoTBConfig.HOLY_TOUGHNESS	= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("holyToughness", 4.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("japanese_light");
		DoTBConfig.JAPANESE_LIGHT_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("japaneseLightDurabilityFactor", 15, 1, 1000);
		DoTBConfig.JAPANESE_LIGHT_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("japaneseLightDefenseHelmet", 2, 1, 100);
		DoTBConfig.JAPANESE_LIGHT_DEF_CHEST		= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("japaneseLightDefenseChest", 6, 1, 100);
		DoTBConfig.JAPANESE_LIGHT_DEF_LEGS		= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("japaneseLightDefenseLegs", 5, 1, 100);
		DoTBConfig.JAPANESE_LIGHT_DEF_FEET		= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("japaneseLightDefenseFeet", 1, 1, 100);
		DoTBConfig.JAPANESE_LIGHT_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("japaneseLightEnchantability", 14, 1, 100);
		DoTBConfig.JAPANESE_LIGHT_TOUGHNESS		= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("japaneseLightToughness", 0.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("o_yoroi");
		DoTBConfig.O_YOROI_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("oYoroiDurabilityFactor", 33, 1, 1000);
		DoTBConfig.O_YOROI_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("oYoroiDefenseHelmet", 3, 1, 100);
		DoTBConfig.O_YOROI_DEF_CHEST	= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("oYoroiDefenseChest", 8, 1, 100);
		DoTBConfig.O_YOROI_DEF_LEGS		= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("oYoroiDefenseLegs", 6, 1, 100);
		DoTBConfig.O_YOROI_DEF_FEET		= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("oYoroiDefenseFeet", 3, 1, 100);
		DoTBConfig.O_YOROI_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("oYoroiEnchantability", 10, 1, 100);
		DoTBConfig.O_YOROI_TOUGHNESS	= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("oYoroiToughness", 2.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("pharaoh");
		DoTBConfig.PHARAOH_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("pharaohDurabilityFactor", 12, 1, 1000);
		DoTBConfig.PHARAOH_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("pharaohDefenseHelmet", 3, 1, 100);
		DoTBConfig.PHARAOH_DEF_CHEST	= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("pharaohDefenseChest", 8, 1, 100);
		DoTBConfig.PHARAOH_DEF_LEGS		= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("pharaohDefenseLegs", 6, 1, 100);
		DoTBConfig.PHARAOH_DEF_FEET		= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("pharaohDefenseFeet", 3, 1, 100);
		DoTBConfig.PHARAOH_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("pharaohEnchantability", 37, 1, 100);
		DoTBConfig.PHARAOH_TOUGHNESS	= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("pharaohToughness", 0.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("raijin");
		DoTBConfig.RAIJIN_DURABILITY	= DoTBConfig.COMMON_BUILDER.comment(
				"DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :")
				.defineInRange("raijinDurabilityFactor", 45, 1, 1000);
		DoTBConfig.RAIJIN_DEF_HELMET	= DoTBConfig.COMMON_BUILDER.comment("Helmet damage reduction :")
				.defineInRange("raijinDefenseHelmet", 4, 1, 100);
		DoTBConfig.RAIJIN_DEF_CHEST		= DoTBConfig.COMMON_BUILDER.comment("Chest damage reduction :")
				.defineInRange("raijinDefenseChest", 10, 1, 100);
		DoTBConfig.RAIJIN_DEF_LEGS		= DoTBConfig.COMMON_BUILDER.comment("Legs damage reduction :")
				.defineInRange("raijinDefenseLegs", 8, 1, 100);
		DoTBConfig.RAIJIN_DEF_FEET		= DoTBConfig.COMMON_BUILDER.comment("Feet damage reduction :")
				.defineInRange("raijinDefenseFeet", 4, 1, 100);
		DoTBConfig.RAIJIN_ENCHANT		= DoTBConfig.COMMON_BUILDER.comment("This armor's enchantability :")
				.defineInRange("raijinEnchantability", 26, 1, 100);
		DoTBConfig.RAIJIN_TOUGHNESS		= DoTBConfig.COMMON_BUILDER.comment("This armor's toughness :")
				.defineInRange("raijinToughness", 2.0, 0.0, 100.0);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.pop();

		DoTBConfig.COMMON_BUILDER.comment(
				"---------------------------------------|| Entity settings ||---------------------------------------")
				.push(DoTBConfig.ENTITY_CATEGORY);
		DoTBConfig.COMMON_BUILDER.push("silkmoth");
		DoTBConfig.SILKMOTH_SPAWN_CHANCE		= DoTBConfig.COMMON_BUILDER.comment(
				"The probability to spawn a Silkmoth on a Mulberry each random tick is equal to 1/x, with x the following value :")
				.defineInRange("silkmothSpawnChance", 400, 10, 10000);
		DoTBConfig.SILKMOTH_ROTATION_MAX_RANGE	= DoTBConfig.COMMON_BUILDER.defineInRange("silkmothRotationMaxRange", 2,
				0, 10);
		DoTBConfig.SILKMOTH_MUST_DIE			= DoTBConfig.COMMON_BUILDER.define("silkmothDiesAfterOneDay", true);
		DoTBConfig.SILKMOTH_ROTATION_CHANGE		= DoTBConfig.COMMON_BUILDER.comment(
				"The probability to change the rotation point each tick is equal to 1/x, with x the following value :")
				.defineInRange("silkmothRotationChange", 400, 10, 10000);
		DoTBConfig.SILKMOTH_MUTE				= DoTBConfig.COMMON_BUILDER.define("silkmothMute", false);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("japanese_dragon");
		DoTBConfig.JAPANESE_DRAGON_HEALTH	= DoTBConfig.COMMON_BUILDER.defineInRange("japaneseDragonDefaultMaxHealth",
				60, 1, 10000);
		DoTBConfig.JAPANESE_DRAGON_ATTACK	= DoTBConfig.COMMON_BUILDER.defineInRange("japaneseDragonDefaultAttack", 4,
				1, 100);
		DoTBConfig.JAPANESE_DRAGON_MUTE		= DoTBConfig.COMMON_BUILDER.define("japaneseDragonMute", false);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.pop();

		DoTBConfig.COMMON_BUILDER.comment(
				"---------------------------------------|| Block settings ||----------------------------------------")
				.push(DoTBConfig.BLOCK_CATEGORY);
		DoTBConfig.COMMON_BUILDER.push("dryer");
		DoTBConfig.DRYING_TIME_VARIATION = DoTBConfig.COMMON_BUILDER.comment(
				"The drying time of an item is randomly set in an interval around the default time from the recipe. The following value defines the high bound of the interval in percents. IE, if you choose '20', the interval will be [ 83.3% , 120%]. If you chose '200', the interval will be [33.3% , 300%] :")
				.defineInRange("dryingTimeVariationRange", 30, 0, 100000);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("climbing_plant");
		DoTBConfig.CLIMBING_PLANT_GROWTH_CHANCE	= DoTBConfig.COMMON_BUILDER
				.comment("The probability to grow is equal to 1/x, with x the following value :")
				.defineInRange("climbingPlantGrowthChance", 16, 1, 200);
		DoTBConfig.CLIMBING_PLANT_SPREAD_CHANCE	= DoTBConfig.COMMON_BUILDER.comment(
				"If the plant could have grown (see climbingPlantGrowthChance), it has a probability to spread to an adjacent block equal to 1/x, with x the following value :")
				.defineInRange("climbingPlantSpreadChance", 5, 1, 1000);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("stick_bundle");
		DoTBConfig.STICK_BUNDLE_GROWTH_CHANCE = DoTBConfig.COMMON_BUILDER
				.comment("Worms have a probability to grow on random tick equal to 1/x, with x the following value :")
				.defineInRange("stickBundleGrowthChance", 25, 1, 1000);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.pop();

		DoTBConfig.COMMON_BUILDER.comment(
				"----------------------------------|| World generation settings ||----------------------------------")
				.push(DoTBConfig.WORLD_GENERATION_CATEGORY);
		DoTBConfig.COMMON_BUILDER.push("camellia");
		DoTBConfig.CAMELLIA_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.defineList("camelliaBiomes", Arrays.asList("minecraft:flower_forest", "minecraft:jungle",
						"minecraft:jungle_hills", "minecraft:jungle_edge"), obj -> true);
		DoTBConfig.CAMELLIA_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("camelliaRolls", 5, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("cypress");
		DoTBConfig.CYPRESS_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("cypressBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:taiga", "minecraft:taiga_hills",
								"minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills",
								"minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills"),
						obj -> true);
		DoTBConfig.CYPRESS_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("cypressRolls", 15, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("red_maple");
		DoTBConfig.RED_MAPLE_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("redMapleBiomes",
						Arrays.asList("minecraft:taiga", "minecraft:taiga_hills", "minecraft:giant_tree_taiga",
								"minecraft:giant_tree_taiga_hills", "minecraft:giant_spruce_taiga",
								"minecraft:giant_spruce_taiga_hills"),
						obj -> true);
		DoTBConfig.RED_MAPLE_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("redMapleRolls", 15, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("mulberry");
		DoTBConfig.MULBERRY_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("mulberryBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:taiga", "minecraft:taiga_hills",
								"minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills",
								"minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills"),
						obj -> true);
		DoTBConfig.MULBERRY_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("mulberryRolls", 64, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("rice");
		DoTBConfig.RICE_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("riceBiomes", Arrays.asList("minecraft:swamp", "minecraft:river", "minecraft:swamp_hills"),
						obj -> true);
		DoTBConfig.RICE_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("riceRolls", 64, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("wild_grape");
		DoTBConfig.WILD_GRAPE_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("wildGrapeBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:tall_birch_forest",
								"minecraft:tall_birch_hills", "minecraft:birch_forest", "minecraft:birch_forest_hills"),
						obj -> true);
		DoTBConfig.WILD_GRAPE_ROLLS			= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("wildGrapeRolls", 64, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("wild_maize");
		DoTBConfig.WILD_MAIZE_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("wildMaizeBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:savanna", "minecraft:savanna_plateau",
								"minecraft:shattered_savanna", "minecraft:shattered_savanna_plateau"),
						obj -> true);
		DoTBConfig.WILD_MAIZE_ROLLS			= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("wildMaizeRolls", 64, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("commelina");
		DoTBConfig.COMMELINA_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("commelinaBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:swamp", "minecraft:swamp_hills"),
						obj -> true);
		DoTBConfig.COMMELINA_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("commelinaRolls", 64, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.push("geranium_pink");
		DoTBConfig.GERANIUM_PINK_GENERATION	= DoTBConfig.COMMON_BUILDER.comment(
				"List of biomes where this plant should spawn during world generation (must be empty if the plant must not spawn :")
				.define("geraniumPinkBiomes",
						Arrays.asList("minecraft:flower_forest", "minecraft:tall_birch_forest",
								"minecraft:tall_birch_hills", "minecraft:birch_forest", "minecraft:birch_forest_hills"),
						obj -> true);
		DoTBConfig.GERANIUM_PINK_ROLLS		= DoTBConfig.COMMON_BUILDER.comment(
				"For each spawn zone, a position will be chose x times to place this plant, with x the following value :")
				.defineInRange("geraniumPinkRolls", 5, 1, 200);
		DoTBConfig.COMMON_BUILDER.pop();
		DoTBConfig.COMMON_BUILDER.pop();

		DoTBConfig.COMMON_CONFIG = DoTBConfig.COMMON_BUILDER.build();
	}
}
