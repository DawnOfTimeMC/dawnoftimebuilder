package org.dawnoftimebuilder.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.recipe.DryerRecipeSerializer;

import java.util.function.Supplier;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBRecipeSerializersRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static final RegistryObject<DryerRecipeSerializer> DRYER = reg("dryer", DryerRecipeSerializer::new);

    private static <T extends RecipeSerializer<? extends Recipe<?>>> RegistryObject<T> reg(String name, Supplier<T> t) {
        return RECIPE_SERIALIZERS.register(name, t);
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}