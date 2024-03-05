package org.dawnoftimebuilder.mixin.client;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.HandlerClient;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class CreativeInventoryHandler {
    public static void onCreativeTabChange(CreativeModeInventoryScreen screen, CreativeModeTab tab) {
        HandlerClient.onCreativeTabChange(screen, tab);
    }
}
