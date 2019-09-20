package org.dawnoftimebuilder.items;

import net.minecraft.item.Item;

public interface IItemCanBeDried {
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
