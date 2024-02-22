package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GroupButton extends Button {

    private final ResourceLocation iconResource;
    private final int iconU;
    private final int iconV;

    public GroupButton(int x, int y, Component message, OnPress pressable, ResourceLocation iconResource, int iconU, int iconV){
        super(x, y, 20, 20, message, pressable, DEFAULT_NARRATION);
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack ps = pGuiGraphics.pose();
        ps.pushPose();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + 2 * 20, this.width / 2, this.height);
        pGuiGraphics.blit(WIDGETS_LOCATION, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, 46 + 2 * 20, this.width / 2, this.height);
        RenderSystem.disableBlend();
        ps.popPose();

        ps.pushPose();
        if(!this.active)
            RenderSystem.clearColor(0.5F, 0.5F, 0.5F, 1.0F);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(iconResource, this.getX() + 2, this.getY() + 2, this.iconU, this.iconV, 16, 16);
        RenderSystem.disableBlend();
        ps.popPose();
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

}
