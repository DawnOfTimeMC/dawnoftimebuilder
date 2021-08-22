package org.dawnoftimebuilder.client.gui.creative;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents.CREATIVE_ICONS;

@OnlyIn(Dist.CLIENT)
public class CategoryButton extends Button {

    private boolean selected;
    private static final ResourceLocation[] BUTTON_ICONS = fillButtonIcons();
    private final int index;

    public CategoryButton(int x, int y, int index, IPressable pressable){
        super(x, y, 32, 28, "", pressable);
        this.selected = false;
        this.index = index;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getCategoryID(){
        return CreativeInventoryEvents.page * 4 + this.index;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if(this.active){
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bindTexture(CREATIVE_ICONS);
            GlStateManager.disableLighting();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            GlStateManager.enableBlend();
            this.blit(this.x - 1, this.y, 0, (this.selected) ? 0 : 28, 31, 28);

            mc.getTextureManager().bindTexture(BUTTON_ICONS[this.getCategoryID()]);
            blit(this.x + ((this.selected) ? 6 : 9), this.y + 6, 0, 0, 0, 16, 16, 16, 16);
        }
    }

    private static ResourceLocation[] fillButtonIcons(){
        int number = CreativeInventoryCategories.values().length;
        ResourceLocation[] table = new ResourceLocation[number];
        for(int i = 0; i < number; i++){
            table[i] = new ResourceLocation(MOD_ID, "textures/gui/logo_" + CreativeInventoryCategories.values()[i].getName() + ".png");
        }
        return table;
    }
}