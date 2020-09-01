package org.dawnoftimebuilder.registries;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import org.dawnoftimebuilder.generation.features.CamelliaFeature;
import org.dawnoftimebuilder.generation.features.RicePlantFeature;

import java.util.ArrayList;
import java.util.List;

public class DoTBFeaturesRegistry {

    public static final List<Feature<?>> FEATURES = new ArrayList<>();

    // plants
    public static final Feature<NoFeatureConfig> RICE_PLANT = (Feature<NoFeatureConfig>) reg(new RicePlantFeature(NoFeatureConfig::deserialize, "rice_plant"));

    // flowers
    public static final Feature<NoFeatureConfig> CAMELLIA_BUSH = (Feature<NoFeatureConfig>) reg(new CamelliaFeature(NoFeatureConfig::deserialize, "dotb_flower_forest"));


    private static Feature<?> reg(Feature<?> feature){
        FEATURES.add(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        addRice();
        addCamellia();
    }

    private static void addCamellia() {
        Biomes.FLOWER_FOREST.addFeature(
            GenerationStage.Decoration.VEGETAL_DECORATION,
            Biome.createDecoratedFeature(
                CAMELLIA_BUSH,
                new NoFeatureConfig(),
                Placement.CHANCE_RANGE,
                new ChanceRangeConfig(1.0f, 60, 0, 255)
            )
        );
    }

    private static void addRice() {
        Biome[] ricePlantBiomes = { Biomes.SWAMP, Biomes.RIVER };
        for (Biome biome : ricePlantBiomes) {
            biome.addFeature(
                GenerationStage.Decoration.VEGETAL_DECORATION,
                Biome.createDecoratedFeature(
                    RICE_PLANT,
                    new NoFeatureConfig(),
                    Placement.CHANCE_RANGE,
                    new ChanceRangeConfig(1.0f, 60, 0, 65)
                )
            );
        }
    }
}