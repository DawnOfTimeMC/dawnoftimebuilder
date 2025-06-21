package org.dawnoftime.dawnoftime.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.dawnoftime.dawnoftime.DoTBCommon;

import java.util.function.Supplier;

public abstract class DoTBCreativeModeTabsRegistry {
    public static DoTBCreativeModeTabsRegistry INSTANCE;
    public Supplier<CreativeModeTab> DOT_TAB = register("dot_tab", () -> new ItemStack(DoTBItemsRegistry.INSTANCE.ANCIENTARCHI.get()), Component.translatable("itemGroup." + DoTBCommon.MOD_ID + ".dottab"));
    public abstract <T extends CreativeModeTab> Supplier<CreativeModeTab> register(final String name, final Supplier<ItemStack> iconSupplier, final Component title);
}
