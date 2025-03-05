package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.jetbrains.annotations.NotNull;

public class ChairRenderer extends EntityRenderer<ChairEntity> {
    public ChairRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ChairEntity p_114482_) {
        return null;
    }
}
