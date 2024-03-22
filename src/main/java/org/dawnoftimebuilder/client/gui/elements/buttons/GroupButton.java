package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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

    public GroupButton(int x, int y, Component message, OnPress pressable, ResourceLocation iconResource, int iconU, int iconV) {
        super(x, y, 20, 20, message, pressable);
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void render(PoseStack ps, int mouseX, int mouseY, float partialTicks){
        super.render(ps, mouseX, mouseY, partialTicks);
        if(!this.visible)
            return;
        ps.pushPose();
        if(!this.active) RenderSystem.clearColor(0.5F, 0.5F, 0.5F, 1.0F);
        RenderSystem.setShaderTexture(0, this.iconResource);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        blit(ps, this.x + 2, this.y + 2, this.iconU, this.iconV, 16, 16);
        RenderSystem.disableBlend();
        ps.popPose();
    }
}
