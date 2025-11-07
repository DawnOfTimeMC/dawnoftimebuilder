package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class PlaylistButton extends Button {
    private final ResourceLocation buttonTexture;

    public PlaylistButton(int x, int y, OnPress pressable) {
        super(x, y, 12, 12, Component.empty(), pressable, DEFAULT_NARRATION);
        this.buttonTexture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Matrix3x2fStack ps = pGuiGraphics.pose();

        ps.pushMatrix();
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, buttonTexture, this.getX() - 1, this.getY(), 28, this.active ? 0 : 12, 12, 12, 256, 256, this.isHovered() && this.active ? ARGB.colorFromFloat(1.0F, 0.7F, 0.7F, 0.7F) : -1);
        ps.popMatrix();
    }
}
