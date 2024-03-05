package org.dawnoftimebuilder;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftimebuilder.entity.SilkmothEntity;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(), SilkmothEntity.createAttributes().build());
    }
}
