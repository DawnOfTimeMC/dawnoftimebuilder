package org.dawnoftime.dawnoftime.platform;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.dawnoftime.dawnoftime.patreon.PatronSyncHandler;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public void sendPatronTierToPlayer(ServerPlayer player, int tier) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(tier);
        ServerPlayNetworking.send(player, PatronSyncHandler.PATRON_TIER_CHANNEL, buf);
    }
}
