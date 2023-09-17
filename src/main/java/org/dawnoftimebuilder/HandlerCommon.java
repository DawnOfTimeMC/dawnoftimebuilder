package org.dawnoftimebuilder;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon {

    public static CreativeModeTab DOT_TAB;

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        DOT_TAB = event.registerCreativeModeTab(new ResourceLocation(DawnOfTimeBuilder.MOD_ID, "dot_tab"),
                builder -> builder.icon(() -> new ItemStack(Items.ANDESITE))
                        .title(Component.translatable("item_group." + DawnOfTimeBuilder.MOD_ID+ ".dottab")));
    }
}
