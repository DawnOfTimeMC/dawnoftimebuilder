package org.dawnoftime.dawnoftime.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.worldgen.feature.DefaultCropsFeature;
import org.dawnoftime.dawnoftime.worldgen.feature.DoTFeature;

import java.util.function.Supplier;

public abstract class DoTBFeaturesRegistry {
    public static final ResourceKey<PlacedFeature> IVY_PLACED_KEY = registerKey("ivy_placed");
    public static DoTBFeaturesRegistry INSTANCE;

    public final Supplier<Feature<SimpleBlockConfiguration>> DOT_FEATURE = register("dot_feature",
            () -> new DoTFeature(SimpleBlockConfiguration.CODEC));

    public final Supplier<Feature<RandomPatchConfiguration>> DEFAULT_CROPS = register("default_crops",
            () -> new DefaultCropsFeature(RandomPatchConfiguration.CODEC));

    public static ResourceKey<PlacedFeature> registerKey(String name) {
                return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(DoTBCommon.MOD_ID, name));
            }

    public abstract <Y extends FeatureConfiguration, T extends Feature<Y>> Supplier<T> register(final String name, final Supplier<T> featureSupplier);
}
