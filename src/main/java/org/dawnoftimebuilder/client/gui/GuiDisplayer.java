package org.dawnoftimebuilder.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.inventory.ContainerDisplayer;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDisplayer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@SideOnly(Side.CLIENT)
public class GuiDisplayer extends GuiContainer {

    private static ResourceLocation GUI_CONTAINER = new ResourceLocation(MOD_ID + ":textures/gui/displayer_gui.png");

    public GuiDisplayer(InventoryPlayer inventoryPlayer, DoTBTileEntityDisplayer inventoryDisplayer, EntityPlayer player) {
        super(new ContainerDisplayer(inventoryPlayer, inventoryDisplayer, player));
        this.xSize = 256;
        this.ySize = 238;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_CONTAINER);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
