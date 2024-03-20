package org.dawnoftimebuilder.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.dawnoftimebuilder.entity.JapaneseDragonEntity;
import org.dawnoftimebuilder.entity.SilkmothEntity;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBEntitiesRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final RegistryObject<EntityType<ChairEntity>> CHAIR_ENTITY = reg("chair", EntityType.Builder.<ChairEntity>of((type, world) -> new ChairEntity(world), MobCategory.MISC).sized(0.0F, 0.0F).noSummon().setCustomClientFactory((spawnEntity, world) -> new ChairEntity(world)));
    public static final RegistryObject<EntityType<SilkmothEntity>> SILKMOTH_ENTITY = reg("silkmoth", EntityType.Builder.<SilkmothEntity>of((type, world) -> new SilkmothEntity(world), MobCategory.AMBIENT).sized(0.3F, 0.3F).setCustomClientFactory((spawnEntity, world) -> new SilkmothEntity(world)));
    public static final RegistryObject<EntityType<JapaneseDragonEntity>> JAPANESE_DRAGON_ENTITY = null;//reg("japanese_dragon", EntityType.Builder.<JapaneseDragonEntity>of((type, world) -> new JapaneseDragonEntity(world), MobCategory.CREATURE).sized(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> new JapaneseDragonEntity(world)));
    //TODO Fix the dragon and find a way to set different size for a unique mob.

    private static <T extends Entity> RegistryObject<EntityType<T>> reg(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(name));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}