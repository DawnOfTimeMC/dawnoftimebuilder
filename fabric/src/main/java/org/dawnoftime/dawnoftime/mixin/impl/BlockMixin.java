package org.dawnoftime.dawnoftime.mixin.impl;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void preventFaceCulling(BlockState currentFace, BlockState neighboringFace, Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (currentFace.is(DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_FLOOR.get()) || currentFace.is(DoTBBlocksRegistry.INSTANCE.TATAMI_FLOOR.get()))
            cir.setReturnValue(true);
    }
}
