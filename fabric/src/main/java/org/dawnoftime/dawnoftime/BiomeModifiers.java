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
