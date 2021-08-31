package org.dawnoftimebuilder.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public interface IItemCanBeDried {

	//TODO delete this crap
	/**
	 * Get the resource location to load the .json model used in dryers
	 * @param itemName Name of the Item from Item.getRegistryName().getPath()
	 * @return ResourceLocation of the model (folder "dryer")
	 */
	static ResourceLocation getResourceLocation(String itemName){
		return new ResourceLocation(MOD_ID, "dryer/" + itemName);
	}

	/**
	 * @return Item before being dried
	 */
	Item getItem();

	/**
	 * @return Quantity of Item needed to be dried
	 */
	int getItemQuantity();

	/**
	 * @return number of seconds needed for the item to be dried.
	 * The drying time is randomly set for a duration of 80-120 % of this value
	 */
	int getDryingTime();

	/**
	 * @return Item to get once dried
	 */
	Item getDriedItem();

	/**
	 * @return Quantity of Item to get once dried
	 */
	int getDriedItemQuantity();
}
