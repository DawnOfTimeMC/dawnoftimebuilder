package org.dawnoftimebuilder.mixin.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockAccessor {

    @Invoker("getGrowthSpeed")
    static float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }
}
