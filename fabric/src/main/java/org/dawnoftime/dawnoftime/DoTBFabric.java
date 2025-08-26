package org.dawnoftime.dawnoftime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.dawnoftime.dawnoftime.loot.DoTBFabricLootModifier;

public class DoTBFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {

        DoTBCommon.init();
        RegistryImpls.init();
        BiomeModifiers.init();

        DoTBFabricLootModifier.modifyLootTables();
    }

    @Override
    public void onInitializeClient() {
        RegistryImpls.initClient();
        RenderLayers.init();
    }
}
