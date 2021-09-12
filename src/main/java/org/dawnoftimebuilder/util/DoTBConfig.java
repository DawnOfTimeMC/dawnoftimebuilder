package org.dawnoftimebuilder.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;

public class DoTBConfig {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final String FOOD_CATEGORY = "food_properties";
    public static ForgeConfigSpec.IntValue GRAPE_HUNGER;
    public static ForgeConfigSpec.DoubleValue GRAPE_SATURATION;
    public static ForgeConfigSpec.IntValue MAIZE_HUNGER;
    public static ForgeConfigSpec.DoubleValue MAIZE_SATURATION;
    public static ForgeConfigSpec.IntValue MULBERRY_HUNGER;
    public static ForgeConfigSpec.DoubleValue MULBERRY_SATURATION;

    public static final String ARMOR_MATERIAL_CATEGORY = "armor_properties";
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

    public static final String WEAPON_CATEGORY = "weapon_properties";
    public static ForgeConfigSpec.IntValue TACHI_ATT_DMG;
    public static ForgeConfigSpec.DoubleValue TACHI_ATT_SPD;

    public static final String BLOCK_CATEGORY = "block_properties";
    public static ForgeConfigSpec.IntValue DRYING_TIME_VARIATION;
    public static ForgeConfigSpec.IntValue CLIMBING_PLANT_GROWTH_CHANCE;
    public static ForgeConfigSpec.IntValue CLIMBING_PLANT_SPREAD_CHANCE;
    public static ForgeConfigSpec.IntValue STICK_BUNDLE_GROWTH_CHANCE;

    public static final String WORLD_GENERATION_CATEGORY = "world_generation";
    public static ForgeConfigSpec.BooleanValue CAMELLIA_GENERATION;
    public static ForgeConfigSpec.IntValue CAMELLIA_ROLLS;
    public static ForgeConfigSpec.IntValue CAMELLIA_BOTTOM;
    public static ForgeConfigSpec.IntValue CAMELLIA_TOP;
    public static ForgeConfigSpec.IntValue CAMELLIA_SPAWN_WIDTH;
    public static ForgeConfigSpec.IntValue CAMELLIA_SPAWN_HIGH;
    public static ForgeConfigSpec.BooleanValue CYPRESS_GENERATION;
    public static ForgeConfigSpec.IntValue CYPRESS_ROLLS;
    public static ForgeConfigSpec.IntValue CYPRESS_BOTTOM;
    public static ForgeConfigSpec.IntValue CYPRESS_TOP;
    public static ForgeConfigSpec.IntValue CYPRESS_SPAWN_WIDTH;
    public static ForgeConfigSpec.IntValue CYPRESS_SPAWN_HIGH;
    public static ForgeConfigSpec.BooleanValue MULBERRY_GENERATION;
    public static ForgeConfigSpec.IntValue MULBERRY_ROLLS;
    public static ForgeConfigSpec.IntValue MULBERRY_BOTTOM;
    public static ForgeConfigSpec.IntValue MULBERRY_TOP;
    public static ForgeConfigSpec.IntValue MULBERRY_SPAWN_WIDTH;
    public static ForgeConfigSpec.IntValue MULBERRY_SPAWN_HIGH;
    public static ForgeConfigSpec.BooleanValue RICE_GENERATION;
    public static ForgeConfigSpec.IntValue RICE_ROLLS;
    public static ForgeConfigSpec.IntValue RICE_BOTTOM;
    public static ForgeConfigSpec.IntValue RICE_TOP;
    public static ForgeConfigSpec.IntValue RICE_SPAWN_WIDTH;
    public static ForgeConfigSpec.IntValue RICE_SPAWN_HIGH;

    public static final String ENTITY_CATEGORY = "entity_properties";
    public static ForgeConfigSpec.IntValue SILKMOTH_SPAWN_CHANCE;
    public static ForgeConfigSpec.IntValue SILKMOTH_HEALTH;
    public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_MAX_RANGE;
    public static ForgeConfigSpec.BooleanValue SILKMOTH_MUST_DIE;
    public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_CHANGE;
    public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_HEALTH;
    public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_ATTACK;

