package org.dawnoftimebuilder.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.container.DisplayerContainer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class DisplayerScreen extends ContainerScreen<DisplayerContainer> {

    private static final ResourceLocation GUI_CONTAINER = new ResourceLocation(MOD_ID + ":textures/gui/displayer_gui.png");

    public DisplayerScreen(DisplayerContainer container, PlayerInventory playerInventory, ITextComponent title){
        super(container, playerInventory, ITextComponent.nullToEmpty(null));
        this.imageWidth = 256;
        this.imageHeight = 238;
        this.inventoryLabelX = 20;
        this.inventoryLabelY = 157;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(this.minecraft != null){
            this.minecraft.getTextureManager().bind(GUI_CONTAINER);
            int widthPosition = (this.width - this.imageWidth) / 2;
            int heightPosition = (this.height - this.imageHeight) / 2;
            this.blit(matrixStack, widthPosition, heightPosition, 0, 0, this.imageWidth, this.imageHeight);
        }
    }
}
