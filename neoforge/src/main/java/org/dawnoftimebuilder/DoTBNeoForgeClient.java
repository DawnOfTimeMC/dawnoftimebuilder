package org.dawnoftimebuilder;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.model.entity.SilkmothModel;
import org.dawnoftimebuilder.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftimebuilder.client.renderer.blockentity.DryerBERenderer;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.registry.DoTBBlockEntitiesRegistry;
import org.dawnoftimebuilder.registry.DoTBColorsRegistry;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;
import org.dawnoftimebuilder.registry.DoTBMenuTypesRegistry;

import java.util.function.Supplier;

@EventBusSubscriber(modid = DoTBCommon.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class DoTBNeoForgeClient {

    @SubscribeEvent
    public static void setupBlockColors(final RegisterColorHandlersEvent.Block event) {
        DoTBColorsRegistry.getBlocksColorRegistry().forEach((blockColor, blocks) -> event.register(blockColor, blocks.stream().map(Supplier::get).toArray(Block[]::new)));
    }

    @SubscribeEvent
    public static void setupItemColors(final RegisterColorHandlersEvent.Item event) {
        DoTBColorsRegistry.getItemsColorRegistry().forEach((itemColor, items) -> event.register(itemColor, items.stream().map(Supplier::get).toArray(Item[]::new)));
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        final IEventBus eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        if (eventBus != null) {
            eventBus.addListener(DoTBNeoForgeClient::setupBlockColors);
            eventBus.addListener(DoTBNeoForgeClient::setupItemColors);
            eventBus.addListener(DoTBNeoForgeClient::registerLayerDefinitions);
            eventBus.addListener(DoTBNeoForgeClient::registerRenderers);
        }

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, screen) -> DoTBNeoForge.HANDLER.generateGui().generateScreen(screen)
        );
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(DoTBMenuTypesRegistry.INSTANCE.DISPLAYER.get(), DisplayerScreen::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SilkmothModel.LAYER_LOCATION, SilkmothModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
        event.registerEntityRenderer(DoTBEntitiesRegistry.INSTANCE.CHAIR_ENTITY.get(), ChairRenderer::new);
        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.INSTANCE.DRYER.get(), DryerBERenderer::new);
        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), DisplayerBERenderer::new);
    }
}
