package org.dawnoftime.dawnoftime;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
import org.dawnoftime.dawnoftime.block.IFlammable;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.ChairRenderer;
import org.dawnoftime.dawnoftime.item.IconItem;
import org.dawnoftime.dawnoftime.registry.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryImpls {
    public static class FabricBlockEntitiesRegistry extends DoTBBlockEntitiesRegistry {
        @Override
        public <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BiFunction<BlockPos, BlockState, T> factoryIn, Supplier<Block[]> validBlocksSupplier) {
            BlockEntityType<T> blockEntity = (BlockEntityType<T>) Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(DoTBCommon.MOD_ID, name), FabricBlockEntityTypeBuilder.create((FabricBlockEntityTypeBuilder.Factory<BlockEntity>) factoryIn::apply, validBlocksSupplier.get()).build());
            return () -> blockEntity;
        }
    }

    public static class FabricBlocksRegistry extends DoTBBlocksRegistry {
        public FabricBlocksRegistry() {
            postRegister();

            for (Map.Entry<ResourceKey<Block>, Block> resourceKeyBlockEntry : BuiltInRegistries.BLOCK.entrySet()) {
                Block block = resourceKeyBlockEntry.getValue();
                if (block instanceof IFlammable) {
                    FlammableBlockRegistry.getDefaultInstance().add(block, ((IFlammable) block).getFireSpreadSpeed(block.defaultBlockState(), null, null, null), ((IFlammable) block).getFlammability(block.defaultBlockState(), null, null, null));
                }
            }
        }

        @SafeVarargs
        @Override
        public final <T extends Block, Y extends Item> Supplier<T> registerWithItem(String id, Supplier<T> block, Function<T, Y> item, TagKey<Block>... tags) {
            T registryBlock = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(DoTBCommon.MOD_ID, id), block.get());
            if(item != null) {
                Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DoTBCommon.MOD_ID, id), item.apply(registryBlock));
            }
            if(tags.length == 0){
                addBlockTag(() -> registryBlock, BlockTags.MINEABLE_WITH_PICKAXE);
            }else{
                for (TagKey<Block> tag : tags) {
                    addBlockTag(() -> registryBlock, tag);
                }
            }
            return () -> registryBlock;
        }
    }

    public static class FabricItemsRegistry extends DoTBItemsRegistry {
        @Override
        public <T extends Item> Supplier<Item> register(String name, Supplier<T> itemSupplier) {
            T item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DoTBCommon.MOD_ID, name), itemSupplier.get());
            return () -> item;
        }
    }

    public static class FabricEntitiesRegistry extends DoTBEntitiesRegistry {
        @Override
        public <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
            var entity = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(DoTBCommon.MOD_ID, name), builder.get().build(name));
            return () -> entity;
        }
    }

    public static class FabricRecipeSerializersRegistry extends DoTBRecipeSerializersRegistry {
        @Override
        public <T extends RecipeSerializer<? extends Recipe<?>>> Supplier<T> register(String name, Supplier<T> recipeSerializer) {
            var recipe = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(DoTBCommon.MOD_ID, name), recipeSerializer.get());
            return () -> recipe;
        }
    }

    public static class FabricRecipeTypesRegistry extends DoTBRecipeTypesRegistry {
        @Override
        public <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name) {
            RecipeType<T> type = RecipeType.register(name);
            return () -> type;
        }
    }

    public static class FabricCreativeModeTabsRegistry extends DoTBCreativeModeTabsRegistry {
        @Override
        public <T extends CreativeModeTab> Supplier<CreativeModeTab> register(String name, Supplier<ItemStack> iconSupplier, Component title) {
            var group = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(DoTBCommon.MOD_ID, name), FabricItemGroup.builder().icon(iconSupplier).title(title).displayItems((itemDisplayParameters, output) -> {
                Set<Item> addedItems = new HashSet<>();
                for (CreativeInventoryCategories category : CreativeInventoryCategories.values()) {
                    for (Item item : category.getItems()) {
                        if (!(item instanceof IconItem) && addedItems.add(item)) {
                            output.accept(item);
                        }
                    }
                }
            }).build());
            return () -> group;
        }
    }

    public static class FabricTagsRegistry extends DoTBTags {
        @Override
        public TagKey<Block> registerBlock(ResourceLocation id) {
            return TagKey.create(Registries.BLOCK, id);
        }

        @Override
        public TagKey<Item> registerItem(ResourceLocation id) {
            return TagKey.create(Registries.ITEM, id);
        }
    }

    public static void initClient() {
        EntityRendererRegistry.register(DoTBEntitiesRegistry.INSTANCE.CHAIR_ENTITY.get(), ChairRenderer::new);
        BlockEntityRenderers.register(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), DisplayerBERenderer::new);

        DoTBColorsRegistry.initialize();
        DoTBColorsRegistry.getBlocksColorRegistry().forEach((blockColor, blocks) -> {
            ColorProviderRegistry.BLOCK.register(blockColor, blocks.stream().map(Supplier::get).toArray(Block[]::new));
        });
        DoTBColorsRegistry.getItemsColorRegistry().forEach((itemColor, items) -> {
            ColorProviderRegistry.ITEM.register(itemColor, items.stream().map(Supplier::get).toArray(Item[]::new));
        });
    }

    public static void init() {
        DoTBEntitiesRegistry.INSTANCE = new FabricEntitiesRegistry();
        DoTBBlocksRegistry.INSTANCE = new FabricBlocksRegistry();
        FabricItemsRegistry.INSTANCE = new FabricItemsRegistry();
        DoTBBlockEntitiesRegistry.INSTANCE = new FabricBlockEntitiesRegistry();
        DoTBRecipeSerializersRegistry.INSTANCE = new FabricRecipeSerializersRegistry();
        DoTBRecipeTypesRegistry.INSTANCE = new FabricRecipeTypesRegistry();
        DoTBTags.INSTANCE = new FabricTagsRegistry();
        DoTBCreativeModeTabsRegistry.INSTANCE = new FabricCreativeModeTabsRegistry();
    }
}
