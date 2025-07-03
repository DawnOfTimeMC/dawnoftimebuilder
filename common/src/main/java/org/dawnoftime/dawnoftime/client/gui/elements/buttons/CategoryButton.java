package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftime.dawnoftime.mixin.api.CreativeScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.dawnoftime.dawnoftime.DoTBCommon.CREATIVE_ICONS;
import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class CategoryButton extends Button {
    private final CreativeScreen parent;
    private boolean selected;
    private static final ResourceLocation[] BUTTON_ICONS = fillButtonIcons();
    private static final Tooltip[] BUTTON_TOOLTIPS = fillButtonTooltips();
    private final int index;

    public CategoryButton(int x, int y, int index, OnPress pressable, CreativeScreen parent) {
        super(x, y, 32, 28, Component.empty(), pressable, DEFAULT_NARRATION);
        this.selected = false;
        this.index = index;
        this.parent = parent;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getCategoryID() {
        return parent.dOTBuilder$getPage() * 4 + this.index;
    }

    public @Nullable Tooltip getTooltipForCategory() {
        int id = this.getCategoryID();
        if (id < BUTTON_TOOLTIPS.length && id >= 0) {
            return BUTTON_TOOLTIPS[id];
        } else {
            return null;
        }
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(this.active) {
            PoseStack ps = pGuiGraphics.pose();

            ps.pushPose();
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            pGuiGraphics.blit(CREATIVE_ICONS, this.getX() - 1, this.getY(), 0, (this.selected) ? 0 : 28, 31, 28);
            RenderSystem.disableBlend();
            ps.popPose();

            ps.pushPose();
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            pGuiGraphics.blit(BUTTON_ICONS[this.getCategoryID()], this.getX() + ((this.selected) ? 6 : 9), this.getY() + 6, 0, 0, 0, 16, 16, 16, 16);
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

    private static Tooltip[] fillButtonTooltips() {
        int number = CreativeInventoryCategories.values().length;
        Tooltip[] tooltips = new Tooltip[number];
        for(int i = 0; i < number; i++) {
            tooltips[i] = Tooltip.create(Component.translatable("gui.dawnoftimebuilder." + CreativeInventoryCategories.values()[i].getName()));
        }
        return tooltips;
    }
}
