package org.dawnoftimebuilder;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(DoTBCommon.MOD_ID)
public class DoTBNeoForge {
    public static final ConfigClassHandler<DoTBConfig> HANDLER = ConfigClassHandler.createBuilder(DoTBConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("dawnoftimebuilder-config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public DoTBNeoForge(IEventBus modEventBus) {
        HANDLER.load();
        DoTBCommon.init();

        RegistryImpls.init(modEventBus);

        //modEventBus.register(DataGenerators.class);
    }
}
