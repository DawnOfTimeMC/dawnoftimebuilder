package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class CenturionArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	public CenturionArmorModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 128, 64, scale);

		switch (slot) {
			case HEAD:
				this.head = new ModelRenderer(this);
				this.head.setPos(0.0F, 0.0F, 0.0F);
				this.head.texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, 0.25F, false);
				this.head.texOffs(75, 0).addBox(-5.0F, -6.0F, -5.25F, 10.0F, 1.0F, 1.0F, 0.0F, false);
				this.head.texOffs(24, 6).addBox(-1.0F, -14.25F, -2.75F, 2.0F, 11.0F, 12.0F, 0.0F, false);
				this.head.texOffs(40, 17).addBox(-1.0F, -14.25F, -2.75F, 2.0F, 11.0F, 12.0F, 0.25F, false);
				this.head.texOffs(0, 0).addBox(-1.0F, -14.25F, -3.75F, 2.0F, 4.0F, 1.0F, 0.0F, false);
				this.head.texOffs(0, 59).addBox(-1.0F, -14.25F, -3.75F, 2.0F, 4.0F, 1.0F, 0.25F, false);
				ModelRenderer jow = new ModelRenderer(this);
				jow.setPos(0.0F, 24.0F, 0.0F);
				jow.texOffs(75, -4).addBox(4.65F, -26.0F, 4.75F, 0.0F, 3.0F, 6.0F, 0.0F, false);
				jow.texOffs(75, -4).addBox(-4.65F, -26.0F, 4.75F, 0.0F, 3.0F, 6.0F, 0.0F, false);
				setRotationAngle(jow, 0.3927F, 0.0F, 0.0F);
				this.head.addChild(jow);

			case CHEST:
				this.body = new ModelRenderer(this);
				this.body.setPos(0.0F, 0.0F, 0.0F);
				this.body.texOffs(0, 18).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.3F, false);
				this.body.texOffs(0, 34).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.55F, false);
				this.body.texOffs(0, 50).addBox(2.1768F, 5.8232F, -3.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

				ModelRenderer chestBelt = new ModelRenderer(this);
				ModelRenderer right_shoulder = new ModelRenderer(this);
				ModelRenderer left_shoulder = new ModelRenderer(this);
				if(isSteve){
					chestBelt.setPos(-4.0F, 1.0F, -2.5F);
					chestBelt.texOffs(24, 29).addBox(0.0F, -1.0F, 0.0F, 2.0F, 10.0F, 5.0F, 0.2F, false);
					setRotationAngle(chestBelt, 0.0F, 0.0F, -0.7854F);
					this.body.addChild(chestBelt);

					this.rightArm = new ModelRenderer(this);
					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(19, 47).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, false);
					this.rightArm.texOffs(39, 47).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, 0.25F, false);
					right_shoulder.setPos(-0.25F, -0.25F, 0.0F);
					right_shoulder.texOffs(56, 17).addBox(-3.2726F, -3.4043F, -3.0F, 5.0F, 2.0F, 6.0F, 0.0F, false);
					right_shoulder.texOffs(74, 11).addBox(0.0F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, 0.25F, false);

					this.leftArm = new ModelRenderer(this);
					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(19, 47).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, true);
					this.leftArm.texOffs(39, 47).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, 0.25F, true);
					left_shoulder.setPos(0.25F, -0.25F, 0.0F);
					left_shoulder.texOffs(56, 17).addBox(-1.7274F, -3.4043F, -3.0F, 5.0F, 2.0F, 6.0F, 0.0F, true);
				}else{
					ModelRenderer chestBreast = new ModelRenderer(this);
					chestBreast.setPos(0.0F, 2.0F, -3.0F);
					chestBreast.texOffs(68, 25).addBox(-3.5F, -1.75F, 0.0F, 7.0F, 4.0F, 2.0F, 0.0F, false);
					setRotationAngle(chestBreast, -0.48F, 0.0F, 0.0F);
					chestBelt.setPos(1.9309F, 5.4684F, -3.542F);
					chestBelt.texOffs(74, 12).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, false);
					setRotationAngle(chestBelt, 0.4386F, -0.0319F, -0.7317F);
					ModelRenderer chestBeltDown = new ModelRenderer(this);
					chestBeltDown.setPos(-4.0505F, 0.6704F, -3.0498F);
					chestBeltDown.texOffs(70, 12).addBox(0.0F, -1.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, false);
					setRotationAngle(chestBeltDown, -0.2397F, -0.0564F, -0.7652F);
					ModelRenderer chestBeltUp = new ModelRenderer(this);
					chestBeltUp.setPos(-4.0F, 1.0F, -2.5F);
					chestBeltUp.texOffs(24, 29).addBox(0.0F, -1.0F, 0.0F, 2.0F, 10.0F, 5.0F, 0.2F, false);
					setRotationAngle(chestBeltUp, 0.0F, 0.0F, -0.7854F);
					this.body.addChild(chestBreast);
					this.body.addChild(chestBelt);
					this.body.addChild(chestBeltUp);
					this.body.addChild(chestBeltDown);

					this.rightArm = new ModelRenderer(this);
					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(19, 47).addBox(-2.5F, -2.5F, -2.5F, 4.0F, 12.0F, 5.0F, 0.0F, false);
					this.rightArm.texOffs(37, 47).addBox(-2.5F, -2.5F, -2.5F, 4.0F, 12.0F, 5.0F, 0.25F, false);
					right_shoulder.setPos(-0.25F, -0.25F, 0.0F);
					right_shoulder.texOffs(56, 17).addBox(-2.2726F, -3.4043F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
					right_shoulder.texOffs(74, 11).addBox(0.0F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, 0.25F, false);

					this.leftArm = new ModelRenderer(this);
					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(19, 47).addBox(-1.5F, -2.5F, -2.5F, 4.0F, 12.0F, 5.0F, 0.0F, true);
					this.leftArm.texOffs(37, 47).addBox(-1.5F, -2.5F, -2.5F, 4.0F, 12.0F, 5.0F, 0.25F, true);
					left_shoulder.setPos(0.25F, -0.25F, 0.0F);
					left_shoulder.texOffs(56, 17).addBox(-1.7274F, -3.4043F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, true);
				}
				
				left_shoulder.texOffs(74, 11).addBox(-1.0F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, 0.25F, true);
				setRotationAngle(right_shoulder, 0.0F, 0.0F, -0.3927F);
				setRotationAngle(left_shoulder, 0.0F, 0.0F, 0.3927F);

				this.rightArm.addChild(right_shoulder);
				this.leftArm.addChild(left_shoulder);
				break;

			case LEGS:
				this.body = new ModelRenderer(this);

				this.rightLeg = new ModelRenderer(this);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg.texOffs(40, 0).addBox(-2.6F, 0.3F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);
				this.rightLeg.texOffs(60, 0).addBox(-2.6F, 0.25F, -2.5F, 5.0F, 7.0F, 5.0F, 0.15F, false);

				this.leftLeg = new ModelRenderer(this);
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg.texOffs(40, 0).addBox(-2.4F, 0.3F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, true);
				this.leftLeg.texOffs(60, 0).addBox(-2.4F, 0.25F, -2.5F, 5.0F, 7.0F, 5.0F, 0.15F, true);
				break;

			case FEET:
				this.rightLeg = new ModelRenderer(this);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg.texOffs(59, 48).addBox(-2.6F, 9.25F, -2.5F, 5.0F, 3.0F, 5.0F, 0.15F, false);
				this.rightLeg.texOffs(59, 56).addBox(-2.6F, 9.3F, -2.5F, 5.0F, 3.0F, 5.0F, 0.0F, false);

				this.leftLeg = new ModelRenderer(this);
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg.texOffs(59, 48).addBox(-2.4F, 9.25F, -2.5F, 5.0F, 3.0F, 5.0F, 0.15F, true);
				this.leftLeg.texOffs(59, 56).addBox(-2.4F, 9.3F, -2.5F, 5.0F, 3.0F, 5.0F, 0.0F, true);
				break;

			default:
				break;
		}
	}

	@Override
	protected void setupArmorAnim(T entityIn, float ageInTicks) {

	}
}