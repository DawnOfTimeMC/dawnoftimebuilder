package org.dawnoftimebuilder;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockAA;
import org.dawnoftimebuilder.entity.SilkmothEntity;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.registry.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryImpls {
    public static class ForgeBlockEntitiesRegistry extends DoTBBlockEntitiesRegistry {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DoTBCommon.MOD_ID);

        @Override
        public <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BiFunction<BlockPos, BlockState, T> factoryIn, Supplier<Block[]> validBlocksSupplier) {
            return BLOCK_ENTITY_TYPES_REGISTRY.register(name, () -> BlockEntityType.Builder.of(factoryIn::apply, validBlocksSupplier.get()).build(null));
        }
    }

    public static class ForgeBlocksRegistry extends DoTBBlocksRegistry {
        public static final DeferredRegister<Block> BLOCKS_REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, DoTBCommon.MOD_ID);
        public static final DeferredRegister<Item> BLOCK_ITEMS_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, DoTBCommon.MOD_ID);
        public ForgeBlocksRegistry() {
            postRegister();
        }

        @SafeVarargs
        @Override
        public final <T extends Block, Y extends Item> Supplier<T> registerWithItem(String id, Supplier<T> block, Function<T, Y> item, TagKey<Block>... tags) {
            Supplier<T> registryBlock = BLOCKS_REGISTRY.register(id, block);
            if(item != null) {
                BLOCK_ITEMS_REGISTRY.register(id, () -> item.apply(registryBlock.get()));
            }
            if(tags.length == 0){
                addBlockTag(registryBlock, BlockTags.MINEABLE_WITH_PICKAXE);
            }else{
                for (TagKey<Block> tag : tags) {
                    addBlockTag(registryBlock, tag);
                }
            }
            return registryBlock;
        }

        @Override
        public <T extends Block, Y extends Item & IHasFlowerPot> Supplier<T> registerWithFlowerPotItem(String blockID, Supplier<T> block, String itemID, Function<T, Y> item) {
            Supplier<T> registryBlock = BLOCKS_REGISTRY.register(blockID, block);
            if (item != null) {
                final String potName = blockID + "_flower_pot";

                Supplier<FlowerPotBlockAA> potBlockObject = this.register(potName, () -> {
                    final FlowerPotBlockAA potBlock = new FlowerPotBlockAA(null);
                    POT_BLOCKS.put(potName, potBlock);
                    return potBlock;
                }, BlockTags.MINEABLE_WITH_PICKAXE);

                BLOCK_ITEMS_REGISTRY.register(itemID, () -> {
                    var item1 = item.apply(registryBlock.get());
                    FlowerPotBlockAA potBlock = potBlockObject.get();

                    item1.setPotBlock(potBlock);
                    potBlock.setItemInPot(item1);
                    return item1;
                });
            }
            addBlockTag(registryBlock, BlockTags.SWORD_EFFICIENT);
            return registryBlock;
        }
    }

    public static class ForgeEntitiesRegistry extends DoTBEntitiesRegistry {
        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES_REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DoTBCommon.MOD_ID);
        @Override
        public <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
            return ENTITY_TYPES_REGISTRY.register(name, () -> builder.get().build(name));
        }
    }

    public static class ForgeFeaturesRegistry extends DoTBFeaturesRegistry {
        public static final DeferredRegister<Feature<?>> FEATURES_REGISTRY = DeferredRegister.create(BuiltInRegistries.FEATURE, DoTBCommon.MOD_ID);

        @Override
        public <Y extends FeatureConfiguration, T extends Feature<Y>> Supplier<T> register(String name, Supplier<T> featureSupplier) {
            return FEATURES_REGISTRY.register(name, featureSupplier);
        }
    }

    public static class ForgeItemsRegistry extends DoTBItemsRegistry {
        public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, DoTBCommon.MOD_ID);

        public final Supplier<Item> SILKMOTH_SPAWN_EGG = register("silkmoth_spawn_egg", () -> new DeferredSpawnEggItem(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY, 0xDBD8BD, 0xFEFEFC, new Item.Properties()));

        public ForgeItemsRegistry() {
            postRegister();
        }

        @Override
        public <T extends Item> Supplier<Item> register(String name, Supplier<T> itemSupplier) {
            return ITEMS_REGISTRY.register(name, itemSupplier);
        }

        @Override
        public <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(String name, Supplier<T> itemSupplier) {
            return registerWithFlowerPot(name, name, itemSupplier);
        }

        @Override
        public <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(String plantName, String seedName, Supplier<T> itemSupplier) {
            final String potName = plantName + "_flower_pot";

            Supplier<FlowerPotBlockAA> potBlockObject = DoTBBlocksRegistry.INSTANCE.register(potName, () -> {
                final FlowerPotBlockAA potBlock = new FlowerPotBlockAA(null);
                DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
                return potBlock;
            }, BlockTags.MINEABLE_WITH_PICKAXE);

            return this.register(seedName, () -> {
                T item = itemSupplier.get();
                FlowerPotBlockAA potBlock = potBlockObject.get();

                item.setPotBlock(potBlock);
                potBlock.setItemInPot(item);

                return item;
            });
        }
    }

    public static class ForgeMenuTypesRegistry extends DoTBMenuTypesRegistry {
        public static final DeferredRegister<MenuType<?>> MENU_TYPES_REGISTRY = DeferredRegister.create(BuiltInRegistries.MENU, DoTBCommon.MOD_ID);

        @Override
        public <T extends AbstractContainerMenu, D> Supplier<MenuType<T>> register(String name, MenuTypeFactory<T, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec) {
            return MENU_TYPES_REGISTRY.register(name, () -> IMenuTypeExtension.create((i, inventory, registryFriendlyByteBuf) -> factory.create(i, inventory, null, registryFriendlyByteBuf)));
        }
    }

    public static class ForgeRecipeSerializersRegistry extends DoTBRecipeSerializersRegistry {
        public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS_REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, DoTBCommon.MOD_ID);

        @Override
        public <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer) {
            return RECIPE_SERIALIZERS_REGISTRY.register(name, recipeSerializer);
        }
    }

    public static class ForgeRecipeTypesRegistry extends DoTBRecipeTypesRegistry {
        public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES_REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, DoTBCommon.MOD_ID);

        @Override
        public <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name) {
            return RECIPE_TYPES_REGISTRY.register(name, () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, name)));
        }
    }

    public static class ForgeCreativeModeTabsRegistry extends DoTBCreativeModeTabsRegistry {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DoTBCommon.MOD_ID);

        @Override
        public <T extends CreativeModeTab> Supplier<CreativeModeTab> register(String name, Supplier<ItemStack> iconSupplier, Component title) {
            return CREATIVE_MODE_TABS_REGISTRY.register(name, () -> CreativeModeTab.builder().icon(iconSupplier).title(title).build());
        }
    }

    public static class ForgeTagsRegistry extends DoTBTags {
        @Override
        public TagKey<Block> registerBlock(ResourceLocation id) {
            return TagKey.create(Registries.BLOCK, id);
        }

        @Override
        public TagKey<Item> registerItem(ResourceLocation id) {
            return TagKey.create(Registries.ITEM, id);
        }
    }

    public static void init(IEventBus bus) {
        DoTBEntitiesRegistry.INSTANCE = new ForgeEntitiesRegistry();
        ForgeEntitiesRegistry.ENTITY_TYPES_REGISTRY.register(bus);

        DoTBBlocksRegistry.INSTANCE = new ForgeBlocksRegistry();
        DoTBItemsRegistry.INSTANCE = new ForgeItemsRegistry();
        DoTBBlockEntitiesRegistry.INSTANCE = new ForgeBlockEntitiesRegistry();
        DoTBFeaturesRegistry.INSTANCE = new ForgeFeaturesRegistry();
        DoTBMenuTypesRegistry.INSTANCE = new ForgeMenuTypesRegistry();
        DoTBRecipeSerializersRegistry.INSTANCE = new ForgeRecipeSerializersRegistry();
        DoTBRecipeTypesRegistry.INSTANCE = new ForgeRecipeTypesRegistry();
        DoTBTags.INSTANCE = new ForgeTagsRegistry();
        DoTBCreativeModeTabsRegistry.INSTANCE = new ForgeCreativeModeTabsRegistry();

        // Register all deffered registries
        ForgeBlocksRegistry.BLOCKS_REGISTRY.register(bus);
        ForgeBlocksRegistry.BLOCK_ITEMS_REGISTRY.register(bus);
        ForgeItemsRegistry.ITEMS_REGISTRY.register(bus);
        ForgeBlockEntitiesRegistry.BLOCK_ENTITY_TYPES_REGISTRY.register(bus);
        ForgeFeaturesRegistry.FEATURES_REGISTRY.register(bus);
        ForgeMenuTypesRegistry.MENU_TYPES_REGISTRY.register(bus);
        ForgeRecipeSerializersRegistry.RECIPE_SERIALIZERS_REGISTRY.register(bus);
        ForgeRecipeTypesRegistry.RECIPE_TYPES_REGISTRY.register(bus);
        ForgeCreativeModeTabsRegistry.CREATIVE_MODE_TABS_REGISTRY.register(bus);

        bus.addListener((EntityAttributeCreationEvent event) -> event.put(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY.get(), SilkmothEntity.createAttributes().build()));
        bus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if(event.getTab() == DoTBCreativeModeTabsRegistry.INSTANCE.DOT_TAB.get()) {
                BuiltInRegistries.ITEM.stream()
                        .filter(entry -> BuiltInRegistries.ITEM.getKey(entry).getNamespace().equalsIgnoreCase(DoTBCommon.MOD_ID) && !(entry instanceof IconItem))
                        .forEachOrdered(event::accept);
            }
        });
    }
}
