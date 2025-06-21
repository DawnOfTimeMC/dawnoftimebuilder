package org.dawnoftime.dawnoftime.datagen.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import org.dawnoftime.dawnoftime.registry.DoTBFeaturesRegistry;

import java.util.List;

public class DoTBPlacedFeatures {
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DoTBFeaturesRegistry.CAMELLIA_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CAMELLIA_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.COMMELINA_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.COMMELINA_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.CYPRESS_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CYPRESS_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.RED_MAPLE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.RED_MAPLE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.BOXWOOD_BUSH_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.BOXWOOD_BUSH_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.MULBERRY_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.MULBERRY_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.RICE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.RICE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.WILD_GRAPE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.WILD_GRAPE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.WILD_MAIZE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.WILD_MAIZE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.GERANIUM_PINK_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.GERANIUM_PINK_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, DoTBFeaturesRegistry.IVY_PLACED_KEY,
                configuredFeatures.getOrThrow(DoTBConfiguredFeatures.IVY_KEY),
                List.of(
                    RarityFilter.onAverageOnceEvery(2),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
                )
        );
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
