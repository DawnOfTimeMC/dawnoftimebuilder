package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents.CREATIVE_ICONS;

@OnlyIn(Dist.CLIENT)
public class CategoryButton extends Button {
    private boolean selected;
    private static final ResourceLocation[] BUTTON_ICONS = fillButtonIcons();
    private final int index;

    public CategoryButton(int x, int y, int index, OnPress pressable) {
        super(x, y, 32, 28, Component.empty(), pressable);
        this.selected = false;
        this.index = index;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getCategoryID() {
        return CreativeInventoryEvents.page * 4 + this.index;
    }

    @Override
    public void render(PoseStack ps, int mouseX, int mouseY, float partialTick) {
        if(this.active && this.visible) {
            ps.pushPose();
            RenderSystem.setShaderTexture(0, CREATIVE_ICONS);
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            this.blit(ps, this.x - 1, this.y, 0, (this.selected) ? 0 : 28, 31, 28);
            RenderSystem.disableBlend();
            ps.popPose();

            ps.pushPose();
            RenderSystem.setShaderTexture(0, BUTTON_ICONS[this.getCategoryID()]);
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            blit(ps, this.x + ((this.selected) ? 6 : 9), this.y + 6, 0, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
            ps.popPose();
        }
    }

    private static ResourceLocation[] fillButtonIcons() {
        int number = CreativeInventoryCategories.values().length;
        ResourceLocation[] table = new ResourceLocation[number];
        for(int i = 0; i < number; i++) {
            table[i] = new ResourceLocation(MOD_ID, "textures/item/logo_" + CreativeInventoryCategories.values()[i].getName() + ".png");
        }
        return table;
    }
}
