package org.dawnoftime.dawnoftime.datagen.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMELLIA_KEY = registerKey("camellia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COMMELINA_KEY = registerKey("commelina");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_KEY = registerKey("cypress");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_KEY = registerKey("red_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BOXWOOD_BUSH_KEY = registerKey("boxwood_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MULBERRY_KEY = registerKey("mulberry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RICE_KEY = registerKey("rice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GRAPE_KEY = registerKey("wild_grape");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_MAIZE_KEY = registerKey("wild_maize");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GERANIUM_PINK_KEY = registerKey("geranium_pink");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IVY_KEY = registerKey("ivy");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, CAMELLIA_KEY, Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(48, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.CAMELLIA.get())
                    )
                )
            )
        );

        register(context, COMMELINA_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(48, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.COMMELINA.get())
                    )
                )
            )
        );

        register(context, CYPRESS_KEY, Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.CYPRESS.get())
                    )
                )
            )
        );

        register(context, RED_MAPLE_KEY, Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 3, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.MAPLE_RED_SAPLING.get())
                    )
                )
            )
        );

        register(context, BOXWOOD_BUSH_KEY, Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.BOXWOOD_BUSH.get())
                    )
                )
            )
        );

        register(context, MULBERRY_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.MULBERRY.get())
                    )
                )
            )
        );

        register(context, RICE_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(64, 5, 2,
                PlacementUtils.filtered(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.RICE.get())
                    ),
                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE
                )
            )
        );

        register(context, WILD_GRAPE_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.WILD_GRAPE.get())
                    )
                )
            )
        );

        register(context, WILD_MAIZE_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(DoTBFeaturesRegistry.INSTANCE.DOT_FEATURE.get(),
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.WILD_MAIZE.get())
                    )
                )
            )
        );

        register(context, GERANIUM_PINK_KEY, DoTBFeaturesRegistry.INSTANCE.DEFAULT_CROPS.get(),
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.INSTANCE.GERANIUM_PINK.get())
                    )
                )
            )
        );

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
