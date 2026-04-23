package org.dawnoftime.dawnoftime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.dawnoftime.dawnoftime.command.PatronRewardCommand;
import org.dawnoftime.dawnoftime.patreon.PatronSyncHandler;

import java.nio.file.Path;

public class DoTBFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        DoTBCommon.init();
        RegistryImpls.init();

        // Server-side command registration
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            PatronRewardCommand.register(dispatcher)
        );

        // Refresh patron list from GitHub each time a player connects (server-side, async)
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Path cacheFile = FabricLoader.getInstance().getConfigDir().resolve("dawnoftimebuilder/patrons_cache.json");
            PatronSyncHandler.onPlayerLogin(cacheFile);
        });
    }

    @Override
    public void onInitializeClient() {
        RegistryImpls.initClient();
        RenderLayers.init();
    }
}
