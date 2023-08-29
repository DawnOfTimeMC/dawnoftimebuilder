package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class AnubisModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public ModelRenderer earRight;
	public ModelRenderer ribbonRight;
	public ModelRenderer earLeft;
	public ModelRenderer ribbonLeft;

	//Chest
	public ModelRenderer collarBack;
	public ModelRenderer crossA;
	public ModelRenderer crossB;
	public ModelRenderer crossC;
	public ModelRenderer crossD;

	//Leggings
	public ModelRenderer ribbonLegs;

	public AnubisModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 128, 64, scale);

		switch (slot) {
			case HEAD:
				this.head = new ModelRenderer(this);
				this.head.setPos(0.0F, 0.0F, 0.0F);
				this.head.texOffs(96, 48).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
				this.head.texOffs(34, 0).addBox(-4.5F, -10.25F, -4.5F, 9.0F, 12.0F, 9.0F, 0.05F, false);
				this.head.texOffs(0, 0).addBox(-4.0F, -10.0F, -6.0F, 8.0F, 7.0F, 9.0F, 0.0F, false);
				this.ribbonLeft = new ModelRenderer(this);
				this.ribbonLeft.setPos(4.75F, -8.0F, -4.386F);
				setRotationAngle(this.ribbonLeft, 0.0F, -0.9599F, 0.0F);
				this.ribbonLeft.texOffs(10, 16).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 0.0F, 0.0F, false);
				ModelRenderer ribbonLeftNode = new ModelRenderer(this);
				ribbonLeftNode.setPos(2.7151F, -6.9F, -4.168F);
				setRotationAngle(ribbonLeftNode, 0.0F, 0.6109F, 0.0F);
				ribbonLeftNode.texOffs(0, 5).addBox(0.1F, -2.1F, -0.032F, 2.0F, 2.0F, 2.0F, 0.1F, false);
				this.ribbonRight = new ModelRenderer(this);
				this.ribbonRight.setPos(-4.75F, -8.0F, -4.386F);
				setRotationAngle(this.ribbonRight, 0.0F, 0.9599F, 0.0F);
				this.ribbonRight.texOffs(10, 16).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 0.0F, 0.0F, true);
				ModelRenderer ribbonRightNode = new ModelRenderer(this);
				ribbonRightNode.setPos(-2.7151F, -6.9F, -4.168F);
				setRotationAngle(ribbonRightNode, 0.0F, -0.6109F, 0.0F);
				ribbonRightNode.texOffs(0, 5).addBox(-2.1F, -2.1F, -0.032F, 2.0F, 2.0F, 2.0F, 0.1F, true);
				ModelRenderer nose_r1 = new ModelRenderer(this);
				nose_r1.setPos(1.0F, -7.0F, -6.0F);
				setRotationAngle(nose_r1, 0.3054F, 0.0F, 0.0F);
				nose_r1.texOffs(25, 0).addBox(-3.0F, 0.0F, -5.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
				ModelRenderer headLayerRight_r1 = new ModelRenderer(this);
				headLayerRight_r1.setPos(-4.55F, -10.3F, -0.55F);
				setRotationAngle(headLayerRight_r1, 0.0F, 0.0F, 0.2618F);
				headLayerRight_r1.texOffs(98, 0).addBox(0.05F, 0.05F, 0.04F, 3.0F, 10.0F, 4.0F, 0.05F, true);
				ModelRenderer headLayerLeft_r1 = new ModelRenderer(this);
				headLayerLeft_r1.setPos(4.55F, -10.3F, -0.55F);
				setRotationAngle(headLayerLeft_r1, 0.0F, 0.0F, -0.2618F);
				headLayerLeft_r1.texOffs(98, 0).addBox(-3.05F, 0.05F, 0.04F, 3.0F, 10.0F, 4.0F, 0.05F, false);
				this.earRight = new ModelRenderer(this);
				this.earRight.setPos(-4.0F, -7.0F, -0.5F);
				this.earRight.texOffs(0, 0).addBox(-0.7342F, -5.0323F, -1.0764F, 2.0F, 4.0F, 1.0F, 0.0F, false);
				this.earRight.texOffs(0, 16).addBox(-3.2342F, -10.5323F, -0.5764F, 5.0F, 10.0F, 0.0F, 0.0F, false);
				this.earLeft = new ModelRenderer(this);
				this.earLeft.setPos(4.0F, -7.0F, -0.5F);
				this.earLeft.texOffs(0, 0).addBox(-1.2658F, -5.0323F, -1.0764F, 2.0F, 4.0F, 1.0F, 0.0F, true);
				this.earLeft.texOffs(0, 16).addBox(-1.7658F, -10.5323F, -0.5764F, 5.0F, 10.0F, 0.0F, 0.0F, true);

				if(isSteve){
					setRotationAngle(this.earRight, 0.1249F, 0.2577F, -0.0465F);
					setRotationAngle(this.earLeft, 0.1249F, -0.2577F, 0.0465F);
				}else{
					setRotationAngle(this.earRight, 0.1681F, 0.2322F, 0.1315F);
					setRotationAngle(this.earLeft, 0.1681F, -0.2322F, -0.1315F);
				}

				this.head.addChild(this.earLeft);
				this.head.addChild(this.earRight);
				this.head.addChild(this.ribbonLeft);
				this.head.addChild(ribbonLeftNode);
				this.head.addChild(this.ribbonRight);
				this.head.addChild(ribbonRightNode);
				this.head.addChild(nose_r1);
				this.head.addChild(headLayerLeft_r1);
				this.head.addChild(headLayerRight_r1);
				break;

			case CHEST:
				this.body = new ModelRenderer(this);
				this.body.setPos(0.0F, 0.0F, 0.0F);
				this.body.texOffs(104, 48).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
				this.body.texOffs(70, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.22F, false);
				this.body.texOffs(-6, 52).addBox(-4.5F, -0.45F, -2.5F, 9.0F, 0.0F, 6.0F, 0.0F, false);
				ModelRenderer chestBack = new ModelRenderer(this);
				chestBack.setPos(0.0F, 6.2F, 2.5F);
				setRotationAngle(chestBack, -0.0873F, 0.0F, 0.0F);
				chestBack.texOffs(20, 31).addBox(-3.5F, -6.5F, -1.7F, 7.0F, 12.0F, 2.0F, 0.0F, false);
				ModelRenderer collarFront = new ModelRenderer(this);
				collarFront.setPos(0.0F, -0.45F, -2.5F);
				collarFront.texOffs(-5, 58).addBox(-4.5F, 0.0F, -5.0F, 9.0F, 0.0F, 5.0F, 0.0F, false);
				this.collarBack = new ModelRenderer(this);
				this.collarBack.setPos(0.0F, -0.45F, 3.5F);
				setRotationAngle(this.collarBack, -1.5708F, 0.0F, 0.0F);
				this.collarBack.texOffs(-8, 44).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 0.0F, 8.0F, 0.0F, false);
				this.crossA = new ModelRenderer(this);
				this.crossA.setPos(0.0F, 20.0F, 4.0F);
				this.crossA.texOffs(18, 45).addBox(-2.5F, -44.0F, 2.0F, 5.0F, 6.0F, 0.0F, 0.0F, false);
				this.crossB = new ModelRenderer(this);
				this.crossB.setPos(0.0F, 20.0F, 4.0F);
				this.crossB.texOffs(18, 45).addBox(-2.5F, -44.0F, 2.0F, 5.0F, 6.0F, 0.0F, 0.0F, false);
				this.crossC = new ModelRenderer(this);
				this.crossC.setPos(0.0F, 20.0F, 4.0F);
				this.crossC.texOffs(18, 45).addBox(-2.5F, -44.0F, 2.0F, 5.0F, 6.0F, 0.0F, 0.0F, false);
				this.crossD = new ModelRenderer(this);
				this.crossD.setPos(0.0F, 20.0F, 4.0F);
				this.crossD.texOffs(18, 45).addBox(-2.5F, -44.0F, 2.0F, 5.0F, 6.0F, 0.0F, 0.0F, false);

				ModelRenderer chestCore = new ModelRenderer(this);
				ModelRenderer chestPecRight = new ModelRenderer(this);
				ModelRenderer chestPecLeft = new ModelRenderer(this);
				ModelRenderer abs = new ModelRenderer(this);
				ModelRenderer shoulderRight = new ModelRenderer(this);
				ModelRenderer shoulderLeft = new ModelRenderer(this);
				this.rightArm = new ModelRenderer(this);
				this.leftArm = new ModelRenderer(this);

				if(isSteve){
					setRotationAngle(collarFront, 1.309F, 0.0F, 0.0F);
					abs.setPos(0.0F, 4.7F, -3.3F);
					setRotationAngle(abs, 0.0873F, 0.0F, 0.0F);
					abs.texOffs(38, 21).addBox(-3.5F, -1.0F, 0.3F, 7.0F, 8.0F, 1.0F, 0.0F, false);
					chestPecRight.setPos(0.5F, 4.0F, -0.5F);
					setRotationAngle(chestPecRight, -0.2007F, 0.0F, -0.2618F);
					chestPecRight.texOffs(108, 10).addBox(-4.0F, -4.0F, -2.9F, 4.0F, 4.0F, 4.0F, 0.0F, true);
					chestPecLeft.setPos(-0.5F, 4.0F, -0.5F);
					setRotationAngle(chestPecLeft, -0.2007F, 0.0F, 0.2618F);
					chestPecLeft.texOffs(108, 10).addBox(0.0F, -4.0F, -2.9F, 4.0F, 4.0F, 4.0F, 0.0F, false);
					chestCore.setPos(0.0F, 4.5F, -3.6F);
					setRotationAngle(chestCore, -0.192F, 0.0F, 0.0F);
					chestCore.texOffs(61, 0).addBox(-1.0F, -3.5F, 0.05F, 2.0F, 3.0F, 1.0F, 0.0F, false);

					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(112, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
					this.rightArm.texOffs(0, 26).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, -0.2F, false);
					shoulderRight.setPos(-2.25F, -0.75F, 0.0F);

					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(112, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
					this.leftArm.texOffs(0, 26).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, -0.2F, true);
					shoulderLeft.setPos(2.25F, -0.75F, 0.0F);
				}else{
					setRotationAngle(collarFront, 1.1345F, 0.0F, 0.0F);
					abs.setPos(0.0F, 4.7F, -3.3F);
					abs.texOffs(38, 21).addBox(-3.5F, -1.0F, 0.8F, 7.0F, 8.0F, 1.0F, 0.0F, false);
					chestPecRight.setPos(0.5F, 4.0F, -0.5F);
					setRotationAngle(chestPecRight, -0.3752F, 0.0F, -0.1745F);
					chestPecRight.texOffs(108, 10).addBox(-4.0F, -3.0F, -3.5F, 4.0F, 4.0F, 4.0F, 0.0F, true);
					chestPecLeft.setPos(-0.5F, 4.0F, -0.5F);
					setRotationAngle(chestPecLeft, -0.3752F, 0.0F, 0.1745F);
					chestPecLeft.texOffs(108, 10).addBox(0.0F, -3.0F, -3.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
					chestCore.setPos(0.0F, 2.5F, -3.6F);
					setRotationAngle(chestCore, -0.3491F, 0.0F, 0.0F);
					chestCore.texOffs(61, 0).addBox(-1.0F, -1.2974F, -0.1F, 2.0F, 3.0F, 1.0F, 0.0F, false);

					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(112, 48).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.25F, false);
					this.rightArm.texOffs(0, 26).addBox(-2.5F, -2.5F, -2.5F, 4.0F, 13.0F, 5.0F, -0.2F, false);
					shoulderRight = new ModelRenderer(this);
					shoulderRight.setPos(-1.25F, -0.75F, 0.0F);

					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(112, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.25F, false);
					this.leftArm.texOffs(0, 26).addBox(-1.5F, -2.5F, -2.5F, 4.0F, 13.0F, 5.0F, -0.2F, true);
					shoulderLeft.setPos(1.25F, -0.75F, 0.0F);
				}

				setRotationAngle(shoulderRight, 0.0F, 0.0F, -0.1745F);
				shoulderRight.texOffs(112, 0).addBox(-1.75F, -2.0F, -2.5F, 3.0F, 5.0F, 5.0F, 0.05F, false);
				shoulderRight.texOffs(25, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
				shoulderRight.texOffs(18, 16).addBox(-3.0F, -6.0F, -3.0F, 4.0F, 9.0F, 6.0F, -0.2F, false);

				setRotationAngle(shoulderLeft, 0.0F, 0.0F, 0.1745F);
				shoulderLeft.texOffs(112, 0).addBox(-1.25F, -2.0F, -2.5F, 3.0F, 5.0F, 5.0F, 0.05F, true);
				shoulderLeft.texOffs(25, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
				shoulderLeft.texOffs(18, 16).addBox(-1.0F, -6.0F, -3.0F, 4.0F, 9.0F, 6.0F, -0.2F, true);

				this.rightArm.addChild(shoulderRight);

				this.leftArm.addChild(shoulderLeft);

				this.body.addChild(this.collarBack);
				this.body.addChild(chestPecLeft);
				this.body.addChild(chestPecRight);
				this.body.addChild(chestCore);
				this.body.addChild(chestBack);
				this.body.addChild(collarFront);
				this.body.addChild(abs);
				this.body.addChild(this.crossA);
				this.body.addChild(this.crossB);
				this.body.addChild(this.crossC);
				this.body.addChild(this.crossD);
				break;

			case LEGS:
				this.body = new ModelRenderer(this);
				this.body.texOffs(65, 18).addBox(-4.5F, 9.5F, -2.5F, 9.0F, 2.0F, 5.0F, 0.1F, false);
				this.body.texOffs(49, 25).addBox(-4.5F, 10.5F, -2.5F, 9.0F, 2.0F, 5.0F, -0.05F, false);
				this.body.texOffs(67, 0).addBox(-1.5F, 8.75F, -2.75F, 3.0F, 2.0F, 1.0F, 0.1F, false);
				this.ribbonLegs = new ModelRenderer(this);
				this.ribbonLegs.setPos(0.0F, 10.75F, -2.5F);
				this.ribbonLegs.texOffs(14, 16).addBox(-1.5F, 0.0F, -0.25F, 3.0F, 6.0F, 0.0F, 0.0F, false);

				this.leftLeg = new ModelRenderer(this);
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg.texOffs(112, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
				this.leftLeg.texOffs(38, 32).addBox(-2.4F, 0.4F, -2.5F, 5.0F, 5.0F, 5.0F, -0.05F, true);
				ModelRenderer layerLegLeft = new ModelRenderer(this);
				layerLegLeft.setPos(1.85F, 1.4F, 0.0F);
				setRotationAngle(layerLegLeft, 0.0F, 0.0F, -0.1745F);
				layerLegLeft.texOffs(108, 18).addBox(-0.75F, -2.25F, -2.5F, 2.0F, 4.0F, 5.0F, 0.15F, true);

				this.rightLeg = new ModelRenderer(this);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg.texOffs(112, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
				this.rightLeg.texOffs(38, 32).addBox(-2.6F, 0.4F, -2.5F, 5.0F, 5.0F, 5.0F, -0.05F, false);
				ModelRenderer layerLegRight = new ModelRenderer(this);
				layerLegRight.setPos(-1.85F, 1.4F, 0.0F);
				setRotationAngle(layerLegRight, 0.0F, 0.0F, 0.1745F);
				layerLegRight.texOffs(108, 18).addBox(-1.25F, -2.25F, -2.5F, 2.0F, 4.0F, 5.0F, 0.15F, false);

				this.body.addChild(this.ribbonLegs);

				this.leftLeg.addChild(layerLegLeft);

				this.rightLeg.addChild(layerLegRight);
				break;

			case FEET:
				this.leftLeg = new ModelRenderer(this);
				this.leftLeg.texOffs(93, 14).addBox(-2.5F, 7.4F, -2.5F, 5.0F, 3.0F, 5.0F, -0.2F, true);

				this.rightLeg = new ModelRenderer(this);
				this.rightLeg.texOffs(93, 14).addBox(-2.5F, 7.4F, -2.5F, 5.0F, 3.0F, 5.0F, -0.2F, false);
				break;

			default:
				break;
		}
	}

	@Override
	public void setupArmorAnim(T entityIn, float ageInTicks) {
		switch (this.slot) {
			case HEAD:
				float rh = 0.1F * sinPI(ageInTicks / 35.0F);
				if(entityIn instanceof AbstractClientPlayerEntity && "slim".equals(((AbstractClientPlayerEntity) entityIn).getModelName())){
					setRotationAngle(this.earRight, 0.1249F + rh, 0.2577F, -0.0465F);
					setRotationAngle(this.earLeft, 0.1249F + rh, -0.2577F, 0.0465F);
				}else{
					setRotationAngle(this.earRight, 0.1681F + rh, 0.2322F, 0.1315F);
					setRotationAngle(this.earLeft, 0.1681F + rh, -0.2322F, -0.1315F);
				}
				rh = 0.05F * sinPI((ageInTicks - 15) / 50.0F);
				this.ribbonRight.zRot = rh;
				this.ribbonLeft.zRot = -rh;
				break;

			case CHEST:
				this.collarBack.xRot = - 1.5708F + 0.08F * (1 + sinPI(ageInTicks / 40.0F));
				this.crossA.x = getOrbitalX(8.5F + 2 * sinPI(ageInTicks / 40.0F), 0.5F, ageInTicks);
				this.crossA.y = getOrbitalY(8.5F + 2 * sinPI((ageInTicks + 10) / 30.0F), -0.5F, ageInTicks);
				this.crossB.x = getOrbitalX(8.0F + 2 * sinPI(ageInTicks / 70.0F), 0.45F, ageInTicks+ 5);
				this.crossB.y = getOrbitalY(8.0F + 2 * sinPI((ageInTicks + 10) / 40.0F), 0.45F, ageInTicks + 25);
				this.crossC.x = getOrbitalX(9.0F + 2 * sinPI((ageInTicks + 25) / 30.0F), 0.55F, ageInTicks+ 25);
				this.crossC.y = getOrbitalY(9.0F + 2 * sinPI((ageInTicks + 5) / 50.0F), 0.55F, ageInTicks + 30);
				this.crossD.x = getOrbitalX(8.0F + 2 * sinPI((ageInTicks + 30) / 80.0F), 0.42F, ageInTicks + 20);
				this.crossD.y = getOrbitalY(8.0F + 2 * sinPI(ageInTicks / 50.0F), 0.42F, ageInTicks + 20);
				break;

			case LEGS:
				this.ribbonLegs.xRot = -Math.abs(0.05F + 1.02F * this.rightLeg.xRot) + 0.05F * (1 + sinPI(ageInTicks / 40.0F));
				break;

			default:
				break;
		}
	}

	private float getOrbitalX(float amplitude, float frequency, float ageInTicks){
		return amplitude * cosPI(2 * frequency * ageInTicks / 40);
	}

	private float getOrbitalY(float amplitude, float frequency, float ageInTicks){
		return 30.0F + amplitude * sinPI(2 * frequency * ageInTicks / 40);
	}
}