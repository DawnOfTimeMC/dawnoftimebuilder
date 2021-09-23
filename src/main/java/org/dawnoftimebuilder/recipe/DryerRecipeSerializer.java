package org.dawnoftimebuilder.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DryerRecipeSerializer<T extends DryerRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final DryerRecipeSerializer.IFactory<T> FACTORY;

    public DryerRecipeSerializer(DryerRecipeSerializer.IFactory<T> factory) {
        this.FACTORY = factory;
    }

    @Override
    @Nonnull
    public T fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {

        if (!json.has("ingredient")) throw new JsonSyntaxException("The object 'ingredient' is missing.");
        if(!json.get("ingredient").isJsonObject()) throw new JsonSyntaxException("'ingredient' is expected to be an object.");
        if (!json.has("result")) throw new JsonSyntaxException("The object 'result' is missing.");
        if(!json.get("result").isJsonObject()) throw new JsonSyntaxException("'result' is expected to be an object.");

        String group = JSONUtils.getAsString(json, "group", "");
        Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
        ingredient.getItems()[0].setCount(JSONUtils.getAsInt(JSONUtils.getAsJsonObject(json, "ingredient"), "count", 1));
        ItemStack itemStackResult = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
        float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
        int dryingTime = JSONUtils.getAsInt(json, "dryingTime", 1200);

        return this.FACTORY.create(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Nullable
    @Override
    public T fromNetwork(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String group = buffer.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack itemStackResult = buffer.readItem();
        float experience = buffer.readFloat();
        int dryingTime = buffer.readVarInt();
        return this.FACTORY.create(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        recipe.ingredient.toNetwork(buffer);
        buffer.writeItemStack(recipe.result, true);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.dryingTime);
    }

    public interface IFactory<T extends DryerRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime);
    }
}
