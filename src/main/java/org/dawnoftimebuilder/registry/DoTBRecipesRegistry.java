package org.dawnoftimebuilder.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.recipe.DryerRecipeSerializer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBRecipesRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final RegistryObject<DryerRecipeSerializer> DRYER_RECIPE = reg("dryer", DryerRecipeSerializer.INSTANCE);

    private static <T extends RecipeSerializer<? extends Recipe<?>>> RegistryObject<T> reg(String name, T t) {
        return RECIPES.register(name, () -> t);
    }
}