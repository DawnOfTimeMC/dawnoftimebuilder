package org.dawnoftimebuilder;

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
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.DISPLAYER_CONTAINER;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

	@SubscribeEvent
	public static void fMLClientSetupEvent(final FMLClientSetupEvent event){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());

		ScreenManager.register(DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CHAIR_ENTITY.get(), ChairRenderer::new);

		//Above Minecraft 1.15, we need to register renderLayer here
		RenderTypeLookup.setRenderLayer(ACACIA_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(ACACIA_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(ACACIA_BEAM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_PERGOLA.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_LATTICE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BIRCH_BEAM.get(), RenderType.cutoutMipped());
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
		RenderTypeLookup.setRenderLayer(FIREPLACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(IRON_PORTCULLIS.get(), RenderType.cutoutMipped());
	}
}
