package org.dawnoftimebuilder.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class DryerRecipeSerializer implements RecipeSerializer<DryerRecipe> {
    private final Factory<DryerRecipe> factory;
    private final MapCodec<DryerRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, DryerRecipe> streamCodec;

    public DryerRecipeSerializer(Factory<DryerRecipe> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(o -> o.group),
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(o -> o.ingredient),
                        ItemStack.CODEC.fieldOf("result").forGetter(o -> o.result),
                        Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(o -> o.experience),
                        Codec.INT.optionalFieldOf("dryingTime", 1200).forGetter(o -> o.dryingTime)
                ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public @NotNull DryerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        String group = buffer.readUtf(32767);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack itemStackResult = ItemStack.STREAM_CODEC.decode(buffer);
        float experience = buffer.readFloat();
        int dryingTime = buffer.readVarInt();
        return factory.create(group, ingredient, itemStackResult, experience, dryingTime);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, DryerRecipe recipe) {
        buffer.writeUtf(recipe.group);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.dryingTime);
    }

    @Override
    public @NotNull MapCodec<DryerRecipe> codec() {
        return codec;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, DryerRecipe> streamCodec() {
        return streamCodec;
    }

    @FunctionalInterface
    public interface Factory<T extends DryerRecipe> {
        T create(String group, Ingredient ingredient, ItemStack result, float experience, int dryingTime);
    }
}
