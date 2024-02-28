package org.dawnoftimebuilder;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
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
    public static CreativeInventoryEvents creativeInventoryEvents;
    private static boolean DOT_SELECTED = false;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerBlockColors);
        eventBus.addListener(DoTBBlockAndItemColorsRegistry::registerItemsColors);

        MenuScreens.register(DoTBMenuTypesRegistry.DISPLAYER.get(), DisplayerScreen::new);

        MinecraftForge.EVENT_BUS.register(creativeInventoryEvents = new CreativeInventoryEvents());
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.DRYER.get(), DryerBERenderer::new);
        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.DISPLAYER.get(), DisplayerBERenderer::new);
    }

    public static void onCreativeTabChange(CreativeModeInventoryScreen screen, CreativeModeTab tab) {
        DOT_SELECTED = tab == DoTBCreativeModeTabsRegistry.DOT_TAB.get();
    }

    public static boolean isDotSelected() {
        return DOT_SELECTED;
    }
}
