package org.dawnoftimebuilder.util;

import net.minecraft.world.food.FoodProperties;
import org.dawnoftimebuilder.DoTBConfig;

public class DoTBFoods {
    public static final FoodProperties GRAPE = (new FoodProperties.Builder())
            .nutrition(DoTBConfig.GRAPE_HUNGER.get())
            .saturationMod(DoTBConfig.GRAPE_SATURATION.get().floatValue())
            .build();
    public static final FoodProperties MAIZE = (new FoodProperties.Builder())
            .nutrition(DoTBConfig.MAIZE_HUNGER.get())
            .saturationMod(DoTBConfig.MAIZE_SATURATION.get().floatValue())
            .build();
    public static final FoodProperties MULBERRY = (new FoodProperties.Builder())
            .nutrition(DoTBConfig.MULBERRY_HUNGER.get())
            .saturationMod(DoTBConfig.MULBERRY_SATURATION.get().floatValue())
            .fast()
            .build();
}
