package org.dawnoftimebuilder.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronPlateArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	public ModelRenderer baseHelmet;
	public ModelRenderer earMiddle;
	public ModelRenderer leftEarBottom;
	public ModelRenderer leftHead;
	public ModelRenderer middleBottom;
	public ModelRenderer middleCraneC;
	public ModelRenderer middleFeatherA;
	public ModelRenderer middleFeatherB;
	public ModelRenderer middleHeadA;
	public ModelRenderer middleHeadB;
	public ModelRenderer rightHead;
	public ModelRenderer rightEarBottom;
	public ModelRenderer topHead;

	//Chest
	public ModelRenderer backRightBody;
	public ModelRenderer backLeftBody;
	public ModelRenderer bodyBase;
	public ModelRenderer leftHand;
	public ModelRenderer leftShoulderA;
	public ModelRenderer leftShoulderB;
	public ModelRenderer leftShoulderC;
	public ModelRenderer leftTopBody;
	public ModelRenderer middleTopBody;
	public ModelRenderer miscA;
	public ModelRenderer rightHand;
	public ModelRenderer rightShoulderA;
	public ModelRenderer rightShoulderB;
	public ModelRenderer rightShoulderC;
	public ModelRenderer rightTopBody;

	//Leggings
	public ModelRenderer beltA;
	public ModelRenderer beltB;
	public ModelRenderer leftLegging;
	public ModelRenderer miscB;
	public ModelRenderer rightLegging;

	//Boots
	public ModelRenderer armorLeftLeg;
	public ModelRenderer armorRightLeg;

	public IronPlateArmorModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 128, 64, scale);

		switch (slot) {
			case HEAD:
				this.baseHelmet = new ModelRenderer(this, 62, 11);
				this.baseHelmet.setPos(0.0F, 0.0F, 0.0F);
				this.baseHelmet.addBox(-4.5F, -8.2F, -4.5F, 9, 9, 9, 0.1F);
				this.earMiddle = new ModelRenderer(this, 57, 49);
				this.earMiddle.setPos(0.0F, -4.0F, 0.4F);
				this.earMiddle.addBox(-5.5F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
				setRotationAngle(earMiddle, -0.31869712141416456F, 0.0F, 0.0F);
				this.leftEarBottom = new ModelRenderer(this, 0, 34);
				this.leftEarBottom.mirror = true;
				this.leftEarBottom.setPos(0.0F, 0.0F, 0.0F);
				this.leftEarBottom.addBox(4.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.leftHead = new ModelRenderer(this, 52, 31);
				this.leftHead.setPos(0.0F, 0.0F, 0.0F);
				this.leftHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				setRotationAngle(leftHead, 0.0F, 0.0F, 0.27314402793711257F);
				this.middleBottom = new ModelRenderer(this, 0, 30);
				this.middleBottom.setPos(0.0F, 0.0F, 0.0F);
				this.middleBottom.addBox(-4.0F, -7.5F, -1.5F, 8, 1, 3, 0.0F);
				setRotationAngle(middleBottom, 1.5707963267948966F, 0.0F, 0.0F);
				this.middleCraneC = new ModelRenderer(this, 45, 28);
				this.middleCraneC.setPos(0.0F, 0.0F, 0.0F);
				this.middleCraneC.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				setRotationAngle(middleCraneC, -0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadA = new ModelRenderer(this, 45, 28);
				this.middleHeadA.setPos(0.0F, 0.0F, 0.0F);
				this.middleHeadA.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				setRotationAngle(middleHeadA, 0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadB = new ModelRenderer(this, 44, 19);
				this.middleHeadB.setPos(0.0F, 0.0F, 0.0F);
				this.middleHeadB.addBox(-1.0F, -10.5F, -2.5F, 2, 2, 5, 0.0F);
				this.middleFeatherA = new ModelRenderer(this, 89, -8);
				this.middleFeatherA.setPos(0.0F, -8.0F, 5.0F);
				this.middleFeatherA.addBox(-2.0F, -4.5F, -1.5F, 4, 9, 9, -2.0F);
				setRotationAngle(middleFeatherA, -0.19052597169560997F, 0.0F, 0.0F);
				this.middleFeatherB = new ModelRenderer(this, 89, 2);
				this.middleFeatherB.setPos(0.0F, 0.0F, 4.5F);
				this.middleFeatherB.addBox(-2.0F, -4.5F, -2.0F, 4, 9, 9, -2.0F);
				setRotationAngle(middleFeatherB, -0.1187509959883641F, 0.0F, 0.0F);
				this.rightEarBottom = new ModelRenderer(this, 0, 34);
				this.rightEarBottom.setPos(0.0F, 0.0F, 0.0F);
				this.rightEarBottom.addBox(-5.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.rightHead = new ModelRenderer(this, 52, 31);
				this.rightHead.mirror = true;
				this.rightHead.setPos(0.0F, 0.0F, 0.0F);
				this.rightHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				setRotationAngle(rightHead, 0.0F, 0.0F, -0.27314402793711257F);
				this.topHead = new ModelRenderer(this, 28, 5);
				this.topHead.setPos(0.0F, 0.0F, 0.0F);
				this.topHead.addBox(-5.0F, -8.2F, -5.0F, 10, 3, 10, -0.3F);

				this.head = baseHelmet;
				this.head.addChild(this.earMiddle);
				this.earMiddle.addChild(this.leftEarBottom);
				this.earMiddle.addChild(this.middleBottom);
				this.earMiddle.addChild(this.rightEarBottom);
				this.head.addChild(this.leftHead);
				this.head.addChild(this.middleCraneC);
				this.head.addChild(this.middleFeatherA);
				this.middleFeatherA.addChild(this.middleFeatherB);
				this.head.addChild(this.middleHeadA);
				this.head.addChild(this.middleHeadB);
				this.head.addChild(this.rightHead);
				this.head.addChild(this.topHead);
				break;

			case CHEST:
				this.backLeftBody = new ModelRenderer(this, 32, 27);
				this.backLeftBody.setPos(0.0F, 0.0F, 0.0F);
				this.backLeftBody.addBox(-1.45F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				setRotationAngle(backLeftBody, 0.0F, 0.31869712141416456F, 0.0F);
				this.backRightBody = new ModelRenderer(this, 32, 27);
				this.backRightBody.mirror = true;
				this.backRightBody.setPos(0.0F, 0.0F, 0.0F);
				this.backRightBody.addBox(-3.55F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				setRotationAngle(backRightBody, 0.0F, -0.31869712141416456F, 0.0F);
				this.bodyBase = new ModelRenderer(this, 0, 4);
				this.bodyBase.setPos(0.0F, 0.0F, 0.0F);
				this.bodyBase.addBox(-4.5F, -0.1F, -2.5F, 9, 13, 5, 0.0F);
				this.leftShoulderC = new ModelRenderer(this, 0, 22);
				this.leftShoulderC.setPos(0.0F, 0.0F, 0.0F);
				this.leftShoulderC.addBox(-0.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				setRotationAngle(leftShoulderC, 0.0F, 0.0F, -0.13962634015954636F);
				this.rightShoulderC = new ModelRenderer(this, 0, 22);
				this.rightShoulderC.mirror = true;
				this.rightShoulderC.setPos(0.0F, 0.0F, 0.0F);
				this.rightShoulderC.addBox(-1.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				setRotationAngle(rightShoulderC, 0.0F, 0.0F, 0.13962634015954636F);
				this.leftTopBody = new ModelRenderer(this, 33, 18);
				this.leftTopBody.setPos(0.0F, 0.0F, 0.0F);
				this.leftTopBody.addBox(-2.9F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				setRotationAngle(leftTopBody, 0.36425021489121656F, -0.4553564018453205F, -0.36425021489121656F);
				this.middleTopBody = new ModelRenderer(this, 54, 57);
				this.middleTopBody.setPos(-1.5F, 9.2F, -2.4F);
				this.middleTopBody.addBox(0.0F, -7.0F, 0.0F, 3, 7, 0, 0.0F);
				setRotationAngle(middleTopBody, 0.3490658503988659F, 0.0F, 0.0F);
				this.rightTopBody = new ModelRenderer(this, 33, 18);
				this.rightTopBody.mirror = true;
				this.rightTopBody.setPos(0.0F, 0.0F, 0.0F);
				this.rightTopBody.addBox(-1.1F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				setRotationAngle(rightTopBody, 0.36425021489121656F, 0.4553564018453205F, 0.36425021489121656F);
				this.miscA = new ModelRenderer(this, 85, 35);
				this.miscA.setPos(0.0F, 8.6F, 3.3F);
				this.miscA.addBox(-3.5F, 0.2F, 0.0F, 7, 12, 0, 0.0F);
				setRotationAngle(miscA, 0.045553093477052F, 0.0F, 0.0F);

				if(isSteve){
					this.leftHand = new ModelRenderer(this, 112, 48);
					this.leftHand.setPos(5.0F, 2.0F, 0.0F);
					this.leftHand.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.leftShoulderA = new ModelRenderer(this, 0, 54);
					this.leftShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.leftShoulderA.addBox(-1.4F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
					this.leftShoulderB = new ModelRenderer(this, 0, 46);
					this.leftShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.leftShoulderB.addBox(-0.4F, -1.3F, -2.5F, 6, 3, 5, 0.2F);
					setRotationAngle(leftShoulderB, 0.0F, 0.0F, 0.39269908169872414F);
					this.rightHand = new ModelRenderer(this, 112, 48);
					this.rightHand.mirror = true;
					this.rightHand.setPos(-5.0F, 2.0F, 0.0F);
					this.rightHand.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.rightShoulderA = new ModelRenderer(this, 0, 54);
					this.rightShoulderA.mirror = true;
					this.rightShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.rightShoulderA.addBox(-5.0F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
					this.rightShoulderB = new ModelRenderer(this, 0, 46);
					this.rightShoulderB.mirror = true;
					this.rightShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.rightShoulderB.addBox(-5.8F, -1.4F, -2.5F, 6, 3, 5, 0.2F);
				}else{
					this.leftHand = new ModelRenderer(this, 112, 48);
					this.leftHand.setPos(5.0F, 2.5F, 0.0F);
					this.leftHand.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.leftShoulderA = new ModelRenderer(this, 0, 54);
					this.leftShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.leftShoulderA.addBox(-1.4F, -3.2F, -2.5F, 5, 5, 5, 0.0F);
					this.leftShoulderB = new ModelRenderer(this, 0, 46);
					this.leftShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.leftShoulderB.addBox(-0.4F, -1.3F, -2.5F, 5, 3, 5, 0.2F);
					setRotationAngle(leftShoulderB, 0.0F, 0.0F, 0.39269908169872414F);
					this.rightHand = new ModelRenderer(this, 112, 48);
					this.rightHand.mirror = true;
					this.rightHand.setPos(-5.0F, 2.5F, 0.0F);
					this.rightHand.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.rightShoulderA = new ModelRenderer(this, 0, 54);
					this.rightShoulderA.mirror = true;
					this.rightShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.rightShoulderA.addBox(-4.0F, -3.2F, -2.5F, 5, 5, 5, 0.0F);
					this.rightShoulderB = new ModelRenderer(this, 0, 46);
					this.rightShoulderB.mirror = true;
					this.rightShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.rightShoulderB.addBox(-4.8F, -1.4F, -2.5F, 5, 3, 5, 0.2F);
				}
				setRotationAngle(rightShoulderB, 0.0F, 0.0F, -0.39269908169872414F);

				this.body = bodyBase;
				this.body.addChild(this.backLeftBody);
				this.body.addChild(this.backRightBody);
				this.body.addChild(this.leftTopBody);
				this.body.addChild(this.middleTopBody);
				this.body.addChild(this.rightTopBody);
				this.body.addChild(this.miscA);

				this.leftArm = leftHand;
				this.leftArm.addChild(this.leftShoulderA);
				this.leftShoulderA.addChild(this.leftShoulderB);
				this.leftShoulderA.addChild(this.leftShoulderC);

				this.rightArm = rightHand;
				this.rightArm.addChild(this.rightShoulderA);
				this.rightShoulderA.addChild(this.rightShoulderB);
				this.rightShoulderA.addChild(this.rightShoulderC);
				break;

			case LEGS:
				this.beltA = new ModelRenderer(this, 84, 57);
				this.beltA.setPos(0.0F, 0.0F, 0.0F);
				this.beltA.addBox(-4.51F, 10.0F, -2.4F, 9, 2, 5, 0.3F);
				this.beltB = new ModelRenderer(this, 60, 54);
				this.beltB.setPos(0.0F, 0.0F, 0.0F);
				this.beltB.addBox(-3.0F, 8.9F, -3.7F, 6, 4, 6, -0.8F);
				this.bodyBase = new ModelRenderer(this, 0, 0);
				this.bodyBase.setPos(0.0F, 0.0F, 0.0F);
				this.armorLeftLeg = new ModelRenderer(this, 0, 0);
				this.armorLeftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLegging = new ModelRenderer(this, 32, 36);
				this.leftLegging.setPos(0.0F, 0.0F, 0.0F);
				this.leftLegging.addBox(-0.3F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				setRotationAngle(leftLegging, 0.0F, 0.0F, -0.136659280431156F);
				this.miscB = new ModelRenderer(this, 50, 44);
				this.miscB.setPos(0.0F, 12.1F, -3.0F);
				this.miscB.addBox(-1.5F, -0.1F, 0.2F, 3, 8, 0, 0.0F);
				setRotationAngle(miscB, -0.045553093477052F, 0.0F, 0.0F);
				this.armorRightLeg = new ModelRenderer(this, 0, 0);
				this.armorRightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLegging = new ModelRenderer(this, 32, 36);
				this.rightLegging.mirror = true;
				this.rightLegging.setPos(0.0F, 0.0F, 0.0F);
				this.rightLegging.addBox(-2.7F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				setRotationAngle(rightLegging, 0.0F, 0.0F, 0.136659280431156F);

				this.body = bodyBase;
				this.body.addChild(this.miscB);
				this.body.addChild(this.beltA);
				this.body.addChild(this.beltB);

				this.leftLeg = armorLeftLeg;
				this.leftLeg.addChild(this.leftLegging);

				this.rightLeg = armorRightLeg;
				this.rightLeg.addChild(this.rightLegging);
				break;

			case FEET:
				this.armorRightLeg = new ModelRenderer(this, 32, 48);
				this.armorRightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.armorRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.armorLeftLeg = new ModelRenderer(this, 32, 48);
				this.armorLeftLeg.mirror = true;
				this.armorLeftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.armorLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

				this.leftLeg = armorLeftLeg;

				this.rightLeg = armorRightLeg;
				break;

			default:
				break;
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		this.setAllVisible(false);

		switch (this.slot) {
			case HEAD:
				head.visible = true;
				break;

			case CHEST:
				body.visible = true;
				leftArm.visible = true;
				rightArm.visible = true;
				break;

			case LEGS:
				body.visible = true;
				leftLeg.visible = true;
				rightLeg.visible = true;
				break;

			case FEET:
				leftLeg.visible = true;
				rightLeg.visible = true;
				break;

			default:
				break;
		}
		super.renderToBuffer(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	public void setupArmorAnim(T entityIn, float ageInTicks) {
		super.setupArmorAnim(entityIn, ageInTicks);

		float f = Math.abs(this.rightLeg.xRot);
		switch (this.slot) {
			case HEAD:
				this.middleFeatherA.xRot = -0.15F - f * 0.1F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);
				this.middleFeatherB.xRot = -0.15F - f * 0.1F + 0.1F * sinPI((ageInTicks - 15)/ 60.0F + 1.0F);
				break;

			case CHEST:
				this.miscA.xRot = 0.05F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);

				if (this.riding) this.miscA.xRot += 1.0F;

				if (this.crouching) this.miscA.xRot -= 0.18F;
				else this.miscA.xRot += f;

				break;

			case LEGS:
				this.miscB.xRot = -(f + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F));

				if (this.riding) this.miscB.xRot -= 1.0F;

				if (this.crouching) {
					this.beltA.y = -3.0F;
					this.beltB.y = -3.0F;
					this.miscB.y = 8.5F;
					this.miscB.xRot -= 0.6F;
				}else{
					this.beltA.y = 0.0F;
					this.beltB.y = 0.0F;
					this.miscB.y = 12.1F;
				}
				break;

			default:
				break;
		}
	}
}