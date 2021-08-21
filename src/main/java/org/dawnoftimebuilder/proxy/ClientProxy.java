package org.dawnoftimebuilder.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;
import org.dawnoftimebuilder.entity.JapaneseDragonEntity;
import org.dawnoftimebuilder.entity.SilkmothEntity;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import static org.dawnoftimebuilder.registries.DoTBContainersRegistry.DISPLAYER_CONTAINER;

public class ClientProxy extends CommonProxy {

	public ClientProxy(){}

	@Override
	public void onSetupClient(){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());

		ScreenManager.registerFactory(DISPLAYER_CONTAINER, DisplayerScreen::new);

		ClientRegistry.bindTileEntitySpecialRenderer(DisplayerTileEntity.class, new DisplayerTERenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(DryerTileEntity.class, new DryerTERenderer());

		RenderingRegistry.registerEntityRenderingHandler(SilkmothEntity.class, SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(JapaneseDragonEntity.class, JapaneseDragonRenderer::new);
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}
}
