package org.dawnoftimebuilder;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.renderer.ChairRenderer;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;


@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

    public static CreativeInventoryEvents events;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
       // MinecraftForge.EVENT_BUS.register(events = new CreativeInventoryEvents());
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);
    }
}
