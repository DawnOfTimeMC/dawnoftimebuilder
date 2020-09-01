package org.dawnoftimebuilder.generation.features.templates;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.function.Function;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class FeatureDoTB<FC extends IFeatureConfig> extends Feature {

    public FeatureDoTB(Function<Dynamic<?>, ? extends FC> configFactoryIn, String name) {
        super(configFactoryIn);
        this.setRegistryName(MOD_ID, name);
    }

}
