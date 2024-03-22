package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SocialsButton extends Button {
    private final ResourceLocation buttonIcons, socialIcons;

    public SocialsButton(int x, int y, String buttonName, OnPress pressable) {
        super(x, y, 32, 28, Component.empty(), pressable);
        this.buttonIcons = new ResourceLocation(MOD_ID, "textures/gui/social_" + buttonName + ".png");
        this.socialIcons = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    public void render(PoseStack ps, int mouseX, int mouseY, float partialTicks) {
        if (this.active && this.visible) {
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if(this.isHovered)
                RenderSystem.clearColor(0.7F, 0.7F, 0.7F, 1.0F);

            ps.pushPose();
            RenderSystem.setShaderTexture(0, socialIcons);
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            blit(ps, this.x - 1, this.y, 0, (this.isHovered) ? 28 : 0, 28, 28);
            RenderSystem.disableBlend();
            ps.popPose();

            ps.pushPose();
            RenderSystem.setShaderTexture(0, buttonIcons);
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            blit(ps, this.x + 3, this.y + 4, 0, 0, 0, 20, 20, 20, 20);
            RenderSystem.disableBlend();
            ps.popPose();
        }
    }
}
