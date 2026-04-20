package org.dawnoftime.dawnoftime;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.dawnoftime.dawnoftime.command.PatronRewardCommand;
import org.dawnoftime.dawnoftime.datagen.DataGenerators;

@Mod(DoTBCommon.MOD_ID)
public class DoTBForge {
    public DoTBForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DoTBCommon.init();

        RegistryImpls.init(modEventBus);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.register(DoTBForgeClient.class);
        }

        modEventBus.register(DataGenerators.class);

        // Commands fire on the game bus (not the mod bus)
        MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent event) ->
            PatronRewardCommand.register(event.getDispatcher())
        );
    }

}