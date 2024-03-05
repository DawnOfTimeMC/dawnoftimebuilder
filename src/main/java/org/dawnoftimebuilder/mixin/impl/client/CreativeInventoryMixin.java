package org.dawnoftimebuilder.mixin.impl.client;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.mixin.client.CreativeInventoryHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(CreativeModeInventoryScreen.class)
@OnlyIn(Dist.CLIENT)
public abstract class CreativeInventoryMixin {
    @Inject(method = "selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V", at = @At(value = "RETURN"))
    public void selectTab(CreativeModeTab pTab, CallbackInfo ci) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        CreativeInventoryHandler.onCreativeTabChange(screen, pTab);
    }
}
