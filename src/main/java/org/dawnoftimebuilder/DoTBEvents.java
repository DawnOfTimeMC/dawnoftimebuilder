package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.dawnoftimebuilder.registry.DoTBFeaturesRegistry;
import org.dawnoftimebuilder.registry.DoTBRecipesRegistry;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.BLOCKS;
import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.CONTAINER_TYPES;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.ENTITY_TYPES;
import static org.dawnoftimebuilder.registry.DoTBFeaturesRegistry.FEATURES;
import static org.dawnoftimebuilder.registry.DoTBItemsRegistry.ITEMS;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.TILE_ENTITY_TYPES;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class DoTBEvents {

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
}
