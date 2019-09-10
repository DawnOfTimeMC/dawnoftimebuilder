package org.dawnoftimebuilder.crafts;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class RecipeBase implements IRecipe{

	private ResourceLocation location;
	
	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		this.location = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return this.location;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}
	
	protected IRecipe setRegistryName(String name){
		return this.setRegistryName(new ResourceLocation(MOD_ID, name));
	}
	
}
