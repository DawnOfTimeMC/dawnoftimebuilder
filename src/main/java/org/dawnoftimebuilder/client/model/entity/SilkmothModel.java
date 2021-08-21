package org.dawnoftimebuilder.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.entity.SilkmothEntity;

@OnlyIn(Dist.CLIENT)
public class SilkmothModel extends EntityModel<SilkmothEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel wingLeft;
	private final RendererModel wingRight;
	private final RendererModel legs;
	private final RendererModel antennaLeft;
	private final RendererModel antennaRight;

	public SilkmothModel(){
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.body = new RendererModel(this, 0, 13);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-1.0F, -1.0F, -0.2F, 2, 2, 5, -0.2F);
		this.setRotateAngle(body, -0.175F, 0.0F, 0.0F);
		this.body.offsetY = 1.4F;
		this.head = new RendererModel(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 3, -1.0F);
		this.setRotateAngle(head, 0.35F, 0.0F, 0.0F);
		this.legs = new RendererModel(this, 9, 8);
		this.legs.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legs.addBox(-2.5F, -2.0F, -1.8F, 5, 5, 5, -1.6F);
		this.antennaLeft = new RendererModel(this, 12, -7);
		this.antennaLeft.setRotationPoint(0.4F, -0.5F, -0.8F);
		this.antennaLeft.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaLeft, -0.53F, -1.05F, 0.0F);
		this.antennaRight = new RendererModel(this, 12, -7);
		this.antennaRight.setRotationPoint(-0.4F, -0.5F, -0.8F);
		this.antennaRight.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaRight, -0.53F, 1.05F, 0.0F);
		this.wingLeft = new RendererModel(this, -12, 20);
		this.wingLeft.mirror = true;
		this.wingLeft.setRotationPoint(0.3F, -0.8F, 0.5F);
		this.wingLeft.addBox(-3.5F, -3.5F, -3.5F, 14, 7, 12, -3.5F);
		this.setRotateAngle(wingLeft, 0.0F, 0.26F, 0.0F);
		this.wingRight = new RendererModel(this, -12, 20);
		this.wingRight.setRotationPoint(-0.3F, -0.8F, 0.5F);
		this.wingRight.addBox(-10.5F, -3.5F, -3.5F, 14, 7, 12, -3.5F);
		this.setRotateAngle(wingRight, 0.0F, -0.26F, 0.0F);

		this.body.addChild(this.head);
		this.head.addChild(this.antennaLeft);
		this.head.addChild(this.antennaRight);
		this.body.addChild(this.legs);
		this.body.addChild(this.wingRight);
		this.body.addChild(this.wingLeft);
	}

	@Override
	public void render(SilkmothEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		float scale1 = 0.5F;
		this.body.render(scale1 * scale);
	}

	@Override
	public void setRotationAngles(SilkmothEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		float flap = 5.0F;
		float d = Math.abs((ageInTicks % flap) / flap - 0.5F) ;
		this.wingLeft.rotateAngleZ = (float) (Math.PI * (0.2F - d));
		this.wingRight.rotateAngleZ = (float) (Math.PI * (-0.2F + d));
		this.head.rotateAngleX = 0.35F + headPitch * 0.017453292F;
	}

	private void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}