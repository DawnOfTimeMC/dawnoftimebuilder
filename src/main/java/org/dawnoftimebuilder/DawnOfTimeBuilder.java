package org.dawnoftimebuilder;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.registries.*;
import org.dawnoftimebuilder.client.gui.DoTGuiHandler;
import org.dawnoftimebuilder.crafts.OreDictionaryHandler;
import org.dawnoftimebuilder.proxy.CommonProxy;

import java.io.File;

@Mod(modid = DawnOfTimeBuilder.MOD_ID, name = DawnOfTimeBuilder.NAME, version = DawnOfTimeBuilder.VERSION, updateJSON = "https://raw.githubusercontent.com/Poulpynou/dawnoftimebuilder/master-1.12.2/update.json")
public class DawnOfTimeBuilder {
	public static final String MOD_ID = "dawnoftimebuilder";
	public static final String NAME = "Dawn Of Time : Builder Edition";
	public static final String VERSION = "1.0.7";
	public static Logger logger;
	public static final NetworkRegistry NETWORK = NetworkRegistry.INSTANCE;

	@Mod.Instance(MOD_ID)
	public static DawnOfTimeBuilder instance;

	public DawnOfTimeBuilder(){
		MinecraftForge.EVENT_BUS.register(new DoTBEvents());
		MinecraftForge.TERRAIN_GEN_BUS.register(new DoTBWorldGen());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		logger = event.getModLog();

		DoTBBlocksRegistry.init();
		DoTBItemsRegistry.init();
		DoTBTileEntitiesRegistry.init();
		DoTBEntitiesRegistry.init();

		DoTBConfigs.init(new File(event.getModConfigurationDirectory(), MOD_ID + ".cfg"));

		proxy.preInit(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		OreDictionaryHandler.init();
		NETWORK.registerGuiHandler(instance, new DoTGuiHandler());
		proxy.init();

		DoTBRecipesRegistry.init();
	}

	@SidedProxy(clientSide = "org.dawnoftimebuilder.proxy.ClientProxy", serverSide = "org.dawnoftimebuilder.proxy.ServerProxy")
	public static CommonProxy proxy;

	public final static CreativeTabs DOTB_TAB = new CreativeTabs(MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(DoTBBlocks.cast_iron_teapot);
		}

		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
			super.displayAllRelevantItems(p_78018_1_);
		}
	};
}
