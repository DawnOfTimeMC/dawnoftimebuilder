package org.dawnoftimebuilder.registry;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftimebuilder.DawnOfTimeBuilder;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * @author Seynax
 */
@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoTBBlockAndItemColorsRegistry {
    private final static Map<BlockColor, List<Supplier<Block>>> BLOCKS_COLOR_REGISTRY = new HashMap<>();
    private final static Map<ItemColor, List<Supplier<Item>>> ITEMS_COLOR_REGISTRY = new HashMap<>();
    // Register colors
    public final static BlockColor WATER_BLOCK_COLOR = DoTBBlockAndItemColorsRegistry.register((blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn),
            DoTBBlocksRegistry.STONE_BRICKS_FAUCET, DoTBBlocksRegistry.STONE_BRICKS_POOL, DoTBBlocksRegistry.STONE_BRICKS_SMALL_POOL, DoTBBlocksRegistry.WATER_FLOWING_TRICKLE,
            DoTBBlocksRegistry.WATER_SOURCE_TRICKLE, DoTBBlocksRegistry.STONE_BRICKS_WATER_JET);
    public final static ItemColor WATER_ITEM_COLOR = DoTBBlockAndItemColorsRegistry.register(
            (itemStackIn, i) -> {
                ClientLevel clientLevel = Minecraft.getInstance().level;
                if(clientLevel == null) {
                    return 0;
                }

                Optional<Registry<Biome>> registryOptional = clientLevel.registryAccess().registry(Registries.BIOME);
                if(registryOptional.isEmpty()) {
                    return 0;
                }

                Biome oceanBiome = registryOptional.get().get(Biomes.OCEAN);
                if(oceanBiome == null) {
                    return 0;
                }

                return oceanBiome.getWaterColor();
            }, () -> DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get().asItem(), () -> DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().asItem(), () -> DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get().asItem());

    // Items
    @SafeVarargs
    private static ItemColor register(final ItemColor itemColorIn, final Supplier<Item>... itemsIn) {
        List<Supplier<Item>> items = DoTBBlockAndItemColorsRegistry.getItems(itemColorIn);

        if(items == null) {
            items = new ArrayList<>();
            DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.put(itemColorIn, items);
        }

        Collections.addAll(items, itemsIn);

        return itemColorIn;
    }

    private static List<Supplier<Item>> getItems(final ItemColor blockColorIn) {
        for(final Entry<ItemColor, List<Supplier<Item>>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet()) {
            if(entry.getKey().getClass() == blockColorIn.getClass()) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerItemsColors(final RegisterColorHandlersEvent.Item eventIn) {
        for(final Entry<ItemColor, List<Supplier<Item>>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet()) {
            final Item[] items = new Item[entry.getValue().size()];
            int i = 0;
            for(final Supplier<Item> item : entry.getValue()) {
                items[i] = item.get();
                i++;
            }
            eventIn.getItemColors().register(entry.getKey(), items);
        }
        DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.clear();
    }

    // Blocks
    @SafeVarargs
    private static BlockColor register(final BlockColor blockColorIn, final Supplier<Block>... blocksIn) {
        List<Supplier<Block>> blocks = DoTBBlockAndItemColorsRegistry.getBlocks(blockColorIn);
        if(blocks == null) {
            blocks = new ArrayList<>();
            DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.put(blockColorIn, blocks);
        }
        Collections.addAll(blocks, blocksIn);
        return blockColorIn;
    }

    private static List<Supplier<Block>> getBlocks(final BlockColor blockColorIn) {
        for(final Entry<BlockColor, List<Supplier<Block>>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
            if(entry.getKey().getClass() == blockColorIn.getClass()) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerBlockColors(final RegisterColorHandlersEvent.Block eventIn) {
        for(final Entry<BlockColor, List<Supplier<Block>>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
            final Block[] blocks = new Block[entry.getValue().size()];
            int i = 0;
            for(final Supplier<Block> block : entry.getValue()) {
                blocks[i] = block.get();

                i++;
            }
            eventIn.getBlockColors().register(entry.getKey(), blocks);
        }

        DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.clear();
    }
}
