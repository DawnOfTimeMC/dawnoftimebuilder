package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.dawnoftime.dawnoftime.entity.ChairEntity;

import java.util.function.Supplier;

public abstract class DoTBEntitiesRegistry {
    public static DoTBEntitiesRegistry INSTANCE;
    public final Supplier<EntityType<ChairEntity>> CHAIR_ENTITY = register("chair", () -> EntityType.Builder.<ChairEntity>of((type, world) -> new ChairEntity(world), MobCategory.MISC).ridingOffset(0.0F).sized(0.0F, 0.0F).noSummon());

    public abstract <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder);
}