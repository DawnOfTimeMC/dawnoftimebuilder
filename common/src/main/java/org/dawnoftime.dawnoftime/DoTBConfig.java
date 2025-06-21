package org.dawnoftime.dawnoftime;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;

public class DoTBConfig {
    @AutoGen(category = "entities")
    @SerialEntry(comment = "The probability to spawn a Silkmoth on a Mulberry each random tick is equal to 1/x, where x is the config value.")
    @IntField(min = 10, max = 10000)
    public int silkmothSpawnChance = 400;
    @SerialEntry
    @AutoGen(category = "entities")
    @IntField(min = 0, max = 10)
    public int silkmothRotationMaxRange = 2;
    @SerialEntry
    @AutoGen(category = "entities")
    @Boolean(colored = true, formatter = Boolean.Formatter.YES_NO)
    public boolean silkmothMustDie = true;
    @SerialEntry(comment = "The probability to change the rotation point each tick is equal to 1/x, where x is the config value.")
    @AutoGen(category = "entities")
    @IntField(min = 10, max = 10000)
    public int silkmothRotationChange = 400;
    @SerialEntry
    @AutoGen(category = "entities")
    @Boolean(colored = true, formatter = Boolean.Formatter.YES_NO)
    public boolean silkmothMute = false;

    // Disabled until JD is implemented.
//    @SerialEntry
//    @AutoGen(category = "entities")
//    @IntField(min = 1, max = 10000)
//    public int japaneseDragonHealth = 60;
//    @SerialEntry
//    @AutoGen(category = "entities")
//    @IntField(min = 1, max = 100)
//    public int japaneseDragonAttack = 4;
//    @SerialEntry
//    @AutoGen(category = "entities")
//    @Boolean(colored = true, formatter = Boolean.Formatter.YES_NO)
//    public boolean japaneseDragonMute = false;

    // Block settings
    @SerialEntry(comment = "The drying time of an item is randomly set in an interval around the default time from the recipe. The following value defines the high bound of the interval in percents. IE, if you choose '20', the interval will be [ 83.3% , 120%]. If you chose '200', the interval will be [33.3% , 300%]")
    @AutoGen(category = "blocks")
    @IntField(min = 0, max = 100000)
    public int dryingTimeVariation = 30;
    @SerialEntry(comment = "The probability to grow is equal to 1/x, where x is the config value.")
    @AutoGen(category = "blocks")
    @IntField(min = 1, max = 200)
    public int climbingPlantGrowthChance = 16;
    @SerialEntry(comment = "If the plant could have grown (see climbingPlantGrowthChance), it has a probability to spread to an adjacent block equal to 1/x, where x is the config value.")
    @AutoGen(category = "blocks")
    @IntField(min = 1, max = 1000)
    public int climbingPlantSpreadChance = 5;
    @SerialEntry(comment = "Worms have a probability to grow on random tick equal to 1/x, where x is the config value.")
    @AutoGen(category = "blocks")
    @IntField(min = 1, max = 1000)
    public int stickBundleGrowthChance = 25;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateChestLoot = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateSilk = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateGrapes = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateMaize = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateRice = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateMulberry = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateBlackClayTile = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateBlueClayTile = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateCyanClayTile = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateOrangeClayTile = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateWhiteClayTile = true;

    @SerialEntry
    @AutoGen(category = "loot", group = "chest_loot")
    @Boolean(colored = true, formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean generateGrayClayRoofTile = true;
}
