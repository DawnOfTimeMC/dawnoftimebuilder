package org.dawnoftimebuilder.mixin;

import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.item.ItemGroup;
import org.apache.logging.log4j.LogManager;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(CreativeScreen.class)
public class CreativeInventoryMixin {
    @Inject(method = "selectTab", at = @At("TAIL"), remap = false)
    private void selectTab(ItemGroup tab, CallbackInfo ci) {
        DawnOfTimeBuilder.get().events.onCreativeTabChange((CreativeScreen) (Object)this, tab);
        if (tab != null) LogManager.getLogger().info("Player selected tab " + tab.getDisplayName().getString());
    }
}
