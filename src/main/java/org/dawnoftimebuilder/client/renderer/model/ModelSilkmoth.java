package org.dawnoftimebuilder.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelSilkmoth extends ModelBase {

	private ModelRenderer body;
	private ModelRenderer head;
	private ModelRenderer wingLeft;
	private ModelRenderer wingRight;
	private ModelRenderer legs;
	private ModelRenderer antennaLeft;
	private ModelRenderer antennaRight;
	private float scale = 0.5F;

	public ModelSilkmoth(){
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.body = new ModelRenderer(this, 0, 13);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-1.0F, -1.0F, -0.2F, 2, 2, 5, -0.2F);
		this.setRotateAngle(body, -0.175F, 0.0F, 0.0F);
		this.body.offsetY = 1.4F;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 3, -1.0F);
		this.setRotateAngle(head, 0.35F, 0.0F, 0.0F);
		this.legs = new ModelRenderer(this, 9, 8);
		this.legs.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legs.addBox(-2.5F, -2.0F, -1.8F, 5, 5, 5, -1.6F);
		this.antennaLeft = new ModelRenderer(this, 12, -7);
		this.antennaLeft.setRotationPoint(0.4F, -0.5F, -0.8F);
		this.antennaLeft.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaLeft, -0.53F, -1.05F, 0.0F);
		this.antennaRight = new ModelRenderer(this, 12, -7);
		this.antennaRight.setRotationPoint(-0.4F, -0.5F, -0.8F);
		this.antennaRight.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaRight, -0.53F, 1.05F, 0.0F);
		this.wingLeft = new ModelRenderer(this, -12, 20);
		this.wingLeft.mirror = true;
		this.wingLeft.setRotationPoint(0.3F, -0.8F, 0.5F);
		this.wingLeft.addBox(-3.5F, -3.5F, -3.5F, 14, 7, 12, -3.5F);
		this.setRotateAngle(wingLeft, 0.0F, 0.26F, 0.0F);
		this.wingRight = new ModelRenderer(this, -12, 20);
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
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadyaw, float headPitch, float scaleFactor) {
		this.body.render(this.scale * scaleFactor);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, this.scale * scaleFactor, entityIn);

		float flap = 5.0F;
		float d = Math.abs((ageInTicks % flap) / flap - 0.5F) ;
		this.wingLeft.rotateAngleZ = (float) (Math.PI * (0.2F - d));
		this.wingRight.rotateAngleZ = (float) (Math.PI * (-0.2F + d));

		this.head.rotateAngleX = 0.35F + headPitch * 0.017453292F;
	}

	private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}