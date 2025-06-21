package org.dawnoftime.dawnoftime;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.dawnoftime.dawnoftime.loot.DoTBFabricLootModifier;

public class DoTBFabric implements ModInitializer, ClientModInitializer {
    public static final ConfigClassHandler<DoTBConfig> HANDLER = ConfigClassHandler.createBuilder(DoTBConfig.class)
            .id(new ResourceLocation(DoTBCommon.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("dawnoftimebuilder-config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @Override
    public void onInitialize() {
        HANDLER.load();

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
