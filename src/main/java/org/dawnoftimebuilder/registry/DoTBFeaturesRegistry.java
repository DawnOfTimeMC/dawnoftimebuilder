package org.dawnoftimebuilder.registry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import org.dawnoftimebuilder.generation.features.*;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBFeaturesRegistry {

    public static final List<Feature<NoFeatureConfig>> FEATURES = new ArrayList<>();

    public static final Feature<NoFeatureConfig> CAMELLIA_FEATURE = reg("camellia_feature", new CamelliaFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> CYPRESS_FEATURE = reg("cypress_feature", new CypressFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> MULBERRY_FEATURE = reg("mulberry_feature", new MulberryFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> RICE_FEATURE = reg("rice_feature", new RiceFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> COMMELINA_FEATURE = reg("commelina_feature", new CommelinaFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> WILD_MAIZE_FEATURE = reg("wild_maize_feature", new WildMaizeFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> WILD_GRAPE_FEATURE = reg("wild_grape_feature", new WildGrapeFeature(NoFeatureConfig::deserialize));

    private static Feature<NoFeatureConfig> reg(String name, Feature<NoFeatureConfig> feature) {
        feature.setRegistryName(MOD_ID, name);
        FEATURES.add(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        addPlants(CAMELLIA_FEATURE,
                DoTBConfig.CAMELLIA_GENERATION.get(),
                DoTBConfig.CAMELLIA_BOTTOM.get(),
                DoTBConfig.CAMELLIA_TOP.get(),
                Biomes.FLOWER_FOREST, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE);
        addPlants(CYPRESS_FEATURE,
                DoTBConfig.CYPRESS_GENERATION.get(),
                DoTBConfig.CYPRESS_BOTTOM.get(),
                DoTBConfig.CYPRESS_TOP.get(),
                Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.FLOWER_FOREST);
        addPlants(MULBERRY_FEATURE,
                DoTBConfig.MULBERRY_GENERATION.get(),
                DoTBConfig.MULBERRY_BOTTOM.get(),
                DoTBConfig.MULBERRY_TOP.get(),
                Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.FLOWER_FOREST);
        addPlants(RICE_FEATURE,
                DoTBConfig.RICE_GENERATION.get(),
                DoTBConfig.RICE_BOTTOM.get(),
                DoTBConfig.RICE_TOP.get(),
                Biomes.SWAMP, Biomes.RIVER, Biomes.SWAMP_HILLS);
        addPlants(COMMELINA_FEATURE,
                DoTBConfig.COMMELINA_GENERATION.get(),
                DoTBConfig.COMMELINA_BOTTOM.get(),
                DoTBConfig.COMMELINA_TOP.get(),
                Biomes.FLOWER_FOREST, Biomes.SWAMP, Biomes.SWAMP_HILLS);
        addPlants(WILD_MAIZE_FEATURE,
                DoTBConfig.WILD_MAIZE_GENERATION.get(),
                DoTBConfig.WILD_MAIZE_BOTTOM.get(),
                DoTBConfig.WILD_MAIZE_TOP.get(),
                Biomes.FLOWER_FOREST, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU);
        addPlants(WILD_GRAPE_FEATURE,
                DoTBConfig.WILD_GRAPE_GENERATION.get(),
                DoTBConfig.WILD_GRAPE_BOTTOM.get(),
                DoTBConfig.WILD_GRAPE_TOP.get(),
                Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);
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