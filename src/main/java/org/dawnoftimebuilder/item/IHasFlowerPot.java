package org.dawnoftimebuilder.item;

import net.minecraft.world.item.Item;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IHasFlowerPot {

    /**
     * Function called at registration and used to decide whether a FlowerPotBlock must be registered for this Item.
     *
     * @return true if this item has an associated PotBlock.
     */
    default boolean hasFlowerPot() {
        return true;
    }

    /**
     * Function called in registration to get the PotBlock instance associated to this block. Only called if hasFlowerPot() is true.
     *
     * @param itemInPot is this item instance.
     * @return the new instance of PotBlock.
     */
    @Nonnull
    default FlowerPotBlockDoTB makeFlowerPotInstance(Item itemInPot) {
        return new FlowerPotBlockDoTB(itemInPot);
    }

    /**
     * @return the instance of the PotBlock. Null if there is no PotBlock associated.
     */
    @Nullable
    FlowerPotBlockDoTB getPotBlock();

    /**
     * Used in registration to bind this item to its PotBlock.
     *
     * @param pot to be associated with this item.
     */
    void setPotBlock(@Nullable FlowerPotBlockDoTB pot);
}
