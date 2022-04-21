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

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.BAMBOO_DRYING_TRAY;
import static org.dawnoftimebuilder.registry.DoTBRecipesRegistry.DRYER_RECIPE;

public class DryerRecipe implements IRecipe<IInventory> {

    public static final IRecipeType<DryerRecipe> DRYING = IRecipeType.register(new ResourceLocation(MOD_ID, "drying").toString());

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
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getItem(0)) && inv.getItem(0).getCount() >= this.ingredient.getItems()[0].getCount();
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
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
    public IRecipeSerializer<?> getSerializer() {
        return DRYER_RECIPE.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return DRYING;
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
        return new ItemStack(BAMBOO_DRYING_TRAY.get());
    }

    @Override
    public boolean isSpecial(){
        return true;
    }
}
