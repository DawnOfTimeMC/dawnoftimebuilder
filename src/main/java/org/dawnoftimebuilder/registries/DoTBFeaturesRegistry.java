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
import org.dawnoftimebuilder.generation.features.RicePlantFeature;
import org.dawnoftimebuilder.utils.DoTBConfig;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBFeaturesRegistry {

    public static final List<Feature<NoFeatureConfig>> FEATURES = new ArrayList<>();

    public static final Feature<NoFeatureConfig> RICE_PLANT = reg("rice_plant", new RicePlantFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> CAMELLIA_BUSH = reg("camellia_bush", new CamelliaFeature(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> MULBERRY_TREE = reg("mulberry_tree", new MulberryFeature(NoFeatureConfig::deserialize));

    private static Feature<NoFeatureConfig> reg(String name, Feature<NoFeatureConfig> feature) {
        feature.setRegistryName(MOD_ID, name);
        FEATURES.add(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        if(DoTBConfig.ACTIVATE_WORLD_GENERATION.get()){
            addRice();
            addCamellia();
            addMulberry();
        }
    }

    private static void addCamellia() {
        Biome[] camelliaBiomes = {Biomes.FLOWER_FOREST, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE};
        for (Biome biome : camelliaBiomes) {
            biome.addFeature(
                    GenerationStage.Decoration.VEGETAL_DECORATION,
                    Biome.createDecoratedFeature(
                            CAMELLIA_BUSH,
                            new NoFeatureConfig(),
                            Placement.CHANCE_RANGE,
                            new ChanceRangeConfig(1.0f, 60, 0, 255)
                    )
            );
        }
    }

    private static void addRice() {
        Biome[] ricePlantBiomes = {Biomes.SWAMP, Biomes.RIVER};
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

    private static void addMulberry() {
        Biome[] mulBerryBiomes = {Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.FLOWER_FOREST};

        for (Biome biome : mulBerryBiomes) {
            biome.addFeature(
                    GenerationStage.Decoration.VEGETAL_DECORATION,
                    Biome.createDecoratedFeature(
                            MULBERRY_TREE,
                            new NoFeatureConfig(),
                            Placement.CHANCE_RANGE,
                            new ChanceRangeConfig(1.0f, 62, 0, 255)
                    )
            );
        }
    }
}