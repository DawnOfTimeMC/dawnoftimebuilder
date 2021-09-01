package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;

@OnlyIn(Dist.CLIENT)
public class DryerTERenderer extends TileEntityRenderer<DryerTileEntity> {

	@Override
	public void render(DryerTileEntity tileEntity,  double x, double y, double z, float partialTicks, int destroyStage){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            if(!h.getStackInSlot(0).isEmpty()){
                GlStateManager.pushMatrix();

                GlStateManager.translated(x, y, z);
                BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
                //Minecraft.getInstance().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                GlStateManager.disableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                //IBakedModel rd = Minecraft.getInstance().getModelManager().getModel();
                //boolean test = rd.equals(Minecraft.getInstance().getModelManager().getMissingModel());
                //dispatcher.getModelForState()
                //dispatcher.getBlockModelRenderer().renderModelBrightnessColor(rd, 1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
            }

            if(!h.getStackInSlot(1).isEmpty()){
                GlStateManager.pushMatrix();

                GlStateManager.translated(x, y + 0.5d, z);
                BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
                //Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                GlStateManager.disableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                //dispatcher.getBlockModelRenderer().renderModelBrightnessColor(tileEntity.getItemCustomModel(1), 1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
            }
        });
	}
}