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
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {

        if (!json.has("ingredient")) throw new JsonSyntaxException("The object 'ingredient' is missing.");
        if(!json.get("ingredient").isJsonObject()) throw new JsonSyntaxException("'ingredient' is expected to be an object.");
        if (!json.has("result")) throw new JsonSyntaxException("The object 'result' is missing.");
        if(!json.get("result").isJsonObject()) throw new JsonSyntaxException("'result' is expected to be an object.");

        String group = JSONUtils.getString(json, "group", "");
        Ingredient ingredient = Ingredient.deserialize(json.get("ingredient"));
        ItemStack itemStackResult = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        float experience = JSONUtils.getFloat(json, "experience", 0.0F);
        int dryingTime = JSONUtils.getInt(json, "dryingTime", 1200);

        return this.FACTORY.create(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Nullable
    @Override
    public T read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String group = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack itemStackResult = buffer.readItemStack();
        float experience = buffer.readFloat();
        int dryingTime = buffer.readVarInt();
        return this.FACTORY.create(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.crushingTime);
    }

    public interface IFactory<T extends DryerRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime);
    }
}
