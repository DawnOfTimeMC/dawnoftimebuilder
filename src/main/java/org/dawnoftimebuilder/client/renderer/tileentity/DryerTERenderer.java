package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import net.minecraft.client.Minecraft;

@OnlyIn(Dist.CLIENT)
public class DryerTERenderer extends TileEntityRenderer<DryerTileEntity> {

	@Override
	public void render(DryerTileEntity tileEntity,  double x, double y, double z, float partialTicks, int destroyStage){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            this.renderItemModel(x, y, z, h.getStackInSlot(0));
            this.renderItemModel(x, y + 0.5D, z, h.getStackInSlot(1));
        });
	}

	public void renderItemModel(double x, double y, double z, ItemStack renderedIS){
	    if(renderedIS.isEmpty()) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        boolean isBlockItem = renderedIS.getItem() instanceof BlockItem;
        for(int i = 0; i < 4; i++) {
            GlStateManager.pushMatrix();
            GlStateManager.translated(x + 0.35D, y, z + 0.35D);
            GlStateManager.translated((i == 1 || i == 2) ? 0.3D : 0, 0.1D, i >= 2 ? 0.3D : 0);
            GlStateManager.rotated(90.0F * i, 0.0F, 1.0F, 0.0F);
            if (isBlockItem) {
                GlStateManager.scaled(0.2F, 0.2F, 0.2F);
                GlStateManager.translated(0.0F, 0.4F, 0.0F);
                itemRenderer.renderItem(renderedIS, ItemCameraTransforms.TransformType.NONE);
            } else {
                GlStateManager.scaled(0.3F, 0.3F, 0.3F);
                GlStateManager.rotated(90.0F, 1.0F, 0.0F, 0.0F);
                itemRenderer.renderItem(renderedIS, ItemCameraTransforms.TransformType.FIXED);
            }
            GlStateManager.popMatrix();
        }
    }
}