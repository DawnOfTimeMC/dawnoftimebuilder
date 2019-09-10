package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PageButtons extends GuiButton {
    private static final ResourceLocation CREATIVE_ICONS = new ResourceLocation("dawnoftimebuilder", "textures/gui/creative_icons.png");

    private boolean up;

    public PageButtons(int buttonId, int x, int y, boolean isUp) {
        super(buttonId, x, y, 11, 7, "");
        this.up = isUp;
        this.visible = false;
    }

    public boolean isUp() {
        return up;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(CREATIVE_ICONS);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();

            if(up){
                this.drawTexturedModalRect(this.x, this.y, 0, 57, 11, 7);
            }else{
                this.drawTexturedModalRect(this.x, this.y, 15, 57, 11, 7);
            }
        }
    }
}
