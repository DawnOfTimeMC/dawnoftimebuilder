package org.dawnoftime.dawnoftime.platform.services;

import net.minecraft.server.level.ServerPlayer;

public interface IPlatformHelper {

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    void sendPatronTierToPlayer(ServerPlayer player, int tier);
}