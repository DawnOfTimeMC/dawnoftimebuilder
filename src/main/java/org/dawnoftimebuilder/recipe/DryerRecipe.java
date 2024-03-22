package org.dawnoftimebuilder.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBRecipeTypesRegistry;

import javax.annotation.Nonnull;

import static org.dawnoftimebuilder.registry.DoTBRecipeSerializersRegistry.DRYER;

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
    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean matches(SimpleContainer inv, Level worldIn) {
        return this.ingredient.test(inv.getItem(0)) && inv.getItem(0).getCount() >= this.ingredient.getItems()[0].getCount();
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ItemStack assemble(SimpleContainer container) {
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
    public RecipeSerializer<?> getSerializer() {
        return DRYER.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return DoTBRecipeTypesRegistry.DRYING.get();
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        return list;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(DoTBBlocksRegistry.BAMBOO_DRYING_TRAY.get());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
