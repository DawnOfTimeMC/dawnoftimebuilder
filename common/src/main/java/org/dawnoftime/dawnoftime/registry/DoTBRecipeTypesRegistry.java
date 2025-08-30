package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public abstract class DoTBRecipeTypesRegistry {
    public static DoTBRecipeTypesRegistry INSTANCE;

    public abstract <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name);
}
