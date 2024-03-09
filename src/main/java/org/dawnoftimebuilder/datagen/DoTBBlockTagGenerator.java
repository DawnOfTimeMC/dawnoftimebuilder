package org.dawnoftimebuilder.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.*;

public class DoTBBlockTagGenerator extends BlockTagsProvider {
    public DoTBBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DawnOfTimeBuilder.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider pProvider) {
        for(TagKey<Block> tag : blockTagsMap.keySet()){
            blockTagsMap.get(tag).forEach(block -> this.tag(tag).add(block.get()));
        }
        blockTagsMap.clear();
    }
}
