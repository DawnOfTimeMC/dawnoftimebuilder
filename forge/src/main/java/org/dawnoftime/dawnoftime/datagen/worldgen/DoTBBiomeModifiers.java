package org.dawnoftime.dawnoftime.datagen.worldgen;

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
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.registry.DoTBFeaturesRegistry;

public class DoTBBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_IVY = registerKey("add_ivy");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_IVY,
            new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.get(Biomes.FLOWER_FOREST).get(),
                        biomes.get(Biomes.BIRCH_FOREST).get(),
                        biomes.get(Biomes.OLD_GROWTH_BIRCH_FOREST).get()),
                HolderSet.direct(placedFeatures.getOrThrow(DoTBFeaturesRegistry.IVY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(DoTBCommon.MOD_ID, name));
    }
}
