package org.dawnoftimebuilder.client.gui.elements.buttons;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class IconButton extends ImageButton {

    private final Component message;
    private boolean enabled = true;
    public final int x,y;

    public IconButton(Component message, int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, OnPress onPressIn) {
        super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn);
        this.message = message;
        this.x = xIn;
        this.y = yIn;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public @Nonnull Component getMessage() {
        return message;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render the button, if enabled is true!
        if (enabled) super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

}
