package org.dawnoftime.dawnoftime.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.dawnoftime.dawnoftime.DoTBConfig;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

public final class DoTBFabricLootModifier {
    private static final DoTBConfig config = Services.PLATFORM.getConfig();

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            switch (id.toString()) {
                case LootTablesToModify.SHIPWRECK_TREASURE -> buildLootTable(DoTBItemsRegistry.INSTANCE.SILK.get(), 1.0f, tableBuilder);
                case LootTablesToModify.VILLAGE_PLAINS_HOUSE -> buildLootTable(DoTBItemsRegistry.INSTANCE.GRAPE.get(), 0.5f, tableBuilder);
                case LootTablesToModify.VILLAGE_SAVANNA_HOUSE -> buildLootTable(DoTBBlocksRegistry.INSTANCE.MAIZE.get().asItem(), 0.5f, tableBuilder);
                case LootTablesToModify.VILLAGE_TAIGA_HOUSE -> {
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.RICE.get().asItem(), 0.5f, tableBuilder);
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.MULBERRY.get().asItem(), 0.5f, tableBuilder);
                }
                case LootTablesToModify.SHIPWRECK_SUPPLY -> {
                    buildLootTable(DoTBItemsRegistry.INSTANCE.GRAPE.get(),0.2f, tableBuilder);
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.MAIZE.get().asItem(), 0.2f, tableBuilder);
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.RICE.get().asItem(), 0.2f, tableBuilder);
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.MULBERRY.get().asItem(), 0.2f, tableBuilder);

                    buildLootTable(DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLACK.get(), 0.1f, tableBuilder);
                    buildLootTable(DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLUE.get(), 0.1f, tableBuilder);
                    buildLootTable(DoTBItemsRegistry.INSTANCE.CLAY_TILE_CYAN.get(), 0.1f, tableBuilder);
                    buildLootTable(DoTBItemsRegistry.INSTANCE.CLAY_TILE_ORANGE.get(), 0.1f, tableBuilder);
                    buildLootTable(DoTBItemsRegistry.INSTANCE.CLAY_TILE_WHITE.get(), 0.1f, tableBuilder);
                    buildLootTable(DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES.get().asItem(), 0.1f, tableBuilder);
                }
            }
        });
    }

    private static void buildLootTable(Item item, float probability, LootTable.Builder tableBuilder) {
        boolean shouldAdd = LootTablesToModify.SHOULD_ADD_MAP.getOrDefault(item, false);

        if (config.generateChestLoot && shouldAdd) {
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .conditionally(LootItemRandomChanceCondition.randomChance(probability).build());

            poolBuilder.with(LootItem.lootTableItem(item)
                            .setWeight(1)
                            .build())
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)));

            tableBuilder.pool(poolBuilder.build());
        }
    }
}
