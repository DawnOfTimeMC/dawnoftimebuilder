package org.dawnoftimebuilder.registry;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.dawnoftimebuilder.recipe.DryerRecipe;
import org.dawnoftimebuilder.recipe.DryerRecipeSerializer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBRecipesRegistry {

	@ObjectHolder(MOD_ID + ":dryer")
	public static final IRecipeSerializer<?> DRYER_RECIPE = null;

	public static void registerAll(IForgeRegistry<IRecipeSerializer<?>> registry) {
		registry.register(new DryerRecipeSerializer<>(DryerRecipe::new).setRegistryName(MOD_ID, "dryer"));
	}
}