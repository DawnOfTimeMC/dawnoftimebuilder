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
import org.dawnoftimebuilder.blocks.japanese.BlockCastIronTeapot;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityStove;

public class RendererTEStove extends TileEntitySpecialRenderer<DoTBTileEntityStove> {

	@Override
	public void render(DoTBTileEntityStove tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		if(!tileEntity.isEmpty()){
			RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
			ItemStack itemStack = tileEntity.getItemStack();
			Item item = itemStack.getItem();

			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5F, y + 1.0F, z + 0.5F);
			GlStateManager.rotate(90.0F, 0.0F,1.0F,0.0F);

			if(item instanceof ItemBlock) {
				Block block = ((ItemBlock) item).getBlock();

				if(block instanceof BlockCastIronTeapot){
					GlStateManager.translate(0.0F, ((BlockCastIronTeapot) block).getRenderYOffset(itemStack.getMetadata()), 0.0F);
				}

				if (block instanceof IBlockSpecialDisplay) {
					float scale = ((IBlockSpecialDisplay) block).getDisplayScale(tileEntity.getBlockMetadata());
					GlStateManager.scale(scale, scale, scale);
					itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
				}
			}

			GlStateManager.popMatrix();
		}
	}
}
