package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.dawnoftime.dawnoftime.recipe.DryerRecipeSerializer;

import java.util.function.Supplier;

public abstract class DoTBRecipeSerializersRegistry {
    public static DoTBRecipeSerializersRegistry INSTANCE;

    public final Supplier<DryerRecipeSerializer> DRYER = register("dryer", DryerRecipeSerializer::new);

    public abstract <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer);
}