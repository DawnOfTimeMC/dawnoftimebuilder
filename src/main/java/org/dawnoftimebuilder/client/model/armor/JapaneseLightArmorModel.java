package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JapaneseLightArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	private RendererModel headBase;
	private RendererModel knotBase;
	private RendererModel ribbonA;
	private RendererModel ribbonB;

	//Chest
	private RendererModel baseBody;
	private RendererModel armLeft;
	private RendererModel armLeftArmor;
	private RendererModel armRight;
	private RendererModel armRightArmor;
	private RendererModel bodyBreast;

	//Leggings
	private RendererModel legLeftArmor;
	private RendererModel legRightArmor;

	//Boots
	private RendererModel legLeft;
	private RendererModel legRight;

	public JapaneseLightArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 64, 32);

		switch (slot) {
			case HEAD:
				this.headBase = new RendererModel(this, 26, 16);
				this.headBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headBase.addBox(-4.0F, -6.5F, -4.0F, 8, 2, 8, 0.4F);
				this.knotBase = new RendererModel(this, 56, 9);
				this.knotBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.knotBase.addBox(2.9F, -4.9F, 3.7F, 2, 2, 1, 0.4F);
				setRotateAngle(knotBase, 0.0F, 0.0F, -0.785F);
				this.ribbonA = new RendererModel(this, 56, 0);
				this.ribbonA.setRotationPoint(0.0F, -5.0F, 4.2F);
				this.ribbonA.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
				setRotateAngle(ribbonA, 0.35F, 0.0F, 0.0F);
				this.ribbonB = new RendererModel(this, 60, 0);
				this.ribbonB.setRotationPoint(0.0F, -5.0F, 4.2F);
				this.ribbonB.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 0, 0.0F);
				setRotateAngle(ribbonB, 0.175F, 0.0F, 0.0F);

				this.bipedHead = headBase;
				this.bipedHead.addChild(knotBase);
				this.bipedHead.addChild(ribbonA);
				this.bipedHead.addChild(ribbonB);
				break;

			case CHEST:
				this.baseBody = new RendererModel(this, 0, 0);
				this.baseBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.baseBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.45F);
				if(isSteve){
					this.armRight = new RendererModel(this, 24, 0);
					this.armRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armRightArmor = new RendererModel(this, 12, 16);
					this.armRightArmor.mirror = true;
					this.armRightArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightArmor.addBox(-3.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
					this.armLeft = new RendererModel(this, 24, 0);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armLeftArmor = new RendererModel(this, 12, 16);
					this.armLeftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftArmor.addBox(1.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
				}else{
					this.armRight = new RendererModel(this, 24, 0);
					this.armRight.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRight.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armRightArmor = new RendererModel(this, 12, 16);
					this.armRightArmor.mirror = true;
					this.armRightArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightArmor.addBox(-2.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);
					this.armLeft = new RendererModel(this, 24, 0);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armLeftArmor = new RendererModel(this, 12, 16);
					this.armLeftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftArmor.addBox(0.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);
					this.bodyBreast = new RendererModel(this, 0, 26);
					this.bodyBreast.setRotationPoint(0.0F, 1.3F, -2.0F);
					this.bodyBreast.addBox(-3.0F, 0.0F, -3.65F, 6, 2, 3, 0.1F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
				}

				this.bipedBody = baseBody;
				if(!isSteve) this.bipedBody.addChild(bodyBreast);

				this.bipedLeftArm = armLeft;
				this.armLeft.addChild(armLeftArmor);

				this.bipedRightArm = armRight;
				this.armRight.addChild(armRightArmor);
				break;

			case LEGS:
				this.legLeftArmor = new RendererModel(this, 0, 16);
				this.legLeftArmor.mirror = true;
				this.legLeftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legLeftArmor.addBox(0.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotateAngle(legLeftArmor, 0.0F, 0.0F, -0.2F);
				this.legRightArmor = new RendererModel(this, 0, 16);
				this.legRightArmor.setRotationPoint(-0.0F, 0.0F, 0.0F);
				this.legRightArmor.addBox(-2.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotateAngle(legRightArmor, 0.0F, 0.0F, 0.2F);

				this.legLeft = new RendererModel(this, 0, 0);
				this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.bipedLeftLeg = legLeft;
				legLeft.addChild(legLeftArmor);

				this.legRight = new RendererModel(this, 0, 0);
				this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.bipedRightLeg = legRight;
				legRight.addChild(legRightArmor);

				break;

			case FEET:
				this.legLeft = new RendererModel(this, 40, 0);
				this.legLeft.mirror = true;
				this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.legRight = new RendererModel(this, 40, 0);
				this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

				this.bipedLeftLeg = legLeft;

				this.bipedRightLeg = legRight;
				break;

			default:
				break;
		}

	}

	@Override
	public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setVisible(false);

		switch (this.slot) {
			case HEAD:
				bipedHead.showModel = true;
				break;

			case CHEST:
				bipedBody.showModel = true;
				bipedLeftArm.showModel = true;
				bipedRightArm.showModel = true;
				break;

			case LEGS:
			case FEET:
				bipedLeftLeg.showModel = true;
				bipedRightLeg.showModel = true;
				break;

			default:
				break;
		}
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		if (this.slot == EquipmentSlotType.HEAD) {
			float f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + Math.abs(this.bipedRightLeg.rotateAngleX);
			this.ribbonA.rotateAngleX = Math.max(0.35F + f * 0.15F - headPitch * 0.017453292F, 0.2F);
			this.ribbonA.rotateAngleZ = -0.1F + f * 0.2F;
			this.ribbonB.rotateAngleX = Math.max(0.175F + f * 0.1F - headPitch * 0.017453292F, 0.075F);
			this.ribbonB.rotateAngleZ = -f * 0.2F;
		}
	}
}