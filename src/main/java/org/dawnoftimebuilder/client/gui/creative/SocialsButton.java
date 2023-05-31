package org.dawnoftimebuilder.client.gui.creative;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents.SOCIAL_ICONS;

public class SocialsButton extends Button {

    private final ResourceLocation buttonIcons;

    public SocialsButton(int x, int y, String buttonName, IPressable pressable) {
        super(x, y, 32, 28, StringTextComponent.EMPTY, pressable);
        this.buttonIcons = new ResourceLocation(MOD_ID, "textures/gui/social_" + buttonName + ".png");
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.active) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(SOCIAL_ICONS);
            RenderSystem.disableLighting();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            this.blit(matrixStack, this.x - 1, this.y, 0, (this.isHovered()) ? 28 : 0, 28, 28);

            mc.getTextureManager().bind(buttonIcons);
            blit(matrixStack, this.x + 3, this.y + 6, 0, 0, 0, 20, 15, 15, 20);
        }
    }
}
