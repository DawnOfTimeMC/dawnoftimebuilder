package org.dawnoftime.dawnoftime.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * @author Seynax
 */
public class DoTBColorsRegistry {
    private static final Map<BlockColor, List<Supplier<Block>>> BLOCKS_COLOR_REGISTRY = new HashMap<>();
    public static final BlockColor WATER_BLOCK_COLOR = DoTBColorsRegistry.register((blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn),
            DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_FAUCET, DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_POOL, DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_SMALL_POOL, DoTBBlocksRegistry.INSTANCE.WATER_FLOWING_TRICKLE,
            DoTBBlocksRegistry.INSTANCE.WATER_SOURCE_TRICKLE, DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_WATER_JET);
    private static final Map<ItemColor, List<Supplier<Item>>> ITEMS_COLOR_REGISTRY = new HashMap<>();
    public static final ItemColor WATER_ITEM_COLOR = DoTBColorsRegistry.register(
            (itemStackIn, i) -> {
                ClientLevel clientLevel = Minecraft.getInstance().level;
                if (clientLevel == null) {
                    return 0;
                }

                Optional<Registry<Biome>> registryOptional = clientLevel.registryAccess().registry(Registries.BIOME);
                if (registryOptional.isEmpty()) {
                    return 0;
                }

                Biome oceanBiome = registryOptional.get().get(Biomes.OCEAN);
                if (oceanBiome == null) {
                    return 0;
                }

                return oceanBiome.getWaterColor();
            }, () -> DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_FAUCET.get().asItem(), () -> DoTBBlocksRegistry.INSTANCE.WATER_SOURCE_TRICKLE.get().asItem(), () -> DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_WATER_JET.get().asItem());

    public static Map<BlockColor, List<Supplier<Block>>> getBlocksColorRegistry() {
        return BLOCKS_COLOR_REGISTRY;
    }

    public static Map<ItemColor, List<Supplier<Item>>> getItemsColorRegistry() {
        return ITEMS_COLOR_REGISTRY;
    }

    @SafeVarargs
    private static ItemColor register(final ItemColor itemColorIn, final Supplier<Item>... itemsIn) {
        List<Supplier<Item>> items = DoTBColorsRegistry.getItems(itemColorIn);

        if (items == null) {
            items = new ArrayList<>();
            DoTBColorsRegistry.ITEMS_COLOR_REGISTRY.put(itemColorIn, items);
        }

        Collections.addAll(items, itemsIn);

        return itemColorIn;
    }

    private static List<Supplier<Item>> getItems(final ItemColor blockColorIn) {
        for (final Entry<ItemColor, List<Supplier<Item>>> entry : DoTBColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet()) {
            if (entry.getKey().getClass() == blockColorIn.getClass()) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SafeVarargs
    private static BlockColor register(final BlockColor blockColorIn, final Supplier<Block>... blocksIn) {
        List<Supplier<Block>> blocks = DoTBColorsRegistry.getBlocks(blockColorIn);
        if (blocks == null) {
            blocks = new ArrayList<>();
            DoTBColorsRegistry.BLOCKS_COLOR_REGISTRY.put(blockColorIn, blocks);
        }
        Collections.addAll(blocks, blocksIn);
        return blockColorIn;
    }

    private static List<Supplier<Block>> getBlocks(final BlockColor blockColorIn) {
        for (final Entry<BlockColor, List<Supplier<Block>>> entry : DoTBColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
            if (entry.getKey().getClass() == blockColorIn.getClass()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static void initialize() {
    }
}
