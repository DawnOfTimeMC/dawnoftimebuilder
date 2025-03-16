package org.dawnoftimebuilder.platform;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.DoTBFabric;
import org.dawnoftimebuilder.mixin.fabric.CropBlockAccessor;
import org.dawnoftimebuilder.platform.services.IPlatformHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public DoTBConfig getConfig() {
        return DoTBFabric.HANDLER.instance();
    }

    @Override
    public <D> void openScreenHandler(Player playerEntity, MenuProvider provider, Function<ServerPlayer, D> dataWriter) {
        playerEntity.openMenu(new ExtendedScreenHandlerFactory<D>() {

            @Override
            public D getScreenOpeningData(ServerPlayer player) {
                return dataWriter.apply(player);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return provider.createMenu(i, inventory, player);
            }
        });
    }

    @Override
    public float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        if (block instanceof CropBlock)
            return CropBlockAccessor.getGrowthSpeed(block, level, pos);

        return 0;
    }
}
