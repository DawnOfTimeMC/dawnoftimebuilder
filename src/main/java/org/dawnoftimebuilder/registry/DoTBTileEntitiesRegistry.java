package org.dawnoftimebuilder.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.blockentity.DisplayerTileEntity;
import org.dawnoftimebuilder.blockentity.DryerTileEntity;

import java.util.function.Supplier;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBTileEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<BlockEntityType<DryerTileEntity>> DRYER_TE = null;/* = reg("dryer",
            DryerTileEntity::new, () -> new Block[]{DoTBBlocksRegistry.BAMBOO_DRYING_TRAY.get()});*/
    public static final RegistryObject<BlockEntityType<DisplayerTileEntity>> DISPLAYER_TE = null;/* = reg("displayer",
            DisplayerTileEntity::new,  () -> new Block[]{DoTBBlocksRegistry.SPRUCE_LOW_TABLE.get(), DoTBBlocksRegistry.WAXED_OAK_TABLE.get()});*/

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> reg(String name, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier){
        return TILE_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
    }
}