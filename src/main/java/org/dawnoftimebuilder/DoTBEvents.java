package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.dawnoftimebuilder.registries.DoTBFeaturesRegistry;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.BLOCKS;
import static org.dawnoftimebuilder.registries.DoTBContainersRegistry.CONTAINER_TYPES;
import static org.dawnoftimebuilder.registries.DoTBEntitiesRegistry.ENTITY_TYPES;
import static org.dawnoftimebuilder.registries.DoTBFeaturesRegistry.FEATURES;
import static org.dawnoftimebuilder.registries.DoTBItemsRegistry.ITEMS;
import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.TILE_ENTITY_TYPES;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class DoTBEvents {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		BLOCKS.forEach(block -> event.getRegistry().register(block));
		BLOCKS.clear();
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		ITEMS.forEach(item -> event.getRegistry().register(item));
		ITEMS.clear();
	}

	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event){
		ENTITY_TYPES.forEach(type -> event.getRegistry().register(type));
		ENTITY_TYPES.clear();
	}

	@SubscribeEvent
	public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event){
		TILE_ENTITY_TYPES.forEach(type -> event.getRegistry().register(type));
		TILE_ENTITY_TYPES.clear();
	}

	@SubscribeEvent
	public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event){
		CONTAINER_TYPES.forEach(type -> event.getRegistry().register(type));
		CONTAINER_TYPES.clear();
	}

	/**
	 * Register features (plants, flowers, etc.)
	 */
	@SubscribeEvent
	public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
		FEATURES.forEach(type -> event.getRegistry().register(type));
		FEATURES.clear();
	}

	/**
	 * Places the features from above into their biomes.
	 */
	@SubscribeEvent
	public static void FMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
		DoTBFeaturesRegistry.addFeaturesToBiomes();
	}
	/*TODO when recipe classes are done, add SpecialModel from items used in recipes of machines that need one. Be careful to register only once per item per machine.
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onModelRegistryEvent(ModelRegistryEvent event){
		SPECIAL_MODELS.forEach(ModelLoader::addSpecialModel);
		SPECIAL_MODELS.clear();
	}
	 */
}
