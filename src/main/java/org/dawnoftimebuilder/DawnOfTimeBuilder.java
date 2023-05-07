package org.dawnoftimebuilder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftimebuilder.client.gui.filters.CreativeInventoryFilters;
import org.dawnoftimebuilder.registry.*;
import org.dawnoftimebuilder.util.DoTBFilterEntry;

import java.util.Set;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.COMMELINA;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {

    public static final String MOD_ID = "dawnoftimebuilder";
    private static DawnOfTimeBuilder instance;
    public CreativeInventoryFilters events;
    public static final ItemGroup DOTB_TAB = new ItemGroup(ItemGroup.getGroupCountSafe(), MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(COMMELINA.get());
        }
    };

    public DawnOfTimeBuilder() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoTBConfig.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this.events = new CreativeInventoryFilters());
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

    public static DawnOfTimeBuilder get() {
        return instance;
    }

    public Set<ItemGroup> getGroups() {
        return ImmutableSet.copyOf(HandlerClient.getFilterMap().keySet());
    }

    public ImmutableList<DoTBFilterEntry> getFilters(ItemGroup group) {
        return ImmutableList.copyOf(HandlerClient.getFilterMap().get(group));
    }

    public boolean hasFilters(ItemGroup group) {
        return HandlerClient.getFilterMap().containsKey(group);
    }

}
//TODO Vérifier le fichier config qui spammerait la console sur server
//TODO En 1.18 remplacer le craft de la statue romaine, et des roofing_slates