package org.dawnoftimebuilder.util;

import net.minecraft.world.food.FoodProperties;

public class Foods {
    public static final FoodProperties GRAPE = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationModifier(0.2f)
            .build();
    public static final FoodProperties MAIZE = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationModifier(1.0f)
            .build();
    public static final FoodProperties MULBERRY = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationModifier(0.5f)
            .fast()
            .build();
}
