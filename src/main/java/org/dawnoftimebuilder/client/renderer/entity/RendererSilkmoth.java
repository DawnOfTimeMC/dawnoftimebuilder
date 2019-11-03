package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.dawnoftimebuilder.client.renderer.model.ModelSilkmoth;
import org.dawnoftimebuilder.entities.EntitySilkmoth;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class RendererSilkmoth extends RenderLiving<EntitySilkmoth> implements IRenderFactory {

	public static final IRenderFactory<EntitySilkmoth> FACTORY = RendererSilkmoth::new;

	private RendererSilkmoth(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSilkmoth(), 0.1F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySilkmoth entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/silkmoth.png");
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return manager.getEntityClassRenderObject(EntitySilkmoth.class);
	}
}
