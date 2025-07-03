package org.dawnoftime.dawnoftime.loot;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.DoTBConfig;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

import java.util.Map;

public final class LootTablesToModify {
    public static final String SHIPWRECK_TREASURE = "minecraft:chests/shipwreck_treasure";
    public static final String SHIPWRECK_SUPPLY = "minecraft:chests/shipwreck_supply";
    public static final String VILLAGE_PLAINS_HOUSE = "minecraft:chests/village/village_plains_house";
    public static final String VILLAGE_SAVANNA_HOUSE = "minecraft:chests/village/village_savanna_house";
    public static final String VILLAGE_TAIGA_HOUSE = "minecraft:chests/village/village_taiga_house";

    private static final DoTBConfig config = Services.PLATFORM.getConfig();

    public static final Map<Item, Boolean> SHOULD_ADD_MAP = ImmutableMap.ofEntries(
            Map.entry(DoTBItemsRegistry.INSTANCE.SILK.get(),             config.generateSilk),
            Map.entry(DoTBItemsRegistry.INSTANCE.GRAPE.get(),            config.generateGrapes),
            Map.entry(DoTBBlocksRegistry.INSTANCE.MAIZE.get().asItem(), config.generateMaize),
            Map.entry(DoTBBlocksRegistry.INSTANCE.RICE.get().asItem(),  config.generateRice),
            Map.entry(DoTBBlocksRegistry.INSTANCE.MULBERRY.get().asItem(), config.generateMulberry),
            Map.entry(DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLACK.get(),  config.generateBlackClayTile),
            Map.entry(DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLUE.get(),   config.generateBlueClayTile),
            Map.entry(DoTBItemsRegistry.INSTANCE.CLAY_TILE_CYAN.get(),   config.generateCyanClayTile),
            Map.entry(DoTBItemsRegistry.INSTANCE.CLAY_TILE_ORANGE.get(), config.generateOrangeClayTile),
            Map.entry(DoTBItemsRegistry.INSTANCE.CLAY_TILE_WHITE.get(),  config.generateWhiteClayTile),
            Map.entry(DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES.get().asItem(), config.generateGrayClayRoofTile)
    );

    private LootTablesToModify() {}
}
