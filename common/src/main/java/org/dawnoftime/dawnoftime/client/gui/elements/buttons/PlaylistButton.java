package org.dawnoftime.dawnoftime.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class PlaylistButton extends Button {
    private final ResourceLocation buttonTexture;

    public PlaylistButton(int x, int y, OnPress pressable) {
        super(x, y, 12, 12, Component.empty(), pressable, DEFAULT_NARRATION);
        this.buttonTexture = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack ps = pGuiGraphics.pose();

        if(this.isHovered())
            pGuiGraphics.setColor(0.7F, 0.7F, 0.7F, 1.0F);

        ps.pushPose();
        RenderSystem.enableBlend();
        pGuiGraphics.blit(buttonTexture, this.getX() - 1, this.getY(), 28, this.active ? 0 : 12, 12, 12);
        RenderSystem.disableBlend();
        ps.popPose();

        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
