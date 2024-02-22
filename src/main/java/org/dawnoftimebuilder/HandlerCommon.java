package org.dawnoftimebuilder;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.registry.DoTBItemsRegistry;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DawnOfTimeBuilder.MOD_ID);
    public static RegistryObject<CreativeModeTab> DOT_TAB = CREATIVE_MODE_TABS.register("dot_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(DoTBItemsRegistry.GENERAL.get()))
                    .title(Component.translatable("item_group." + DawnOfTimeBuilder.MOD_ID + ".dottab")).withSearchBar().build());
    public static boolean DOT_SELECTED = false;

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    @SubscribeEvent
    public static void setDotTab(BuildCreativeModeTabContentsEvent event) {
        DOT_SELECTED = event.getTab() == DOT_TAB.get();
    }

    public static boolean isDotSelected() {
        return DOT_SELECTED;
    }
}
