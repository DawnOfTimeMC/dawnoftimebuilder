package org.dawnoftimebuilder.client.renderer.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PharaohArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	private RendererModel head;
	private RendererModel snakeBody;
	private RendererModel snakeHead;
	private RendererModel headLeftSideTop;
	private RendererModel headLeftSideMiddle;
	private RendererModel headLeftSideBottom;
	private RendererModel headRightSideTop;
	private RendererModel headRightSideMiddle;
	private RendererModel headRightSideBottom;
	private RendererModel headCap;
	private RendererModel headTop;
	private RendererModel headTail;

	//Chest
	private RendererModel body;
	private RendererModel bodyCollar;
	private RendererModel bodyBreast;
	private RendererModel bodyBreastCollar;
	private RendererModel armLeft;
	private RendererModel armRight;

	//Leggings
	private RendererModel bodyBottom;
	private RendererModel bodyJewel;
	private RendererModel bodyGoldenStrip;
	private RendererModel legLeft;
	private RendererModel legRight;

	//Boots
	private RendererModel footRight;
	private RendererModel footLeft;

	public PharaohArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 64, 64);

		switch (slot) {
			case HEAD:
				this.head = new RendererModel(this, 20, 19);
				this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.head.addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, 0.1F);
				this.headLeftSideBottom = new RendererModel(this, 0, 57);
				this.headLeftSideBottom.mirror = true;
				this.headLeftSideBottom.setRotationPoint(9.58F, -1.23F, 0.91F);
				this.headLeftSideBottom.addBox(-3.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotateAngle(headLeftSideBottom, 0.0F, 0.9147270609702279F, -0.26878070480712674F);
				this.headLeftSideMiddle = new RendererModel(this, 14, 51);
				this.headLeftSideMiddle.mirror = true;
				this.headLeftSideMiddle.setRotationPoint(7.82F, -7.11F, 0.86F);
				this.headLeftSideMiddle.addBox(-5.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotateAngle(headLeftSideMiddle, 0.0F, 0.0F, -0.26878070480712674F);
				this.headRightSideTop = new RendererModel(this, 0, 51);
				this.headRightSideTop.setRotationPoint(-4.6F, -11.4F, 0.86F);
				this.headRightSideTop.addBox(-5.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotateAngle(headRightSideTop, 0.0F, 0.0F, -0.890117918517108F);
				this.headRightSideMiddle = new RendererModel(this, 14, 51);
				this.headRightSideMiddle.setRotationPoint(-7.82F, -7.11F, 0.86F);
				this.headRightSideMiddle.addBox(0.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotateAngle(headRightSideMiddle, 0.0F, 0.0F, 0.26878070480712674F);
				this.headRightSideBottom = new RendererModel(this, 0, 57);
				this.headRightSideBottom.setRotationPoint(-9.58F, -1.23F, 0.91F);
				this.headRightSideBottom.addBox(0.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotateAngle(headRightSideBottom, 0.0F, -0.9147270609702279F, 0.26878070480712674F);
				this.headLeftSideTop = new RendererModel(this, 0, 51);
				this.headLeftSideTop.mirror = true;
				this.headLeftSideTop.setRotationPoint(4.6F, -11.4F, 0.86F);
				this.headLeftSideTop.addBox(0.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotateAngle(headLeftSideTop, 0.0F, 0.0F, 0.890117918517108F);
				this.headTop = new RendererModel(this, 20, 37);
				this.headTop.setRotationPoint(0.0F, -8.55F, -4.45F);
				this.headTop.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 8, 0.09F);
				setRotateAngle(headTop, 0.4714134309636684F, 0.0F, 0.0F);
				this.headCap = new RendererModel(this, 18, 49);
				this.headCap.setRotationPoint(0.0F, -11.4F, 0.85F);
				this.headCap.addBox(-4.5F, 0.15F, 0.19F, 9, 0, 2, 0.131F);
				this.headTail = new RendererModel(this, 28, 51);
				this.headTail.setRotationPoint(-1.5F, 0.4F, 2.5F);
				this.headTail.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, -0.2F);
				this.snakeBody = new RendererModel(this, 60, 0);
				this.snakeBody.setRotationPoint(0.0F, -7.5F, -4.6F);
				this.snakeBody.addBox(-0.5F, -3.0F, -1.0F, 1, 3, 1, 0.0F);
				setRotateAngle(snakeBody, -0.3490658503988659F, 0.0F, 0.0F);
				this.snakeHead = new RendererModel(this, 47, 19);
				this.snakeHead.setRotationPoint(0.0F, -10.65F, -4.5F);
				this.snakeHead.addBox(-1.5F, -1.0F, -2.0F, 3, 3, 3, -0.99F);
				setRotateAngle(snakeHead, 0.2792526803190927F, 0.0F, 0.0F);

				this.bipedHead = head;
				this.bipedHead.addChild(snakeBody);
				this.bipedHead.addChild(headLeftSideBottom);
				this.bipedHead.addChild(snakeHead);
				this.bipedHead.addChild(headLeftSideMiddle);
				this.bipedHead.addChild(headRightSideTop);
				this.bipedHead.addChild(headRightSideMiddle);
				this.bipedHead.addChild(headRightSideBottom);
				this.bipedHead.addChild(headTail);
				this.bipedHead.addChild(headLeftSideTop);
				this.bipedHead.addChild(headTop);
				this.bipedHead.addChild(headCap);
				break;

			case CHEST:
				this.body = new RendererModel(this, 0, 0);
				this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.body.addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, -0.1F);
				this.bodyCollar = new RendererModel(this, 28, 0);
				this.bodyCollar.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyCollar.addBox(-5.5F, -0.25F, -2.5F, 11, 5, 5, 0.2F);
				if(isSteve){
					this.armLeft = new RendererModel(this, 0, 18);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
					this.armRight = new RendererModel(this, 0, 18);
					this.armRight.setRotationPoint(-5.5F, 2.0F, -0.5F);
					this.armRight.addBox(-3.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
				}else{
					this.armLeft = new RendererModel(this, 0, 18);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armLeft.addBox(-1.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.armRight = new RendererModel(this, 0, 18);
					this.armRight.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRight.addBox(-2.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.bodyBreast = new RendererModel(this, 38, 49);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastCollar = new RendererModel(this, 38, 54);
					this.bodyBreastCollar.setRotationPoint(0.0F, -0.25F, -2.5F);
					this.bodyBreastCollar.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 0, 0.2F);
					setRotateAngle(bodyBreastCollar, -0.3403392041388943F, 0.0F, 0.0F);
				}

				this.bipedBody = body;
				this.bipedBody.addChild(bodyCollar);
				if(!isSteve){
					this.bipedBody.addChild(bodyBreast);
					this.bipedBody.addChild(bodyBreastCollar);
				}

				this.bipedLeftArm = armLeft;

				this.bipedRightArm = armRight;
				break;

			case LEGS:
				this.bodyBottom = new RendererModel(this, 28, 10);
				this.bodyBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyBottom.addBox(-4.5F, 8.5F, -2.5F, 9, 4, 5, 0.0F);
				this.bodyJewel = new RendererModel(this, 56, 10);
				this.bodyJewel.setRotationPoint(0.0F, 10.0F, -2.0F);
				this.bodyJewel.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, -0.3F);
				setRotateAngle(bodyJewel, 0.0F, 0.0F, 0.7853981633974483F);
				this.bodyGoldenStrip = new RendererModel(this, 56, 14);
				this.bodyGoldenStrip.setRotationPoint(0.0F, 10.0F, -2.6F);
				this.bodyGoldenStrip.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
				this.legLeft = new RendererModel(this, 0, 33);
				this.legLeft.mirror = true;
				this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
				this.legLeft.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);
				this.legRight = new RendererModel(this, 0, 33);
				this.legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
				this.legRight.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);

				this.bipedBody = bodyBottom;
				this.bipedBody.addChild(bodyGoldenStrip);
				this.bipedBody.addChild(bodyJewel);

				this.bipedRightLeg = legRight;

				this.bipedLeftLeg = legLeft;
				break;

			case FEET:
				this.footLeft = new RendererModel(this, 0, 44);
				this.footLeft.mirror = true;
				this.footLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.footLeft.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);
				this.footRight = new RendererModel(this, 0, 44);
				this.footRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.footRight.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);

				this.bipedLeftLeg = footLeft;

				this.bipedRightLeg = footRight;
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

		float f = 1.0F;
		if (entityIn.getTicksElytraFlying() > 4) {
			f = (float) (entityIn.getMotion().getX() * entityIn.getMotion().getX() + entityIn.getMotion().getY() * entityIn.getMotion().getY() + entityIn.getMotion().getZ() * entityIn.getMotion().getZ());
			f = f / 0.2F;
			f = f * f * f;
			if (f < 1.0F) f = 1.0F;
		}
		switch (this.slot) {
			case HEAD:
				f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + MathHelper.cos(limbSwing * 0.3331F + (float) Math.PI) * limbSwingAmount / f;
				this.headTail.rotateAngleX = f * 0.1F - headPitch * 0.017453292F * 0.8F;
				this.headTail.rotateAngleZ = -f * 0.1F;
				break;

			case LEGS:
				f = MathHelper.abs(MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f);
				this.bodyGoldenStrip.rotateAngleX = -f;

				if (this.isSitting) {
					this.bodyGoldenStrip.rotateAngleX += -1.0F;
				}

				if (this.isSneak) {
					this.bodyGoldenStrip.rotateAngleX += -0.75F;
					this.bodyGoldenStrip.rotationPointY = 8.0F;
					this.bodyJewel.rotationPointY = 8.0F;
				} else {
					this.bodyGoldenStrip.rotationPointY = 10.0F;
					this.bodyJewel.rotationPointY = 10.0F;
				}
				break;

			default:
				break;
		}
	}
}