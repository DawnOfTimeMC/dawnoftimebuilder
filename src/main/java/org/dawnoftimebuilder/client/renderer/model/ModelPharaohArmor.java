package org.dawnoftimebuilder.client.renderer.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPharaohArmor extends ModelBiped {

	private final EntityEquipmentSlot slot;

	//Helmet
	private ModelRenderer head;
	private ModelRenderer snakeBody;
	private ModelRenderer snakeHead;
	private ModelRenderer headLeftSideTop;
	private ModelRenderer headLeftSideMiddle;
	private ModelRenderer headLeftSideBottom;
	private ModelRenderer headRightSideTop;
	private ModelRenderer headRightSideMiddle;
	private ModelRenderer headRightSideBottom;
	private ModelRenderer headCap;
	private ModelRenderer headTop;
	private ModelRenderer headTail;

	//Chest
	private ModelRenderer body;
	private ModelRenderer bodyCollar;
	private ModelRenderer bodyBreast;
	private ModelRenderer bodyBreastCollar;
	private ModelRenderer armLeft;
	private ModelRenderer armRight;

	//Leggings
	private ModelRenderer bodyBottom;
	private ModelRenderer bodyJewel;
	private ModelRenderer bodyGoldenStrip;
	private ModelRenderer legLeft;
	private ModelRenderer legRight;

	//Boots
	private ModelRenderer footRight;
	private ModelRenderer footLeft;

	public ModelPharaohArmor(float scale, EntityEquipmentSlot slot, boolean isSteve) {
		super(scale, 0, 64, 64);

		this.slot = slot;

		switch (slot) {
			case HEAD:
				this.head = new ModelRenderer(this, 20, 19);
				this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.head.addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, 0.1F);
				this.headLeftSideBottom = new ModelRenderer(this, 0, 57);
				this.headLeftSideBottom.mirror = true;
				this.headLeftSideBottom.setRotationPoint(9.58F, -1.23F, 0.91F);
				this.headLeftSideBottom.addBox(-3.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				this.setRotateAngle(headLeftSideBottom, 0.0F, 0.9147270609702279F, -0.26878070480712674F);
				this.headLeftSideMiddle = new ModelRenderer(this, 14, 51);
				this.headLeftSideMiddle.mirror = true;
				this.headLeftSideMiddle.setRotationPoint(7.82F, -7.11F, 0.86F);
				this.headLeftSideMiddle.addBox(-5.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				this.setRotateAngle(headLeftSideMiddle, 0.0F, 0.0F, -0.26878070480712674F);
				this.headRightSideTop = new ModelRenderer(this, 0, 51);
				this.headRightSideTop.setRotationPoint(-4.6F, -11.4F, 0.86F);
				this.headRightSideTop.addBox(-5.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				this.setRotateAngle(headRightSideTop, 0.0F, 0.0F, -0.890117918517108F);
				this.headRightSideMiddle = new ModelRenderer(this, 14, 51);
				this.headRightSideMiddle.setRotationPoint(-7.82F, -7.11F, 0.86F);
				this.headRightSideMiddle.addBox(0.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				this.setRotateAngle(headRightSideMiddle, 0.0F, 0.0F, 0.26878070480712674F);
				this.headRightSideBottom = new ModelRenderer(this, 0, 57);
				this.headRightSideBottom.setRotationPoint(-9.58F, -1.23F, 0.91F);
				this.headRightSideBottom.addBox(0.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				this.setRotateAngle(headRightSideBottom, 0.0F, -0.9147270609702279F, 0.26878070480712674F);
				this.headLeftSideTop = new ModelRenderer(this, 0, 51);
				this.headLeftSideTop.mirror = true;
				this.headLeftSideTop.setRotationPoint(4.6F, -11.4F, 0.86F);
				this.headLeftSideTop.addBox(0.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				this.setRotateAngle(headLeftSideTop, 0.0F, 0.0F, 0.890117918517108F);
				this.headTop = new ModelRenderer(this, 20, 37);
				this.headTop.setRotationPoint(0.0F, -8.55F, -4.45F);
				this.headTop.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 8, 0.09F);
				this.setRotateAngle(headTop, 0.4714134309636684F, 0.0F, 0.0F);
				this.headCap = new ModelRenderer(this, 18, 49);
				this.headCap.setRotationPoint(0.0F, -11.4F, 0.85F);
				this.headCap.addBox(-4.5F, 0.15F, 0.19F, 9, 0, 2, 0.131F);
				this.headTail = new ModelRenderer(this, 28, 51);
				this.headTail.setRotationPoint(-1.5F, 0.4F, 2.5F);
				this.headTail.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, -0.2F);
				this.snakeBody = new ModelRenderer(this, 60, 0);
				this.snakeBody.setRotationPoint(0.0F, -7.5F, -4.6F);
				this.snakeBody.addBox(-0.5F, -3.0F, -1.0F, 1, 3, 1, 0.0F);
				this.setRotateAngle(snakeBody, -0.3490658503988659F, 0.0F, 0.0F);
				this.snakeHead = new ModelRenderer(this, 47, 19);
				this.snakeHead.setRotationPoint(0.0F, -10.65F, -4.5F);
				this.snakeHead.addBox(-1.5F, -1.0F, -2.0F, 3, 3, 3, -0.99F);
				this.setRotateAngle(snakeHead, 0.2792526803190927F, 0.0F, 0.0F);

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
				this.body = new ModelRenderer(this, 0, 0);
				this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.body.addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, -0.1F);
				this.bodyCollar = new ModelRenderer(this, 28, 0);
				this.bodyCollar.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyCollar.addBox(-5.5F, -0.25F, -2.5F, 11, 5, 5, 0.2F);
				if(isSteve){
					this.armLeft = new ModelRenderer(this, 0, 18);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
					this.armRight = new ModelRenderer(this, 0, 18);
					this.armRight.setRotationPoint(-5.5F, 2.0F, -0.5F);
					this.armRight.addBox(-3.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
				}else{
					this.armLeft = new ModelRenderer(this, 0, 18);
					this.armLeft.mirror = true;
					this.armLeft.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armLeft.addBox(-1.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.armRight = new ModelRenderer(this, 0, 18);
					this.armRight.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRight.addBox(-2.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.bodyBreast = new ModelRenderer(this, 38, 49);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					this.setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastCollar = new ModelRenderer(this, 38, 54);
					this.bodyBreastCollar.setRotationPoint(0.0F, -0.25F, -2.5F);
					this.bodyBreastCollar.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 0, 0.2F);
					this.setRotateAngle(bodyBreastCollar, -0.3403392041388943F, 0.0F, 0.0F);
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
				this.bodyBottom = new ModelRenderer(this, 28, 10);
				this.bodyBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyBottom.addBox(-4.5F, 8.5F, -2.5F, 9, 4, 5, 0.0F);
				this.bodyJewel = new ModelRenderer(this, 56, 10);
				this.bodyJewel.setRotationPoint(0.0F, 10.0F, -2.0F);
				this.bodyJewel.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, -0.3F);
				this.setRotateAngle(bodyJewel, 0.0F, 0.0F, 0.7853981633974483F);
				this.bodyGoldenStrip = new ModelRenderer(this, 56, 14);
				this.bodyGoldenStrip.setRotationPoint(0.0F, 10.0F, -2.6F);
				this.bodyGoldenStrip.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
				this.legLeft = new ModelRenderer(this, 0, 33);
				this.legLeft.mirror = true;
				this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
				this.legLeft.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);
				this.legRight = new ModelRenderer(this, 0, 33);
				this.legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
				this.legRight.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);

				this.bipedBody = bodyBottom;
				this.bipedBody.addChild(bodyGoldenStrip);
				this.bipedBody.addChild(bodyJewel);

				this.bipedRightLeg = legRight;

				this.bipedLeftLeg = legLeft;
				break;

			case FEET:
				this.footLeft = new ModelRenderer(this, 0, 44);
				this.footLeft.mirror = true;
				this.footLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.footLeft.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);
				this.footRight = new ModelRenderer(this, 0, 44);
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
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadyaw, float headPitch, float scaleFactor) {
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
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadyaw, headPitch, scaleFactor);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		float f = 1.0F;
		if (entityIn instanceof EntityLivingBase) {
			if (((EntityLivingBase) entityIn).getTicksElytraFlying() > 4) {
				f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
				f = f / 0.2F;
				f = f * f * f;
				if (f < 1.0F) f = 1.0F;
			}
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

				if (this.isRiding) {
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

		//Fix the "breathing" and wrong head rotation on ArmorStands
		if (entityIn instanceof EntityArmorStand) {
			EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
			this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
			this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
			this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
			this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
			copyModelAngles(this.bipedHead, this.bipedHeadwear);
		}
	}

	private float sinPI(float f) {
		return MathHelper.sin(f * (float)Math.PI);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
