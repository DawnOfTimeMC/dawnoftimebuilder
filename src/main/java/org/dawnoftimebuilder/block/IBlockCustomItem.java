package org.dawnoftimebuilder.block;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IBlockCustomItem {

    /**
     * @return The custom ItemBlock that will be registered for this block, null if this block has no ItemBlock.
     */
    @Nullable
    Item getCustomItemBlock();

    /**
     * @return The name used to register this customItem. If return 'null', the name used in the registry is the block's name.
     */
    @Nullable
    default String getCustomItemName(){
        return null;
    }
}
