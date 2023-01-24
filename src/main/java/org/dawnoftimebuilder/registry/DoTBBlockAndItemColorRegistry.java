/**
 *
 */
package org.dawnoftimebuilder.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author seyro
 *
 */
public class DoTBBlockAndItemColorRegistry {

	private final static Map<IBlockColor, List<Block>>	blocksColorRegistry	= new HashMap<>();
	private final static Map<IItemColor, List<Item>>	itemsColorRegistry	= new HashMap<>();

	public final static IBlockColor						faucetBlockColor	= DoTBBlockAndItemColorRegistry.register((blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn), DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get());

	// Items

	@SuppressWarnings("unused")
	private final static IItemColor register(final IItemColor itemColorIn, final RegistryObject<Item>[] itemssRegistryObjectsIn) {
		final Item[] items = new Item[itemssRegistryObjectsIn.length];

		for (int i = 0; i < itemssRegistryObjectsIn.length; i++) {
			items[i] = itemssRegistryObjectsIn[i].get();
		}

		DoTBBlockAndItemColorRegistry.register(itemColorIn, items);

		return itemColorIn;
	}

	private final static IItemColor register(final IItemColor itemColorIn, final Item... itemsIn) {
		List<Item> items = DoTBBlockAndItemColorRegistry.getItems(itemColorIn);

		if (items == null) {
			items = new ArrayList<>();
			DoTBBlockAndItemColorRegistry.itemsColorRegistry.put(itemColorIn, items);
		}

		Collections.addAll(items, itemsIn);

		return itemColorIn;
	}

	private final static List<Item> getItems(final IItemColor blockColorIn) {
		for (final Entry<IItemColor, List<Item>> entry : DoTBBlockAndItemColorRegistry.itemsColorRegistry.entrySet()) {
			if (entry.getKey().getClass() == blockColorIn.getClass()) {
				return entry.getValue();
			}
		}

		return null;
	}

	@SubscribeEvent
	public final static void registerItemsColors(final ColorHandlerEvent.Item eventIn) {
		for (final Entry<IItemColor, List<Item>> entry : DoTBBlockAndItemColorRegistry.itemsColorRegistry.entrySet()) {
			final Item[]	items	= new Item[entry.getValue().size()];
			int				i		= 0;
			for (final Item item : entry.getValue()) {
				items[i] = item;

				i++;
			}
			eventIn.getItemColors().register(entry.getKey(), items);
		}

		DoTBBlockAndItemColorRegistry.itemsColorRegistry.clear();
	}

	// Blocks

	@SuppressWarnings("unused")
	private final static IBlockColor register(final IBlockColor blockColorIn, final RegistryObject<Block>[] blocksRegistryObjectsIn) {
		final Block[] blocks = new Block[blocksRegistryObjectsIn.length];

		for (int i = 0; i < blocksRegistryObjectsIn.length; i++) {
			blocks[i] = blocksRegistryObjectsIn[i].get();
		}

		DoTBBlockAndItemColorRegistry.register(blockColorIn, blocks);

		return blockColorIn;
	}

	private final static IBlockColor register(final IBlockColor blockColorIn, final Block... blocksIn) {
		List<Block> blocks = DoTBBlockAndItemColorRegistry.getBlocks(blockColorIn);

		if (blocks == null) {
			blocks = new ArrayList<>();
			DoTBBlockAndItemColorRegistry.blocksColorRegistry.put(blockColorIn, blocks);
		}

		Collections.addAll(blocks, blocksIn);

		return blockColorIn;
	}

	private final static List<Block> getBlocks(final IBlockColor blockColorIn) {
		for (final Entry<IBlockColor, List<Block>> entry : DoTBBlockAndItemColorRegistry.blocksColorRegistry.entrySet()) {
			if (entry.getKey().getClass() == blockColorIn.getClass()) {
				return entry.getValue();
			}
		}

		return null;
	}

	@SubscribeEvent
	public final static void registerBlockColors(final ColorHandlerEvent.Block eventIn) {
		for (final Entry<IBlockColor, List<Block>> entry : DoTBBlockAndItemColorRegistry.blocksColorRegistry.entrySet()) {
			final Block[]	blocks	= new Block[entry.getValue().size()];
			int				i		= 0;
			for (final Block block : entry.getValue()) {
				blocks[i] = block;

				i++;
			}
			eventIn.getBlockColors().register(entry.getKey(), blocks);
		}

		DoTBBlockAndItemColorRegistry.blocksColorRegistry.clear();
	}
}
