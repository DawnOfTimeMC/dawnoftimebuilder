package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class PharaohArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	private ModelRenderer headTail;

	//Chest
	private ModelRenderer bodyBreast;
	private ModelRenderer bodyBreastCollar;

	//Leggings
	private ModelRenderer bodyJewel;
	private ModelRenderer bodyGoldenStrip;


	public PharaohArmorModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 64, 64, scale);

		ModelRenderer armorArmLeft;
		ModelRenderer armorArmRight;
		switch (slot) {
			case HEAD:
				ModelRenderer armorHead = new ModelRenderer(this);
				armorHead.setPos(0.0F, 0.0F, 0.0F);
				armorHead.texOffs(20, 19).addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, 0.1F, false);
				ModelRenderer headLeftSideBottom = new ModelRenderer(this, 0, 57);
				headLeftSideBottom.mirror = true;
				headLeftSideBottom.setPos(9.58F, -1.23F, 0.91F);
				headLeftSideBottom.addBox(-3.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotationAngle(headLeftSideBottom, 0.0F, 0.9147270609702279F, -0.26878070480712674F);
				ModelRenderer headLeftSideMiddle = new ModelRenderer(this, 14, 51);
				headLeftSideMiddle.mirror = true;
				headLeftSideMiddle.setPos(7.82F, -7.11F, 0.86F);
				headLeftSideMiddle.addBox(-5.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotationAngle(headLeftSideMiddle, 0.0F, 0.0F, -0.26878070480712674F);
				ModelRenderer headRightSideTop = new ModelRenderer(this, 0, 51);
				headRightSideTop.setPos(-4.6F, -11.4F, 0.86F);
				headRightSideTop.addBox(-5.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotationAngle(headRightSideTop, 0.0F, 0.0F, -0.890117918517108F);
				ModelRenderer headRightSideMiddle = new ModelRenderer(this, 14, 51);
				headRightSideMiddle.setPos(-7.82F, -7.11F, 0.86F);
				headRightSideMiddle.addBox(0.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotationAngle(headRightSideMiddle, 0.0F, 0.0F, 0.26878070480712674F);
				ModelRenderer headRightSideBottom = new ModelRenderer(this, 0, 57);
				headRightSideBottom.setPos(-9.58F, -1.23F, 0.91F);
				headRightSideBottom.addBox(0.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotationAngle(headRightSideBottom, 0.0F, -0.9147270609702279F, 0.26878070480712674F);
				ModelRenderer headLeftSideTop = new ModelRenderer(this, 0, 51);
				headLeftSideTop.mirror = true;
				headLeftSideTop.setPos(4.6F, -11.4F, 0.86F);
				headLeftSideTop.addBox(0.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotationAngle(headLeftSideTop, 0.0F, 0.0F, 0.890117918517108F);
				ModelRenderer headTop = new ModelRenderer(this, 20, 37);
				headTop.setPos(0.0F, -8.55F, -4.45F);
				headTop.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 8, 0.09F);
				setRotationAngle(headTop, 0.4714134309636684F, 0.0F, 0.0F);
				ModelRenderer headCap = new ModelRenderer(this, 18, 49);
				headCap.setPos(0.0F, -11.4F, 0.85F);
				headCap.addBox(-4.5F, 0.15F, 0.19F, 9, 0, 2, 0.131F);
				this.headTail = new ModelRenderer(this, 28, 51);
				this.headTail.setPos(-1.5F, 0.4F, 2.5F);
				this.headTail.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, -0.2F);
				ModelRenderer snakeBody = new ModelRenderer(this, 60, 0);
				snakeBody.setPos(0.0F, -7.5F, -4.6F);
				snakeBody.addBox(-0.5F, -3.0F, -1.0F, 1, 3, 1, 0.0F);
				setRotationAngle(snakeBody, -0.3490658503988659F, 0.0F, 0.0F);
				ModelRenderer snakeHead = new ModelRenderer(this, 47, 19);
				snakeHead.setPos(0.0F, -10.65F, -4.5F);
				snakeHead.addBox(-1.5F, -1.0F, -2.0F, 3, 3, 3, -0.99F);
				setRotationAngle(snakeHead, 0.2792526803190927F, 0.0F, 0.0F);

				this.head = armorHead;
				this.head.addChild(snakeBody);
				this.head.addChild(headLeftSideBottom);
				this.head.addChild(snakeHead);
				this.head.addChild(headLeftSideMiddle);
				this.head.addChild(headRightSideTop);
				this.head.addChild(headRightSideMiddle);
				this.head.addChild(headRightSideBottom);
				this.head.addChild(headTail);
				this.head.addChild(headLeftSideTop);
				this.head.addChild(headTop);
				this.head.addChild(headCap);
				break;

			case CHEST:
				ModelRenderer armorBody = new ModelRenderer(this, 0, 0);
				armorBody.setPos(0.0F, 0.0F, 0.0F);
				armorBody.addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, -0.1F);
				ModelRenderer bodyCollar = new ModelRenderer(this, 28, 0);
				bodyCollar.setPos(0.0F, 0.0F, 0.0F);
				bodyCollar.addBox(-5.5F, -0.25F, -2.5F, 11, 5, 5, 0.2F);
				if(isSteve){
					armorArmLeft = new ModelRenderer(this, 0, 18);
					armorArmLeft.mirror = true;
					armorArmLeft.setPos(5.0F, 2.0F, 0.0F);
					armorArmLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
					armorArmRight = new ModelRenderer(this, 0, 18);
					armorArmRight.setPos(-5.5F, 2.0F, -0.5F);
					armorArmRight.addBox(-3.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
				}else{
					armorArmLeft = new ModelRenderer(this, 0, 18);
					armorArmLeft.mirror = true;
					armorArmLeft.setPos(5.0F, 2.5F, 0.0F);
					armorArmLeft.addBox(-1.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					armorArmRight = new ModelRenderer(this, 0, 18);
					armorArmRight.setPos(-5.0F, 2.5F, 0.0F);
					armorArmRight.addBox(-2.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.bodyBreast = new ModelRenderer(this, 38, 49);
					this.bodyBreast.setPos(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					setRotationAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastCollar = new ModelRenderer(this, 38, 54);
					this.bodyBreastCollar.setPos(0.0F, -0.25F, -2.5F);
					this.bodyBreastCollar.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 0, 0.2F);
					setRotationAngle(bodyBreastCollar, -0.3403392041388943F, 0.0F, 0.0F);
				}

				this.body = armorBody;
				this.body.addChild(bodyCollar);
				if(!isSteve){
					this.body.addChild(bodyBreast);
					this.body.addChild(bodyBreastCollar);
				}

				this.leftArm = armorArmLeft;

				this.rightArm = armorArmRight;
				break;

			case LEGS:
				ModelRenderer bodyBottom = new ModelRenderer(this, 28, 10);
				bodyBottom.setPos(0.0F, 0.0F, 0.0F);
				bodyBottom.addBox(-4.5F, 8.5F, -2.5F, 9, 4, 5, 0.0F);
				this.bodyJewel = new ModelRenderer(this, 56, 10);
				this.bodyJewel.setPos(0.0F, 10.0F, -2.0F);
				this.bodyJewel.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, -0.3F);
				setRotationAngle(bodyJewel, 0.0F, 0.0F, 0.7853981633974483F);
				this.bodyGoldenStrip = new ModelRenderer(this, 56, 14);
				this.bodyGoldenStrip.setPos(0.0F, 10.0F, -2.6F);
				this.bodyGoldenStrip.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
				ModelRenderer armorLegLeft = new ModelRenderer(this, 0, 33);
				armorLegLeft.mirror = true;
				armorLegLeft.setPos(2.0F, 12.0F, 0.0F);
				armorLegLeft.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);
				ModelRenderer armorLegRight = new ModelRenderer(this, 0, 33);
				armorLegRight.setPos(-2.0F, 12.0F, 0.0F);
				armorLegRight.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);

				this.body = bodyBottom;
				this.body.addChild(bodyGoldenStrip);
				this.body.addChild(bodyJewel);

				this.rightLeg = armorLegRight;

				this.leftLeg = armorLegLeft;
				break;

			case FEET:
				ModelRenderer footLeft = new ModelRenderer(this, 0, 44);
				footLeft.mirror = true;
				footLeft.setPos(1.9F, 12.0F, 0.0F);
				footLeft.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);
				ModelRenderer footRight = new ModelRenderer(this, 0, 44);
				footRight.setPos(-1.9F, 12.0F, 0.0F);
				footRight.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);

				this.leftLeg = footLeft;

				this.rightLeg = footRight;
				break;

			default:
				break;
		}

	}

	@Override
	public void setupArmorAnim(T entityIn, float ageInTicks) {

		float f = 1.0F;
		if (entityIn.getFallFlyingTicks() > 4) {
			f = (float)entityIn.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}
		if (f < 1.0F) {
			f = 1.0F;
		}
		switch (this.slot) {
			case HEAD:
				f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + Math.abs(this.rightLeg.xRot);
				this.headTail.xRot = f * 0.1F - this.head.xRot * 0.8F;
				this.headTail.zRot = -f * 0.1F;
				break;

			case LEGS:
				this.bodyGoldenStrip.xRot = Math.abs(this.rightLeg.xRot);

				if (this.riding) {
					this.bodyGoldenStrip.xRot -= 1.0F;
				}

				if (this.crouching) {
					this.bodyGoldenStrip.xRot -= 0.75F;
					this.bodyGoldenStrip.y = 8.0F;
					this.bodyJewel.y = 8.0F;
				} else {
					this.bodyGoldenStrip.y = 10.0F;
					this.bodyJewel.y = 10.0F;
				}
				break;

			default:
				break;
		}
	}
}