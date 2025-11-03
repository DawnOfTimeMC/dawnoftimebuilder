package org.dawnoftime.dawnoftime.mixin.impl;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {

    @Inject(method = "shouldRenderFace(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZLnet/minecraft/core/Direction;Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private static void preventFaceCulling(BlockAndTintGetter blockAndTintGetter, BlockPos pos, BlockState blockState, boolean checkSides, Direction direction, BlockPos pos1, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_FLOOR.get()) || blockState.is(DoTBBlocksRegistry.INSTANCE.TATAMI_FLOOR.get()))
            cir.setReturnValue(true);
    }
}
