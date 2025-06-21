package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;




public class GroupButton extends Button {
    private final ResourceLocation iconResource;
    private final int iconU;
    private final int iconV;

    public GroupButton(int x, int y, Component message, OnPress pressable, ResourceLocation iconResource, int iconU, int iconV) {
        super(x, y, 20, 20, message, pressable, DEFAULT_NARRATION);
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(!this.visible)
            return;

        this.isHovered = pMouseX >= this.getX() && pMouseY >= this.getY() && pMouseX < this.getX() + this.width && pMouseY < this.getY() + this.height;

        int offset = this.getTextureY();
        PoseStack ps = pGuiGraphics.pose();
        ps.pushPose();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + offset * 20, this.width / 2, this.height);
        pGuiGraphics.blit(WIDGETS_LOCATION, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, 46 + offset * 20, this.width / 2, this.height);
        RenderSystem.disableBlend();
        ps.popPose();

        ps.pushPose();
        if(!this.active)
            pGuiGraphics.setColor(0.5F, 0.5F, 0.5F, 1.0F);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(iconResource, this.getX() + 2, this.getY() + 2, this.iconU, this.iconV, 16, 16);
        RenderSystem.disableBlend();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        ps.popPose();
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return i;
    }
}
