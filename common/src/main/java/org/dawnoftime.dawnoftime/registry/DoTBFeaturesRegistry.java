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
    public static DoTBFeaturesRegistry INSTANCE;
//    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Constants.MOD_ID);

    public final Supplier<Feature<SimpleBlockConfiguration>> DOT_FEATURE = register("dot_feature",
            () -> new DoTFeature(SimpleBlockConfiguration.CODEC));

    public final Supplier<Feature<RandomPatchConfiguration>> DEFAULT_CROPS = register("default_crops",
            () -> new DefaultCropsFeature(RandomPatchConfiguration.CODEC));

    public static ResourceKey<PlacedFeature> registerKey(String name) {
                return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(DoTBCommon.MOD_ID, name));
            }

    public abstract <Y extends FeatureConfiguration, T extends Feature<Y>> Supplier<T> register(final String name, final Supplier<T> featureSupplier);
}
