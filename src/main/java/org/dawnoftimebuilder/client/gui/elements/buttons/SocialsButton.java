package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.active) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().getTexture(socialIcons);
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            this.blit(matrixStack, this.getX() - 1, this.getY(), 0, (this.isHovered()) ? 28 : 0, 28, 28);

            mc.getTextureManager().getTexture(buttonIcons);
            blit(matrixStack, this.getX() + 3, this.getY() + 4, 0, 0, 0, 20, 20, 20, 20);
        }
    }
}
