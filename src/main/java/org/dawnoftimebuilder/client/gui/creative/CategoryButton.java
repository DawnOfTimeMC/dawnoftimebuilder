package org.dawnoftimebuilder.client.gui.creative;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents.CREATIVE_ICONS;

public class CategoryButton extends Button {

    private CreativeInventoryCategories category;
    private boolean selected;
    private ResourceLocation buttonIconLoc;

    public CategoryButton(int x, int y, CreativeInventoryCategories category, IPressable pressable){
        super(x, y, 32, 28, "", pressable);
        this.category = category;
        this.selected = false;
        this.buttonIconLoc = new ResourceLocation(MOD_ID, "textures/gui/logo_" + category.getName() + ".png");
    }

    public CreativeInventoryCategories getCategory() {
        return this.category;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(CREATIVE_ICONS);
        GlStateManager.disableLighting();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        GlStateManager.enableBlend();
        this.blit(this.x - 1, this.y, 0, (this.selected) ? 0 : 28, 31, 28);

        mc.getTextureManager().bindTexture(this.buttonIconLoc);
        blit(this.x + ((this.selected) ? 6 : 9), this.y + 6, 0, 0, 0, 16, 16, 16, 16);
    }
}