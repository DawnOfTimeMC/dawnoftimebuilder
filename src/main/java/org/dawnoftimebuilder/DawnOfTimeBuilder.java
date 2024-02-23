package org.dawnoftimebuilder;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.registry.*;

import java.util.Map;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
    public static final String MOD_ID = "dawnoftimebuilder";

    public DawnOfTimeBuilder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        DoTBItemsRegistry.register(modEventBus);
        DoTBBlocksRegistry.register(modEventBus);
        DoTBEntitiesRegistry.register(modEventBus);
        DoTBCreativeModeTabsRegistry.register(modEventBus);

        modEventBus.addListener(HandlerClient::clientSetup);

        modEventBus.addListener(HandlerCommon::setDotTab);
        modEventBus.addListener(HandlerClient::entityRenderers);

        modEventBus.addListener(this::createCreativeTab);
    }

    public void createCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == DoTBCreativeModeTabsRegistry.DOT_TAB.get()) {
            ForgeRegistries.ITEMS.getEntries().stream().filter(entry ->
                            entry.getKey().location().getNamespace().equalsIgnoreCase(MOD_ID) &&
                            !(entry.getValue() instanceof IconItem))
                    .map(Map.Entry::getValue)
                    .forEachOrdered(event::accept);
        }
    }
}
