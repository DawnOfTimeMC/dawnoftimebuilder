package org.dawnoftimebuilder.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
	/* TODO Seynax pleeeeease :)
	public final static IItemColor WATER_ITEM_COLOR = register(
			(itemStack, p_210235_2_) -> {
				BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
				return BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn);
			},
			DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get().asItem(),
			DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().asItem(),
			DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get().asItem());
	*/

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
