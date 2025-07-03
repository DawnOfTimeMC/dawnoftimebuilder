package org.dawnoftime.dawnoftime.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;
import org.dawnoftime.dawnoftime.blockentity.DryerBlockEntity;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class DoTBBlockEntitiesRegistry {
    public static DoTBBlockEntitiesRegistry INSTANCE;
//    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public final Supplier<BlockEntityType<DryerBlockEntity>> DRYER = register("dryer",
            DryerBlockEntity::new, () -> new Block[] { DoTBBlocksRegistry.INSTANCE.BAMBOO_DRYING_TRAY.get() });
    public final Supplier<BlockEntityType<DisplayerBlockEntity>> DISPLAYER = register("displayer",
            DisplayerBlockEntity::new, () -> new Block[] { DoTBBlocksRegistry.INSTANCE.SPRUCE_LOW_TABLE.get(), DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TABLE.get() });

    public abstract <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BiFunction<BlockPos, BlockState, T> factoryIn, Supplier<Block[]> validBlocksSupplier);

//    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> reg(String name, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier) {
//        return TILE_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
//    }
}