package org.dawnoftimebuilder;


import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
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

	public static void fMLCommonSetupEvent(final FMLCommonSetupEvent event){

	}

	private static void fMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
		GlobalEntityTypeAttributes.put(SILKMOTH_ENTITY.get(), SilkmothEntity.createAttributes().build());
		GlobalEntityTypeAttributes.put(JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonEntity.createAttributes().build());

		//DoTBFeaturesRegistry.addFeaturesToBiomes();
	}
}