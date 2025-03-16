package org.dawnoftimebuilder.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


import org.dawnoftimebuilder.container.DisplayerMenu;

import static org.dawnoftimebuilder.DoTBCommon.MOD_ID;


public class DisplayerScreen extends AbstractContainerScreen<DisplayerMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.tryParse(MOD_ID + ":textures/gui/displayer_gui.png");

    public DisplayerScreen(DisplayerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 256;
        this.imageHeight = 238;
        this.inventoryLabelX = 20;
        this.inventoryLabelY = 157;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int widthPosition = (this.width - this.imageWidth) / 2;
        int heightPosition = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(GUI_TEXTURE, widthPosition, heightPosition, 0, 0, this.imageWidth, this.imageHeight);
    }
}
