package org.dawnoftimebuilder.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.dawnoftimebuilder.entity.ChairEntity;
//import org.dawnoftimebuilder.entity.JapaneseDragonEntity;
import org.dawnoftimebuilder.entity.SilkmothEntity;

import java.util.function.Supplier;

public abstract class DoTBEntitiesRegistry {
    public static DoTBEntitiesRegistry INSTANCE;
    public final Supplier<EntityType<ChairEntity>> CHAIR_ENTITY = register("chair", () -> EntityType.Builder.<ChairEntity>of((type, world) -> new ChairEntity(world), MobCategory.MISC).ridingOffset(0.0F).sized(0.0F, 0.0F).noSummon());
    public final Supplier<EntityType<SilkmothEntity>> SILKMOTH_ENTITY = register("silkmoth", () -> EntityType.Builder.<SilkmothEntity>of((type, world) -> new SilkmothEntity(world), MobCategory.AMBIENT).sized(0.3F, 0.3F));
//    public final Supplier<EntityType<JapaneseDragonEntity>> JAPANESE_DRAGON_ENTITY = null; //reg("japanese_dragon", EntityType.Builder.<JapaneseDragonEntity>of((type, world) -> new JapaneseDragonEntity(world), MobCategory.CREATURE).sized(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> new JapaneseDragonEntity(world)));

    public abstract <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder);
}