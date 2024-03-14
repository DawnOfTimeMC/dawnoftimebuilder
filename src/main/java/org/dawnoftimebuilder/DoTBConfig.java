package org.dawnoftimebuilder;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public class DoTBConfig {
    //public final static String ARMOR_MATERIAL_CATEGORY = "armor_properties";
    public final static String BLOCK_CATEGORY = "block_properties";
    public final static String WORLD_GENERATION_CATEGORY = "world_generation";
    public final static String ENTITY_CATEGORY = "entity_properties";
    public final static Config COMMON_CONFIG;
    final static ForgeConfigSpec COMMON_SPEC;
    // General
    public static ForgeConfigSpec.IntValue DRYING_TIME_VARIATION;
    public static ForgeConfigSpec.IntValue CLIMBING_PLANT_GROWTH_CHANCE;
    public static ForgeConfigSpec.IntValue CLIMBING_PLANT_SPREAD_CHANCE;
    public static ForgeConfigSpec.IntValue STICK_BUNDLE_GROWTH_CHANCE;
    public static ForgeConfigSpec.IntValue SILKMOTH_SPAWN_CHANCE;
    public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_MAX_RANGE;
    public static ForgeConfigSpec.BooleanValue SILKMOTH_MUST_DIE;
    public static ForgeConfigSpec.IntValue SILKMOTH_ROTATION_CHANGE;
    public static ForgeConfigSpec.BooleanValue SILKMOTH_MUTE;
    public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_HEALTH;
    public static ForgeConfigSpec.IntValue JAPANESE_DRAGON_ATTACK;
    public static ForgeConfigSpec.BooleanValue JAPANESE_DRAGON_MUTE;

    static {
        final Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);
        COMMON_SPEC = pair.getRight();
        COMMON_CONFIG = pair.getLeft();
    }

    public final static class Config {
        Config(final ForgeConfigSpec.Builder builderIn) {

            builderIn.comment("---------------------------------------|| Entity settings ||---------------------------------------").push(ENTITY_CATEGORY);
            builderIn.push("silkmoth");
            SILKMOTH_SPAWN_CHANCE = builderIn.comment("The probability to spawn a Silkmoth on a Mulberry each random tick is equal to 1/x, with x the following value :").defineInRange("silkmothSpawnChance", 400, 10, 10000);
            SILKMOTH_ROTATION_MAX_RANGE = builderIn.defineInRange("silkmothRotationMaxRange", 2, 0, 10);
            SILKMOTH_MUST_DIE = builderIn.define("silkmothDiesAfterOneDay", true);
            SILKMOTH_ROTATION_CHANGE = builderIn.comment("The probability to change the rotation point each tick is equal to 1/x, with x the following value :").defineInRange("silkmothRotationChange", 400, 10, 10000);
            SILKMOTH_MUTE = builderIn.define("silkmothMute", false);
            builderIn.pop();
            builderIn.push("japanese_dragon");
            JAPANESE_DRAGON_HEALTH = builderIn.defineInRange("japaneseDragonDefaultMaxHealth", 60, 1, 10000);
            JAPANESE_DRAGON_ATTACK = builderIn.defineInRange("japaneseDragonDefaultAttack", 4, 1, 100);
            JAPANESE_DRAGON_MUTE = builderIn.define("japaneseDragonMute", false);
            builderIn.pop();
            builderIn.pop();

            builderIn.comment("---------------------------------------|| Block settings ||----------------------------------------").push(BLOCK_CATEGORY);
            builderIn.push("dryer");
            DRYING_TIME_VARIATION = builderIn.comment("The drying time of an item is randomly set in an interval around the default time from the recipe. The following value defines the high bound of the interval in percents. IE, if you choose '20', the interval will be [ 83.3% , 120%]. If you chose '200', the interval will be [33.3% , 300%] :").defineInRange("dryingTimeVariationRange", 30, 0, 100000);
            builderIn.pop();
            builderIn.push("climbing_plant");
            CLIMBING_PLANT_GROWTH_CHANCE = builderIn.comment("The probability to grow is equal to 1/x, with x the following value :").defineInRange("climbingPlantGrowthChance", 16, 1, 200);
            CLIMBING_PLANT_SPREAD_CHANCE = builderIn.comment("If the plant could have grown (see climbingPlantGrowthChance), it has a probability to spread to an adjacent block equal to 1/x, with x the following value :").defineInRange("climbingPlantSpreadChance", 5, 1, 1000);
            builderIn.pop();
            builderIn.push("stick_bundle");
            STICK_BUNDLE_GROWTH_CHANCE = builderIn.comment("Worms have a probability to grow on random tick equal to 1/x, with x the following value :").defineInRange("stickBundleGrowthChance", 25, 1, 1000);
            builderIn.pop();
            builderIn.pop();
        }
    }
}
