package org.dawnoftimebuilder.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.dawnoftimebuilder.DoTBCommon;

public abstract class DoTBTags {
    public static DoTBTags INSTANCE;
    //Item tags
    public final TagKey<Item> LIGHTERS = registerItem(ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, "lighters"));
    //Block tags
    public final TagKey<Block> COVERED_BLOCKS = registerBlock(ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, "covered_blocks"));
    public final TagKey<Block> GRAVEL = registerBlock(ResourceLocation.fromNamespaceAndPath("c", "gravel"));


    public abstract TagKey<Block> registerBlock(ResourceLocation id);
    public abstract TagKey<Item> registerItem(ResourceLocation id);
}
