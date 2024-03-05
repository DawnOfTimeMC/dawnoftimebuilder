package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SocialsButton extends Button {
    private final ResourceLocation buttonIcons, socialIcons;

    public SocialsButton(int x, int y, String buttonName, OnPress pressable) {
        super(x, y, 32, 28, Component.empty(), pressable, DEFAULT_NARRATION);
        this.buttonIcons = new ResourceLocation(MOD_ID, "textures/gui/social_" + buttonName + ".png");
        this.socialIcons = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(this.active && this.visible) {
            PoseStack ps = pGuiGraphics.pose();

            this.isHovered = pMouseX >= this.getX() && pMouseY >= this.getY() && pMouseX < this.getX() + this.width && pMouseY < this.getY() + this.height;

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
}
