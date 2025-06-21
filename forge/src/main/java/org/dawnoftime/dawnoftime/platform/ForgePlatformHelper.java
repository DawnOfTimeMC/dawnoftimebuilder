package org.dawnoftime.dawnoftime.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkHooks;
import org.dawnoftime.dawnoftime.DoTBConfig;
import org.dawnoftime.dawnoftime.DoTBForge;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.BiConsumer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public DoTBConfig getConfig() {
        return DoTBForge.HANDLER.instance();
    }

    @Override
    public void openScreenHandler(Player playerEntity, MenuProvider provider, BiConsumer<ServerPlayer, FriendlyByteBuf> dataWriter) {
        NetworkHooks.openScreen((ServerPlayer) playerEntity, provider, ((BlockEntity) provider).getBlockPos());
    }
}