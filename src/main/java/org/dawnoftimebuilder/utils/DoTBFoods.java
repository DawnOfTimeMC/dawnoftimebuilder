package org.dawnoftimebuilder.utils;

import net.minecraft.item.Food;

public class DoTBFoods {
    public static final Food GRAPE = (new Food.Builder())
            .hunger(DoTBConfig.GRAPE_HUNGER.get())
            .saturation(DoTBConfig.GRAPE_SATURATION.get().floatValue())
            .build();
}
