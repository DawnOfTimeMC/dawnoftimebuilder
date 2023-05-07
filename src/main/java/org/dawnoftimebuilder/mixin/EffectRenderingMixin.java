package org.dawnoftimebuilder.mixin;

import net.minecraft.client.gui.DisplayEffectsScreen;
import org.apache.logging.log4j.LogManager;
import org.dawnoftimebuilder.util.filters.Hooks;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
@Mixin(DisplayEffectsScreen.class)
public class EffectRenderingMixin {

    @Redirect(method = "renderEffects", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/DisplayEffectsScreen;leftPos:I", opcode = Opcodes.GETFIELD), remap = false)
    private int renderEffects(DisplayEffectsScreen screen) {
        int offset = Hooks.getPotionEffectOffset(screen);
        LogManager.getLogger().info("Mixin: renderEffects: returning offset " + offset);
        return offset;
    }

    @ModifyConstant(method = "checkEffectRendering", constant = @Constant(intValue = 160), remap = false)
    private int checkEffectRendering(int constant) {
        int offset = Hooks.getEffectsGuiOffset((DisplayEffectsScreen)(Object) this);
        LogManager.getLogger().info("Mixin: checkEffectRendering: returning offset " + offset);
        return offset;
    }
}
