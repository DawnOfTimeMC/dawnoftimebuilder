package org.dawnoftimebuilder.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.datagen.worldgen.DoTBBiomeModifiers;
import org.dawnoftimebuilder.datagen.worldgen.DoTBConfiguredFeatures;
import org.dawnoftimebuilder.datagen.worldgen.DoTBPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DoTBWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, DoTBConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, DoTBPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DoTBBiomeModifiers::bootstrap);

    public DoTBWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DawnOfTimeBuilder.MOD_ID));
    }
}
