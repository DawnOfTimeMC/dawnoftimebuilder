package org.dawnoftime.dawnoftime.item;

import org.dawnoftime.dawnoftime.block.templates.FlowerPotBlockAA;

import javax.annotation.Nullable;

public interface IHasFlowerPot {
    /**
     * @return the instance of the PotBlock. Null if there is no PotBlock associated.
     */
    @Nullable
    FlowerPotBlockAA getPotBlock();

    /**
     * Used in registration to bind this item to its PotBlock.
     *
     * @param pot to be associated with this item.
     */
    void setPotBlock(@Nullable FlowerPotBlockAA pot);
}
