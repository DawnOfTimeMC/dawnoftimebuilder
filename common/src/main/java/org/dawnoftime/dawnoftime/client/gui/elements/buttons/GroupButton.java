package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.dawnoftime.dawnoftime.mixin.impl.client.AbstractButtonAccessor;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;


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
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(!this.visible)
            return;

        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        Matrix3x2fStack ps = guiGraphics.pose();
        ps.pushMatrix();
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ((AbstractButtonAccessor) this).getSprites().get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ((AbstractButtonAccessor) this).getSprites().get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        ps.popMatrix();

        ps.pushMatrix();
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, iconResource, this.getX() + 2, this.getY() + 2, this.iconU, this.iconV, 16, 16, 256, 256, this.active ? -1 : ARGB.colorFromFloat(1.0F, 0.5F, 0.5F, 0.5F));
        ps.pushMatrix();
    }
}
