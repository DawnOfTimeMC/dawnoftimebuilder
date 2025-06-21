package org.dawnoftime.dawnoftime;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftime.dawnoftime.client.gui.screen.DisplayerScreen;
import org.dawnoftime.dawnoftime.client.model.entity.SilkmothModel;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DryerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.ChairRenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.SilkmothRenderer;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBColorsRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBEntitiesRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBMenuTypesRegistry;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DoTBCommon.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DoTBForgeClient {
    public DoTBForgeClient() {}
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
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(DoTBForgeClient::setupBlockColors);
        eventBus.addListener(DoTBForgeClient::setupItemColors);
        eventBus.addListener(DoTBForgeClient::registerLayerDefinitions);
        eventBus.addListener(DoTBForgeClient::registerRenderers);

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraftClient, parent) -> DoTBForge.HANDLER.generateGui().generateScreen(parent)
                )
        );

        MenuScreens.register(DoTBMenuTypesRegistry.INSTANCE.DISPLAYER.get(), DisplayerScreen::new);
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
