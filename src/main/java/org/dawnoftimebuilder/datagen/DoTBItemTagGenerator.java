package org.dawnoftimebuilder.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBItemTagGenerator extends ItemTagsProvider {
    public DoTBItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> lookupBlock, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, lookupBlock, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {

    }
}