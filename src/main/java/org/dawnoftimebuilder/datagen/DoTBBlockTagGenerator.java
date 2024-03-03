package org.dawnoftimebuilder.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.*;

public class DoTBBlockTagGenerator extends BlockTagsProvider {
    public DoTBBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DawnOfTimeBuilder.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        RED_PLASTERED_STONE.get(),
                        RED_PLASTERED_STONE_EDGE.get(),
                        RED_PLASTERED_STONE_FRIEZE.get(),
                        RED_PLASTERED_STONE_PLATE.get(),
                        RED_PLASTERED_STONE_SLAB.get(),
                        RED_PLASTERED_STONE_STAIRS.get(),
                        COBBLED_LIMESTONE.get(),
                        LIMESTONE_BALUSTER.get(),
                        LIMESTONE_BRICKS.get(),
                        LIMESTONE_BRICKS_EDGE.get(),
                        LIMESTONE_BRICKS_PLATE.get(),
                        LIMESTONE_CHIMNEY.get(),
                        LIMESTONE_BRICKS_SLAB.get(),
                        LIMESTONE_BRICKS_STAIRS.get(),
                        LIMESTONE_BRICKS_WALL.get(),
                        LIMESTONE_FIREPLACE.get(),
                        LIMESTONE_GARGOYLE.get(),
                        LIMESTONE_SIDED_COLUMN.get(),
                        CHARRED_SPRUCE_ROOF_SUPPORT.get(),
                        GRAY_ROOF_TILES.get(),
                        GRAY_ROOF_TILES_EDGE.get(),
                        GRAY_ROOF_TILES_PLATE.get(),
                        GRAY_ROOF_TILES_SLAB.get(),
                        GRAY_ROOF_TILES_WALL.get(),
                        GRAY_ROOF_TILES_STAIRS.get(),
                        FLAT_ROOF_TILES.get(),
                        FLAT_ROOF_TILES_EDGE.get(),
                        FLAT_ROOF_TILES_PLATE.get(),
                        FLAT_ROOF_TILES_SLAB.get(),
                        FLAT_ROOF_TILES_WALL.get(),
                        FLAT_ROOF_TILES_STAIRS.get(),
                        OCHRE_ROOF_TILES.get(),
                        OCHRE_ROOF_TILES_EDGE.get(),
                        OCHRE_ROOF_TILES_PLATE.get(),
                        OCHRE_ROOF_TILES_SLAB.get(),
                        OCHRE_ROOF_TILES_WALL.get(),
                        OCHRE_ROOF_TILES_STAIRS.get(),
                        STONE_LANTERN.get()
                );
    }
}
