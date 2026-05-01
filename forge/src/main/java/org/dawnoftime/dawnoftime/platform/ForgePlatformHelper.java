package org.dawnoftime.dawnoftime.platform;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import org.dawnoftime.dawnoftime.network.ForgePatronNetwork;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public void sendPatronTierToPlayer(ServerPlayer player, int tier) {
        ForgePatronNetwork.sendToPlayer(player, tier);
    }
}
