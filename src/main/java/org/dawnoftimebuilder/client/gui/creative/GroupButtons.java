package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class GroupButtons extends GuiButton {
    private static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");

    private CreativeInventoryCategories category;
    private boolean selected;
    private ResourceLocation buttonIconLoc;

    public GroupButtons(int buttonId, int x, int y, int widthIn, int heightIn, CreativeInventoryCategories category) {
        super(buttonId, x, y, widthIn, heightIn, "");

        this.category = category;
        this.selected = false;
        this.visible = false;
        this.buttonIconLoc = new ResourceLocation(MOD_ID, "textures/gui/logo_" + category.name().toLowerCase() + ".png");
    }

    public CreativeInventoryCategories getCategory() {
        return category;
    }

    public void setSelected() {
        this.selected = true;
    }

    public void deSelect() {
        this.selected = false;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(CREATIVE_ICONS);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();

            if(selected) {
                this.drawTexturedModalRect(this.x, this.y, 0, 0, 31, 28);
            } else {
                this.drawTexturedModalRect(this.x, this.y, 0, 28, 31, 28);
            }

            mc.getTextureManager().bindTexture(this.buttonIconLoc);
            if(selected) {
                drawScaledCustomSizeModalRect(this.x + 9, this.y + 6, 0, 0, 16, 16, 16, 16, 16, 16);
            } else {
                drawScaledCustomSizeModalRect(this.x + 11, this.y + 6, 0, 0, 16, 16, 16, 16, 16, 16);
            }
        }
    }
}
