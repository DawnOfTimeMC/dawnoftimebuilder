package org.dawnoftimebuilder;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;
import org.dawnoftimebuilder.registry.DoTBItemsRegistry;

import java.util.Map;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
    public static final String MOD_ID = "dawnoftimebuilder";

    public DawnOfTimeBuilder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        DoTBItemsRegistry.register(modEventBus);
        DoTBBlocksRegistry.register(modEventBus);
        DoTBEntitiesRegistry.register(modEventBus);

        modEventBus.addListener(HandlerCommon::commonSetup);
        modEventBus.addListener(HandlerClient::clientSetup);

        modEventBus.addListener(HandlerCommon::registerCreativeModeTabs);
        modEventBus.addListener(HandlerCommon::setDotTab);
        modEventBus.addListener(HandlerClient::entityRenderers);

        modEventBus.addListener(this::createCreativeTab);
    }

    public void createCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == HandlerCommon.DOT_TAB) {
            ForgeRegistries.ITEMS.getEntries().stream().filter(entry -> entry.getKey().location().getNamespace()
                    .equalsIgnoreCase(MOD_ID)).map(Map.Entry::getValue)
                    .forEachOrdered(event::accept);
        }
    }
}
