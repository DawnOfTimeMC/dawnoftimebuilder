package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OYoroiArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public RendererModel headLeftSide;
	public RendererModel headFront;
	public RendererModel headBack;
	public RendererModel headRightSide;
	public RendererModel helmet;
	public RendererModel headLeft;
	public RendererModel headRight;
	public RendererModel helmetHorn;
	public RendererModel helmetJewel;

	//Chest
	public RendererModel chestSub;
	public RendererModel chestProtTop;
	public RendererModel chestProtBot;
	public RendererModel bodyBreast;
	public RendererModel bodyBreastProt;
	public RendererModel armLeftSub;
	public RendererModel armLeftTop;
	public RendererModel armLeftMid;
	public RendererModel armLeftBot;
	public RendererModel armLeftShoulder;
	public RendererModel armRightSub;
	public RendererModel armRightBot;
	public RendererModel armRightMid;
	public RendererModel armRightTop;
	public RendererModel armRightShoulder;

	//Leggings
	public RendererModel thighFront;
	public RendererModel thighFrontSub;
	public RendererModel thighBack;
	public RendererModel thighBackSub;
	public RendererModel thighRight;
	public RendererModel thighRightSub;
	public RendererModel thighLeft;
	public RendererModel thighLeftSub;

	//Boots
	public RendererModel legLeftProt;
	public RendererModel legLeftSub;
	public RendererModel legRightProt;
	public RendererModel legRightSub;

	public OYoroiArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot,64, 64);

		switch (slot) {
			case HEAD:
				this.helmet = new RendererModel(this, 28, 21);
				this.helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.helmet.addBox(-4.5F, -8.5F, -4.5F, 9, 4, 9, -0.1F);
				this.headFront = new RendererModel(this, 38, 17);
				this.headFront.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headFront.addBox(-4.5F, -7.3F, -2.0F, 9, 1, 3, -0.1F);
				setRotateAngle(headFront, 0.6981317007977318F, 0.0F, 0.0F);
				this.helmetHorn = new RendererModel(this, 46, 0);
				this.helmetHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.helmetHorn.addBox(2.2F, -6.0F, 2.2F, 6, 0, 6, 0.0F);
				setRotateAngle(helmetHorn, 1.4311699866353502F, -0.13962634015954636F, -0.7853981633974483F);
				this.helmetJewel = new RendererModel(this, 52, 6);
				this.helmetJewel.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.helmetJewel.addBox(-1.0F, -5.0F, -6.8F, 2, 2, 1, 0.0F);
				setRotateAngle(helmetJewel, -0.2617993877991494F, 0.0F, 0.0F);
				this.headRight = new RendererModel(this, 11, 20);
				this.headRight.mirror = true;
				this.headRight.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headRight.addBox(0.0F, -6.5F, -4.5F, 4, 1, 9, -0.1F);
				setRotateAngle(headRight, 0.0F, 0.0F, 0.6981317007977318F);
				this.headRightSide = new RendererModel(this, 52, 9);
				this.headRightSide.mirror = true;
				this.headRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headRightSide.addBox(0.0F, -8.3F, -4.5F, 3, 2, 1, -0.1F);
				setRotateAngle(headRightSide, 0.0F, 0.0F, 0.6981317007977318F);
				this.headLeft = new RendererModel(this, 11, 20);
				this.headLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headLeft.addBox(-4.0F, -6.5F, -4.5F, 4, 1, 9, -0.1F);
				setRotateAngle(headLeft, 0.0F, 0.0F, -0.6981317007977318F);
				this.headLeftSide = new RendererModel(this, 52, 9);
				this.headLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headLeftSide.addBox(-3.0F, -8.3F, -4.5F, 3, 2, 1, -0.1F);
				setRotateAngle(headLeftSide, 0.0F, 0.0F, -0.6981317007977318F);
				this.headBack = new RendererModel(this, 36, 12);
				this.headBack.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headBack.addBox(-4.5F, -6.5F, 0.0F, 9, 1, 4, -0.1F);
				setRotateAngle(headBack, -0.6981317007977318F, 0.0F, 0.0F);

				this.bipedHead = helmet;
				this.bipedHead.addChild(headLeftSide);
				this.bipedHead.addChild(headFront);
				this.bipedHead.addChild(headBack);
				this.bipedHead.addChild(headRightSide);
				this.bipedHead.addChild(headLeft);
				this.bipedHead.addChild(headRight);
				this.bipedHead.addChild(helmetHorn);
				this.bipedHead.addChild(helmetJewel);
				break;

			case CHEST:
				this.chestSub = new RendererModel(this, 0, 0);
				this.chestSub.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chestSub.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.3F);
				this.chestProtBot = new RendererModel(this, 24, 0);
				this.chestProtBot.setRotationPoint(0.0F, 0.0F, -0.5F);
				this.chestProtBot.addBox(-4.5F, 4.0F, -2.0F, 9, 7, 5, 0.1F);
				if(isSteve){
					this.chestProtTop = new RendererModel(this, 19, 12);
					this.chestProtTop.setRotationPoint(0.0F, 0.0F, -0.5F);
					this.chestProtTop.addBox(-3.0F, 1.0F, -2.0F, 6, 2, 5, 0.1F);

					this.armRightSub = new RendererModel(this, 0, 30);
					this.armRightSub.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRightSub.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armRightBot = new RendererModel(this, 38, 34);
					this.armRightBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightBot.addBox(-3.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armRightMid = new RendererModel(this, 27, 36);
					this.armRightMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightMid.addBox(-3.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armRightTop = new RendererModel(this, 16, 30);
					this.armRightTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightTop.addBox(-3.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armRightShoulder = new RendererModel(this, 46, 35);
					this.armRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulder.addBox(-4.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					setRotateAngle(armRightShoulder, 0.0F, 0.0F, 0.3490658503988659F);

					this.armLeftSub = new RendererModel(this, 0, 30);
					this.armLeftSub.mirror = true;
					this.armLeftSub.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeftSub.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armLeftBot = new RendererModel(this, 38, 34);
					this.armLeftBot.mirror = true;
					this.armLeftBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftBot.addBox(1.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armLeftMid = new RendererModel(this, 27, 36);
					this.armLeftMid.mirror = true;
					this.armLeftMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftMid.addBox(0.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armLeftTop = new RendererModel(this, 16, 30);
					this.armLeftTop.mirror = true;
					this.armLeftTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftTop.addBox(0.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armLeftShoulder = new RendererModel(this, 46, 35);
					this.armLeftShoulder.mirror = true;
					this.armLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulder.addBox(3.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					setRotateAngle(armLeftShoulder, 0.0F, 0.0F, -0.3490658503988659F);
				}else{
					this.chestProtTop = new RendererModel(this, 24, 12);
					this.chestProtTop.setRotationPoint(0.0F, 0.0F, -0.5F);
					this.chestProtTop.addBox(-3.0F, 1.0F, 2.0F, 6, 2, 1, 0.1F);
					this.bodyBreast = new RendererModel(this, 27, 47);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastProt = new RendererModel(this, 24, 15);
					this.bodyBreastProt.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreastProt.addBox(-3.0F, 0.8F, -0.1F, 6, 2, 1, 0.1F);
					setRotateAngle(bodyBreastProt, -0.5759586531581287F, 0.0F, 0.0F);

					this.armRightSub = new RendererModel(this, 0, 30);
					this.armRightSub.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRightSub.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armRightBot = new RendererModel(this, 38, 34);
					this.armRightBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightBot.addBox(-2.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armRightMid = new RendererModel(this, 27, 36);
					this.armRightMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightMid.addBox(-2.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armRightTop = new RendererModel(this, 16, 30);
					this.armRightTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightTop.addBox(-2.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armRightShoulder = new RendererModel(this, 46, 35);
					this.armRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulder.addBox(-3.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					setRotateAngle(armRightShoulder, 0.0F, 0.0F, 0.2617993877991494F);

					this.armLeftSub = new RendererModel(this, 0, 30);
					this.armLeftSub.mirror = true;
					this.armLeftSub.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armLeftSub.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armLeftBot = new RendererModel(this, 38, 34);
					this.armLeftBot.mirror = true;
					this.armLeftBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftBot.addBox(0.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armLeftMid = new RendererModel(this, 27, 36);
					this.armLeftMid.mirror = true;
					this.armLeftMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftMid.addBox(-0.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armLeftTop = new RendererModel(this, 16, 30);
					this.armLeftTop.mirror = true;
					this.armLeftTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftTop.addBox(-0.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armLeftShoulder = new RendererModel(this, 46, 35);
					this.armLeftShoulder.mirror = true;
					this.armLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulder.addBox(2.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					setRotateAngle(armLeftShoulder, 0.0F, 0.0F, -0.2617993877991494F);
				}

				this.bipedBody = chestSub;
				this.bipedBody.addChild(chestProtTop);
				this.bipedBody.addChild(chestProtBot);
				if(!isSteve){
					this.bipedBody.addChild(bodyBreast);
					this.bipedBody.addChild(bodyBreastProt);
				}

				this.bipedLeftArm = armLeftSub;
				this.bipedLeftArm.addChild(armLeftTop);
				this.bipedLeftArm.addChild(armLeftMid);
				this.bipedLeftArm.addChild(armLeftBot);
				this.bipedLeftArm.addChild(armLeftShoulder);

				this.bipedRightArm = armRightSub;
				this.bipedRightArm.addChild(armRightBot);
				this.bipedRightArm.addChild(armRightMid);
				this.bipedRightArm.addChild(armRightTop);
				this.bipedRightArm.addChild(armRightShoulder);
				break;

			case LEGS:
				this.thighFrontSub = new RendererModel(this, 4, 21);
				this.thighFrontSub.setRotationPoint(0.0F, 11.0F, -2.5F);
				this.thighFrontSub.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 0, 0.0F);
				this.thighFront = new RendererModel(this, 0, 16);
				this.thighFront.setRotationPoint(0.0F, 11.0F, -2.5F);
				this.thighFront.addBox(-4.0F, 1.0F, -0.5F, 8, 4, 1, 0.0F);
				this.thighBackSub = new RendererModel(this, 4, 21);
				this.thighBackSub.setRotationPoint(0.0F, 11.0F, 2.5F);
				this.thighBackSub.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 0, 0.0F);
				this.thighBack = new RendererModel(this, 0, 16);
				this.thighBack.setRotationPoint(0.0F, 11.0F, 2.5F);
				this.thighBack.addBox(-4.0F, 1.0F, -0.5F, 8, 4, 1, 0.0F);
				this.thighRightSub = new RendererModel(this, 12, 18);
				this.thighRightSub.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.thighRightSub.addBox(-4.5F, 11.0F, -2.0F, 0, 1, 4, 0.0F);
				this.thighRight = new RendererModel(this, 0, 22);
				this.thighRight.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.thighRight.addBox(-5.0F, 12.0F, -2.0F, 1, 4, 4, 0.0F);
				this.thighLeftSub = new RendererModel(this, 12, 18);
				this.thighLeftSub.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.thighLeftSub.addBox(4.5F, 11.0F, -2.0F, 0, 1, 4, 0.0F);
				this.thighLeft = new RendererModel(this, 0, 22);
				this.thighLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.thighLeft.addBox(4.0F, 12.0F, -2.0F, 1, 4, 4, 0.0F);

				this.bipedBody = thighRight;
				this.bipedBody.addChild(thighRightSub);
				this.bipedBody.addChild(thighLeft);
				this.bipedBody.addChild(thighLeftSub);
				this.bipedBody.addChild(thighFront);
				this.bipedBody.addChild(thighFrontSub);
				this.bipedBody.addChild(thighBack);
				this.bipedBody.addChild(thighBackSub);
				break;

			case FEET:
				this.legRightSub = new RendererModel(this, 0, 46);
				this.legRightSub.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRightSub.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.legRightProt = new RendererModel(this, 12, 42);
				this.legRightProt.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legRightProt.addBox(-2.5F, 5.5F, -2.5F, 5, 4, 4, 0.1F);
				this.legLeftSub = new RendererModel(this, 0, 46);
				this.legLeftSub.mirror = true;
				this.legLeftSub.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeftSub.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.legLeftProt = new RendererModel(this, 12, 42);
				this.legLeftProt.mirror = true;
				this.legLeftProt.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legLeftProt.addBox(-2.5F, 5.5F, -2.5F, 5, 4, 4, 0.1F);

				this.bipedLeftLeg = legLeftSub;
				this.bipedLeftLeg.addChild(legLeftProt);

				this.bipedRightLeg = legRightSub;
				this.bipedRightLeg.addChild(legRightProt);
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

		if (this.slot == EquipmentSlotType.LEGS) {
			float f = Math.abs(this.bipedRightLeg.rotateAngleX);
			this.thighBack.rotateAngleX = f;
			this.thighBackSub.rotateAngleX = f;
			this.thighFront.rotateAngleX = -f;
			this.thighFrontSub.rotateAngleX = -f;

			if (this.isSitting) {
				this.thighBack.rotateAngleX += 1.0F;
				this.thighBackSub.rotateAngleX += 1.0F;
                this.thighFront.rotateAngleX -= 1.0F;
                this.thighFrontSub.rotateAngleX -= 1.0F;
			}

			if (this.isSneak) {
                this.thighBack.rotateAngleX -= 0.5F;
                this.thighBackSub.rotateAngleX -= 0.5F;
                this.thighFront.rotateAngleX -= 0.5F;
                this.thighFrontSub.rotateAngleX -= 0.5F;
				this.thighFront.rotationPointY = 8.0F;
				this.thighFrontSub.rotationPointY = 8.0F;
			} else {
				this.thighFront.rotationPointY = 11.0F;
				this.thighFrontSub.rotationPointY = 11.0F;
			}
		}
    }
}
