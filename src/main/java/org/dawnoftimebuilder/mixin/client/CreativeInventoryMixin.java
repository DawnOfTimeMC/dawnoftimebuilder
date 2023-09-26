package org.dawnoftimebuilder.mixin.client;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.logging.log4j.LogManager;
import org.dawnoftimebuilder.HandlerClient;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeInventoryMixin {

    @Inject(method = "selectTab", at = @At("TAIL"))
    private void selectTab(CreativeModeTab tab, CallbackInfo ci) {
        HandlerClient.events.onCreativeTabChange((CreativeModeInventoryScreen) (Object)this, tab);
        if (tab != null) LogManager.getLogger().info("Player selected tab " + tab.getDisplayName().getString());
    }
}
