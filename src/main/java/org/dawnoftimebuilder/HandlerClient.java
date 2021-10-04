package org.dawnoftimebuilder;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;

import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.DISPLAYER_CONTAINER;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

	public static void fMLClientSetupEvent(final FMLClientSetupEvent event){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());

		ScreenManager.register(DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
	}
}
