package org.dawnoftime.dawnoftime.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class DoTBItemTagGenerator extends ItemTagsProvider {
    public DoTBItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MOD_ID);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        // TODO: Implement Item Tags
    }
}