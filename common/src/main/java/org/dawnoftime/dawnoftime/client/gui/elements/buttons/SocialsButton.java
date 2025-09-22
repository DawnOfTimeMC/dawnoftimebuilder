package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class SocialsButton extends Button {
    private final ResourceLocation buttonIcons, socialIcons;

    public SocialsButton(int x, int y, String buttonName, OnPress pressable) {
        super(x, y, 28, 28, Component.empty(), pressable, DEFAULT_NARRATION);
        this.buttonIcons = new ResourceLocation(MOD_ID, "textures/gui/social_" + buttonName + ".png");
        this.socialIcons = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(this.active) {
            PoseStack ps = pGuiGraphics.pose();

            if(this.isHovered())
                pGuiGraphics.setColor(0.7F, 0.7F, 0.7F, 1.0F);

            ps.pushPose();
            RenderSystem.enableBlend();
            pGuiGraphics.blit(socialIcons, this.getX() - 1, this.getY(), 0, 0, 28, 28);
            RenderSystem.disableBlend();
            ps.popPose();

            ps.pushPose();
            RenderSystem.enableBlend();
            pGuiGraphics.blit(buttonIcons, this.getX() + 3, this.getY() + 4, 0, 0, 0, 20, 20, 20, 20);
            RenderSystem.disableBlend();
            ps.popPose();

            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return (screenWidth, screenHeight, mouseX, mouseY, tooltipWidth, tooltipHeight) -> {
            int tooltipX = SocialsButton.this.getX() + SocialsButton.this.width + 2;
            int tooltipY = SocialsButton.this.getY();

            if (tooltipX + tooltipWidth > screenWidth) {
                tooltipX = SocialsButton.this.getX() - tooltipWidth - 2;
            }
            if (tooltipY + tooltipHeight > screenHeight) {
                tooltipY = screenHeight - tooltipHeight;
            }
            if (tooltipY < 0) {
                tooltipY = 0;
            }

            return new Vector2i(tooltipX, tooltipY);
        };
    }
}
