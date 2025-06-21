package org.dawnoftime.dawnoftime.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.dawnoftime.dawnoftime.client.model.entity.SilkmothModel;
import org.dawnoftime.dawnoftime.entity.SilkmothEntity;
import org.jetbrains.annotations.NotNull;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public class SilkmothRenderer extends MobRenderer<SilkmothEntity, SilkmothModel<SilkmothEntity>> {
	private static final ResourceLocation SILKMOTH_TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/silkmoth.png");

	public SilkmothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SilkmothModel<>(ctx.bakeLayer(SilkmothModel.LAYER_LOCATION)), 0.1F);
	}

	@Override
    @NotNull
	public ResourceLocation getTextureLocation(@NotNull SilkmothEntity entity) {
		return SILKMOTH_TEXTURE;
	}
}
