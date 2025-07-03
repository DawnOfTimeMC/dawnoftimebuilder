package org.dawnoftime.dawnoftime;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.dawnoftime.dawnoftime.registry.DoTBFeaturesRegistry;

public class BiomeModifiers {
    public static void init() {
        addFeatureToBiomes(DoTBFeaturesRegistry.CAMELLIA_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.JUNGLE, Biomes.SPARSE_JUNGLE);
        addFeatureToBiomes(DoTBFeaturesRegistry.COMMELINA_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        addFeatureToBiomes(DoTBFeaturesRegistry.CYPRESS_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        addFeatureToBiomes(DoTBFeaturesRegistry.RED_MAPLE_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.DARK_FOREST);
        addFeatureToBiomes(DoTBFeaturesRegistry.BOXWOOD_BUSH_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.WINDSWEPT_FOREST);
        addFeatureToBiomes(DoTBFeaturesRegistry.MULBERRY_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        addFeatureToBiomes(DoTBFeaturesRegistry.RICE_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        addFeatureToBiomes(DoTBFeaturesRegistry.WILD_GRAPE_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
        addFeatureToBiomes(DoTBFeaturesRegistry.WILD_MAIZE_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.FLOWER_FOREST);
        addFeatureToBiomes(DoTBFeaturesRegistry.GERANIUM_PINK_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
        addFeatureToBiomes(DoTBFeaturesRegistry.IVY_PLACED_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
    }

    @SafeVarargs
    private static void addFeatureToBiomes(ResourceKey<PlacedFeature> featureKey, GenerationStep.Decoration step, ResourceKey<Biome>... biomes) {
        for (ResourceKey<Biome> biome : biomes) {
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(biome),
                    step,
                    featureKey
            );
        }
    }
}
