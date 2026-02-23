package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class SubTabButton extends Button {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    private boolean selected;

    public SubTabButton(int x, int y, Component tooltip, OnPress pressable) {
        super(x, y, 12, 12, Component.empty(), pressable, DEFAULT_NARRATION);
        this.setTooltip(Tooltip.create(tooltip));
        this.selected = false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack ps = pGuiGraphics.pose();

        if ((this.isHovered() || this.selected) && this.active) {
            pGuiGraphics.setColor(0.7F, 0.7F, 0.7F, 1.0F);
        }

        ps.pushPose();
        RenderSystem.enableBlend();
        pGuiGraphics.blit(TEXTURE, this.getX() - 1, this.getY(), 28, this.active ? 0 : 12, 12, 12);
        RenderSystem.disableBlend();
        ps.popPose();

        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public @Nullable Tooltip getTooltip() {
        return this.active ? super.getTooltip() : null;
    }
}
