package org.dawnoftimebuilder.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registries.DoTBContainersRegistry.DISPLAYER_CONTAINER;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSetupClient(){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());
		ScreenManager.registerFactory(DISPLAYER_CONTAINER, DisplayerScreen::new);
		System.out.print("Screens Registered");
		ClientRegistry.bindTileEntitySpecialRenderer(DisplayerTileEntity.class, new DisplayerTERenderer());
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	public ClientProxy(){}
/*
	public void preInit(File configFile){
		super.preInit(configFile);
		OBJLoader.INSTANCE.addDomain(MOD_ID);

		RenderingRegistry.registerEntityRenderingHandler(EntitySilkmoth.class, RendererSilkmoth.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityJapaneseDragon.class, RendererJapaneseDragon.FACTORY);
	}

	public void init(){
		bindTESR(DoTBTileEntityBed.class, new RendererTEBed());
		bindTESR(DoTBTileEntityDryer.class, new RendererTEDryer());
		bindTESR(DoTBTileEntityStove.class, new RendererTEStove());

		registerItemColors();
		registerBlockColors();

		DoTBItemsRegistry.initCustomModels();

		MinecraftForge.EVENT_BUS.register(new CreativeInventoryDrawEvent());
	}

	private static <T extends TileEntity> void bindTESR(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer){
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
	}

	private static void registerItemColors(){
		registerItemColor(new ItemLittleFlagColor(), DoTBItems.little_flag);
	}

	private static void registerItemColor(IItemColor itemColor, Item item){
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, item);
	}

	private static void registerBlockColors(){
		registerBlockColor(new BlockLittleFlagColor(), DoTBBlocks.little_flag);
	}

	private static void registerBlockColor(IBlockColor blockColor, Block block){
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, block);
	}*/
}
