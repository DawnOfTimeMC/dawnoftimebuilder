package org.dawnoftimebuilder.block;

import net.minecraft.item.Item;

import javax.annotation.Nullable;

public interface IBlockCustomItem {

    /**
     * @return The custom ItemBlock that will be registered for this block, null if this block has no ItemBlock.
     */
    @Nullable
    Item getCustomItemBlock();

}
