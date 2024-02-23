package org.dawnoftimebuilder.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.DawnOfTimeBuilder;

public class DoTBCreativeModeTabsRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DawnOfTimeBuilder.MOD_ID);
    public static RegistryObject<CreativeModeTab> DOT_TAB = CREATIVE_MODE_TABS.register("dot_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(DoTBItemsRegistry.GENERAL.get())).title(Component.translatable("item_group." + DawnOfTimeBuilder.MOD_ID + ".dottab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
