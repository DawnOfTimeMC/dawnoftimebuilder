package org.dawnoftime.dawnoftime.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.block.templates.DisplayerBlock;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;

public class DisplayerBERenderer implements BlockEntityRenderer<DisplayerBlockEntity> {
	public DisplayerBERenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(DisplayerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
			BlockState state = pBlockEntity.getBlockState();
			Level level = pBlockEntity.getLevel();
			DisplayerBlock block = (DisplayerBlock) state.getBlock();
			double xStart = block.getDisplayerX(state);
			double yStart = block.getDisplayerY(state);
			double zStart = block.getDisplayerZ(state);

			ItemStack itemStack;
			float rotationAngle;

			int j = (int) pBlockEntity.getBlockPos().asLong();

			for(int i = 0; i < 9; i++) {
				//After checking if the capability is there and set all the variables, we render each of the non-empty itemStack
				itemStack = pBlockEntity.itemHandler.getItem(i);
				ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

				if (!itemStack.isEmpty()) {
					pPoseStack.pushPose();
					pPoseStack.translate(xStart, yStart, zStart);
					pPoseStack.translate((0.5D - xStart) * (i % 3), 0.015D, (0.5D - zStart) * Math.floor((double) i / 3));
					if (i == 0 || i == 8) rotationAngle = 20.0F;
					else if (i == 2 || i == 6) rotationAngle = -20.0F;
					else rotationAngle = 0.0F;
					Item item = itemStack.getItem();
					if (item instanceof BlockItem) {
						pPoseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
						Block blockFromItem = ((BlockItem) item).getBlock();
						if (blockFromItem instanceof IBlockSpecialDisplay) {
							float scale = ((IBlockSpecialDisplay) blockFromItem).getDisplayScale();
							pPoseStack.scale(scale, scale, scale);
							pPoseStack.translate(0.0F, 0.485F, 0.0F);
						} else {
							pPoseStack.scale(0.2F, 0.2F, 0.2F);
							pPoseStack.translate(0.0F, 0.45F, 0.0F);
						}
						itemRenderer.renderStatic(itemStack, ItemDisplayContext.NONE, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, level, i + j);
					} else {
						pPoseStack.scale(0.3F, 0.3F, 0.3F);
						pPoseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle + 90.0F));
						pPoseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
						itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, level, i + j);
					}
					pPoseStack.popPose();
				}
			}
	}
}
