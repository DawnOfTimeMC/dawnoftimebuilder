package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.dawnoftime.dawnoftime.util.CustomWidgetTooltipHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class SocialsButton extends Button {
    private final ResourceLocation buttonIcons, socialIcons;

    public SocialsButton(int x, int y, String buttonName, OnPress pressable) {
        super(x, y, 28, 28, Component.empty(), pressable, DEFAULT_NARRATION);
        this.buttonIcons = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/social_" + buttonName + ".png");
        this.socialIcons = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/social_icons.png");
        this.tooltip = new CustomWidgetTooltipHolder();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(this.active) {
            Matrix3x2fStack ps = pGuiGraphics.pose();

            ps.pushMatrix();
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, socialIcons, this.getX() - 1, this.getY(), 0, 0, 28, 28, 256, 256, !this.isHovered() ? -1 : ARGB.colorFromFloat(1.0F, 0.7F, 0.7F, 0.7F));
            ps.popMatrix();

            ps.pushMatrix();
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, buttonIcons, this.getX() + 3, this.getY() + 4, 0, 0, 20, 20, 20, 20, !this.isHovered() ? -1 : ARGB.colorFromFloat(1.0F, 0.7F, 0.7F, 0.7F));
            ps.popMatrix();
        }
    }
}
