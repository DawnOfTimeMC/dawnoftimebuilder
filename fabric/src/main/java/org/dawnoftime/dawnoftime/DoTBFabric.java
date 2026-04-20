package org.dawnoftime.dawnoftime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.dawnoftime.dawnoftime.command.PatronRewardCommand;

public class DoTBFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        DoTBCommon.init();
        RegistryImpls.init();

        // Server-side command registration
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            PatronRewardCommand.register(dispatcher)
        );
    }

    @Override
    public void onInitializeClient() {
        RegistryImpls.initClient();
        RenderLayers.init();
    }
}
