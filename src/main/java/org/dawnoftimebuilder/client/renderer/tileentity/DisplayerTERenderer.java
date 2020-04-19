package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

public class DisplayerTERenderer extends TileEntityRenderer<DisplayerTileEntity> {

	@Override
	public void render(DisplayerTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage){
		ItemStack itemStack;
		double xStart = 0.0D;
		double yStart = 0.0D;
		double zStart = 0.0D;
		float rotationAngle;
		BlockState state = tileEntity.getBlockState();
		if(state.getBlock() instanceof DisplayerBlock){
			DisplayerBlock block = (DisplayerBlock) state.getBlock();
			xStart = block.getDisplayerX(state);
			yStart = block.getDisplayerY(state);
			zStart = block.getDisplayerZ(state);
		}

		for(int i = 0; i < 9; i++){
			itemStack = tileEntity.getStackInSlot(i);
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

			if (!itemStack.isEmpty()) {
				GlStateManager.pushMatrix();
				GlStateManager.translated(x + xStart, y + yStart, z + zStart);
				GlStateManager.translated((0.5D - xStart) * (i % 3), 0.05D, (0.5D - zStart) * Math.floor((double) i / 3));
				if(i == 0 || i == 8) rotationAngle = 20.0F;
				else if(i == 2 || i == 6) rotationAngle = -20.0F;
				else rotationAngle = 0.0F;
				GlStateManager.rotated(rotationAngle, 0.0F,1.0F,0.0F);
				Item item = itemStack.getItem();
				if(item instanceof BlockItem){
					Block block = ((BlockItem)item).getBlock();
					if(block instanceof IBlockSpecialDisplay){
						float scale = ((IBlockSpecialDisplay)block).getDisplayScale();
						GlStateManager.scaled(scale, scale, scale);
						GlStateManager.translated(0.0F, 0.485F, 0.0F);
					}else{
						GlStateManager.scaled(0.2F, 0.2F, 0.2F);
						GlStateManager.translated(0.0F, 0.4F, 0.0F);
					}
					itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
				}else{
					GlStateManager.scaled(0.3F, 0.3F, 0.3F);
					GlStateManager.rotated(90.0F, 1.0F,0.0F,0.0F);
					itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
				}
				GlStateManager.popMatrix();
			}
		}
	}
}
