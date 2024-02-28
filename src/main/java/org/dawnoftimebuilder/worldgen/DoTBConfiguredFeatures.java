package org.dawnoftimebuilder.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.roman.CypressBlock;
import org.dawnoftimebuilder.block.templates.GrowingBushBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

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

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, CAMELLIA_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(48, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.CAMELLIA.get()
                                .defaultBlockState().setValue(GrowingBushBlock.AGE, 5)
                        )
                    )
                )
            )
        );

        register(context, COMMELINA_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(48, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.COMMELINA.get()
                                .defaultBlockState().setValue(CropBlock.AGE, CropBlock.MAX_AGE)
                        )
                    )
                )
            )
        );

        register(context, CYPRESS_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.CYPRESS.get()
                                .defaultBlockState().setValue(CypressBlock.SIZE, 2)
                        )
                    )
                )
            )
        );

        register(context, RED_MAPLE_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 3, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get())
                    )
                )
            )
        );

        register(context, BOXWOOD_BUSH_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.BOXWOOD_BUSH.get())
                    )
                )
            )
        );

        register(context, MULBERRY_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.MULBERRY.get())
                    )
                )
            )
        );

        register(context, RICE_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.RICE.get()
                                .defaultBlockState().setValue(CropBlock.AGE, CropBlock.MAX_AGE)
                        )
                    )
                )
            )
        );

        register(context, WILD_GRAPE_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.WILD_GRAPE.get())
                    )
                )
            )
        );

        register(context, WILD_MAIZE_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            BlockStateProvider.simple(DoTBBlocksRegistry.WILD_MAIZE.get())
                    )
                )
            )
        );

        register(context, GERANIUM_PINK_KEY, Feature.FLOWER,
            new RandomPatchConfiguration(32, 5, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(DoTBBlocksRegistry.GERANIUM_PINK.get())
                    )
                )
            )
        );
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
