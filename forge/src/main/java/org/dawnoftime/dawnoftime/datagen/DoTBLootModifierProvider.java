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
