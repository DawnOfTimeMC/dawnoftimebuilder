package org.dawnoftimebuilder.worldgen;

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

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CAMELLIA_PLACED_KEY, configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CAMELLIA_KEY),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, COMMELINA_PLACED_KEY, configuredFeatures.getOrThrow(DoTBConfiguredFeatures.COMMELINA_KEY),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, CYPRESS_PLACED_KEY, configuredFeatures.getOrThrow(DoTBConfiguredFeatures.CYPRESS_KEY),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
