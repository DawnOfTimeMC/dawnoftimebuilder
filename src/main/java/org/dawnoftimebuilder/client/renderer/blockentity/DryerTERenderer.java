package org.dawnoftimebuilder.client.renderer.blockentity;
/*
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

@OnlyIn(Dist.CLIENT)
public class DryerTERenderer extends TileEntityRenderer<DryerTileEntity> {

    public DryerTERenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
	public void render(DryerTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            this.renderItemModel(matrixStack, h.getStackInSlot(0), buffer, combinedLightIn, combinedOverlayIn);
            matrixStack.translate(0, 0.5D, 0);
            this.renderItemModel(matrixStack, h.getStackInSlot(1), buffer, combinedLightIn, combinedOverlayIn);
        });
	}

	public void renderItemModel(MatrixStack matrixStack, ItemStack itemStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn){
	    if(itemStack.isEmpty()) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        boolean isBlockItem = itemStack.getItem() instanceof BlockItem;
        for(int i = 0; i < 4; i++) {
            matrixStack.pushPose();
            matrixStack.translate(0.35D, 0, 0.35D);
            matrixStack.translate((i == 1 || i == 2) ? 0.3D : 0, 0.1D, i >= 2 ? 0.3D : 0);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(90.0F * i));
            if (isBlockItem) {
                matrixStack.scale(0.2F, 0.2F, 0.2F);
                matrixStack.translate(0.0F, 0.4F, 0.0F);
                itemRenderer.renderStatic(itemStack, ItemCameraTransforms.TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
            } else {
                matrixStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
                matrixStack.scale(0.3F, 0.3F, 0.3F);
                itemRenderer.renderStatic(itemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
            }
            matrixStack.popPose();
        }
    }
}*/