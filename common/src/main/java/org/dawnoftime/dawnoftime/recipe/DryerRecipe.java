package org.dawnoftime.dawnoftime.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBRecipeSerializersRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBRecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DryerRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final ItemStack result;
    final float experience;
    final int dryingTime;

    public DryerRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int dryingTime) {
        this.id = resourceLocation;
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
    public boolean matches(SimpleContainer inv, @NotNull Level worldIn) {
        return this.ingredient.test(inv.getItem(0)) && inv.getItem(0).getCount() >= this.ingredient.getItems()[0].getCount();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return DoTBRecipeSerializersRegistry.INSTANCE.DRYER.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return DoTBRecipeTypesRegistry.INSTANCE.DRYING.get();
    }

    @Override
    @Nonnull
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
