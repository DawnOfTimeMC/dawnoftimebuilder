package org.dawnoftimebuilder.block;

import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public interface ICustomBlockItem {
    /**
     * @return The custom ItemBlock that will be registered for this block, null if this block has no ItemBlock.
     */
    @Nullable
    Item getCustomBlockItem();

    /**
     * @return The name used to register this customItem. If return 'null', the name used in the registry is the block's name.
     */
    @Nullable
    default String getCustomItemName() {
        return null;
    }
}
