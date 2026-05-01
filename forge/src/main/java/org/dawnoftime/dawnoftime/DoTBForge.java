package org.dawnoftime.dawnoftime;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.dawnoftime.dawnoftime.command.PatronRewardCommand;
import org.dawnoftime.dawnoftime.datagen.DataGenerators;
import org.dawnoftime.dawnoftime.network.ForgePatronNetwork;
import org.dawnoftime.dawnoftime.patreon.PatronSyncHandler;

import java.nio.file.Path;

@Mod(DoTBCommon.MOD_ID)
public class DoTBForge {
    public DoTBForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DoTBCommon.init();

        RegistryImpls.init(modEventBus);

        ForgePatronNetwork.init();

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.register(DoTBForgeClient.class);
        }

        modEventBus.register(DataGenerators.class);

        MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent event) ->
            PatronRewardCommand.register(event.getDispatcher())
        );

        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            Path cacheFile = FMLPaths.CONFIGDIR.get().resolve("dawnoftimebuilder/patrons_cache.json");
            PatronSyncHandler.onPlayerLogin(cacheFile, (ServerPlayer) event.getEntity());
        });
    }

}
