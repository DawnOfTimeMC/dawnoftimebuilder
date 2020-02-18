package org.dawnoftimebuilder.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public enum DoTBTags {

	;

	private final ResourceLocation resource;

	DoTBTags(String tagID){
		this.resource = new ResourceLocation(MOD_ID, tagID);
	}

	public boolean contains(Item item){
		return ItemTags.getCollection().getOrCreate(this.resource).contains(item);
	}

	public boolean contains(Block block){
		return BlockTags.getCollection().getOrCreate(this.resource).contains(block);
	}
}
