package org.dawnoftime.dawnoftime.datagen.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBFeaturesRegistry;

public class DoTBConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> IVY_KEY = registerKey("ivy");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, IVY_KEY, Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.IVY.get())
                    )
                )
            )
        );
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DoTBCommon.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
