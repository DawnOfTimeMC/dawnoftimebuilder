package org.dawnoftimebuilder.client.gui.filters;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;

import javax.annotation.Nonnull;

public class IconButton extends ImageButton {

    private final TextComponent message;
    private boolean enabled = true;
    public final int x,y;

    public IconButton(TextComponent message, int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, IPressable onPressIn) {
        super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn);
        this.message = message;
        this.x = xIn;
        this.y = yIn;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public @Nonnull TextComponent getMessage() {
        return message;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render the button, if enabled is true!
        if (enabled) super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    }

}
