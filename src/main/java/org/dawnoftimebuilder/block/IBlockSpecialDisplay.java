package org.dawnoftimebuilder.block;

public interface IBlockSpecialDisplay {
    default float getDisplayScale() {
        return 1.0F;
    }

    default boolean emitsLight() {
        return false;
    }
}
