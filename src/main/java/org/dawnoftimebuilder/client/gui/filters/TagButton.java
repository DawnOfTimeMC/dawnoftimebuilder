package org.dawnoftimebuilder.client.gui.filters;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.dawnoftimebuilder.util.DoTBFilterEntry;

import javax.annotation.Nonnull;

public class TagButton extends Button {

    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private final DoTBFilterEntry category;
    private final ItemStack stack;
    private boolean selected;
    public final int x;
    public final int y;

    public TagButton(int x, int y, DoTBFilterEntry filter, IPressable onPress) {
        super(x, y, 32 /* width */, 28, new TranslationTextComponent(""), onPress);
        this.category = filter;
        this.stack = filter.getIcon();
        this.selected = filter.isEnabled();
        this.x = x;
        this.y = y;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public DoTBFilterEntry getFilter() {
        return category;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderButton();
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
    }

    public void renderButton() {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(TABS);

        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, alpha /* this.alpha */);
        RenderSystem.disableLighting();
        RenderSystem.enableBlend();

        int width = this.selected ? 32 : 28;
        int textureX = 28;
        int textureY = this.selected ? 32 : 0;
        this.drawRotatedTexture(this.x, this.y, textureX, textureY, width, 28);

        RenderSystem.enableRescaleNormal();
        ItemRenderer renderer = mc.getItemRenderer();
        renderer.blitOffset = 100.0F; // zLevel
        renderer.renderAndDecorateItem(this.stack, x + 8, y + 6);
        renderer.renderGuiItemDecorations(mc.font, this.stack, x + 8, y + 6);
        renderer.blitOffset = 0.0F; // zLevel
    }

    private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, @SuppressWarnings("SameParameterValue") int height) {
        float scaleX = 0.00390625F;
        float scaleY = 0.00390625F;
        Tessellator tesselator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(x, y + height, 0.0).uv((float) (textureX + height) * scaleX, (float) (textureY) * scaleY).endVertex(); // .pos(...).tex(...)
        bufferbuilder.vertex(x + width, y + height, 0.0).uv((float) (textureX + height) * scaleX, (float) (textureY + width) * scaleY).endVertex();
        bufferbuilder.vertex(x + width, y, 0.0).uv((float) (textureX) * scaleX, (float) (textureY + width) * scaleY).endVertex();
        bufferbuilder.vertex(x, y, 0.0).uv((float) (textureX) * scaleX, (float) (textureY) * scaleY).endVertex();
        bufferbuilder.vertex(x, y, 0.0).uv((float) (textureX) * scaleX, (float) (textureY) * scaleY).endVertex();
        tesselator.end();
    }

    public void updateState() {
        this.selected = category.isEnabled();
    }
}
