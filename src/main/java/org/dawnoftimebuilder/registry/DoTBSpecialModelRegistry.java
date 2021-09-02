package org.dawnoftimebuilder.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import org.dawnoftimebuilder.recipe.DryerRecipe;

import java.util.Collection;
import java.util.Hashtable;

public class DoTBSpecialModelRegistry {

	public static final Hashtable<String, ResourceLocation> SPECIAL_MODELS = new Hashtable<>();
	static{
		registerModelsFromRecipe(DryerRecipe.DRYING);
	}

	private static void registerModelsFromRecipe(IRecipeType<?> drying) {
		RecipeManager rm = Minecraft.getInstance().world.getRecipeManager();
		//SPECIAL_MODELS.put(name, new ResourceLocation());
	}
}
