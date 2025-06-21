package org.dawnoftime.dawnoftime.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.DoTBConfig;
import org.dawnoftime.dawnoftime.loot.DoTBLootModifiersForge;
import org.dawnoftime.dawnoftime.loot.LootTablesToModify;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

public class DoTBLootModifierProvider extends GlobalLootModifierProvider {
    private static final DoTBConfig config = Services.PLATFORM.getConfig();

    public DoTBLootModifierProvider(PackOutput output) {
        super(output, DoTBCommon.MOD_ID);
    }

    @Override
    protected void start() {
        add("silk_in_shipwreck_treasure",
                buildLootTable(LootTablesToModify.SHIPWRECK_TREASURE,
                        DoTBItemsRegistry.INSTANCE.SILK.get(),
                        1.0f));

        add("grape_in_village_plains_house",
                buildLootTable(LootTablesToModify.VILLAGE_PLAINS_HOUSE,
                        DoTBItemsRegistry.INSTANCE.GRAPE.get(),
                        0.5f));

        add("maize_in_village_savanna_house",
                buildLootTable(LootTablesToModify.VILLAGE_SAVANNA_HOUSE,
                        DoTBBlocksRegistry.INSTANCE.MAIZE.get().asItem(),
                        0.5f));

        add("rice_in_village_taiga_house",
                buildLootTable(LootTablesToModify.VILLAGE_TAIGA_HOUSE,
                        DoTBBlocksRegistry.INSTANCE.RICE.get().asItem(),
                        0.5f));

        add("mulberry_in_village_taiga_house",
                buildLootTable(LootTablesToModify.VILLAGE_TAIGA_HOUSE,
                        DoTBBlocksRegistry.INSTANCE.MULBERRY.get().asItem(),
                        0.5f));

        add("grape_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.GRAPE.get(),
                        0.2f));

        add("maize_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBBlocksRegistry.INSTANCE.MAIZE.get().asItem(),
                        0.2f));

        add("rice_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBBlocksRegistry.INSTANCE.RICE.get().asItem(),
                        0.2f));

        add("mulberry_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBBlocksRegistry.INSTANCE.MULBERRY.get().asItem(),
                        0.2f));

        add("black_clay_tile_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLACK.get(),
                        0.1f));

        add("blue_clay_tile_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLUE.get(),
                        0.1f));

        add("cyan_clay_tile_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.CLAY_TILE_CYAN.get(),
                        0.1f));

        add("orange_clay_tile_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.CLAY_TILE_ORANGE.get(),
                        0.1f));

        add("white_clay_tile_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBItemsRegistry.INSTANCE.CLAY_TILE_WHITE.get(),
                        0.1f));

        add("gray_roof_tiles_in_shipwreck_supply",
                buildLootTable(LootTablesToModify.SHIPWRECK_SUPPLY,
                        DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES.get().asItem(),
                        0.1f));
    }

    private DoTBLootModifiersForge buildLootTable(String lootTableName, Item item, float probability) {
        return new DoTBLootModifiersForge(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation(lootTableName)).build(),
                        LootItemRandomChanceCondition.randomChance(probability).build()
                },
                item
        );
    }
}
