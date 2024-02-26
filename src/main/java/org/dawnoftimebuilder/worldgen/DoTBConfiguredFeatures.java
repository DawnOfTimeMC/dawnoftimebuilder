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
import org.dawnoftimebuilder.block.templates.GrowingBushBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

public class DoTBConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMELLIA_KEY = registerKey("camellia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COMMELINA_KEY = registerKey("commelina");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, CAMELLIA_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(64, 5, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(
                                    BlockStateProvider.simple(DoTBBlocksRegistry.CAMELLIA.get()
                                            .defaultBlockState().setValue(GrowingBushBlock.AGE, 5))))));

        register(context, COMMELINA_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(64, 5, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(DoTBBlocksRegistry.COMMELINA.get()
                                                .defaultBlockState().setValue(CropBlock.AGE, CropBlock.MAX_AGE))))));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
