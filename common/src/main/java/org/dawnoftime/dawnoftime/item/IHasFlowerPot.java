package org.dawnoftime.dawnoftime.item;

import org.dawnoftime.dawnoftime.block.templates.FlowerPotBlockDoT;

import javax.annotation.Nullable;

public interface IHasFlowerPot {
    /**
     * @return the instance of the PotBlock. Null if there is no PotBlock associated.
     */
    @Nullable
    FlowerPotBlockDoT getPotBlock();

    /**
     * Used in registration to bind this item to its PotBlock.
     *
     * @param pot to be associated with this item.
     */
    void setPotBlock(@Nullable FlowerPotBlockDoT pot);
}
