package org.dawnoftimebuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.dawnoftimebuilder.registry.*;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
    public static final String MOD_ID = "dawnoftimebuilder";
    public static final CreativeModeTab DOTB_TAB = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), DawnOfTimeBuilder.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DoTBBlocksRegistry.COMMELINA.get());
        }
    };

    public DawnOfTimeBuilder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoTBConfig.COMMON_SPEC);

        DoTBItemsRegistry.register(modEventBus);
        DoTBBlocksRegistry.register(modEventBus);
        DoTBEntitiesRegistry.register(modEventBus);
        DoTBFeaturesRegistry.register(modEventBus);
        DoTBRecipeSerializersRegistry.register(modEventBus);
        DoTBRecipeTypesRegistry.register(modEventBus);
        DoTBBlockEntitiesRegistry.register(modEventBus);
        DoTBMenuTypesRegistry.register(modEventBus);

        modEventBus.register(HandlerCommon.class);
        modEventBus.register(HandlerClient.class);
        modEventBus.register(DataGenerator.class);
    }
}
