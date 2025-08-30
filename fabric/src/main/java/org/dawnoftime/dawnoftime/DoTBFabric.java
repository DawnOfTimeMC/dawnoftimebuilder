package org.dawnoftime.dawnoftime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class DoTBFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        DoTBCommon.init();
        RegistryImpls.init();
    }

    @Override
    public void onInitializeClient() {
        RegistryImpls.initClient();
        RenderLayers.init();
    }
}
