package org.dawnoftimebuilder.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DawnOfTimeBuilder;

public class DoTBBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CAMELLIA = registerKey("add_camellia");
    public static final ResourceKey<BiomeModifier> ADD_COMMELINA = registerKey("add_commelina");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_CAMELLIA, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get(), biomes.get(Biomes.JUNGLE).get(), biomes.get(Biomes.BAMBOO_JUNGLE).get(), biomes.get(Biomes.SPARSE_JUNGLE).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.CAMELLIA_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_COMMELINA, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.SWAMP).get(), biomes.get(Biomes.MANGROVE_SWAMP).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.COMMELINA_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }
}
