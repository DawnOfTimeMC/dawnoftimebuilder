package org.dawnoftimebuilder.mixin.impl;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.WidgetSprites;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractButton.class)
public interface AbstractButtonAccessor {

    @Accessor("SPRITES")
    WidgetSprites getSprites();
}
