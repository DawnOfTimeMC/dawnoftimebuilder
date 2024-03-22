package org.dawnoftimebuilder.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.dawnoftimebuilder.blockentity.DryerBlockEntity;

@OnlyIn(Dist.CLIENT)
public class DryerBERenderer implements BlockEntityRenderer<DryerBlockEntity> {
    public DryerBERenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(DryerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            this.renderItemModel(pBlockEntity, pPoseStack, h.getStackInSlot(0), pBuffer, pPackedLight, pPackedOverlay);
            pPoseStack.translate(0, 0.5D, 0);
            this.renderItemModel(pBlockEntity, pPoseStack, h.getStackInSlot(1), pBuffer, pPackedLight, pPackedOverlay);
        });
    }

	public void renderItemModel(DryerBlockEntity dryerBlockEntity, PoseStack poseStack, ItemStack itemStack, MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
	    if(itemStack.isEmpty())
            return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        boolean isBlockItem = itemStack.getItem() instanceof BlockItem;
        Level level = dryerBlockEntity.getLevel();

        int j = (int) dryerBlockEntity.getBlockPos().asLong();

        for(int i = 0; i < 4; i++) {
            poseStack.pushPose();
            poseStack.translate(0.35D, 0, 0.35D);
            poseStack.translate((i == 1 || i == 2) ? 0.3D : 0, 0.1D, i >= 2 ? 0.3D : 0);
            poseStack.mulPose(Vector3f.YN.rotationDegrees(90.0F * i));
            if (isBlockItem) {
                poseStack.scale(0.2F, 0.2F, 0.2F);
                poseStack.translate(0.0F, 0.4F, 0.0F);
                itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, pPackedLight, pPackedOverlay, poseStack, buffer, i + j);
            } else {
                poseStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
                poseStack.scale(0.3F, 0.3F, 0.3F);
                itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, pPackedLight, pPackedOverlay, poseStack, buffer, i + j);
            }
            poseStack.popPose();
        }
    }
}