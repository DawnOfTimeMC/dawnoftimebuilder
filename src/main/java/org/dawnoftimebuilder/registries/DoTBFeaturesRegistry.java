package org.dawnoftimebuilder.registries;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import org.dawnoftimebuilder.generation.features.CamelliaFeature;
import org.dawnoftimebuilder.generation.features.MulberryFeature;
import org.dawnoftimebuilder.generation.features.RiceFeature;
import org.dawnoftimebuilder.utils.DoTBConfig;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBFeaturesRegistry {

    public static final List<Feature<NoFeatureConfig>> FEATURES = new ArrayList<>();

    public static final Feature<NoFeatureConfig> RICE_FEATURE = reg("rice_feature", new RiceFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> CAMELLIA_FEATURE = reg("camellia_feature", new CamelliaFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> MULBERRY_FEATURE = reg("mulberry_feature", new MulberryFeature(NoFeatureConfig::deserialize));

    private static Feature<NoFeatureConfig> reg(String name, Feature<NoFeatureConfig> feature) {
        feature.setRegistryName(MOD_ID, name);
        FEATURES.add(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        addPlants(CAMELLIA_FEATURE,
                DoTBConfig.CAMELLIA_GENERATION.get(),
                DoTBConfig.CAMELLIA_BOTTOM_OFFSET.get(),
                DoTBConfig.CAMELLIA_TOP.get(),
                Biomes.FLOWER_FOREST, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE);
        addPlants(RICE_FEATURE,
                DoTBConfig.RICE_GENERATION.get(),
                DoTBConfig.RICE_BOTTOM_OFFSET.get(),
                DoTBConfig.RICE_TOP.get(),
                Biomes.SWAMP, Biomes.RIVER);
        addPlants(MULBERRY_FEATURE,
                DoTBConfig.MULBERRY_GENERATION.get(),
                DoTBConfig.MULBERRY_BOTTOM_OFFSET.get(),
                DoTBConfig.MULBERRY_TOP.get(),
                Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.FLOWER_FOREST);
    }

    private static void addPlants(Feature<NoFeatureConfig> plant, boolean shouldSpawn, int bottomOffset, int top, Biome... biomeList){
        if(shouldSpawn){
            for (Biome biome : biomeList) {
                biome.addFeature(
                        GenerationStage.Decoration.VEGETAL_DECORATION,
                        Biome.createDecoratedFeature(
                                plant,
                                new NoFeatureConfig(),
                                Placement.CHANCE_RANGE,
                                new ChanceRangeConfig(1.0F, bottomOffset, 0, top)
                        )
                );
            }
        }
    }
}