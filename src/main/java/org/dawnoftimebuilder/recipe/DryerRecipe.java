package org.dawnoftimebuilder.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.BAMBOO_DRYING_TRAY;
import static org.dawnoftimebuilder.registry.DoTBRecipesRegistry.DRYER_RECIPE;

public class DryerRecipe implements IRecipe<IInventory> {
    /*
    This class will register the parameters for Dryer Recipes from json file :
        - input item
        - input required quantity
        - time processing
        - output item
        - output quantity
        - generate the ModelResourceLocation for both InputItem and OutputItem
    */
    // TODO The event to register SpecialModels will check each recipe in the registry and register a model for each item used in machines that need one.

    public static final IRecipeType<DryerRecipe> DRYING = IRecipeType.register("drying");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final ItemStack result;
    final float experience;
    final int crushingTime;

    public DryerRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int crushingTime) {
        this.type = DRYING;
        this.id = resourceLocation;
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.crushingTime = crushingTime;
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return DRYER_RECIPE;
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return this.type;
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        return list;
    }

    @Override
    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(BAMBOO_DRYING_TRAY);
    }

    public int getCrushingTime() {
        return this.crushingTime;
    }

}