    static{
        COMMON_BUILDER.comment("----------------------------------------|| Food settings ||----------------------------------------").push(FOOD_CATEGORY);
            COMMON_BUILDER.push("grape");
                GRAPE_HUNGER = COMMON_BUILDER.defineInRange("grapeHunger", 4,1,20);
                GRAPE_SATURATION = COMMON_BUILDER.defineInRange("grapeSaturation", 0.2,0.1,3.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("maize");
                MAIZE_HUNGER = COMMON_BUILDER.defineInRange("maizeHunger", 6,1,20);
                MAIZE_SATURATION = COMMON_BUILDER.defineInRange("maizeSaturation", 1.0,0.1,3.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("mulberry");
                MULBERRY_HUNGER = COMMON_BUILDER.defineInRange("mulberryHunger", 1,1,20);
                MULBERRY_SATURATION = COMMON_BUILDER.defineInRange("mulberrySaturation", 0.5,0.1,3.0);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("----------------------------------------|| Armor settings ||---------------------------------------").push(ARMOR_MATERIAL_CATEGORY);
            COMMON_BUILDER.push("iron_plate");
                IRON_PLATE_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("ironPlateDurabilityFactor", 15,1,1000);
                IRON_PLATE_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("ironPlateDefenseHelmet", 2,1,100);
                IRON_PLATE_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("ironPlateDefenseChest", 6,1,100);
                IRON_PLATE_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("ironPlateDefenseLegs", 5,1,100);
                IRON_PLATE_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("ironPlateDefenseFeet", 2,1,100);
                IRON_PLATE_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("ironPlateEnchantability", 20,1,100);
                IRON_PLATE_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("ironPlateToughness", 0.0,0.0,100.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("holy");
                HOLY_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("holyDurabilityFactor", 45,1,1000);
                HOLY_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("holyDefenseHelmet", 5,1,100);
                HOLY_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("holyDefenseChest", 12,1,100);
                HOLY_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("holyDefenseLegs", 9,1,100);
                HOLY_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("holyDefenseFeet", 5,1,100);
                HOLY_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("holyEnchantability", 10,1,100);
                HOLY_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("holyToughness", 5.0,0.0,100.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("japanese_light");
                JAPANESE_LIGHT_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("japaneseLightDurabilityFactor", 15,1,1000);
                JAPANESE_LIGHT_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("japaneseLightDefenseHelmet", 2,1,100);
                JAPANESE_LIGHT_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("japaneseLightDefenseChest", 6,1,100);
                JAPANESE_LIGHT_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("japaneseLightDefenseLegs", 5,1,100);
                JAPANESE_LIGHT_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("japaneseLightDefenseFeet", 1,1,100);
                JAPANESE_LIGHT_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("japaneseLightEnchantability", 14,1,100);
                JAPANESE_LIGHT_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("japaneseLightToughness", 0.0,0.0,100.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("o_yoroi");
                O_YOROI_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("oYoroiDurabilityFactor", 33,1,1000);
                O_YOROI_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("oYoroiDefenseHelmet", 3,1,100);
                O_YOROI_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("oYoroiDefenseChest", 8,1,100);
                O_YOROI_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("oYoroiDefenseLegs", 6,1,100);
                O_YOROI_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("oYoroiDefenseFeet", 3,1,100);
                O_YOROI_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("oYoroiEnchantability", 10,1,100);
                O_YOROI_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("oYoroiToughness", 2.0,0.0,100.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("pharaoh");
                PHARAOH_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("pharaohDurabilityFactor", 12,1,1000);
                PHARAOH_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("pharaohDefenseHelmet", 3,1,100);
                PHARAOH_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("pharaohDefenseChest", 8,1,100);
                PHARAOH_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("pharaohDefenseLegs", 6,1,100);
                PHARAOH_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("pharaohDefenseFeet", 3,1,100);
                PHARAOH_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("pharaohEnchantability", 37,1,100);
                PHARAOH_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("pharaohToughness", 0.0,0.0,100.0);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("raijin");
                RAIJIN_DURABILITY = COMMON_BUILDER.comment("DurabilityFactor is multiplied with a value that depends on the armor part (between 11 and 16) to get the total durability :").defineInRange("raijinDurabilityFactor", 45,1,1000);
                RAIJIN_DEF_HELMET = COMMON_BUILDER.comment("Helmet damage reduction :").defineInRange("raijinDefenseHelmet", 4,1,100);
                RAIJIN_DEF_CHEST = COMMON_BUILDER.comment("Chest damage reduction :").defineInRange("raijinDefenseChest", 10,1,100);
                RAIJIN_DEF_LEGS = COMMON_BUILDER.comment("Legs damage reduction :").defineInRange("raijinDefenseLegs", 8,1,100);
                RAIJIN_DEF_FEET = COMMON_BUILDER.comment("Feet damage reduction :").defineInRange("raijinDefenseFeet", 4,1,100);
                RAIJIN_ENCHANT = COMMON_BUILDER.comment("This armor's enchantability :").defineInRange("raijinEnchantability", 26,1,100);
                RAIJIN_TOUGHNESS = COMMON_BUILDER.comment("This armor's toughness :").defineInRange("raijinToughness", 2.0,0.0,100.0);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("---------------------------------------|| Weapon settings ||---------------------------------------").push(WEAPON_CATEGORY);
            COMMON_BUILDER.push("tachi_sword");
                TACHI_ATT_DMG = COMMON_BUILDER.comment("Tachi sword attack damage :").defineInRange("tachiAttackDamage", 3,1,100);
                TACHI_ATT_SPD = COMMON_BUILDER.comment("Tachi sword attack speed :").defineInRange("tachiAttackSpeed", -2.4,-100.0,0.0);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("---------------------------------------|| Entity settings ||---------------------------------------").push(ENTITY_CATEGORY);
            COMMON_BUILDER.push("silkmoth");
                SILKMOTH_SPAWN_CHANCE = COMMON_BUILDER.comment("The probability to spawn a Silkmoth on a Mulberry each random tick is equal to 1/x, with x the following value :").defineInRange("climbingPlantGrowthChance", 400, 10, 10000);
                SILKMOTH_HEALTH = COMMON_BUILDER.defineInRange("silkmoth_max_health", 3, 1, 10000);
                SILKMOTH_ROTATION_MAX_RANGE = COMMON_BUILDER.defineInRange("silkmoth_rotation_max_range", 2, 0, 10);
                SILKMOTH_MUST_DIE = COMMON_BUILDER.define("silkmoth_dies_after_one_day", true);
                SILKMOTH_ROTATION_CHANGE = COMMON_BUILDER.comment("The probability to change the rotation point each tick is equal to 1/x, with x the following value :").defineInRange("silkmoth_rotation_change", 400, 10, 10000);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("japanese_dragon");
                JAPANESE_DRAGON_HEALTH = COMMON_BUILDER.defineInRange("japanese_dragon_default_max_health", 60, 1, 10000);
                JAPANESE_DRAGON_ATTACK = COMMON_BUILDER.defineInRange("japanese_dragon_default_attack", 4, 1, 100);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("---------------------------------------|| Block settings ||----------------------------------------").push(BLOCK_CATEGORY);
            COMMON_BUILDER.push("dryer");
                DRYING_TIME_VARIATION = COMMON_BUILDER.comment("The drying time of an item is randomly set in an interval around the default time from the recipe. The following value defines the high bound of the interval in percents. IE, if you choose '20', the interval will be [ 83.3% , 120%]. If you chose '200', the interval will be [33.3% , 300%] :").defineInRange("dryingTimeVariationRange", 30, 0, 100000);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("climbing_plant");
                CLIMBING_PLANT_GROWTH_CHANCE = COMMON_BUILDER.comment("The probability to grow is equal to 1/x, with x the following value :").defineInRange("climbingPlantGrowthChance", 20, 1, 1000);
                CLIMBING_PLANT_SPREAD_CHANCE = COMMON_BUILDER.comment("If the plant could have grown (see climbingPlantGrowthChance), it has a probability to spread to an adjacent block equal to 1/x, with x the following value :").defineInRange("climbingPlantSpreadChance", 5, 1, 1000);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("stick_bundle");
                STICK_BUNDLE_GROWTH_CHANCE = COMMON_BUILDER.comment("Worms have a probability to grow on random tick equal to 1/x, with x the following value :").defineInRange("stickBundleGrowthChance", 25, 1, 1000);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("----------------------------------|| World generation settings ||----------------------------------").push(WORLD_GENERATION_CATEGORY);
            COMMON_BUILDER.push("camellia");
                CAMELLIA_GENERATION = COMMON_BUILDER.comment("Must spawn this plant during world generation :").define("camelliaGeneration", true);
                CAMELLIA_ROLLS = COMMON_BUILDER.comment("For each spawn zone, a position will be chose x times to place this plant, with x the following value :").defineInRange("camelliaRolls", 60,1,200);
                CAMELLIA_BOTTOM = COMMON_BUILDER.defineInRange("camelliaLowestY", 60,1,HIGHEST_Y);
                CAMELLIA_TOP = COMMON_BUILDER.defineInRange("camelliaHighestY", HIGHEST_Y,1,HIGHEST_Y);
                CAMELLIA_SPAWN_WIDTH = COMMON_BUILDER.comment("Maximal horizontal distance from the center of the spawn zone :").defineInRange("camelliaSpawnWidth", 8,1,20);
                CAMELLIA_SPAWN_HIGH = COMMON_BUILDER.comment("Maximal vertical distance from the center of the spawn zone :").defineInRange("camelliaSpawnHigh", 4,1,20);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("cypress");
                CYPRESS_GENERATION = COMMON_BUILDER.comment("Must spawn this plant during world generation :").define("cypressGeneration", true);
                CYPRESS_ROLLS = COMMON_BUILDER.comment("For each spawn zone, a position will be chose x times to place this plant, with x the following value :").defineInRange("cypressRolls", 80,1,200);
                CYPRESS_BOTTOM = COMMON_BUILDER.defineInRange("cypressLowestY", 60,1,HIGHEST_Y);
                CYPRESS_TOP = COMMON_BUILDER.defineInRange("cypressHighestY", HIGHEST_Y,1,HIGHEST_Y);
                CYPRESS_SPAWN_WIDTH = COMMON_BUILDER.comment("Maximal horizontal distance from the center of the spawn zone :").defineInRange("cypressSpawnWidth", 8,1,20);
                CYPRESS_SPAWN_HIGH = COMMON_BUILDER.comment("Maximal vertical distance from the center of the spawn zone :").defineInRange("cypressSpawnHigh", 4,1,20);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("mulberry");
                MULBERRY_GENERATION = COMMON_BUILDER.comment("Must spawn this plant during world generation :").define("mulberryGeneration", true);
                MULBERRY_ROLLS = COMMON_BUILDER.comment("For each spawn zone, a position will be chose x times to place this plant, with x the following value :").defineInRange("mulberryRolls", 50,1,200);
                MULBERRY_BOTTOM = COMMON_BUILDER.defineInRange("mulberryLowestY", 62,1,HIGHEST_Y);
                MULBERRY_TOP = COMMON_BUILDER.defineInRange("mulberryHighestY", HIGHEST_Y,1,HIGHEST_Y);
                MULBERRY_SPAWN_WIDTH = COMMON_BUILDER.comment("Maximal horizontal distance from the center of the spawn zone :").defineInRange("mulberrySpawnWidth", 8,1,20);
                MULBERRY_SPAWN_HIGH = COMMON_BUILDER.comment("Maximal vertical distance from the center of the spawn zone :").defineInRange("mulberrySpawnHigh", 4,1,20);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("rice");
                RICE_GENERATION = COMMON_BUILDER.comment("Must spawn this plant during world generation :").define("riceGeneration", true);
                RICE_ROLLS = COMMON_BUILDER.comment("For each spawn zone, a position will be chose x times to place this plant, with x the following value :").defineInRange("riceRolls", 80,1,200);
                RICE_BOTTOM = COMMON_BUILDER.defineInRange("riceLowestY", 60,1,HIGHEST_Y);
                RICE_TOP = COMMON_BUILDER.defineInRange("riceHighestY", 65,1,HIGHEST_Y);
                RICE_SPAWN_WIDTH = COMMON_BUILDER.comment("Maximal horizontal distance from the center of the spawn zone :").defineInRange("riceSpawnWidth", 4,1,20);
                RICE_SPAWN_HIGH = COMMON_BUILDER.comment("Maximal vertical distance from the center of the spawn zone :").defineInRange("riceSpawnHigh", 4,1,20);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path){
        final CommentedFileConfig configData = CommentedFileConfig
                .builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
