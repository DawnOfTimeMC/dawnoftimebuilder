package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.dawnoftimebuilder.client.renderer.model.ModelJapaneseDragon;
import org.dawnoftimebuilder.entities.EntityJapaneseDragon;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class RendererJapaneseDragon extends RenderLiving<EntityJapaneseDragon> implements IRenderFactory {

	public static final IRenderFactory<EntityJapaneseDragon> FACTORY = RendererJapaneseDragon::new;

	private RendererJapaneseDragon(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelJapaneseDragon(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityJapaneseDragon entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/japanese_dragon.png");
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return manager.getEntityClassRenderObject(EntityJapaneseDragon.class);
	}
}
