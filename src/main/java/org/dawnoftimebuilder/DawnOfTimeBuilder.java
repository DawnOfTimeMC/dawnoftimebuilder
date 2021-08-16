package org.dawnoftimebuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dawnoftimebuilder.network.PacketHandler;
import org.dawnoftimebuilder.proxy.ClientProxy;
import org.dawnoftimebuilder.proxy.CommonProxy;

import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.COMMELINA;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
	public static final String MOD_ID = "dawnoftimebuilder";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	public static final ItemGroup DOTB_TAB = new ItemGroup(ItemGroup.getGroupCountSafe(), MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(COMMELINA);
		}
	};

	public DawnOfTimeBuilder(){
		//ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FurnitureConfig.clientSpec);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

		MinecraftForge.EVENT_BUS.register(DoTBEvents.class);
		//PacketHandler.init();
	}

	private void onCommonSetup(FMLCommonSetupEvent event){
		PROXY.onSetupCommon();
	}

	private void onClientSetup(FMLClientSetupEvent event){
		PROXY.onSetupClient();
	}
/*
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		DoTBBlocksRegistry.init();
		DoTBTileEntitiesRegistry.init();
		DoTBEntitiesRegistry.init();

		DoTBConfigs.init(new File(event.getModConfigurationDirectory(), MOD_ID + "_disabled_objects.cfg"));

		proxy.preInit(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		OreDictionaryHandler.init();
		NETWORK.registerGuiHandler(instance, new DoTGuiHandler());
		proxy.init();

		DoTBRecipesRegistry.init();
	}
*/

}
