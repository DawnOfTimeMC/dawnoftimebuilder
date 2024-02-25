package org.dawnoftimebuilder;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftimebuilder.client.renderer.blockentity.DryerBERenderer;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.registry.*;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {
    public static CreativeInventoryEvents events;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // MinecraftForge.EVENT_BUS.register(events = new CreativeInventoryEvents());
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerBlockColors);
        eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerItemsColors);

        MenuScreens.register(DoTBMenuTypesRegistry.DISPLAYER.get(), DisplayerScreen::new);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.DRYER.get(), DryerBERenderer::new);
        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.DISPLAYER.get(), DisplayerBERenderer::new);
    }
}
