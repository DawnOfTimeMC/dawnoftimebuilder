package org.dawnoftimebuilder.registry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import java.util.function.Supplier;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBTileEntitiesRegistry {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);

	public static final RegistryObject<TileEntityType<DryerTileEntity>> DRYER_TE = reg("dryer", DryerTileEntity::new, () -> new Block[]{DoTBBlocksRegistry.BAMBOO_DRYING_TRAY.get()});
	public static final RegistryObject<TileEntityType<DisplayerTileEntity>> DISPLAYER_TE = reg("displayer", DisplayerTileEntity::new,  () -> new Block[]{DoTBBlocksRegistry.SPRUCE_LOW_TABLE.get(), DoTBBlocksRegistry.WAXED_OAK_TABLE.get()});

	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> reg(String name, Supplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier){
		return TILE_ENTITY_TYPES.register(name, () -> TileEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
	}
}