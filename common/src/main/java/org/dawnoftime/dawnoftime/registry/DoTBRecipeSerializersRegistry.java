package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public abstract class DoTBRecipeSerializersRegistry {
    public static DoTBRecipeSerializersRegistry INSTANCE;

    public abstract <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer);
}