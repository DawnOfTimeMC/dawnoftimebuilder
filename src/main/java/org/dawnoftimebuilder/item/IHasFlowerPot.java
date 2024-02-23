package org.dawnoftimebuilder.item;

import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;

import javax.annotation.Nullable;

public interface IHasFlowerPot {
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
