package org.dawnoftimebuilder.registries;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.BAMBOO_DRYING_TRAY;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.SPRUCE_LOW_TABLE;

public class DoTBEntitiesRegistry {

	public static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();

	public static final EntityType<ChairEntity> CHAIR_ENTITY = buildType("chair", EntityType.Builder.<ChairEntity>create((type, world) -> new ChairEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new ChairEntity(world)));
	//public static final EntityType<SilkmothEntity> SILKMOTH_ENTITY = buildType("silkmoth", EntityType.Builder.<SilkmothEntity>create((type, world) -> new SilkmothEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new SilkmothEntity(world)));
	//public static final EntityType<JapaneseDragonEntity> JAPANESE_DRAGON_ENTITY = buildType("japanese_dragon", EntityType.Builder.<JapaneseDragonEntity>create((type, world) -> new JapaneseDragonEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new JapaneseDragonEntity(world)));

	private static <T extends Entity> EntityType<T> buildType(String id, EntityType.Builder<T> builder) {
		EntityType<T> type = builder.build(id);
		type.setRegistryName(MOD_ID, id);
		ENTITY_TYPES.add(type);
		return type;
	}
}