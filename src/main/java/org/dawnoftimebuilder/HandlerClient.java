package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
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
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.filters.CreativeInventoryFilters;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;
import org.dawnoftimebuilder.registry.DoTBBlockAndItemColorsRegistry;
import org.dawnoftimebuilder.registry.DoTBItemsRegistry;
import org.dawnoftimebuilder.util.DoTBFilterEntry;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.DISPLAYER_CONTAINER;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

	private static final Map<ItemGroup, Set<DoTBFilterEntry>> filterMap = new HashMap<>();

	@SubscribeEvent
	public static void fMLClientSetupEvent(final FMLClientSetupEvent event){
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerItemsColors);
		eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerBlockColors);

		ScreenManager.register(DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CHAIR_ENTITY.get(), ChairRenderer::new);

		// Above Minecraft 1.15, we need to register renderLayer here
		// General
		RenderTypeLookup.setRenderLayer(ACACIA_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(ACACIA_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(ACACIA_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CRIMSON_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CRIMSON_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CRIMSON_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DARK_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DARK_OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DARK_OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(JUNGLE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(JUNGLE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(JUNGLE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WARPED_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WARPED_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WARPED_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(IRON_PORTCULLIS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WATER_FLOWING_TRICKLE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(WATER_SOURCE_TRICKLE.get(), RenderType.translucent());

		//French
		RenderTypeLookup.setRenderLayer(LIMESTONE_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BLACK_WROUGHT_IRON_BALUSTER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(REINFORCED_BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(REINFORCED_GOLDEN_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BOXWOOD_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BOXWOOD_SMALL_HEDGE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BOXWOOD_TALL_HEDGE.get(), RenderType.cutoutMipped());

		//German
		RenderTypeLookup.setRenderLayer(LATTICE_GLASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(LATTICE_GLASS_PANE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(LATTICE_WAXED_OAK_WINDOW.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(LATTICE_STONE_BRICKS_WINDOW.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_ARROWSLIT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_POOL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_SMALL_POOL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_DOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_TRAPDOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WAXED_OAK_CHANDELIER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_FAUCET.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(STONE_BRICKS_WATER_JET.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(IVY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(GERANIUM_PINK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PLANTER_GERANIUM_PINK.get(), RenderType.cutoutMipped());
		//Japanese
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_DOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_TRAPDOOR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CHARRED_SPRUCE_FANCY_RAILING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(RED_PAINTED_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CAST_IRON_TEAPOT_GRAY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CAST_IRON_TEAPOT_GREEN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CAST_IRON_TEAPOT_DECORATED.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BAMBOO_DRYING_TRAY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CAMELLIA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MULBERRY.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(IKEBANA_FLOWER_POT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WHITE_LITTLE_FLAG.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(RICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(IRORI_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SAKE_BOTTLE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STICK_BUNDLE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(STONE_LANTERN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MAPLE_RED_TRUNK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MAPLE_RED_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MAPLE_RED_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PAUSED_MAPLE_RED_SAPLING.get(), RenderType.cutoutMipped());

		//Persian
		RenderTypeLookup.setRenderLayer(MORAQ_MOSAIC_RECESS.get(), RenderType.cutoutMipped());

		//Pre_columbian
		RenderTypeLookup.setRenderLayer(COMMELINA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WILD_MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PLASTERED_STONE_CRESSET.get(), RenderType.cutoutMipped());

		//Roman
		RenderTypeLookup.setRenderLayer(BIRCH_FANCY_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(MARBLE_FANCY_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIG_FLOWER_POT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(WILD_GRAPE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CYPRESS.get(), RenderType.cutoutMipped());

		//TODO For Choco
		//TODO Temporary : added for Choco's testings
		RenderTypeLookup.setRenderLayer(WAXED_OAK_CHAIR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SERPENT_SCULPTED_COLUMN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(RED_PAPER_LANTERN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PAPER_LAMP.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(SMALL_TATAMI_MAT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(TATAMI_MAT.get(), RenderType.cutoutMipped());

		//Flower pots
		for(Block pot : POT_BLOCKS.values()){
			RenderTypeLookup.setRenderLayer(pot, RenderType.cutoutMipped());
		}

		MinecraftForge.EVENT_BUS.register(DawnOfTimeBuilder.get().events = new CreativeInventoryFilters());

		registerTabs();
	}

	public static void registerTabs() {
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/natural"), new ItemStack(Blocks.GRASS_BLOCK));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/stones"), new ItemStack(Blocks.STONE));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/woods"), new ItemStack(Blocks.OAK_LOG));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/minerals"), new ItemStack(Blocks.EMERALD_BLOCK));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("stairs"), new ItemStack(Blocks.OAK_STAIRS));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("slabs"), new ItemStack(Blocks.OAK_SLAB));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("forge", "glass"), new ItemStack(Blocks.GLASS));
		register(ItemGroup.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/colored"), new ItemStack(Blocks.RED_WOOL));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/vegetation"), new ItemStack(Blocks.GRASS));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/functional"), new ItemStack(Blocks.CRAFTING_TABLE));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/fences_and_walls"), new ItemStack(Blocks.OAK_FENCE));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/interior"), new ItemStack(Blocks.RED_BED));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/glass"), new ItemStack(Blocks.GLASS_PANE));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/colored"), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/special"), new ItemStack(Blocks.DRAGON_HEAD));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/infested"), new ItemStack(Blocks.INFESTED_CRACKED_STONE_BRICKS));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/lights"), new ItemStack(Blocks.TORCH));
		register(ItemGroup.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/bees"), new ItemStack(Blocks.BEE_NEST));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/core"), new ItemStack(Items.REDSTONE));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/components"), new ItemStack(Items.STICKY_PISTON));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/inputs"), new ItemStack(Items.TRIPWIRE_HOOK));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/doors"), new ItemStack(Items.OAK_DOOR));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/trapdoors"), new ItemStack(Items.OAK_TRAPDOOR));
		register(ItemGroup.TAB_REDSTONE, new ResourceLocation("redstone/fence_gates"), new ItemStack(Items.OAK_FENCE_GATE));
		register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/minecarts"), new ItemStack(Items.MINECART));
		register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/boats"), new ItemStack(Items.OAK_BOAT));
		register(ItemGroup.TAB_TRANSPORTATION, new ResourceLocation("transportation/rails"), new ItemStack(Items.RAIL));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/materials"), new ItemStack(Items.GOLD_INGOT));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/eggs"), new ItemStack(Items.TURTLE_EGG));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/plants_and_seeds"), new ItemStack(Items.SUGAR_CANE));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/dyes"), new ItemStack(Items.RED_DYE));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/discs"), new ItemStack(Items.MUSIC_DISC_MALL));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/buckets"), new ItemStack(Items.BUCKET));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/horse_armor"), new ItemStack(Items.DIAMOND_HORSE_ARMOR));
		register(ItemGroup.TAB_MISC, new ResourceLocation("miscellaneous/banner_patterns"), new ItemStack(Items.CREEPER_BANNER_PATTERN));
		register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/raw"), new ItemStack(Items.BEEF));
		register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/cooked"), new ItemStack(Items.COOKED_PORKCHOP));
		register(ItemGroup.TAB_FOOD, new ResourceLocation("foodstuffs/special"), new ItemStack(Items.GOLDEN_APPLE));
		register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/armor"), new ItemStack(Items.IRON_CHESTPLATE));
		register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/weapons"), new ItemStack(Items.IRON_SWORD));
		register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/arrows"), new ItemStack(Items.ARROW));
		register(ItemGroup.TAB_COMBAT, new ResourceLocation("combat/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
		register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/tools"), new ItemStack(Items.IRON_SHOVEL));
		register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/equipment"), new ItemStack(Items.COMPASS));
		register(ItemGroup.TAB_TOOLS, new ResourceLocation("tools/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
		register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/potions"), new ItemStack(Items.DRAGON_BREATH));
		register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/ingredients"), new ItemStack(Items.BLAZE_POWDER));
		register(ItemGroup.TAB_BREWING, new ResourceLocation("brewing/equipment"), new ItemStack(Items.BREWING_STAND));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/general"), new ItemStack(DoTBItemsRegistry.GENERAL.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/egyptian"), new ItemStack(DoTBItemsRegistry.EGYPTIAN.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/french"), new ItemStack(DoTBItemsRegistry.FRENCH.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/german"), new ItemStack(DoTBItemsRegistry.GERMAN.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/persian"), new ItemStack(DoTBItemsRegistry.PERSIAN.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/japanese"), new ItemStack(DoTBItemsRegistry.JAPANESE.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/pre_columbian"), new ItemStack(DoTBItemsRegistry.PRE_COLUMBIAN.get().asItem()));
		register(DawnOfTimeBuilder.DOTB_TAB, new ResourceLocation("dawnoftimebuilder", "dotblocks/roman"), new ItemStack(DoTBItemsRegistry.ROMAN.get().asItem()));
	}

	public static void register(ItemGroup group, ResourceLocation tag, ItemStack icon) {
		Set<DoTBFilterEntry> entries = filterMap.computeIfAbsent(group, (itemGroup) -> new LinkedHashSet<>());
		entries.add(new DoTBFilterEntry(tag, icon));
	}

	public static Map<ItemGroup, Set<DoTBFilterEntry>> getFilterMap() {
		return filterMap;
	}

}
