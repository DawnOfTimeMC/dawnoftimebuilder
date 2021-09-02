package org.dawnoftimebuilder.util;

import net.minecraft.item.Food;

public class DoTBFoods {
    public static final Food GRAPE = (new Food.Builder())
            .hunger(DoTBConfig.GRAPE_HUNGER.get())
            .saturation(DoTBConfig.GRAPE_SATURATION.get().floatValue())
            .build();
    public static final Food MAIZE = (new Food.Builder())
            .hunger(DoTBConfig.MAIZE_HUNGER.get())
            .saturation(DoTBConfig.MAIZE_SATURATION.get().floatValue())
            .build();
    public static final Food MULBERRY = (new Food.Builder())
            .hunger(DoTBConfig.MULBERRY_HUNGER.get())
            .saturation(DoTBConfig.MULBERRY_SATURATION.get().floatValue())
            .fastToEat()
            .build();
}
