package org.dawnoftimebuilder.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.dawnoftimebuilder.DawnOfTimeBuilder;

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
@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoTBBlockAndItemColorsRegistry
{
	private final static Map<IBlockColor, List<Supplier<Block>>>	BLOCKS_COLOR_REGISTRY	= new HashMap<>();
	private final static Map<IItemColor, List<Supplier<Item>>>		ITEMS_COLOR_REGISTRY	= new HashMap<>();

	// Register colors
	public final static IBlockColor WATER_BLOCK_COLOR = DoTBBlockAndItemColorsRegistry.register((blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn),
			() -> DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get(), () -> DoTBBlocksRegistry.STONE_BRICKS_POOL.get(), () -> DoTBBlocksRegistry.STONE_BRICKS_SMALL_POOL.get(), () -> DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get(),
			() -> DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get(), () -> DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get());

	public final static IItemColor WATER_ITEM_COLOR = DoTBBlockAndItemColorsRegistry.register((p_getColor_1_, p_getColor_2_) -> ForgeRegistries.BIOMES.getValue(Biomes.OCEAN.location()).getWaterColor(),
			() -> DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get().asItem(), () -> DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get().asItem(), () -> DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get().asItem());

	// Items
	@SafeVarargs
	private static IItemColor register(final IItemColor itemColorIn, final Supplier<Item>... itemsIn)
	{
		List<Supplier<Item>> items = DoTBBlockAndItemColorsRegistry.getItems(itemColorIn);

		if (items == null)
		{
			items = new ArrayList<>();
			DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.put(itemColorIn, items);
		}

		Collections.addAll(items, itemsIn);

		return itemColorIn;
	}

	private static List<Supplier<Item>> getItems(final IItemColor blockColorIn)
	{
		for (final Entry<IItemColor, List<Supplier<Item>>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet())
		{
			if (entry.getKey().getClass() == blockColorIn.getClass())
			{
				return entry.getValue();
			}
		}
		return null;
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemsColors(final ColorHandlerEvent.Item eventIn)
	{
		for (final Entry<IItemColor, List<Supplier<Item>>> entry : DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.entrySet())
		{
			final Item[]	items	= new Item[entry.getValue().size()];
			int				i		= 0;
			for (final Supplier<Item> item : entry.getValue())
			{
				items[i] = item.get();
				i++;
			}
			eventIn.getItemColors().register(entry.getKey(), items);
		}
		DoTBBlockAndItemColorsRegistry.ITEMS_COLOR_REGISTRY.clear();
	}

	// Blocks
	@SafeVarargs
	private static IBlockColor register(final IBlockColor blockColorIn, final Supplier<Block>... blocksIn)
	{
		List<Supplier<Block>> blocks = DoTBBlockAndItemColorsRegistry.getBlocks(blockColorIn);
		if (blocks == null)
		{
			blocks = new ArrayList<>();
			DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.put(blockColorIn, blocks);
		}
		Collections.addAll(blocks, blocksIn);
		return blockColorIn;
	}

	private static List<Supplier<Block>> getBlocks(final IBlockColor blockColorIn)
	{
		for (final Entry<IBlockColor, List<Supplier<Block>>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet())
		{
			if (entry.getKey().getClass() == blockColorIn.getClass())
			{
				return entry.getValue();
			}
		}
		return null;
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(final ColorHandlerEvent.Block eventIn)
	{
		for (final Entry<IBlockColor, List<Supplier<Block>>> entry : DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet())
		{
			final Block[]	blocks	= new Block[entry.getValue().size()];
			int				i		= 0;
			for (final Supplier<Block> block : entry.getValue())
			{
				blocks[i] = block.get();

				i++;
			}
			eventIn.getBlockColors().register(entry.getKey(), blocks);
		}

		DoTBBlockAndItemColorsRegistry.BLOCKS_COLOR_REGISTRY.clear();
	}
}
