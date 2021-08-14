package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;

@OnlyIn(Dist.CLIENT)
public class DryerTERenderer extends TileEntityRenderer<DryerTileEntity> {
	/*
	@Override
	public void render(DryerTileEntity tileEntity,  double x, double y, double z, float partialTicks, int destroyStage){
		try {
			if(tileEntity.containsItemInSlot(0)){
				GlStateManager.pushMatrix();

				GlStateManager.translated(x, y, z);
				BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
				Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.disableRescaleNormal();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				dispatcher.getBlockModelRenderer().renderModelBrightnessColor(tileEntity.getItemCustomModel(0), 1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}

			if(tileEntity.containsItemInSlot(1)){
				GlStateManager.pushMatrix();

				GlStateManager.translated(x, y + 0.5d, z);
				BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
				Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.disableRescaleNormal();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				dispatcher.getBlockModelRenderer().renderModelBrightnessColor(tileEntity.getItemCustomModel(1), 1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
