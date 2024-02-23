package org.dawnoftimebuilder;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftimebuilder.registry.DoTBCreativeModeTabsRegistry;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {
    public static boolean DOT_SELECTED = false;

    @SubscribeEvent
    public static void setDotTab(BuildCreativeModeTabContentsEvent event) {
        DOT_SELECTED = event.getTab() == DoTBCreativeModeTabsRegistry.DOT_TAB.get();
    }

    public static boolean isDotSelected() {
        return DOT_SELECTED;
    }
}
