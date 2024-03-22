package org.dawnoftimebuilder.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;
import org.dawnoftimebuilder.blockentity.DisplayerBlockEntity;

@OnlyIn(Dist.CLIENT)
public class DisplayerBERenderer implements BlockEntityRenderer<DisplayerBlockEntity> {
	public DisplayerBERenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(DisplayerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		pBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
			BlockState state = pBlockEntity.getBlockState();
			DisplayerBlock block = (DisplayerBlock) state.getBlock();
			double xStart = block.getDisplayerX(state);
			double yStart = block.getDisplayerY(state);
			double zStart = block.getDisplayerZ(state);

			ItemStack itemStack;
			float rotationAngle;

			int j = (int) pBlockEntity.getBlockPos().asLong();

			for(int i = 0; i < 9; i++) {
				//After checking if the capability is there and set all the variables, we render each of the non-empty itemStack
				itemStack = h.getStackInSlot(i);
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
						pPoseStack.mulPose(Vector3f.YP.rotationDegrees(rotationAngle));
						Block blockFromItem = ((BlockItem) item).getBlock();
						if (blockFromItem instanceof IBlockSpecialDisplay) {
							float scale = ((IBlockSpecialDisplay) blockFromItem).getDisplayScale();
							pPoseStack.scale(scale, scale, scale);
							pPoseStack.translate(0.0F, 0.485F, 0.0F);
						} else {
							pPoseStack.scale(0.2F, 0.2F, 0.2F);
							pPoseStack.translate(0.0F, 0.45F, 0.0F);
						}
						itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, i + j);
					} else {
						pPoseStack.scale(0.3F, 0.3F, 0.3F);
						pPoseStack.mulPose(Vector3f.YP.rotationDegrees(rotationAngle + 90.0F));
						pPoseStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
						itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, i + j);
					}
					pPoseStack.popPose();
				}
			}
		});
	}
}
