package org.dawnoftimebuilder.datagen.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import org.dawnoftimebuilder.DawnOfTimeBuilder;

import java.util.List;

public class DoTBPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CAMELLIA_PLACED_KEY = registerKey("camellia_placed");
    public static final ResourceKey<PlacedFeature> COMMELINA_PLACED_KEY = registerKey("commelina_placed");
    public static final ResourceKey<PlacedFeature> CYPRESS_PLACED_KEY = registerKey("cypress_placed");
    public static final ResourceKey<PlacedFeature> RED_MAPLE_PLACED_KEY = registerKey("red_maple_placed");
    public static final ResourceKey<PlacedFeature> BOXWOOD_BUSH_PLACED_KEY = registerKey("boxwood_bush_placed");
    public static final ResourceKey<PlacedFeature> MULBERRY_PLACED_KEY = registerKey("mulberry_placed");
    public static final ResourceKey<PlacedFeature> RICE_PLACED_KEY = registerKey("rice_placed");
    public static final ResourceKey<PlacedFeature> WILD_GRAPE_PLACED_KEY = registerKey("wild_grape_placed");
    public static final ResourceKey<PlacedFeature> WILD_MAIZE_PLACED_KEY = registerKey("wild_maize_placed");
    public static final ResourceKey<PlacedFeature> GERANIUM_PINK_PLACED_KEY = registerKey("geranium_pink_placed");
    public static final ResourceKey<PlacedFeature> IVY_PLACED_KEY = registerKey("ivy_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CAMELLIA_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CAMELLIA_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, COMMELINA_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.COMMELINA_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, CYPRESS_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CYPRESS_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, RED_MAPLE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.RED_MAPLE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, BOXWOOD_BUSH_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.BOXWOOD_BUSH_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, MULBERRY_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.MULBERRY_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, RICE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.RICE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
            )
        );

        register(context, WILD_GRAPE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.WILD_GRAPE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, WILD_MAIZE_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.WILD_MAIZE_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, GERANIUM_PINK_PLACED_KEY,
            configuredFeatures.getOrThrow(DoTBConfiguredFeatures.GERANIUM_PINK_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        );

        register(context, IVY_PLACED_KEY,
                configuredFeatures.getOrThrow(DoTBConfiguredFeatures.IVY_KEY),
                List.of(
                    RarityFilter.onAverageOnceEvery(2),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
                )
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
