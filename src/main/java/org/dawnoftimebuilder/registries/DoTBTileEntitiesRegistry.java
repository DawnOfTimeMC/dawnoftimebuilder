package org.dawnoftimebuilder.registries;

import net.minecraft.tileentity.TileEntityType;

import net.minecraft.tileentity.TileEntity;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.BAMBOO_DRYING_TRAY;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.SPRUCE_LOW_TABLE;

public class DoTBTileEntitiesRegistry {

	public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();

	public static final TileEntityType<DryerTileEntity> DRYER_TE = buildType("dryer", TileEntityType.Builder.create(DryerTileEntity::new, BAMBOO_DRYING_TRAY));
	public static final TileEntityType<DisplayerTileEntity> DISPLAYER_TE = buildType("displayer", TileEntityType.Builder.create(DisplayerTileEntity::new, SPRUCE_LOW_TABLE));
	//public static final TileEntityType<DoTBTileEntityStove> STOVE_TE = buildType("stove", TileEntityType.Builder.create(DoTBTileEntityStove::new, ));

	private static <T extends TileEntity> TileEntityType<T> buildType(String id, TileEntityType.Builder<T> builder) {
		TileEntityType<T> type = builder.build(null);
		type.setRegistryName(MOD_ID, id);
		TILE_ENTITY_TYPES.add(type);
		return type;
	}
}