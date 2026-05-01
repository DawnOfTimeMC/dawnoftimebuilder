package org.dawnoftime.dawnoftime.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.dawnoftime.dawnoftime.client.patreon.ClientPatronState;
import org.dawnoftime.dawnoftime.patreon.PatronSyncHandler;

import java.util.Optional;

public class ForgePatronNetwork {

    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        PatronSyncHandler.PATRON_TIER_CHANNEL,
        () -> "1", "1"::equals, "1"::equals
    );

    public static void init() {
        CHANNEL.registerMessage(
            0,
            PatronTierMsg.class,
            (msg, buf) -> buf.writeInt(msg.tier),
            buf -> new PatronTierMsg(buf.readInt()),
            (msg, ctxSup) -> {
                ctxSup.get().enqueueWork(() -> ClientPatronState.playerTier = msg.tier);
                ctxSup.get().setPacketHandled(true);
            },
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    public static void sendToPlayer(ServerPlayer player, int tier) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PatronTierMsg(tier));
    }

    record PatronTierMsg(int tier) {}
}
