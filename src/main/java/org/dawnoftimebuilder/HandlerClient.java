package org.dawnoftimebuilder;

import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBContainersRegistry;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;
import org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

	@SubscribeEvent
	public static void fMLClientSetupEvent(final FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());

		ScreenManager.register(DoTBContainersRegistry.DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DoTBTileEntitiesRegistry.DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DoTBTileEntitiesRegistry.DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

		//Above Minecraft 1.15, we need to register renderLayer here
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

		//French
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.LIMESTONE_FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BLACK_WROUGHT_IRON_BALUSTER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.REINFORCED_BLACK_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.REINFORCED_GOLDEN_WROUGHT_IRON_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_SMALL_HEDGE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BOXWOOD_TALL_HEDGE.get(), RenderType.cutoutMipped());

		//German
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

		//Japanese
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

		//Persian
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MORAQ_MOSAIC_RECESS.get(), RenderType.cutoutMipped());

		//Pre_columbian
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.COMMELINA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WILD_MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.MAIZE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PLASTERED_STONE_CRESSET.get(), RenderType.cutoutMipped());

		//Roman
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.BIG_FLOWER_POT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WILD_GRAPE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.CYPRESS.get(), RenderType.cutoutMipped());

		//TODO Temporary : added for Choco's testings
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.WAXED_OAK_CHAIR.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SERPENT_SCULPTED_COLUMN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.RED_PAPER_LANTERN.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.PAPER_LAMP.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.SMALL_TATAMI_MAT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DoTBBlocksRegistry.TATAMI_MAT.get(), RenderType.cutoutMipped());

		//Flower pots
		for (final Block pot : DoTBBlocksRegistry.POT_BLOCKS.values()) {
			RenderTypeLookup.setRenderLayer(pot, RenderType.cutoutMipped());
		}
	}
}
