package org.dawnoftime.dawnoftime;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.ChairRenderer;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBColorsRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBEntitiesRegistry;

import java.util.function.Supplier;

@EventBusSubscriber(modid = DoTBCommon.MOD_ID, value = Dist.CLIENT)
public class DoTBNeoForgeClient {
    public DoTBNeoForgeClient() {}
    @SubscribeEvent
    public static void setupBlockColors(final RegisterColorHandlersEvent.Block event) {
        DoTBColorsRegistry.getBlocksColorRegistry().forEach((blockColor, blocks) -> event.register(blockColor, blocks.stream().map(Supplier::get).toArray(Block[]::new)));
    }

    @SubscribeEvent
    public static void registerItemTintSources(final RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, "biome_item_tint_source"),
                DoTBColorsRegistry.BiomeItemTintSource.MAP_CODEC
        );
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        final IEventBus eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        if (eventBus == null) return;

        eventBus.addListener(DoTBNeoForgeClient::setupBlockColors);
        eventBus.addListener(DoTBNeoForgeClient::registerItemTintSources);
        eventBus.addListener(DoTBNeoForgeClient::registerRenderers);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoTBEntitiesRegistry.INSTANCE.CHAIR_ENTITY.get(), ChairRenderer::new);
        event.registerBlockEntityRenderer(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), DisplayerBERenderer::new);
    }
}
