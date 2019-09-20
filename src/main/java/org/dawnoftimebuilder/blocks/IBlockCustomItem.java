package org.dawnoftimebuilder.blocks;

import net.minecraft.item.Item;

public interface IBlockCustomItem {

    /**
     * Implement this on any Block that has a dryer ItemBlock.
     * This should take care of registry naming, if possible.
     * @return the item, for this blocks item.
     */
    Item getCustomItemBlock();

}
