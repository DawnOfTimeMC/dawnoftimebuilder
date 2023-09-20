package org.dawnoftimebuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftimebuilder.registry.*;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
	public static final String MOD_ID = "dawnoftimebuilder";
	public static final ItemGroup DOTB_TAB = new ItemGroup(ItemGroup.getGroupCountSafe(), DawnOfTimeBuilder.MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoTBBlocksRegistry.COMMELINA.get());
		}
	};

	private static DawnOfTimeBuilder instance;

	public DawnOfTimeBuilder() {
		DawnOfTimeBuilder.instance = this;

		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		DoTBBlocksRegistry.BLOCKS.register(eventBus);
		DoTBEntitiesRegistry.ENTITY_TYPES.register(eventBus);// TODO Use dragons config with addTransientModifier
		DoTBItemsRegistry.ITEMS.register(eventBus);
		DoTBRecipesRegistry.RECIPES.register(eventBus);
		DoTBTileEntitiesRegistry.TILE_ENTITY_TYPES.register(eventBus);
		DoTBContainersRegistry.CONTAINER_TYPES.register(eventBus);
		DoTBBlockPlacerRegistry.PLACER_TYPES.register(eventBus);

		eventBus.addListener(HandlerCommon::modConfigLoadingEvent);
		eventBus.addListener(HandlerCommon::fMLDedicatedServerSetupEvent);
		eventBus.addListener(HandlerCommon::fMLCommonSetupEvent);
		eventBus.addListener(HandlerCommon::entityAttributeCreationEvent);
		eventBus.addListener(HandlerClient::fMLClientSetupEvent);

		final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.HIGH, HandlerCommon::biomeLoadingEvent);
	}

	public static DawnOfTimeBuilder get() {
		return DawnOfTimeBuilder.instance;
	}
}
//TODO Vérifier le fichier config qui spammerait la console sur server
//TODO En 1.18 remplacer le craft de la statue romaine, et des roofing_slates