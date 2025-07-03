package org.dawnoftime.dawnoftime.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DryerRecipeSerializer implements RecipeSerializer<DryerRecipe> {
    @Override
    @Nonnull
    public DryerRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {

        if(!json.has("ingredient"))
            throw new JsonSyntaxException("The object 'ingredient' is missing.");
        if(!json.get("ingredient").isJsonObject())
            throw new JsonSyntaxException("'ingredient' is expected to be an object.");
        if(!json.has("result"))
            throw new JsonSyntaxException("The object 'result' is missing.");
        if(!json.get("result").isJsonObject())
            throw new JsonSyntaxException("'result' is expected to be an object.");

        String group = GsonHelper.getAsString(json, "group", "");
        Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
        ingredient.getItems()[0].setCount(GsonHelper.getAsInt(GsonHelper.getAsJsonObject(json, "ingredient"), "count", 1));
        ItemStack itemStackResult = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")).getDefaultInstance();
        float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
        int dryingTime = GsonHelper.getAsInt(json, "dryingTime", 1200);

        return new DryerRecipe(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Override
    public @NotNull DryerRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        String group = buffer.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack itemStackResult = buffer.readItem();
        float experience = buffer.readFloat();
        int dryingTime = buffer.readVarInt();
        return new DryerRecipe(recipeId, group, ingredient, itemStackResult, experience, dryingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, DryerRecipe recipe) {
        buffer.writeUtf(recipe.group);
        recipe.ingredient.toNetwork(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.dryingTime);
    }
}
