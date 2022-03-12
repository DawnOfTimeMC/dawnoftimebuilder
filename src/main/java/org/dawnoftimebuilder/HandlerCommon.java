package org.dawnoftimebuilder;


import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.dawnoftimebuilder.entity.JapaneseDragonEntity;
import org.dawnoftimebuilder.entity.SilkmothEntity;
import org.dawnoftimebuilder.registry.DoTBFeaturesRegistry;

import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {

	@SubscribeEvent
	public static void fMLCommonSetupEvent(final FMLCommonSetupEvent event){

	}

	@SubscribeEvent
	public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		//We need to set att least the default values here
		event.put(SILKMOTH_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).build());
		event.put(JAPANESE_DRAGON_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).build());
	}

}