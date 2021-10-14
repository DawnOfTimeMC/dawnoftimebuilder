package org.dawnoftimebuilder.registry;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.recipe.DryerRecipeSerializer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBRecipesRegistry {

	public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

	public static final RegistryObject<DryerRecipeSerializer>  DRYER_RECIPE = reg("dryer", DryerRecipeSerializer.INSTANCE);

	private static <T extends IRecipeSerializer<? extends IRecipe<?>>> RegistryObject<T> reg(String name, T t){
		return RECIPES.register(name, () -> t);
	}
}