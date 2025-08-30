package org.dawnoftime.dawnoftime.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public interface IPlatformHelper {

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    void openScreenHandler(Player playerEntity, MenuProvider provider, BiConsumer<ServerPlayer, FriendlyByteBuf> dataWriter);
}