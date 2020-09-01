package org.dawnoftimebuilder.registries;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.generation.features.RicePlantFeature;

import java.util.ArrayList;
import java.util.List;

public class DoTBFeaturesRegistry {

    public static final List<Feature<?>> FEATURES = new ArrayList<>();

    // plants
    public static final Feature<NoFeatureConfig> RICE_PLANT = (Feature<NoFeatureConfig>) reg(new RicePlantFeature(NoFeatureConfig::deserialize, "rice_plant"));

    private static Feature<?> reg(Feature<?> feature){
        FEATURES.add(feature);
        return feature;
    }
}