package org.dawnoftimebuilder.mixin.impl;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

    @Shadow @Final private BlockEntityType<?> type;

    @Inject(method = "isValidBlockState", at = @At("HEAD"), cancellable = true)
    private void dawnoftimebuilder$isValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.type == BlockEntityType.BED && state.is(DoTBBlocksRegistry.INSTANCE.LIGHT_GRAY_FUTON.get()))
            cir.setReturnValue(true);
    }
}
