package org.dawnoftimebuilder.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.container.DisplayerMenu;
import org.jetbrains.annotations.NotNull;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class DisplayerScreen extends AbstractContainerScreen<DisplayerMenu> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(MOD_ID + ":textures/gui/displayer_gui.png");

    public DisplayerScreen(DisplayerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 256;
        this.imageHeight = 238;
        this.inventoryLabelX = 20;
        this.inventoryLabelY = 157;
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int widthPosition = (this.width - this.imageWidth) / 2;
        int heightPosition = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, widthPosition, heightPosition, 0, 0, this.imageWidth, this.imageHeight);
    }
}
