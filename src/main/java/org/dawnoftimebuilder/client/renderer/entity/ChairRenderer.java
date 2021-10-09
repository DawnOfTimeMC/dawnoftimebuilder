package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.entity.ChairEntity;

@OnlyIn(Dist.CLIENT)
public class ChairRenderer extends EntityRenderer<ChairEntity> {

	public ChairRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ChairEntity entity) {
		return null;
	}
}
