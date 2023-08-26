package org.dawnoftimebuilder;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.dawnoftimebuilder.client.gui.filters.CreativeInventoryFilters;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;
import org.dawnoftimebuilder.registry.DoTBBlockAndItemColorsRegistry;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBContainersRegistry;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;
import org.dawnoftimebuilder.registry.DoTBItemsRegistry;
import org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry;
import org.dawnoftimebuilder.util.DoTBFilterEntry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient
{

	private static final Map<ItemGroup, Set<DoTBFilterEntry>> filterMap = new HashMap<>();

	@SubscribeEvent
	public static void fMLClientSetupEvent(final FMLClientSetupEvent event)
	{
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerBlockColors);
		eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerItemsColors);

		ScreenManager.register(DoTBContainersRegistry.DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DoTBTileEntitiesRegistry.DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DoTBTileEntitiesRegistry.DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

		// Above Minecraft 1.15, we need to register renderLayer here
		// General
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.ACACIA_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.ACACIA_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.ACACIA_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIRCH_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIRCH_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIRCH_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CRIMSON_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CRIMSON_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CRIMSON_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.DARK_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.DARK_OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.DARK_OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.JUNGLE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.JUNGLE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.JUNGLE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WARPED_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WARPED_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WARPED_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.IRON_PORTCULLIS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WATER_SOURCE_TRICKLE.get(), RenderType.translucent());

		// French
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LIMESTONE_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BLACK_WROUGHT_IRON_BALUSTER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.REINFORCED_BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.REINFORCED_GOLDEN_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_SMALL_HEDGE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_TALL_HEDGE.get(), RenderType.cutoutMipped());

		// German
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LATTICE_GLASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LATTICE_GLASS_PANE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LATTICE_WAXED_OAK_WINDOW.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LATTICE_STONE_BRICKS_WINDOW.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_ARROWSLIT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_POOL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_SMALL_POOL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_DOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_TRAPDOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_CHANDELIER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_FAUCET.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_BRICKS_WATER_JET.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.IVY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.GERANIUM_PINK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PLANTER_GERANIUM_PINK.get(), RenderType.cutoutMipped());
		// Japanese
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_DOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_TRAPDOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CHARRED_SPRUCE_FANCY_RAILING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.RED_PAINTED_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CAST_IRON_TEAPOT_GRAY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CAST_IRON_TEAPOT_GREEN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CAST_IRON_TEAPOT_DECORATED.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BAMBOO_DRYING_TRAY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CAMELLIA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MULBERRY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.IKEBANA_FLOWER_POT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WHITE_LITTLE_FLAG.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.RICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.IRORI_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SAKE_BOTTLE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STICK_BUNDLE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.STONE_LANTERN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MAPLE_RED_TRUNK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MAPLE_RED_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PAUSED_MAPLE_RED_SAPLING.get(), RenderType.cutoutMipped());

		// Persian
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MORAQ_MOSAIC_RECESS.get(), RenderType.cutoutMipped());

		// Pre_columbian
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.COMMELINA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WILD_MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PLASTERED_STONE_CRESSET.get(), RenderType.cutoutMipped());

		// Roman
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIRCH_FANCY_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MARBLE_FANCY_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIG_FLOWER_POT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WILD_GRAPE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CYPRESS.get(), RenderType.cutoutMipped());

		// TODO For Choco
		// TODO Temporary : added for Choco's testings
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_CHAIR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SERPENT_SCULPTED_COLUMN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.RED_PAPER_LANTERN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PAPER_LAMP.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SMALL_TATAMI_MAT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.TATAMI_MAT.get(), RenderType.cutoutMipped());

		// Flower pots
		for (final Block pot : DoTBBlocksRegistry.POT_BLOCKS.values())
		{
			RenderTypeLookup.setRenderLayer(pot, RenderType.cutoutMipped());
		}

		MinecraftForge.EVENT_BUS.register(DawnOfTimeBuilder.get().events = new CreativeInventoryFilters());

		HandlerClient.registerTabs();
	}

	public static void registerTabs()
	{
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/natural"), new ItemStack(Blocks.GRASS_BLOCK));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/stones"), new ItemStack(Blocks.STONE));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/woods"), new ItemStack(Blocks.OAK_LOG));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/minerals"), new ItemStack(Blocks.EMERALD_BLOCK));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("stairs"), new ItemStack(Blocks.OAK_STAIRS));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("slabs"), new ItemStack(Blocks.OAK_SLAB));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("forge", "glass"), new ItemStack(Blocks.GLASS));
		HandlerClient.register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/colored"), new ItemStack(Blocks.RED_WOOL));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/vegetation"), new ItemStack(Blocks.GRASS));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/functional"), new ItemStack(Blocks.CRAFTING_TABLE));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/fences_and_walls"), new ItemStack(Blocks.OAK_FENCE));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/interior"), new ItemStack(Blocks.RED_BED));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/glass"), new ItemStack(Blocks.GLASS_PANE));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/colored"), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/special"), new ItemStack(Blocks.DRAGON_HEAD));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/infested"), new ItemStack(Blocks.INFESTED_CRACKED_STONE_BRICKS));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/lights"), new ItemStack(Blocks.TORCH));
		HandlerClient.register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/bees"), new ItemStack(Blocks.BEE_NEST));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/core"), new ItemStack(Items.REDSTONE));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/components"), new ItemStack(Items.STICKY_PISTON));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/inputs"), new ItemStack(Items.TRIPWIRE_HOOK));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/doors"), new ItemStack(Items.OAK_DOOR));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/trapdoors"), new ItemStack(Items.OAK_TRAPDOOR));
		HandlerClient.register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/fence_gates"), new ItemStack(Items.OAK_FENCE_GATE));
		HandlerClient.register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/minecarts"), new ItemStack(Items.MINECART));
		HandlerClient.register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/boats"), new ItemStack(Items.OAK_BOAT));
		HandlerClient.register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/rails"), new ItemStack(Items.RAIL));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/materials"), new ItemStack(Items.GOLD_INGOT));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/eggs"), new ItemStack(Items.TURTLE_EGG));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/plants_and_seeds"), new ItemStack(Items.SUGAR_CANE));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/dyes"), new ItemStack(Items.RED_DYE));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/discs"), new ItemStack(Items.MUSIC_DISC_MALL));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/buckets"), new ItemStack(Items.BUCKET));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/horse_armor"), new ItemStack(Items.DIAMOND_HORSE_ARMOR));
		HandlerClient.register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/banner_patterns"), new ItemStack(Items.CREEPER_BANNER_PATTERN));
		HandlerClient.register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/raw"), new ItemStack(Items.BEEF));
		HandlerClient.register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/cooked"), new ItemStack(Items.COOKED_PORKCHOP));
		HandlerClient.register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/special"), new ItemStack(Items.GOLDEN_APPLE));
		HandlerClient.register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/armor"), new ItemStack(Items.IRON_CHESTPLATE));
		HandlerClient.register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/weapons"), new ItemStack(Items.IRON_SWORD));
		HandlerClient.register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/arrows"), new ItemStack(Items.ARROW));
		HandlerClient.register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
		HandlerClient.register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/tools"), new ItemStack(Items.IRON_SHOVEL));
		HandlerClient.register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/equipment"), new ItemStack(Items.COMPASS));
		HandlerClient.register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
		HandlerClient.register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/potions"), new ItemStack(Items.DRAGON_BREATH));
		HandlerClient.register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/ingredients"), new ItemStack(Items.BLAZE_POWDER));
		HandlerClient.register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/equipment"), new ItemStack(Items.BREWING_STAND));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/general"), new ItemStack(DoTBItemsRegistry.GENERAL.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/egyptian"), new ItemStack(DoTBItemsRegistry.EGYPTIAN.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/french"), new ItemStack(DoTBItemsRegistry.FRENCH.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/german"), new ItemStack(DoTBItemsRegistry.GERMAN.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/persian"), new ItemStack(DoTBItemsRegistry.PERSIAN.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/japanese"), new ItemStack(DoTBItemsRegistry.JAPANESE.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/pre_columbian"), new ItemStack(DoTBItemsRegistry.PRE_COLUMBIAN.get().asItem()));
		HandlerClient.register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/roman"), new ItemStack(DoTBItemsRegistry.ROMAN.get().asItem()));
	}

	public static void register(final ItemGroup group, final ResourceLocation tag, final ItemStack icon)
	{
		final Set<DoTBFilterEntry> entries = HandlerClient.filterMap.computeIfAbsent(group, itemGroup -> new LinkedHashSet<>());
		entries.add(new DoTBFilterEntry(tag, icon));
	}

	public static Map<ItemGroup, Set<DoTBFilterEntry>> getFilterMap()
	{
		return HandlerClient.filterMap;
	}

}
