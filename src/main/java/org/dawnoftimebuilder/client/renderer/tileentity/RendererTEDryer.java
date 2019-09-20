package org.dawnoftimebuilder.client.renderer.tileentity;

import org.dawnoftimebuilder.tileentity.DoTBTileEntityDryer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererTEDryer extends TileEntitySpecialRenderer<DoTBTileEntityDryer>{

	@Override
	public void render(DoTBTileEntityDryer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		try {

			if(te.containsItemInSlot(0)){
				GlStateManager.pushMatrix();

				GlStateManager.translate(x, y, z);
				BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
				Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.disableRescaleNormal();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				dispatcher.getBlockModelRenderer().renderModelBrightnessColor(te.getItemCustomModel(0), 1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}

			if(te.containsItemInSlot(1)){
				GlStateManager.pushMatrix();

				GlStateManager.translate(x, y + 0.5d, z);
				BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
				Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.disableRescaleNormal();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				dispatcher.getBlockModelRenderer().renderModelBrightnessColor(te.getItemCustomModel(1), 1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.popMatrix();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
