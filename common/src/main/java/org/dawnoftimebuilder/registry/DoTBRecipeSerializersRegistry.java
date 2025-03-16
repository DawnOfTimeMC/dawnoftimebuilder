package org.dawnoftimebuilder.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.dawnoftimebuilder.recipe.DryerRecipe;
import org.dawnoftimebuilder.recipe.DryerRecipeSerializer;

import java.util.function.Supplier;

public abstract class DoTBRecipeSerializersRegistry {
    public static DoTBRecipeSerializersRegistry INSTANCE;

    public final Supplier<DryerRecipeSerializer> DRYER = register("dryer", () -> new DryerRecipeSerializer(DryerRecipe::new));

    public abstract <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer);
}