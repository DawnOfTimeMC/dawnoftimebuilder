package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.entity.SilkmothModel;
import org.dawnoftimebuilder.entity.SilkmothEntity;
import org.jetbrains.annotations.NotNull;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
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
