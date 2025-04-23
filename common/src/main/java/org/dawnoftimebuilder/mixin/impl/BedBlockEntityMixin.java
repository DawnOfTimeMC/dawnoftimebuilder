package org.dawnoftimebuilder.mixin.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BedBlockEntity.class)
public class BedBlockEntityMixin extends BlockEntity {

    public BedBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public boolean isValidBlockState(@NotNull BlockState state) {
        return super.isValidBlockState(state) || (DoTBBlocksRegistry.INSTANCE != null && state.is(DoTBBlocksRegistry.INSTANCE.LIGHT_GRAY_FUTON.get()));
    }
}
