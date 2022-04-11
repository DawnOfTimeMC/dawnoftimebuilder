package org.dawnoftimebuilder;


import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.dawnoftimebuilder.generation.DefaultCropsFeature;
import org.dawnoftimebuilder.generation.DoTBBlockPlacer;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import static org.dawnoftimebuilder.DoTBConfig.*;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {

    @SubscribeEvent
    public static void fMLCommonSetupEvent(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        //We need to set att least the default values here
        event.put(SILKMOTH_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).build());
        event.put(JAPANESE_DRAGON_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).build());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ResourceLocation name = event.getName();
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        DefaultCropsFeature cropsFeature = new DefaultCropsFeature(BlockClusterFeatureConfig.CODEC);

        if (name != null) {
            if (CAMELLIA_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.CAMELLIA.get().defaultBlockState()), new DoTBBlockPlacer()).tries(CAMELLIA_ROLLS.get()).xspread(2).zspread(2).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
            if (COMMELINA_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, cropsFeature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.COMMELINA.get().defaultBlockState()), new DoTBBlockPlacer()).tries(64).build()).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
            if (CYPRESS_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.CYPRESS.get().defaultBlockState()), new DoTBBlockPlacer()).tries(CYPRESS_ROLLS.get()).xspread(2).zspread(2).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
            if (MULBERRY_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, cropsFeature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.MULBERRY.get().defaultBlockState()), new DoTBBlockPlacer()).tries(MULBERRY_ROLLS.get()).xspread(2).zspread(2).build()).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
            if (RICE_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, cropsFeature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.RICE.get().defaultBlockState()), new DoTBBlockPlacer()).tries(RICE_ROLLS.get()).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
            }
            if (WILD_GRAPE_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, cropsFeature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.WILD_GRAPE.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE).tries(WILD_GRAPE_ROLLS.get()).xspread(2).zspread(2).build()).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
            if (WILD_MAIZE_GENERATION.get().contains(name.toString())) {
                generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, cropsFeature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DoTBBlocksRegistry.WILD_MAIZE.get().defaultBlockState()), new DoTBBlockPlacer()).tries(WILD_MAIZE_ROLLS.get()).xspread(2).zspread(2).build()).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
            }
        }
    }
}