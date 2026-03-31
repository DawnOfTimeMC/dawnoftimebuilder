package org.dawnoftime.dawnoftime;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftime.dawnoftime.item.IconItem;
import org.dawnoftime.dawnoftime.registry.*;

import java.util.HashSet;
import java.util.Set;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryImpls {
    public static class ForgeBlockEntitiesRegistry extends DoTBBlockEntitiesRegistry {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DoTBCommon.MOD_ID);

        @Override
        public <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BiFunction<BlockPos, BlockState, T> factoryIn, Supplier<Block[]> validBlocksSupplier) {
            return BLOCK_ENTITY_TYPES_REGISTRY.register(name, () -> BlockEntityType.Builder.of(factoryIn::apply, validBlocksSupplier.get()).build(null));
        }
    }

    public static class ForgeBlocksRegistry extends DoTBBlocksRegistry {
        public static final DeferredRegister<Block> BLOCKS_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, DoTBCommon.MOD_ID);
        public static final DeferredRegister<Item> BLOCK_ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DoTBCommon.MOD_ID);
        public ForgeBlocksRegistry() {
            postRegister();
        }

        @SafeVarargs
        @Override
        public final <T extends Block, Y extends Item> Supplier<T> registerWithItem(String id, Supplier<T> block, Function<T, Y> item, TagKey<Block>... tags) {
            RegistryObject<T> registryBlock = BLOCKS_REGISTRY.register(id, block);
            if (item != null) {
                BLOCK_ITEMS_REGISTRY.register(id, () -> item.apply(registryBlock.get()));
            }
            if (tags.length == 0) {
                addBlockTag(registryBlock, BlockTags.MINEABLE_WITH_PICKAXE);
            } else {
                for (TagKey<Block> tag : tags) {
                    addBlockTag(registryBlock, tag);
                }
            }
            return registryBlock;
        }
    }

    public static class ForgeEntitiesRegistry extends DoTBEntitiesRegistry {
        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DoTBCommon.MOD_ID);
        @Override
        public <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
            return ENTITY_TYPES_REGISTRY.register(name, () -> builder.get().build(name));
        }
    }

    public static class ForgeItemsRegistry extends DoTBItemsRegistry {
        public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DoTBCommon.MOD_ID);

        public ForgeItemsRegistry() {
        }

        @Override
        public <T extends Item> Supplier<Item> register(String name, Supplier<T> itemSupplier) {
            return ITEMS_REGISTRY.register(name, itemSupplier);
        }
    }

    public static class ForgeRecipeSerializersRegistry extends DoTBRecipeSerializersRegistry {
        public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DoTBCommon.MOD_ID);

        @Override
        public <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer) {
            return RECIPE_SERIALIZERS_REGISTRY.register(name, recipeSerializer);
        }
    }

    public static class ForgeRecipeTypesRegistry extends DoTBRecipeTypesRegistry {
        public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, DoTBCommon.MOD_ID);

        @Override
        public <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name) {
            return RECIPE_TYPES_REGISTRY.register(name, () -> RecipeType.simple(new ResourceLocation(DoTBCommon.MOD_ID, name)));
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
        DoTBRecipeSerializersRegistry.INSTANCE = new ForgeRecipeSerializersRegistry();
        DoTBRecipeTypesRegistry.INSTANCE = new ForgeRecipeTypesRegistry();
        DoTBTags.INSTANCE = new ForgeTagsRegistry();
        DoTBCreativeModeTabsRegistry.INSTANCE = new ForgeCreativeModeTabsRegistry();

        // Register all deferred registries
        ForgeBlocksRegistry.BLOCKS_REGISTRY.register(bus);
        ForgeBlocksRegistry.BLOCK_ITEMS_REGISTRY.register(bus);
        ForgeItemsRegistry.ITEMS_REGISTRY.register(bus);
        ForgeBlockEntitiesRegistry.BLOCK_ENTITY_TYPES_REGISTRY.register(bus);
        ForgeRecipeSerializersRegistry.RECIPE_SERIALIZERS_REGISTRY.register(bus);
        ForgeRecipeTypesRegistry.RECIPE_TYPES_REGISTRY.register(bus);
        ForgeCreativeModeTabsRegistry.CREATIVE_MODE_TABS_REGISTRY.register(bus);

        bus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if(event.getTab() == DoTBCreativeModeTabsRegistry.INSTANCE.DOT_TAB.get()) {
                Set<Item> addedItems = new HashSet<>();
                for (CreativeInventoryCategories category : CreativeInventoryCategories.values()) {
                    for (Item item : category.getItems()) {
                        if (!(item instanceof IconItem) && addedItems.add(item)) {
                            event.accept(item);
                        }
                    }
                }
            }
        });
    }
}
