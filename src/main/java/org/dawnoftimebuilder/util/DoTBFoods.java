package org.dawnoftimebuilder.util;

import net.minecraft.item.Food;
import org.dawnoftimebuilder.DoTBConfig;

public class DoTBFoods {
    public static final Food GRAPE = (new Food.Builder())
            .nutrition(DoTBConfig.GRAPE_HUNGER.get())
            .saturationMod(DoTBConfig.GRAPE_SATURATION.get().floatValue())
            .build();
    public static final Food MAIZE = (new Food.Builder())
            .nutrition(DoTBConfig.MAIZE_HUNGER.get())
            .saturationMod(DoTBConfig.MAIZE_SATURATION.get().floatValue())
            .build();
    public static final Food MULBERRY = (new Food.Builder())
            .nutrition(DoTBConfig.MULBERRY_HUNGER.get())
            .saturationMod(DoTBConfig.MULBERRY_SATURATION.get().floatValue())
            .fast()
            .build();
}
