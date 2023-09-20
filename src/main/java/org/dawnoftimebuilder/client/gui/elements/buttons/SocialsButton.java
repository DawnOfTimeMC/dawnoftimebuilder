package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SocialsButton extends Button {

    private final ResourceLocation buttonIcons, socialIcons;

    public SocialsButton(int x, int y, String buttonName, IPressable pressable) {
        super(x, y, 32, 28, StringTextComponent.EMPTY, pressable);
        this.buttonIcons = new ResourceLocation(MOD_ID, "textures/gui/social_" + buttonName + ".png");
        this.socialIcons = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.active) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(socialIcons);
            RenderSystem.disableLighting();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            this.blit(matrixStack, this.x - 1, this.y, 0, (this.isHovered()) ? 28 : 0, 28, 28);

            mc.getTextureManager().bind(buttonIcons);
            blit(matrixStack, this.x + 3, this.y + 4, 0, 0, 0, 20, 20, 20, 20);
        }
    }
}
