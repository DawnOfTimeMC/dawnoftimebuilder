package org.dawnoftime.dawnoftime.util;

import net.minecraft.world.food.FoodProperties;

public class Foods {
    public static final FoodProperties GRAPE = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.2f)
            .build();
    public static final FoodProperties MAIZE = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationMod(1.0f)
            .build();
    public static final FoodProperties MULBERRY = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationMod(0.5f)
            .fast()
            .build();
}
