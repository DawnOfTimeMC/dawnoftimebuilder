package org.dawnoftime.dawnoftime.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class DoTBBlockEntitiesRegistry {
    public static DoTBBlockEntitiesRegistry INSTANCE;

    public final Supplier<BlockEntityType<DisplayerBlockEntity>> DISPLAYER = register("displayer",
            DisplayerBlockEntity::new, () -> new Block[] { DoTBBlocksRegistry.INSTANCE.SPRUCE_LOW_TABLE.get(), DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TABLE.get() });

    public abstract <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BiFunction<BlockPos, BlockState, T> factoryIn, Supplier<Block[]> validBlocksSupplier);

}