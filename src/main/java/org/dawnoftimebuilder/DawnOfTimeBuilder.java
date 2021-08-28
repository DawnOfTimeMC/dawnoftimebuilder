package org.dawnoftimebuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.dawnoftimebuilder.proxy.ClientProxy;
import org.dawnoftimebuilder.proxy.CommonProxy;
import org.dawnoftimebuilder.utils.DoTBConfig;

import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.COMMELINA;

@Mod(DawnOfTimeBuilder.MOD_ID)
public class DawnOfTimeBuilder {
	public static final String MOD_ID = "dawnoftimebuilder";
	//public static final Logger LOGGER = LogManager.getLogger(MOD_ID); Useless currently !
	public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	public static final ItemGroup DOTB_TAB = new ItemGroup(ItemGroup.getGroupCountSafe(), MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(COMMELINA);
		}
	};

	public DawnOfTimeBuilder(){
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoTBConfig.COMMON_CONFIG);
		DoTBConfig.loadConfig(DoTBConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-common.toml"));
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
		MinecraftForge.EVENT_BUS.register(DoTBEvents.class);
	}

	private void onCommonSetup(FMLCommonSetupEvent event){
		PROXY.onSetupCommon();
	}

	private void onClientSetup(FMLClientSetupEvent event){
		PROXY.onSetupClient();
	}
}
