package org.dawnoftimebuilder.recipes;

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

public abstract class DoTBRecipes implements IRecipe<IInventory> {
/*
    //Abstract class that get the recipes from "data" folder.
    //These recipes are used in DoTB new machines, like the Dryer.

    public static final IRecipeType<CrusherRecipe> crushing = IRecipeType.register("crushing");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final ItemStack result;
    final float experience;
    final int crushingTime;

    public DoTBRecipes(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int crushingTime) {
        this.type = crushing;
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
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSubscriber.crusher;
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
        return new ItemStack(BlockSubscriber.crusher);
    }

    public int getCrushingTime() {
        return this.crushingTime;
    }

 */
}
