package org.dawnoftime.dawnoftime.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.block.templates.DisplayerBlock;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayerBERenderer implements BlockEntityRenderer<DisplayerBlockEntity, DisplayerRenderState> {
    private final ItemModelResolver itemModelResolver;

    public DisplayerBERenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(DisplayerBlockEntity blockEntity, DisplayerRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPos, crumblingOverlay);
        state.items.clear();
        final Level level = blockEntity.getLevel();
        final int seedBase = (int) blockEntity.getBlockPos().asLong();

        // Pré-resolve os modelos dos 9 slots
        for (int i = 0; i < 9; i++) {
            ItemStack stack = blockEntity.getItem(i);
            ItemStackRenderState itemState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(
                    itemState,
                    stack,
                    ItemDisplayContext.FIXED,
                    level,
                    null,
                    seedBase + i
            );
            state.items.add(itemState);
        }
    }

    @Override
    public void submit(DisplayerRenderState renderState, @NotNull PoseStack stack, @NotNull SubmitNodeCollector nodeCollector, @NotNull CameraRenderState cameraRenderState) {
        BlockState state = renderState.blockState;
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        DisplayerBlock block = (DisplayerBlock) state.getBlock();
        double xStart = block.getDisplayerX(state);
        double yStart = block.getDisplayerY(state);
        double zStart = block.getDisplayerZ(state);

        float rotationAngle;

        for (int i = 0; i < 9; i++) {
            BlockEntity be = level.getBlockEntity(renderState.blockPos);
            if (!(be instanceof DisplayerBlockEntity displayerBlockEntity)) return;

            ItemStack itemStack = displayerBlockEntity.getItem(i);
            ItemStackRenderState itemStackRenderState = (i < renderState.items.size()) ? renderState.items.get(i) : null;

            if (itemStackRenderState == null || itemStackRenderState.isEmpty()) continue;

            stack.pushPose();
            stack.translate(xStart, yStart, zStart);
            stack.translate((0.5D - xStart) * (i % 3), 0.015D, (0.5D - zStart) * Math.floor((double) i / 3));
            if (i == 0 || i == 8) rotationAngle = 20.0F;
            else if (i == 2 || i == 6) rotationAngle = -20.0F;
            else rotationAngle = 0.0F;

            Item item = itemStack.getItem();
            if (item instanceof BlockItem) {
                stack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
                Block blockFromItem = ((BlockItem) item).getBlock();
                if (blockFromItem instanceof IBlockSpecialDisplay special) {
                    float scale = special.getDisplayScale();
                    stack.scale(scale, scale, scale);
                    stack.translate(0.0F, 0.485F, 0.0F);
                } else {
                    stack.scale(0.2F, 0.2F, 0.2F);
                    stack.translate(0.0F, 0.45F, 0.0F);
                }
                itemStackRenderState.submit(stack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            } else {
                stack.scale(0.3F, 0.3F, 0.3F);
                stack.mulPose(Axis.YP.rotationDegrees(rotationAngle + 90.0F));
                stack.mulPose(Axis.XN.rotationDegrees(90.0F));
                itemStackRenderState.submit(stack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }
            stack.popPose();
        }
    }

    @Override
    public @NotNull DisplayerRenderState createRenderState() {
        return new DisplayerRenderState();
    }
}