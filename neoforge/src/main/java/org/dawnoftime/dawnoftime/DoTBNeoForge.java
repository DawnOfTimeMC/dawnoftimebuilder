package org.dawnoftime.dawnoftime;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.dawnoftime.dawnoftime.datagen.DataGenerators;

@Mod(DoTBCommon.MOD_ID)
public class DoTBNeoForge {
    public DoTBNeoForge(IEventBus modEventBus) {
        DoTBCommon.init();

        RegistryImpls.init(modEventBus);

        if (FMLEnvironment.dist.isClient())
            modEventBus.register(DoTBNeoForgeClient.class);

        modEventBus.register(DataGenerators.class);
    }

}