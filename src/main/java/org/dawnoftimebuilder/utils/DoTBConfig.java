package org.dawnoftimebuilder.utils;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class DoTBConfig {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final String FOOD_CATEGORY = "food_properties";
    public static ForgeConfigSpec.IntValue GRAPE_HUNGER;
    public static ForgeConfigSpec.DoubleValue GRAPE_SATURATION;

    public static final String ARMOR_MATERIAL_CATEGORY = "armor_properties";
    public static ForgeConfigSpec.IntValue IRON_PLATE_DURABILITY;
    public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_FEET;
    public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_LEGS;
    public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_CHEST;
    public static ForgeConfigSpec.IntValue IRON_PLATE_DEF_HELMET;
    public static ForgeConfigSpec.IntValue IRON_PLATE_ENCHANT;
    public static ForgeConfigSpec.DoubleValue IRON_PLATE_TOUGHNESS;
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

    public static final String WORLD_GENERATION_CATEGORY = "world_generation";
    public static ForgeConfigSpec.BooleanValue ACTIVATE_WORLD_GENERATION;

    static{
        COMMON_BUILDER.comment("----------------------------------------|| Food settings ||----------------------------------------").push(FOOD_CATEGORY);
            COMMON_BUILDER.push("grape");
                GRAPE_HUNGER = COMMON_BUILDER.defineInRange("grapeHunger", 4,1,20);
                GRAPE_SATURATION = COMMON_BUILDER.defineInRange("grapeSaturation", 0.2,0.1,3.0);
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

        COMMON_BUILDER.comment("----------------------------------|| World generation settings ||----------------------------------").push(WORLD_GENERATION_CATEGORY);
            ACTIVATE_WORLD_GENERATION = COMMON_BUILDER.comment("Must spawn DoTB's plants during world generation :").define("worldGeneration", true);
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
