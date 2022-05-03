package org.dawnoftimebuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftimebuilder.registry.*;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.COMMELINA;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {

    public static final String MOD_ID = "dawnoftimebuilder";
    public static final ItemGroup DOTB_TAB = new ItemGroup(ItemGroup.getGroupCountSafe(), MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(COMMELINA.get());
        }
    };

    public DawnOfTimeBuilder(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoTBConfig.COMMON_CONFIG);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DoTBBlocksRegistry.BLOCKS.register(eventBus);
        DoTBEntitiesRegistry.ENTITY_TYPES.register(eventBus);// TODO Use dragons config with addTransientModifier
        DoTBItemsRegistry.ITEMS.register(eventBus);
        DoTBRecipesRegistry.RECIPES.register(eventBus);
        DoTBTileEntitiesRegistry.TILE_ENTITY_TYPES.register(eventBus);
        DoTBContainersRegistry.CONTAINER_TYPES.register(eventBus);
        DoTBBlockPlacerRegistry.PLACER_TYPES.register(eventBus);
        eventBus.addListener(HandlerCommon::fMLCommonSetupEvent);
        eventBus.addListener(HandlerCommon::entityAttributeCreationEvent);
        eventBus.addListener(HandlerClient::fMLClientSetupEvent);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, HandlerCommon::biomeLoadingEvent);
    }
}
//TODO Ajouter les blocks manquants pour les deux bois du Nether
//TODO En 1.18 remplacer le craft de la statue romaine, et des roofing_slates