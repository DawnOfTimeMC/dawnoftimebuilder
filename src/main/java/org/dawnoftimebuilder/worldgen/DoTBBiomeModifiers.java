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
    public static final ResourceKey<BiomeModifier> ADD_CYPRESS = registerKey("add_cypress");
    public static final ResourceKey<BiomeModifier> ADD_RED_MAPLE = registerKey("add_red_maple");
    public static final ResourceKey<BiomeModifier> ADD_BOXWOOD_BUSH = registerKey("add_boxwood_bush");
    public static final ResourceKey<BiomeModifier> ADD_MULBERRY = registerKey("add_mulberry");
    public static final ResourceKey<BiomeModifier> ADD_RICE = registerKey("add_rice");
    public static final ResourceKey<BiomeModifier> ADD_WILD_GRAPE = registerKey("add_wild_grape");
    public static final ResourceKey<BiomeModifier> ADD_WILD_MAIZE = registerKey("add_wild_maize");
    public static final ResourceKey<BiomeModifier> ADD_GERANIUM_PINK = registerKey("add_geranium_pink");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_CAMELLIA,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.CAMELLIA_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_COMMELINA,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.SWAMP).get(), biomes.get(Biomes.MANGROVE_SWAMP).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.COMMELINA_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_CYPRESS,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.TAIGA).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.CYPRESS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_RED_MAPLE,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.TAIGA).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.RED_MAPLE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_BOXWOOD_BUSH,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.BOXWOOD_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_MULBERRY,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.TAIGA).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.MULBERRY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_RICE,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.SWAMP).get(), biomes.get(Biomes.MANGROVE_SWAMP).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.RICE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_WILD_GRAPE,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.WILD_GRAPE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_WILD_MAIZE,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.WILD_MAIZE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );

        context.register(ADD_GERANIUM_PINK,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.get(Biomes.FLOWER_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBPlacedFeatures.GERANIUM_PINK_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(DawnOfTimeBuilder.MOD_ID, name));
    }
}
