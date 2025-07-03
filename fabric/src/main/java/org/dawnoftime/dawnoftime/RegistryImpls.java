package org.dawnoftime.dawnoftime;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.dawnoftime.dawnoftime.block.IFlammable;
import org.dawnoftime.dawnoftime.block.templates.FlowerPotBlockDoT;
import org.dawnoftime.dawnoftime.client.gui.screen.DisplayerScreen;
import org.dawnoftime.dawnoftime.client.model.entity.SilkmothModel;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DisplayerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.blockentity.DryerBERenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.ChairRenderer;
import org.dawnoftime.dawnoftime.client.renderer.entity.SilkmothRenderer;
import org.dawnoftime.dawnoftime.entity.SilkmothEntity;
import org.dawnoftime.dawnoftime.item.IHasFlowerPot;
import org.dawnoftime.dawnoftime.item.IconItem;
import org.dawnoftime.dawnoftime.registry.*;

import java.util.Map;
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

        @Override
        public <T extends Block, Y extends Item & IHasFlowerPot> Supplier<T> registerWithFlowerPotItem(String blockID, Supplier<T> block, String itemID, Function<T, Y> item) {
            T toReturn = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(DoTBCommon.MOD_ID, blockID), block.get());
            if(item != null) {
                final String potName = blockID + "_flower_pot";

                Supplier<FlowerPotBlockDoT> potBlockObject = this.register(potName, () -> {
                    final FlowerPotBlockDoT potBlock = new FlowerPotBlockDoT(null);
                    POT_BLOCKS.put(potName, potBlock);
                    return potBlock;
                }, BlockTags.MINEABLE_WITH_PICKAXE);

                Y item1 = item.apply(toReturn);
                FlowerPotBlockDoT potBlock = potBlockObject.get();

                item1.setPotBlock(potBlock);
                potBlock.setItemInPot(item1);

                Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DoTBCommon.MOD_ID, itemID), item1);
            }
            // Flower can be broken with sword, and in the ItemRegistry, pot can be broken with Pickaxe.
            addBlockTag(() -> toReturn, BlockTags.SWORD_EFFICIENT);
            return () -> toReturn;
        }
    }

    public static class FabricEntitiesRegistry extends DoTBEntitiesRegistry {
        @Override
        public <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
            var entity = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(DoTBCommon.MOD_ID, name), builder.get().build(name));
            return () -> entity;
        }
    }

    public static class FabricFeaturesRegistry extends DoTBFeaturesRegistry {
        @Override
        public <Y extends FeatureConfiguration, T extends Feature<Y>> Supplier<T> register(String name, Supplier<T> featureSupplier) {
            var feature = Registry.register(BuiltInRegistries.FEATURE, new ResourceLocation(DoTBCommon.MOD_ID, name), featureSupplier.get());
            return () -> feature;
        }
    }

    public static class FabricItemsRegistry extends DoTBItemsRegistry {
        public final Supplier<Item> SILKMOTH_SPAWN_EGG = register("silkmoth_spawn_egg", () -> new SpawnEggItem(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY.get(), 0xDBD8BD, 0xFEFEFC, new Item.Properties()));

        public FabricItemsRegistry() {
            postRegister();
        }

        @Override
        public <T extends Item> Supplier<Item> register(String name, Supplier<T> itemSupplier) {
            T item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DoTBCommon.MOD_ID, name), itemSupplier.get());
            return () -> item;
        }

        @Override
        public <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(String name, Supplier<T> itemSupplier) {
            return registerWithFlowerPot(name, name, itemSupplier);
        }

        @Override
        public <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(String plantName, String seedName, Supplier<T> itemSupplier) {
            final String potName = plantName + "_flower_pot";

            Supplier<FlowerPotBlockDoT> potBlockObject = DoTBBlocksRegistry.INSTANCE.register(potName, () -> {
                final FlowerPotBlockDoT potBlock = new FlowerPotBlockDoT(null);
                DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
                return potBlock;
            }, BlockTags.MINEABLE_WITH_PICKAXE);

            return this.register(seedName, () -> {
                T item = itemSupplier.get();
                FlowerPotBlockDoT potBlock = potBlockObject.get();

                item.setPotBlock(potBlock);
                potBlock.setItemInPot(item);

                return item;
            });
        }
    }

    public static class FabricMenuTypesRegistry extends DoTBMenuTypesRegistry {
        @Override
        public <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String name, MenuTypeFactory<T> factory) {
            ExtendedScreenHandlerType<AbstractContainerMenu> type = Registry.register(BuiltInRegistries.MENU, new ResourceLocation(DoTBCommon.MOD_ID, name), new ExtendedScreenHandlerType<>(factory::create));
            return () -> (MenuType<T>) type;
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
                BuiltInRegistries.ITEM.entrySet().forEach(entry -> {
                    var loc = entry.getKey().location();
                    if(entry.getValue() instanceof IconItem) return;
                    if (loc.getNamespace().equals(DoTBCommon.MOD_ID)) {
                        output.accept(entry.getValue());
                    }
                });
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
        EntityRendererRegistry.register(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
        EntityRendererRegistry.register(DoTBEntitiesRegistry.INSTANCE.CHAIR_ENTITY.get(), ChairRenderer::new);
        BlockEntityRenderers.register(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), DisplayerBERenderer::new);
        BlockEntityRenderers.register(DoTBBlockEntitiesRegistry.INSTANCE.DRYER.get(), DryerBERenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SilkmothModel.LAYER_LOCATION, SilkmothModel::createBodyLayer);
        MenuScreens.register(DoTBMenuTypesRegistry.INSTANCE.DISPLAYER.get(), DisplayerScreen::new);

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
        DoTBItemsRegistry.INSTANCE = new FabricItemsRegistry();
        DoTBBlockEntitiesRegistry.INSTANCE = new FabricBlockEntitiesRegistry();
        DoTBFeaturesRegistry.INSTANCE = new FabricFeaturesRegistry();
        DoTBMenuTypesRegistry.INSTANCE = new FabricMenuTypesRegistry();
        DoTBRecipeSerializersRegistry.INSTANCE = new FabricRecipeSerializersRegistry();
        DoTBRecipeTypesRegistry.INSTANCE = new FabricRecipeTypesRegistry();
        DoTBTags.INSTANCE = new FabricTagsRegistry();
        DoTBCreativeModeTabsRegistry.INSTANCE = new FabricCreativeModeTabsRegistry();

        FabricDefaultAttributeRegistry.register(DoTBEntitiesRegistry.INSTANCE.SILKMOTH_ENTITY.get(), SilkmothEntity.createAttributes());
    }
}
