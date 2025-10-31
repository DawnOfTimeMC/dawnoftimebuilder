package org.dawnoftime.dawnoftime.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.dawnoftime.dawnoftime.entity.ChairEntity;
import org.jetbrains.annotations.NotNull;

public class ChairRenderer extends EntityRenderer<ChairEntity, EntityRenderState> {
    public ChairRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public @NotNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
