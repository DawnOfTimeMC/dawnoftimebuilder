//package org.dawnoftime.dawnoftime;
//
//import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import org.dawnoftime.dawnoftime.entity.SilkmothEntity;
//import org.dawnoftime.dawnoftime.registry.DoTBEntitiesRegistry;
//
//@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class HandlerCommon {
//    private HandlerCommon() {}
//    @SubscribeEvent
//    public static void entityAttributes(EntityAttributeCreationEvent event) {
//        event.put(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(), SilkmothEntity.createAttributes().build());
//    }
//}
