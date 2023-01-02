package org.dawnoftimebuilder.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal")
public class PharaohArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	private ModelRenderer armorHead;
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
	private ModelRenderer armorBody;
	private ModelRenderer bodyCollar;
	private ModelRenderer bodyBreast;
	private ModelRenderer bodyBreastCollar;
	private ModelRenderer armorArmLeft;
	private ModelRenderer armorArmRight;

	//Leggings
	private ModelRenderer bodyBottom;
	private ModelRenderer bodyJewel;
	private ModelRenderer bodyGoldenStrip;
	private ModelRenderer armorLegLeft;
	private ModelRenderer armorLegRight;

	//Boots
	private ModelRenderer footRight;
	private ModelRenderer footLeft;

	public PharaohArmorModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 64, 64, scale);

		switch (slot) {
			case HEAD:
				this.armorHead = new ModelRenderer(this);
				this.armorHead.setPos(0.0F, 0.0F, 0.0F);
				this.armorHead.texOffs(20, 19).addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, 0.1F, false);
				this.headLeftSideBottom = new ModelRenderer(this, 0, 57);
				this.headLeftSideBottom.mirror = true;
				this.headLeftSideBottom.setPos(9.58F, -1.23F, 0.91F);
				this.headLeftSideBottom.addBox(-3.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotateAngle(headLeftSideBottom, 0.0F, 0.9147270609702279F, -0.26878070480712674F);
				this.headLeftSideMiddle = new ModelRenderer(this, 14, 51);
				this.headLeftSideMiddle.mirror = true;
				this.headLeftSideMiddle.setPos(7.82F, -7.11F, 0.86F);
				this.headLeftSideMiddle.addBox(-5.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotateAngle(headLeftSideMiddle, 0.0F, 0.0F, -0.26878070480712674F);
				this.headRightSideTop = new ModelRenderer(this, 0, 51);
				this.headRightSideTop.setPos(-4.6F, -11.4F, 0.86F);
				this.headRightSideTop.addBox(-5.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotateAngle(headRightSideTop, 0.0F, 0.0F, -0.890117918517108F);
				this.headRightSideMiddle = new ModelRenderer(this, 14, 51);
				this.headRightSideMiddle.setPos(-7.82F, -7.11F, 0.86F);
				this.headRightSideMiddle.addBox(0.0F, 0.0F, 0.19F, 5, 6, 2, 0.14F);
				setRotateAngle(headRightSideMiddle, 0.0F, 0.0F, 0.26878070480712674F);
				this.headRightSideBottom = new ModelRenderer(this, 0, 57);
				this.headRightSideBottom.setPos(-9.58F, -1.23F, 0.91F);
				this.headRightSideBottom.addBox(0.0F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
				setRotateAngle(headRightSideBottom, 0.0F, -0.9147270609702279F, 0.26878070480712674F);
				this.headLeftSideTop = new ModelRenderer(this, 0, 51);
				this.headLeftSideTop.mirror = true;
				this.headLeftSideTop.setPos(4.6F, -11.4F, 0.86F);
				this.headLeftSideTop.addBox(0.15F, 0.13F, 0.19F, 5, 4, 2, 0.13F);
				setRotateAngle(headLeftSideTop, 0.0F, 0.0F, 0.890117918517108F);
				this.headTop = new ModelRenderer(this, 20, 37);
				this.headTop.setPos(0.0F, -8.55F, -4.45F);
				this.headTop.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 8, 0.09F);
				setRotateAngle(headTop, 0.4714134309636684F, 0.0F, 0.0F);
				this.headCap = new ModelRenderer(this, 18, 49);
				this.headCap.setPos(0.0F, -11.4F, 0.85F);
				this.headCap.addBox(-4.5F, 0.15F, 0.19F, 9, 0, 2, 0.131F);
				this.headTail = new ModelRenderer(this, 28, 51);
				this.headTail.setPos(-1.5F, 0.4F, 2.5F);
				this.headTail.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, -0.2F);
				this.snakeBody = new ModelRenderer(this, 60, 0);
				this.snakeBody.setPos(0.0F, -7.5F, -4.6F);
				this.snakeBody.addBox(-0.5F, -3.0F, -1.0F, 1, 3, 1, 0.0F);
				setRotateAngle(snakeBody, -0.3490658503988659F, 0.0F, 0.0F);
				this.snakeHead = new ModelRenderer(this, 47, 19);
				this.snakeHead.setPos(0.0F, -10.65F, -4.5F);
				this.snakeHead.addBox(-1.5F, -1.0F, -2.0F, 3, 3, 3, -0.99F);
				setRotateAngle(snakeHead, 0.2792526803190927F, 0.0F, 0.0F);

				this.head = this.armorHead;
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
				this.armorBody = new ModelRenderer(this, 0, 0);
				this.armorBody.setPos(0.0F, 0.0F, 0.0F);
				this.armorBody.addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, -0.1F);
				this.bodyCollar = new ModelRenderer(this, 28, 0);
				this.bodyCollar.setPos(0.0F, 0.0F, 0.0F);
				this.bodyCollar.addBox(-5.5F, -0.25F, -2.5F, 11, 5, 5, 0.2F);
				if(isSteve){
					this.armorArmLeft = new ModelRenderer(this, 0, 18);
					this.armorArmLeft.mirror = true;
					this.armorArmLeft.setPos(5.0F, 2.0F, 0.0F);
					this.armorArmLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
					this.armorArmRight = new ModelRenderer(this, 0, 18);
					this.armorArmRight.setPos(-5.5F, 2.0F, -0.5F);
					this.armorArmRight.addBox(-3.5F, -2.5F, -2.5F, 5, 10, 5, -0.2F);
				}else{
					this.armorArmLeft = new ModelRenderer(this, 0, 18);
					this.armorArmLeft.mirror = true;
					this.armorArmLeft.setPos(5.0F, 2.5F, 0.0F);
					this.armorArmLeft.addBox(-1.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.armorArmRight = new ModelRenderer(this, 0, 18);
					this.armorArmRight.setPos(-5.0F, 2.5F, 0.0F);
					this.armorArmRight.addBox(-2.5F, -2.5F, -2.5F, 4, 10, 5, -0.2F);
					this.bodyBreast = new ModelRenderer(this, 38, 49);
					this.bodyBreast.setPos(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastCollar = new ModelRenderer(this, 38, 54);
					this.bodyBreastCollar.setPos(0.0F, -0.25F, -2.5F);
					this.bodyBreastCollar.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 0, 0.2F);
					setRotateAngle(bodyBreastCollar, -0.3403392041388943F, 0.0F, 0.0F);
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
				this.bodyBottom = new ModelRenderer(this, 28, 10);
				this.bodyBottom.setPos(0.0F, 0.0F, 0.0F);
				this.bodyBottom.addBox(-4.5F, 8.5F, -2.5F, 9, 4, 5, 0.0F);
				this.bodyJewel = new ModelRenderer(this, 56, 10);
				this.bodyJewel.setPos(0.0F, 10.0F, -2.0F);
				this.bodyJewel.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, -0.3F);
				setRotateAngle(bodyJewel, 0.0F, 0.0F, 0.7853981633974483F);
				this.bodyGoldenStrip = new ModelRenderer(this, 56, 14);
				this.bodyGoldenStrip.setPos(0.0F, 10.0F, -2.6F);
				this.bodyGoldenStrip.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
				this.armorLegLeft = new ModelRenderer(this, 0, 33);
				this.armorLegLeft.mirror = true;
				this.armorLegLeft.setPos(2.0F, 12.0F, 0.0F);
				this.armorLegLeft.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);
				this.armorLegRight = new ModelRenderer(this, 0, 33);
				this.armorLegRight.setPos(-2.0F, 12.0F, 0.0F);
				this.armorLegRight.addBox(-2.5F, -0.5F, -2.5F, 5, 6, 5, 0.0F);

				this.body = bodyBottom;
				this.body.addChild(bodyGoldenStrip);
				this.body.addChild(bodyJewel);

				this.rightLeg = armorLegRight;

				this.leftLeg = armorLegLeft;
				break;

			case FEET:
				this.footLeft = new ModelRenderer(this, 0, 44);
				this.footLeft.mirror = true;
				this.footLeft.setPos(1.9F, 12.0F, 0.0F);
				this.footLeft.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);
				this.footRight = new ModelRenderer(this, 0, 44);
				this.footRight.setPos(-1.9F, 12.0F, 0.0F);
				this.footRight.addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, -0.2F);

				this.leftLeg = footLeft;

				this.rightLeg = footRight;
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