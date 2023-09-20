package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class JapaneseLightArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	private ModelRenderer ribbonA;
	private ModelRenderer ribbonB;

	public JapaneseLightArmorModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 64, 32, scale);

		ModelRenderer armLeftArmor;
		ModelRenderer armRightArmor;
		switch (slot) {
			case HEAD:
				this.head = new ModelRenderer(this, 26, 16);
				this.head.setPos(0.0F, 0.0F, 0.0F);
				this.head.addBox(-4.0F, -6.5F, -4.0F, 8, 2, 8, 0.4F);
				ModelRenderer knotBase = new ModelRenderer(this, 56, 9);
				knotBase.setPos(0.0F, 0.0F, 0.0F);
				knotBase.addBox(2.9F, -4.9F, 3.7F, 2, 2, 1, 0.4F);
				setRotationAngle(knotBase, 0.0F, 0.0F, -0.785F);
				this.ribbonA = new ModelRenderer(this, 56, 0);
				this.ribbonA.setPos(0.0F, -5.0F, 4.2F);
				this.ribbonA.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
				setRotationAngle(ribbonA, 0.35F, 0.0F, 0.0F);
				this.ribbonB = new ModelRenderer(this, 60, 0);
				this.ribbonB.setPos(0.0F, -5.0F, 4.2F);
				this.ribbonB.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 0, 0.0F);
				setRotationAngle(ribbonB, 0.175F, 0.0F, 0.0F);

				this.head.addChild(knotBase);
				this.head.addChild(ribbonA);
				this.head.addChild(ribbonB);
				break;

			case CHEST:
				this.body = new ModelRenderer(this, 0, 0);
				this.body.setPos(0.0F, 0.0F, 0.0F);
				this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.45F);
				if(isSteve){
					this.rightArm = new ModelRenderer(this, 24, 0);
					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					armRightArmor = new ModelRenderer(this, 12, 16);
					armRightArmor.mirror = true;
					armRightArmor.setPos(0.0F, 0.0F, 0.0F);
					armRightArmor.addBox(-3.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);

					this.leftArm = new ModelRenderer(this, 24, 0);
					this.leftArm.mirror = true;
					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					armLeftArmor = new ModelRenderer(this, 12, 16);
					armLeftArmor.setPos(0.0F, 0.0F, 0.0F);
					armLeftArmor.addBox(1.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
				}else{
					ModelRenderer bodyBreast = new ModelRenderer(this, 0, 26);
					bodyBreast.setPos(0.0F, 1.3F, -2.0F);
					bodyBreast.addBox(-3.0F, 0.0F, -3.65F, 6, 2, 3, 0.1F);
					setRotationAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.body.addChild(bodyBreast);

					this.rightArm = new ModelRenderer(this, 24, 0);
					this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
					this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					armRightArmor = new ModelRenderer(this, 12, 16);
					armRightArmor.mirror = true;
					armRightArmor.setPos(0.0F, 0.0F, 0.0F);
					armRightArmor.addBox(-2.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);

					this.leftArm = new ModelRenderer(this, 24, 0);
					this.leftArm.mirror = true;
					this.leftArm.setPos(5.0F, 2.5F, 0.0F);
					this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					armLeftArmor = new ModelRenderer(this, 12, 16);
					armLeftArmor.setPos(0.0F, 0.0F, 0.0F);
					armLeftArmor.addBox(0.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);
				}

				this.leftArm.addChild(armLeftArmor);
				this.rightArm.addChild(armRightArmor);
				break;

			case LEGS:
				this.body = new ModelRenderer(this);

				this.rightLeg = new ModelRenderer(this, 0, 0);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				ModelRenderer legRightArmor = new ModelRenderer(this, 0, 16);
				legRightArmor.setPos(-0.0F, 0.0F, 0.0F);
				legRightArmor.addBox(-2.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotationAngle(legRightArmor, 0.0F, 0.0F, 0.2F);

				this.leftLeg = new ModelRenderer(this, 0, 0);
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				ModelRenderer legLeftArmor = new ModelRenderer(this, 0, 16);
				legLeftArmor.mirror = true;
				legLeftArmor.setPos(0.0F, 0.0F, 0.0F);
				legLeftArmor.addBox(0.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotationAngle(legLeftArmor, 0.0F, 0.0F, -0.2F);

				this.rightLeg.addChild(legRightArmor);
				this.leftLeg.addChild(legLeftArmor);

				break;

			case FEET:
				this.rightLeg = new ModelRenderer(this, 40, 0);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

				this.leftLeg = new ModelRenderer(this, 40, 0);
				this.leftLeg.mirror = true;
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				break;

			default:
				break;
		}

	}

	@Override
	public void setupArmorAnim(T entityIn, float ageInTicks) {

		if (this.slot == EquipmentSlotType.HEAD) {
			float f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + Math.abs(this.rightLeg.xRot);
			this.ribbonA.xRot = Math.max(0.35F + f * 0.15F - this.head.xRot, 0.2F);
			this.ribbonA.zRot = -0.1F + f * 0.2F;
			this.ribbonB.xRot = Math.max(0.175F + f * 0.1F - this.head.xRot, 0.075F);
			this.ribbonB.zRot = -f * 0.2F;
		}
	}
}