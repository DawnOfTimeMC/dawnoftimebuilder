package org.dawnoftimebuilder.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class DryerRecipes extends DoTBRecipes {

    public DryerRecipes(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int crushingTime) {
        //super(resourceLocation, group, ingredient, result, experience, crushingTime);
    }

    /*TODO DryerRecipes class and DotBRecipes class
    This class will register the parameters for Dryer Recipes from json file :
        - input item
        - input required quantity
        - time processing
        - output item
        - output quantity
        - generate the ModelResourceLocation for both InputItem and OutputItem

    Each recipe will be saved in the new RecipeRegistry I need to create.
    The event to register SpecialModels will check each recipe in the registry and register a model for each item used in machines that need one.
    */
}
