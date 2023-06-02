package org.dawnoftimebuilder.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Seynax
 */
public class DoTBBlockAndItemColorsRegistry {

	private final static Map<IBlockColor, List<Block>> BLOCKS_COLOR_REGISTRY = new HashMap<>();
	private final static Map<IItemColor, List<Item>> ITEMS_COLOR_REGISTRY = new HashMap<>();

	// Register colors
	public final static IBlockColor WATER_BLOCK_COLOR = register(
			(blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn),
			DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get(),
			DoTBBlocksRegistry.STONE_BRICKS_POOL.get(),
			DoTBBlocksRegistry.STONE_BRICKS_SMALL_POOL.get(),
			DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get(),
			DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get(),
			DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get());

	public final static IItemColor WATER_ITEM_COLOR = DoTBBlockAndItemColorsRegistry.register((p_getColor_1_, p_getColor_2_) -> ForgeRegistries.BIOMES.getValue(Biomes.OCEAN.location()).getWaterColor(),
			DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get().asItem(),
			DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().asItem(),
			DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get().asItem());

	// Items
	private static IItemColor register(final IItemColor itemColorIn, final Item... itemsIn) {
		List<Item> items = DoTBBlockAndItemColorsRegistry.getItems(itemColorIn);

		if (items == null) {
			items = new ArrayList<>();
			DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.put(itemColorIn, items);
		}

		Collections.addAll(items, itemsIn);

		return itemColorIn;
	}

	private static List<Item> getItems(final IItemColor blockColorIn) {
		for (final Entry<IItemColor, List<Item>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet()) {
			if (entry.getKey().getClass() == blockColorIn.getClass()) {
				return entry.getValue();
			}
		}
		return null;
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemsColors(final ColorHandlerEvent.Item eventIn) {
		for (final Entry<IItemColor, List<Item>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet()) {
			final Item[]	items	= new Item[entry.getValue().size()];
			int				i		= 0;
			for (final Item item : entry.getValue()) {
				items[i] = item;
				i++;
			}
			eventIn.getItemColors().register(entry.getKey(), items);
		}
		DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.clear();
	}

	// Blocks
	private static IBlockColor register(final IBlockColor blockColorIn, final Block... blocksIn) {
		List<Block> blocks = DoTBBlockAndItemColorsRegistry.getBlocks(blockColorIn);
		if (blocks == null) {
			blocks = new ArrayList<>();
			DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.put(blockColorIn, blocks);
		}
		Collections.addAll(blocks, blocksIn);
		return blockColorIn;
	}

	private static List<Block> getBlocks(final IBlockColor blockColorIn) {
		for (final Entry<IBlockColor, List<Block>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
			if (entry.getKey().getClass() == blockColorIn.getClass()) {
				return entry.getValue();
			}
		}
		return null;
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(final ColorHandlerEvent.Block eventIn) {
		for (final Entry<IBlockColor, List<Block>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
			final Block[]	blocks	= new Block[entry.getValue().size()];
			int				i		= 0;
			for (final Block block : entry.getValue()) {
				blocks[i] = block;

				i++;
			}
			eventIn.getBlockColors().register(entry.getKey(), blocks);
		}

		DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.clear();
	}
}
