package org.dawnoftimebuilder.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;

import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSetupClient(){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());
		OBJLoader.INSTANCE.addDomain(MOD_ID);//TODO It doesn't work currently...
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
