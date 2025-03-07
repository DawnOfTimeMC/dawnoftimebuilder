package org.dawnoftimebuilder.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.DoTBNeoForge;
import org.dawnoftimebuilder.mixin.neoforge.CropBlockAccessor;
import org.dawnoftimebuilder.platform.services.IPlatformHelper;

import java.util.function.Function;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
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
        return DoTBNeoForge.HANDLER.instance();
    }

    @Override
    public <D> void openScreenHandler(Player playerEntity, MenuProvider provider, Function<ServerPlayer, D> dataWriter) {
        playerEntity.openMenu(provider, ((BlockEntity) provider).getBlockPos());
    }

    @Override
    public float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        if (block instanceof CropBlock)
            return CropBlockAccessor.getGrowthSpeed(block.defaultBlockState(), level, pos);

        return 0;
    }
}