package org.dawnoftime.dawnoftime.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkHooks;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;

import java.util.function.BiConsumer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public void openScreenHandler(Player playerEntity, MenuProvider provider, BiConsumer<ServerPlayer, FriendlyByteBuf> dataWriter) {
        NetworkHooks.openScreen((ServerPlayer) playerEntity, provider, ((BlockEntity) provider).getBlockPos());
    }
}