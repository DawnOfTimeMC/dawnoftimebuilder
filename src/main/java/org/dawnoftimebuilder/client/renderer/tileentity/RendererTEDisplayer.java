package org.dawnoftimebuilder.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.dawnoftimebuilder.blocks.IBlockDisplayer;
import org.dawnoftimebuilder.blocks.IBlockSpecialDisplay;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDisplayer;

public class RendererTEDisplayer extends TileEntitySpecialRenderer<DoTBTileEntityDisplayer> {

	@Override
	public void render(DoTBTileEntityDisplayer tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		ItemStack itemStack;
		double xStart = 0.0D;
		double yStart = 0.0D;
		double zStart = 0.0D;
		float rotationAngle;
		if(tileEntity.getBlockType() instanceof IBlockDisplayer){
			IBlockDisplayer block = (IBlockDisplayer) tileEntity.getBlockType();
			int meta = tileEntity.getBlockMetadata();
			xStart = block.getDisplayerX(meta);
			yStart = block.getDisplayerY(meta);
			zStart = block.getDisplayerZ(meta);
		}

		for(int i = 0; i < 9; i++){
			itemStack = tileEntity.getStackInSlot(i);
			RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

			if (!itemStack.isEmpty()) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + xStart, y + yStart, z + zStart);
				GlStateManager.translate((0.5D - xStart) * (i % 3), 0.05D, (0.5D - zStart) * Math.floor((double) i / 3));
				if(i == 0 || i == 8) rotationAngle = 20.0F;
				else if(i == 2 || i == 6) rotationAngle = -20.0F;
				else rotationAngle = 0.0F;
				GlStateManager.rotate(rotationAngle, 0.0F,1.0F,0.0F);
				Item item = itemStack.getItem();
				if(item instanceof ItemBlock){
					Block block = ((ItemBlock)item).getBlock();
					if(block instanceof IBlockSpecialDisplay){
						float scale = ((IBlockSpecialDisplay)block).getDisplayScale(tileEntity.getBlockMetadata());
						GlStateManager.scale(scale, scale, scale);
						GlStateManager.translate(0.0F, 0.485F, 0.0F);
					}else{
						GlStateManager.scale(0.2F, 0.2F, 0.2F);
						GlStateManager.translate(0.0F, 0.4F, 0.0F);
					}
					itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
				}else{
					GlStateManager.scale(0.3F, 0.3F, 0.3F);
					GlStateManager.rotate(90.0F, 1.0F,0.0F,0.0F);
					itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
				}
				GlStateManager.popMatrix();
			}
		}
	}
}
