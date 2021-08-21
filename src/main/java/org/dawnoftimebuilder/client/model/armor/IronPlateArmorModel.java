package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronPlateArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	public RendererModel baseHelmet;
	public RendererModel earMiddle;
	public RendererModel leftEarBottom;
	public RendererModel leftHead;
	public RendererModel middleBottom;
	public RendererModel middleCraneC;
	public RendererModel middleFeatherA;
	public RendererModel middleFeatherB;
	public RendererModel middleHeadA;
	public RendererModel middleHeadB;
	public RendererModel rightHead;
	public RendererModel rightEarBottom;
	public RendererModel topHead;

	//Chest
	public RendererModel backRightBody;
	public RendererModel backLeftBody;
	public RendererModel bodyBase;
	public RendererModel leftHand;
	public RendererModel leftShoulderA;
	public RendererModel leftShoulderB;
	public RendererModel leftShoulderC;
	public RendererModel leftTopBody;
	public RendererModel middleTopBody;
	public RendererModel miscA;
	public RendererModel rightHand;
	public RendererModel rightShoulderA;
	public RendererModel rightShoulderB;
	public RendererModel rightShoulderC;
	public RendererModel rightTopBody;

	//Leggings
	public RendererModel beltA;
	public RendererModel beltB;
	public RendererModel leftLegging;
	public RendererModel miscB;
	public RendererModel rightLegging;

	//Boots
	public RendererModel leftLeg;
	public RendererModel rightLeg;

	public IronPlateArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 128, 64);

		switch (slot) {
			case HEAD:
				this.baseHelmet = new RendererModel(this, 62, 11);
				this.baseHelmet.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.baseHelmet.addBox(-4.5F, -8.2F, -4.5F, 9, 9, 9, 0.1F);
				this.earMiddle = new RendererModel(this, 57, 49);
				this.earMiddle.setRotationPoint(0.0F, -4.0F, 0.4F);
				this.earMiddle.addBox(-5.5F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
				setRotateAngle(earMiddle, -0.31869712141416456F, 0.0F, 0.0F);
				this.leftEarBottom = new RendererModel(this, 0, 34);
				this.leftEarBottom.mirror = true;
				this.leftEarBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftEarBottom.addBox(4.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.leftHead = new RendererModel(this, 52, 31);
				this.leftHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				setRotateAngle(leftHead, 0.0F, 0.0F, 0.27314402793711257F);
				this.middleBottom = new RendererModel(this, 0, 30);
				this.middleBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleBottom.addBox(-4.0F, -7.5F, -1.5F, 8, 1, 3, 0.0F);
				setRotateAngle(middleBottom, 1.5707963267948966F, 0.0F, 0.0F);
				this.middleCraneC = new RendererModel(this, 45, 28);
				this.middleCraneC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleCraneC.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				setRotateAngle(middleCraneC, -0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadA = new RendererModel(this, 45, 28);
				this.middleHeadA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleHeadA.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				setRotateAngle(middleHeadA, 0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadB = new RendererModel(this, 44, 19);
				this.middleHeadB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleHeadB.addBox(-1.0F, -10.5F, -2.5F, 2, 2, 5, 0.0F);
				this.middleFeatherA = new RendererModel(this, 89, -8);
				this.middleFeatherA.setRotationPoint(0.0F, -8.0F, 5.0F);
				this.middleFeatherA.addBox(-2.0F, -4.5F, -1.5F, 4, 9, 9, -2.0F);
				setRotateAngle(middleFeatherA, -0.19052597169560997F, 0.0F, 0.0F);
				this.middleFeatherB = new RendererModel(this, 89, 2);
				this.middleFeatherB.setRotationPoint(0.0F, 0.0F, 4.5F);
				this.middleFeatherB.addBox(-2.0F, -4.5F, -2.0F, 4, 9, 9, -2.0F);
				setRotateAngle(middleFeatherB, -0.1187509959883641F, 0.0F, 0.0F);
				this.rightEarBottom = new RendererModel(this, 0, 34);
				this.rightEarBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightEarBottom.addBox(-5.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.rightHead = new RendererModel(this, 52, 31);
				this.rightHead.mirror = true;
				this.rightHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				setRotateAngle(rightHead, 0.0F, 0.0F, -0.27314402793711257F);
				this.topHead = new RendererModel(this, 28, 5);
				this.topHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.topHead.addBox(-5.0F, -8.2F, -5.0F, 10, 3, 10, -0.3F);

				this.bipedHead = baseHelmet;
				this.bipedHead.addChild(this.earMiddle);
				this.earMiddle.addChild(this.leftEarBottom);
				this.earMiddle.addChild(this.middleBottom);
				this.earMiddle.addChild(this.rightEarBottom);
				this.bipedHead.addChild(this.leftHead);
				this.bipedHead.addChild(this.middleCraneC);
				this.bipedHead.addChild(this.middleFeatherA);
				this.middleFeatherA.addChild(this.middleFeatherB);
				this.bipedHead.addChild(this.middleHeadA);
				this.bipedHead.addChild(this.middleHeadB);
				this.bipedHead.addChild(this.rightHead);
				this.bipedHead.addChild(this.topHead);
				break;

			case CHEST:
				this.backLeftBody = new RendererModel(this, 32, 27);
				this.backLeftBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.backLeftBody.addBox(-1.45F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				setRotateAngle(backLeftBody, 0.0F, 0.31869712141416456F, 0.0F);
				this.backRightBody = new RendererModel(this, 32, 27);
				this.backRightBody.mirror = true;
				this.backRightBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.backRightBody.addBox(-3.55F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				setRotateAngle(backRightBody, 0.0F, -0.31869712141416456F, 0.0F);
				this.bodyBase = new RendererModel(this, 0, 4);
				this.bodyBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyBase.addBox(-4.5F, -0.1F, -2.5F, 9, 13, 5, 0.0F);
				this.leftShoulderC = new RendererModel(this, 0, 22);
				this.leftShoulderC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftShoulderC.addBox(-0.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				setRotateAngle(leftShoulderC, 0.0F, 0.0F, -0.13962634015954636F);
				this.rightShoulderC = new RendererModel(this, 0, 22);
				this.rightShoulderC.mirror = true;
				this.rightShoulderC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightShoulderC.addBox(-1.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				setRotateAngle(rightShoulderC, 0.0F, 0.0F, 0.13962634015954636F);
				this.leftTopBody = new RendererModel(this, 33, 18);
				this.leftTopBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftTopBody.addBox(-2.9F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				setRotateAngle(leftTopBody, 0.36425021489121656F, -0.4553564018453205F, -0.36425021489121656F);
				this.middleTopBody = new RendererModel(this, 54, 57);
				this.middleTopBody.setRotationPoint(-1.5F, 9.2F, -2.4F);
				this.middleTopBody.addBox(0.0F, -7.0F, 0.0F, 3, 7, 0, 0.0F);
				setRotateAngle(middleTopBody, 0.3490658503988659F, 0.0F, 0.0F);
				this.miscA = new RendererModel(this, 85, 35);
				this.miscA.setRotationPoint(0.0F, 8.6F, 3.3F);
				this.miscA.addBox(-3.5F, 0.2F, 0.0F, 7, 12, 0, 0.0F);
				setRotateAngle(miscA, 0.045553093477052F, 0.0F, 0.0F);
				this.rightTopBody = new RendererModel(this, 33, 18);
				this.rightTopBody.mirror = true;
				this.rightTopBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightTopBody.addBox(-1.1F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				setRotateAngle(rightTopBody, 0.36425021489121656F, 0.4553564018453205F, 0.36425021489121656F);

				if(isSteve){
					this.leftHand = new RendererModel(this, 112, 48);
					this.leftHand.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.leftHand.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.leftShoulderA = new RendererModel(this, 0, 54);
					this.leftShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.leftShoulderA.addBox(-1.4F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
					this.leftShoulderB = new RendererModel(this, 0, 46);
					this.leftShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.leftShoulderB.addBox(-0.4F, -1.3F, -2.5F, 6, 3, 5, 0.2F);
					setRotateAngle(leftShoulderB, 0.0F, 0.0F, 0.39269908169872414F);
					this.rightHand = new RendererModel(this, 112, 48);
					this.rightHand.mirror = true;
					this.rightHand.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.rightHand.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.rightShoulderA = new RendererModel(this, 0, 54);
					this.rightShoulderA.mirror = true;
					this.rightShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.rightShoulderA.addBox(-5.0F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
					this.rightShoulderB = new RendererModel(this, 0, 46);
					this.rightShoulderB.mirror = true;
					this.rightShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.rightShoulderB.addBox(-5.8F, -1.4F, -2.5F, 6, 3, 5, 0.2F);
					setRotateAngle(rightShoulderB, 0.0F, 0.0F, -0.39269908169872414F);
				}else{
					this.leftHand = new RendererModel(this, 112, 48);
					this.leftHand.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.leftHand.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.leftShoulderA = new RendererModel(this, 0, 54);
					this.leftShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.leftShoulderA.addBox(-1.4F, -3.2F, -2.5F, 5, 5, 5, 0.0F);
					this.leftShoulderB = new RendererModel(this, 0, 46);
					this.leftShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.leftShoulderB.addBox(-0.4F, -1.3F, -2.5F, 5, 3, 5, 0.2F);
					setRotateAngle(leftShoulderB, 0.0F, 0.0F, 0.39269908169872414F);
					this.rightHand = new RendererModel(this, 112, 48);
					this.rightHand.mirror = true;
					this.rightHand.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.rightHand.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.rightShoulderA = new RendererModel(this, 0, 54);
					this.rightShoulderA.mirror = true;
					this.rightShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.rightShoulderA.addBox(-4.0F, -3.2F, -2.5F, 5, 5, 5, 0.0F);
					this.rightShoulderB = new RendererModel(this, 0, 46);
					this.rightShoulderB.mirror = true;
					this.rightShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.rightShoulderB.addBox(-4.8F, -1.4F, -2.5F, 5, 3, 5, 0.2F);
					setRotateAngle(rightShoulderB, 0.0F, 0.0F, -0.39269908169872414F);
				}

				this.bipedBody = bodyBase;
				this.bipedBody.addChild(this.backLeftBody);
				this.bipedBody.addChild(this.backRightBody);
				this.bipedBody.addChild(this.leftTopBody);
				this.bipedBody.addChild(this.middleTopBody);
				this.bipedBody.addChild(this.miscA);
				this.bipedBody.addChild(this.rightTopBody);

				this.bipedLeftArm = leftHand;
				this.bipedLeftArm.addChild(this.leftShoulderA);
				this.leftShoulderA.addChild(this.leftShoulderB);
				this.leftShoulderA.addChild(this.leftShoulderC);

				this.bipedRightArm = rightHand;
				this.bipedRightArm.addChild(this.rightShoulderA);
				this.rightShoulderA.addChild(this.rightShoulderB);
				this.rightShoulderA.addChild(this.rightShoulderC);
				break;

			case LEGS:
				this.beltA = new RendererModel(this, 84, 57);
				this.beltA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.beltA.addBox(-4.51F, 10.0F, -2.4F, 9, 2, 5, 0.3F);
				this.beltB = new RendererModel(this, 60, 54);
				this.beltB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.beltB.addBox(-3.0F, 8.9F, -3.7F, 6, 4, 6, -0.8F);
				this.bodyBase = new RendererModel(this, 0, 0);
				this.bodyBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftLeg = new RendererModel(this, 0, 0);
				this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.leftLegging = new RendererModel(this, 32, 36);
				this.leftLegging.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftLegging.addBox(-0.3F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				setRotateAngle(leftLegging, 0.0F, 0.0F, -0.136659280431156F);
				this.miscB = new RendererModel(this, 50, 44);
				this.miscB.setRotationPoint(0.0F, 12.1F, -3.0F);
				this.miscB.addBox(-1.5F, -0.1F, 0.2F, 3, 8, 0, 0.0F);
				setRotateAngle(miscB, -0.045553093477052F, 0.0F, 0.0F);
				this.rightLeg = new RendererModel(this, 0, 0);
				this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.rightLegging = new RendererModel(this, 32, 36);
				this.rightLegging.mirror = true;
				this.rightLegging.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightLegging.addBox(-2.7F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				setRotateAngle(rightLegging, 0.0F, 0.0F, 0.136659280431156F);

				this.bipedBody = bodyBase;
				this.bipedBody.addChild(this.miscB);
				this.bipedBody.addChild(this.beltA);
				this.bipedBody.addChild(this.beltB);

				this.bipedLeftLeg = leftLeg;
				this.bipedLeftLeg.addChild(this.leftLegging);

				this.bipedRightLeg = rightLeg;
				this.bipedRightLeg.addChild(this.rightLegging);
				break;

			case FEET:
				this.rightLeg = new RendererModel(this, 32, 48);
				this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.leftLeg = new RendererModel(this, 32, 48);
				this.leftLeg.mirror = true;
				this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

				this.bipedLeftLeg = leftLeg;

				this.bipedRightLeg = rightLeg;
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
				bipedBody.showModel = true;
				bipedLeftLeg.showModel = true;
				bipedRightLeg.showModel = true;
				break;

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

		float f = Math.abs(this.bipedRightLeg.rotateAngleX);
		switch (this.slot) {
			case HEAD:
				this.middleFeatherA.rotateAngleX = -0.15F - f * 0.1F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);
				this.middleFeatherB.rotateAngleX = -0.15F - f * 0.1F + 0.1F * sinPI((ageInTicks - 15)/ 60.0F + 1.0F);
				break;

			case CHEST:
				this.miscA.rotateAngleX = 0.05F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);

				if (this.isSitting) this.miscA.rotateAngleX += 1.0F;

				if (this.isSneak) this.miscA.rotateAngleX += -0.18F;
				else this.miscA.rotateAngleX += f;

				break;

			case LEGS:
				this.miscB.rotateAngleX = -(f + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F));

				if (this.isSitting) this.miscB.rotateAngleX += -1.0F;

				if (this.isSneak) {
					this.beltA.rotationPointY = -3.0F;
					this.beltB.rotationPointY = -3.0F;
					this.miscB.rotationPointY = 8.5F;
					this.miscB.rotateAngleX += -0.6F;
				}else{
					this.beltA.rotationPointY = 0.0F;
					this.beltB.rotationPointY = 0.0F;
					this.miscB.rotationPointY = 12.1F;
				}
				break;

			default:
				break;
		}
	}
}