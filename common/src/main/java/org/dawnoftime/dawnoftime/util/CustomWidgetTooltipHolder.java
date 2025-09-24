package org.dawnoftime.dawnoftime.util;

import net.minecraft.client.gui.components.WidgetTooltipHolder;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import org.jetbrains.annotations.NotNull;

public class CustomWidgetTooltipHolder extends WidgetTooltipHolder {

    @Override
    public @NotNull ClientTooltipPositioner createTooltipPositioner(@NotNull ScreenRectangle screenRectangle, boolean hovering, boolean focused) {
        return DefaultTooltipPositioner.INSTANCE;
    }
}
