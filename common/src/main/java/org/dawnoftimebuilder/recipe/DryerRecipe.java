package org.dawnoftimebuilder.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBRecipeSerializersRegistry;
import org.dawnoftimebuilder.registry.DoTBRecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;


public class DryerRecipe implements Recipe<SingleRecipeInput> {
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final float experience;
    protected final int dryingTime;

    public DryerRecipe(String group, Ingredient ingredient, ItemStack result, float experience, int dryingTime) {
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.dryingTime = dryingTime;
    }

    public int getDryingTime() {
        return this.dryingTime;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput input, @NotNull Level level) {
        return this.ingredient.test(input.getItem(0)) && input.getItem(0).getCount() >= this.ingredient.getItems()[0].getCount();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

//    @Override
//    @Nonnull
//    public ResourceLocation getId() {
//        return this.id;
//    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return DoTBRecipeSerializersRegistry.INSTANCE.DRYER.get();
    }

    @Override
    @NotNull
    public RecipeType<?> getType() {
        return DoTBRecipeTypesRegistry.INSTANCE.DRYING.get();
    }

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        return list;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(DoTBBlocksRegistry.INSTANCE.BAMBOO_DRYING_TRAY.get());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
