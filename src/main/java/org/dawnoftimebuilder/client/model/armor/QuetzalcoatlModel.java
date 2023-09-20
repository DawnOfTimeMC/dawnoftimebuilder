package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class QuetzalcoatlModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public ModelRenderer featherCrownLeft;
	public ModelRenderer featherCrownRight;
	public ModelRenderer featherCrownMiddle;

	//Chest
	public ModelRenderer neckStart;
	public ModelRenderer neckEnd;
	public ModelRenderer snakeHead;
	public ModelRenderer jow;
	public ModelRenderer snakeCrownLeft;
	public ModelRenderer snakeCrownRight;
	public ModelRenderer tail;
	public ModelRenderer tailTip;
	public ModelRenderer armFeather;

	//Leggings
	public ModelRenderer underwearFront;
	public ModelRenderer underwearBack;

	//Boots
	public ModelRenderer legFeatherRight;
	public ModelRenderer legFeatherLeft;

	public QuetzalcoatlModel(EquipmentSlotType slot, boolean isSteve, float scale) {
		super(slot, 128, 64, scale);

		switch (slot) {
			case HEAD:
				this.head = new ModelRenderer(this);
				this.head.setPos(0.0F, 0.0F, 0.0F);
				this.head.texOffs(28, 0).addBox(-2.0F, -15.0F, -1.0F, 4.0F, 5.0F, 2.0F, 0.0F, false);
				this.head.texOffs(52, 0).addBox(-5.0F, -10.0F, -1.0F, 10.0F, 3.0F, 2.0F, 0.0F, false);
				this.head.texOffs(19, 4).addBox(-6.25F, -7.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);
				this.head.texOffs(19, 4).addBox(3.25F, -7.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, true);

				this.featherCrownLeft = new ModelRenderer(this);
				this.featherCrownLeft.setPos(0.0F, -10.0F, 0.0F);
				setRotationAngle(featherCrownLeft, -0.4363F, 0.0F, 1.3963F);
				this.featherCrownLeft.texOffs(40, 7).addBox(-1.5F, -9.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, true);

				this.featherCrownRight = new ModelRenderer(this);
				this.featherCrownRight.setPos(0.0F, -10.0F, 0.0F);
				setRotationAngle(this.featherCrownRight, -0.4363F, 0.0F, -1.3963F);
				this.featherCrownRight.texOffs(40, 7).addBox(-4.5F, -9.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);

				this.featherCrownMiddle = new ModelRenderer(this);
				this.featherCrownMiddle.setPos(0.0F, -10.0F, 0.0F);
				setRotationAngle(this.featherCrownMiddle, -0.4363F, 0.0F, 0.0F);
				this.featherCrownMiddle.texOffs(0, 8).addBox(-6.5F, -13.0F, 0.0F, 13.0F, 12.0F, 0.0F, 0.0F, false);

				ModelRenderer smallEarringLeft = new ModelRenderer(this);
				smallEarringLeft.setPos(4.75F, -3.5F, 0.0F);
				setRotationAngle(smallEarringLeft, 0.0F, 1.5708F, 0.0F);
				smallEarringLeft.texOffs(18, 3).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, -0.5F, true);
				ModelRenderer smallEarringRight = new ModelRenderer(this);
				smallEarringRight.setPos(-4.75F, -3.5F, 0.0F);
				setRotationAngle(smallEarringRight, 0.0F, -1.5708F, 0.0F);
				smallEarringRight.texOffs(18, 3).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, -0.5F, false);
				ModelRenderer crownLeft = new ModelRenderer(this);
				crownLeft.setPos(0.0F, 0.0F, 0.0F);
				setRotationAngle(crownLeft, 0.0F, 0.0F, 0.7854F);
				crownLeft.texOffs(40, 0).addBox(-8.0F, -12.0F, -0.75F, 4.0F, 5.0F, 2.0F, 0.0F, true);
				ModelRenderer crownRight = new ModelRenderer(this);
				crownRight.setPos(0.0F, 0.0F, 0.0F);
				setRotationAngle(crownRight, 0.0F, 0.0F, -0.7854F);
				crownRight.texOffs(40, 0).addBox(4.0F, -12.0F, -0.75F, 4.0F, 5.0F, 2.0F, 0.0F, false);
				ModelRenderer skull = new ModelRenderer(this);
				skull.setPos(0.0F, -6.0F, -3.0F);
				setRotationAngle(skull, -0.8727F, 0.0F, 0.0F);
				skull.texOffs(0, 0).addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 2.0F, 0.0F, false);
				skull.texOffs(18, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

				this.head.addChild(featherCrownLeft);
				this.head.addChild(featherCrownRight);
				this.head.addChild(featherCrownMiddle);
				this.head.addChild(smallEarringLeft);
				this.head.addChild(smallEarringRight);
				this.head.addChild(crownLeft);
				this.head.addChild(crownRight);
				this.head.addChild(skull);
				break;

			case CHEST:
				this.body = new ModelRenderer(this);
				this.body.setPos(0.0F, 0.0F, 0.0F);
				this.body.texOffs(0, 20).addBox(-4.0F, -0.5F, -2.5F, 8.0F, 4.0F, 5.0F, 0.0F, false);
				ModelRenderer snakeA = new ModelRenderer(this);
				snakeA.setPos(-0.67F, 0.2454F, -1.0F);
				setRotationAngle(snakeA, 0.0F, 0.0F, 0.4363F);
				snakeA.texOffs(50, 19).addBox(-6.0F, 0.0F, -3.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
				snakeA.texOffs(76, 0).addBox(-6.0F, 0.0F, 2.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
				ModelRenderer snakeB = new ModelRenderer(this);
				snakeB.setPos(2.6523F, -0.9639F, 0.25F);
				setRotationAngle(snakeB, 0.0F, 0.0F, 0.4363F);
				snakeB.texOffs(85, 11).addBox(3.5F, 2.5F, -3.25F, 3.0F, 3.0F, 7.0F, 0.0F, false);
				ModelRenderer snakeC = new ModelRenderer(this);
				snakeC.setPos(2.6523F, -0.9639F, 0.0F);
				setRotationAngle(snakeC, 0.0F, 0.0F, 0.4363F);
				snakeC.texOffs(98, 10).addBox(3.5F, -2.5F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
				ModelRenderer snakeD = new ModelRenderer(this);
				snakeD.setPos(3.0428F, 1.4415F, -2.6225F);
				setRotationAngle(snakeD, -1.0308F, -0.0415F, 0.1632F);
				snakeD.texOffs(114, 18).addBox(3.25F, -5.5F, -3.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
				this.neckStart = new ModelRenderer(this);
				this.neckStart.setPos(8.0F, -1.25F, 1.5F);
				setRotationAngle(this.neckStart, -0.6929F, 0.0315F, 0.1992F);
				this.neckStart.texOffs(102, 18).addBox(-1.3977F, -6.9113F, -2.4979F, 3.0F, 7.0F, 3.0F, 0.0F, false);
				this.neckEnd = new ModelRenderer(this);
				this.neckEnd.setPos(-0.0551F, -6.6055F, -0.1796F);
				setRotationAngle(this.neckEnd, 0.9904F, 0.0148F, -0.0067F);
				this.neckEnd.texOffs(110, 10).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 5.0F, 3.0F, 0.0F, false);
				ModelRenderer featherA = new ModelRenderer(this);
				featherA.setPos(-0.1511F, -1.337F, 1.1273F);
				setRotationAngle(featherA, -1.078F, 0.7476F, 0.3405F);
				featherA.texOffs(100, 0).addBox(-0.2694F, -0.4602F, -2.4251F, 4.0F, 4.0F, 6.0F, 0.0F, false);
				ModelRenderer featherB = new ModelRenderer(this);
				featherB.setPos(-0.1511F, -6.337F, 1.1273F);
				setRotationAngle(featherB, -0.5992F, 0.4909F, 0.6321F);
				featherB.texOffs(68, 6).addBox(0.5858F, 0.3358F, 0.0F, 6.0F, 6.0F, 6.0F, -0.25F, false);
				this.snakeHead = new ModelRenderer(this);
				this.snakeHead.setPos(-0.1999F, -5.0126F, -0.4824F);
				setRotationAngle(this.snakeHead, 0.1176F, 0.0076F, -0.0741F);
				this.snakeHead.texOffs(61, 19).addBox(-1.9512F, -1.3244F, -7.3903F, 4.0F, 1.0F, 4.0F, 0.0F, true);
				this.snakeHead.texOffs(86, 6).addBox(-1.4512F, -0.5744F, -6.8903F, 3.0F, 1.0F, 4.0F, 0.0F, false);
				this.snakeHead.texOffs(52, 5).addBox(-1.9512F, -3.3244F, -3.3903F, 4.0F, 4.0F, 4.0F, 0.0F, false);
				this.snakeCrownLeft = new ModelRenderer(this);
				this.snakeCrownLeft.setPos(0.0488F, -8.0026F, 2.1767F);
				setRotationAngle(this.snakeCrownLeft, -2.5831F, 0.3378F, -2.9374F);
				this.snakeCrownLeft.texOffs(26, 7).addBox(-7.0F, 0.0F, 0.0F, 7.0F, 12.0F, 0.0F, 0.0F, false);
				this.snakeCrownRight = new ModelRenderer(this);
				this.snakeCrownRight.setPos(0.0488F, -8.0026F, 2.1767F);
				setRotationAngle(this.snakeCrownRight, -0.5585F, 0.3378F, -0.2042F);
				this.snakeCrownRight.texOffs(26, 7).addBox(-7.0F, 0.0F, 0.0F, 7.0F, 12.0F, 0.0F, 0.0F, false);
				ModelRenderer featherC = new ModelRenderer(this);
				featherC.setPos(0.0488F, -1.3244F, 1.6097F);
				setRotationAngle(featherC, -0.5992F, 0.4909F, 0.6321F);
				featherC.texOffs(68, 6).addBox(-0.1642F, -0.1642F, -2.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
				ModelRenderer featherD = new ModelRenderer(this);
				featherD.setPos(0.0488F, -3.3244F, 1.6097F);
				setRotationAngle(featherD, 0.0F, 0.0F, 0.7854F);
				featherD.texOffs(68, 6).addBox(0.25F, 0.25F, -2.0F, 6.0F, 6.0F, 6.0F, 0.25F, false);
				ModelRenderer featherE = new ModelRenderer(this);
				featherE.setPos(0.0488F, -3.3244F, -0.3903F);
				setRotationAngle(featherE, 0.4711F, -0.3981F, 0.6996F);
				featherE.texOffs(68, 6).addBox(-0.2324F, -0.25F, -0.3181F, 6.0F, 6.0F, 6.0F, -0.25F, false);
				ModelRenderer headA = new ModelRenderer(this);
				headA.setPos(3.4488F, -1.28F, -6.2866F);
				setRotationAngle(headA, -0.1309F, 0.0F, 0.0F);
				headA.texOffs(120, 6).addBox(-4.9F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.1F, false);
				ModelRenderer headB = new ModelRenderer(this);
				headB.setPos(2.0488F, -3.3244F, -3.3903F);
				setRotationAngle(headB, 0.3491F, 0.0F, 0.0F);
				headB.texOffs(114, 0).addBox(-3.5F, 0.0F, -4.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);
				ModelRenderer headC = new ModelRenderer(this);
				headC.setPos(-2.4204F, -3.6286F, -3.8423F);
				setRotationAngle(headC, 0.2236F, 0.1398F, 0.224F);
				headC.texOffs(68, 8).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
				ModelRenderer headD = new ModelRenderer(this);
				headD.setPos(-0.4512F, -1.8244F, -3.8903F);
				setRotationAngle(headD, 0.2589F, -0.5398F, 0.0571F);
				headD.texOffs(68, 5).addBox(-1.75F, -1.35F, -0.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
				ModelRenderer headE = new ModelRenderer(this);
				headE.setPos(-1.9512F, -1.3244F, -3.3903F);
				setRotationAngle(headE, 0.0F, -0.2618F, 0.0F);
				headE.texOffs(119, 8).addBox(-0.45F, -1.75F, -1.8F, 2.0F, 2.0F, 2.0F, -0.25F, false);
				ModelRenderer headF = new ModelRenderer(this);
				headF.setPos(2.5179F, -3.6286F, -3.8423F);
				setRotationAngle(headF, 0.2236F, -0.1398F, -0.224F);
				headF.texOffs(68, 8).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, true);
				ModelRenderer headG = new ModelRenderer(this);
				headG.setPos(0.5488F, -1.8244F, -3.8903F);
				setRotationAngle(headG, 0.2589F, 0.5398F, -0.0571F);
				headG.texOffs(68, 5).addBox(0.75F, -1.35F, -0.5F, 1.0F, 1.0F, 2.0F, 0.0F, true);
				ModelRenderer headH = new ModelRenderer(this);
				headH.setPos(2.0488F, -1.3244F, -3.3903F);
				setRotationAngle(headH, 0.0F, 0.2618F, 0.0F);
				headH.texOffs(119, 8).addBox(-1.55F, -1.75F, -1.8F, 2.0F, 2.0F, 2.0F, -0.25F, true);
				this.jow = new ModelRenderer(this);
				this.jow.setPos(1.4959F, 0.4269F, -1.5853F);
				setRotationAngle(this.jow, 0.1309F, 0.0F, 0.0F);
				this.jow.texOffs(73, 18).addBox(-3.4471F, 0.7487F, -5.805F, 4.0F, 1.0F, 4.0F, 0.0F, true);
				this.jow.texOffs(86, 6).addBox(-2.9471F, -0.0013F, -5.305F, 3.0F, 1.0F, 4.0F, 0.0F, false);
				this.jow.texOffs(52, 13).addBox(-3.9471F, -1.2513F, -1.805F, 5.0F, 3.0F, 3.0F, 0.0F, false);

				this.tail = new ModelRenderer(this);
				this.tail.setPos(-1.9225F, -1.3601F, -3.5F);
				setRotationAngle(this.tail, 0.0F, 0.0F, 0.2182F);
				this.tail.texOffs(87, 21).addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
				this.tailTip = new ModelRenderer(this);
				this.tailTip.setPos(0.5F, 6.0F, 0.5F);
				ModelRenderer tailTipFeather = new ModelRenderer(this);
				tailTipFeather.setPos(-0.5F, 0.0F, 0.0F);
				setRotationAngle(tailTipFeather, 0.0F, 0.0F, -0.3491F);
				tailTipFeather.texOffs(20, 33).addBox(1.0F, 0.0F, -1.0F, 0.0F, 9.0F, 3.0F, 0.0F, false);
				tailTipFeather.texOffs(96, 21).addBox(-0.5F, 0.0F, 0.5F, 3.0F, 9.0F, 0.0F, 0.0F, false);
				tailTipFeather.texOffs(122, 12).addBox(0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

				ModelRenderer necklaceJewel = new ModelRenderer(this);
				ModelRenderer shoulderRightProtection = new ModelRenderer(this);
				ModelRenderer leftArmJewel = new ModelRenderer(this);
				this.rightArm = new ModelRenderer(this);
				this.leftArm = new ModelRenderer(this);
				this.armFeather = new ModelRenderer(this);

				if(isSteve){
					necklaceJewel.setPos(0.0F, 3.0F, -2.5F);
					setRotationAngle(necklaceJewel, 0.0F, 0.0F, -0.7854F);
					necklaceJewel.texOffs(21, 20).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(30, 19).addBox(-3.5F, 0.5F, -2.5F, 5.0F, 2.0F, 5.0F, 0.1F, false);
					shoulderRightProtection.setPos(-3.6F, 0.4F, -2.6F);
					setRotationAngle(shoulderRightProtection, 0.0F, 0.0F, -0.1745F);
					shoulderRightProtection.texOffs(20, 24).addBox(-0.5F, -5.25F, -0.4F, 2.0F, 6.0F, 6.0F, 0.0F, false);

					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(0, 29).addBox(-1.5F, 5.5F, -2.5F, 5.0F, 3.0F, 5.0F, 0.1F, true);
					leftArmJewel.setPos(3.5F, 7.0F, 0.0F);
					setRotationAngle(leftArmJewel, 0.7854F, 0.0F, 0.0F);
					leftArmJewel.texOffs(29, 20).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, true);
					this.armFeather.setPos(3.75F, 5.5F, 1.0F);
					setRotationAngle(this.armFeather, 0.0F, 0.0873F, 0.0F);
					this.armFeather.texOffs(36, 19).addBox(0.15F, -5.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, true);
				}else{
					ModelRenderer chestBreast = new ModelRenderer(this);
					chestBreast.setPos(0.0F, 2.0F, -3.0F);
					setRotationAngle(chestBreast, -0.48F, 0.0F, 0.0F);
					chestBreast.texOffs(26, 40).addBox(-3.5F, -1.75F, 0.0F, 7.0F, 4.0F, 2.0F, 0.0F, false);
					necklaceJewel.setPos(0.0F, 3.0F, -2.5F);
					setRotationAngle(necklaceJewel, -0.3527F, 0.3326F, 0.7256F);
					necklaceJewel.texOffs(21, 20).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

					this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
					this.rightArm.texOffs(30, 19).addBox(-2.5F, 0.5F, -2.5F, 4.0F, 2.0F, 5.0F, 0.1F, false);
					shoulderRightProtection.setPos(-3.6F, 0.4F, -2.6F);
					setRotationAngle(shoulderRightProtection, 0.0F, 0.0F, -0.1745F);
					shoulderRightProtection.texOffs(20, 24).addBox(0.5F, -5.25F, -0.4F, 2.0F, 6.0F, 6.0F, 0.0F, false);

					this.leftArm.setPos(5.0F, 2.0F, 0.0F);
					this.leftArm.texOffs(0, 29).addBox(-1.5F, 5.5F, -2.5F, 4.0F, 3.0F, 5.0F, 0.1F, true);
					leftArmJewel.setPos(2.5F, 7.0F, 0.0F);
					setRotationAngle(leftArmJewel, 0.7854F, 0.0F, 0.0F);
					leftArmJewel.texOffs(29, 20).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, true);
					this.armFeather.setPos(2.75F, 5.5F, 1.0F);
					setRotationAngle(this.armFeather, 0.0F, 0.0873F, 0.0F);
					this.armFeather.texOffs(36, 19).addBox(0.15F, -5.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, true);

					this.body.addChild(chestBreast);
				}

				this.body.addChild(necklaceJewel);
				this.body.addChild(snakeA);
				this.body.addChild(snakeB);
				this.body.addChild(snakeC);
				this.body.addChild(snakeD);
				this.body.addChild(this.neckStart);
				this.neckStart.addChild(this.neckEnd);
				this.neckEnd.addChild(featherA);
				this.neckEnd.addChild(featherB);
				this.neckEnd.addChild(this.snakeHead);
				this.snakeHead.addChild(this.snakeCrownLeft);
				this.snakeHead.addChild(this.snakeCrownRight);
				this.snakeHead.addChild(featherC);
				this.snakeHead.addChild(featherD);
				this.snakeHead.addChild(featherE);
				this.snakeHead.addChild(headA);
				this.snakeHead.addChild(headB);
				this.snakeHead.addChild(headC);
				this.snakeHead.addChild(headD);
				this.snakeHead.addChild(headE);
				this.snakeHead.addChild(headF);
				this.snakeHead.addChild(headG);
				this.snakeHead.addChild(headH);
				this.snakeHead.addChild(this.jow);

				this.rightArm.addChild(shoulderRightProtection);
				this.rightArm.addChild(this.tail);
				this.tail.addChild(this.tailTip);
				this.tailTip.addChild(tailTipFeather);
				
				this.leftArm.addChild(leftArmJewel);
				this.leftArm.addChild(this.armFeather);
				break;

			case LEGS:
				this.body = new ModelRenderer(this);
				this.body.setPos(0.0F, 0.0F, 0.0F);
				this.body.texOffs(45, 31).addBox(-4.5F, 9.5F, -2.5F, 9.0F, 2.0F, 5.0F, -0.1F, false);
				this.body.texOffs(61, 24).addBox(-4.5F, 10.0F, -2.5F, 9.0F, 1.0F, 5.0F, 0.0F, false);
				this.body.texOffs(26, 36).addBox(-2.0F, 9.0F, -2.46F, 4.0F, 2.0F, 1.0F, 0.0F, false);
				ModelRenderer stomachProtectionTop = new ModelRenderer(this);
				stomachProtectionTop.setPos(5.6586F, 4.0503F, -0.087F);
				setRotationAngle(stomachProtectionTop, 0.0F, 0.0F, 0.7854F);
				stomachProtectionTop.texOffs(36, 36).addBox(-2.0F, 6.0F, -2.45F, 3.0F, 3.0F, 1.0F, -0.087F, false);
				ModelRenderer stomachProtectionLeft = new ModelRenderer(this);
				stomachProtectionLeft.setPos(4.45F, 11.0F, -2.46F);
				setRotationAngle(stomachProtectionLeft, 0.0F, 0.0F, 0.3927F);
				stomachProtectionLeft.texOffs(98, 28).addBox(-2.35F, -2.0F, -0.04F, 2.0F, 2.0F, 5.0F, -0.05F, true);
				ModelRenderer stomachProtectionRight = new ModelRenderer(this);
				stomachProtectionRight.setPos(-4.45F, 11.0F, -2.46F);
				setRotationAngle(stomachProtectionRight, 0.0F, 0.0F, -0.3927F);
				stomachProtectionRight.texOffs(98, 28).addBox(0.35F, -2.0F, -0.04F, 2.0F, 2.0F, 5.0F, -0.05F, false);
				ModelRenderer stomachJewel = new ModelRenderer(this);
				stomachJewel.setPos(0.0F, 11.0F, -2.25F);
				setRotationAngle(stomachJewel, 0.0F, 0.0F, -0.7854F);
				stomachJewel.texOffs(30, 27).addBox(-1.0F, -1.0F, -0.75F, 2.0F, 2.0F, 1.0F, -0.2F, false);
				this.underwearFront = new ModelRenderer(this);
				this.underwearFront.setPos(0.0F, 11.25F, -2.25F);
				this.underwearFront.texOffs(78, 30).addBox(-2.5F, 0.15F, -0.15F, 5.0F, 7.0F, 0.0F, 0.0F, false);
				ModelRenderer underwearFeather = new ModelRenderer(this);
				underwearFeather.setPos(0.0F, 12.75F, 2.25F);
				setRotationAngle(underwearFeather, 0.0F, 0.0F, -0.7854F);
				underwearFeather.texOffs(40, 13).addBox(5.25F, -11.25F, -2.6F, 6.0F, 6.0F, 0.0F, 0.0F, false);
				this.underwearBack = new ModelRenderer(this);
				this.underwearBack.setPos(0.0F, 11.3F, 2.3F);
				this.underwearBack.texOffs(68, 30).addBox(-2.5F, 0.1F, 0.1F, 5.0F, 5.0F, 0.0F, 0.0F, false);

				this.rightLeg = new ModelRenderer(this);

				this.leftLeg = new ModelRenderer(this);

				this.body.addChild(stomachProtectionTop);
				this.body.addChild(stomachProtectionLeft);
				this.body.addChild(stomachProtectionRight);
				this.body.addChild(stomachJewel);
				this.body.addChild(this.underwearFront);
				this.underwearFront.addChild(underwearFeather);
				this.body.addChild(this.underwearBack);
				break;

			case FEET:
				this.rightLeg = new ModelRenderer(this);
				this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg.texOffs(0, 37).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 11.0F, 5.0F, -0.15F, false);
				this.legFeatherRight = new ModelRenderer(this);
				this.legFeatherRight.setPos(-2.1F, 2.25F, 0.0F);
				setRotationAngle(this.legFeatherRight, -0.7931F, -0.1231F, 0.124F);
				this.legFeatherRight.texOffs(50, 23).addBox(-0.3F, -2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
				ModelRenderer legJewelRight = new ModelRenderer(this);
				legJewelRight.setPos(-2.1F, 2.25F, 0.0F);
				setRotationAngle(legJewelRight, -0.7854F, 0.0F, 0.0F);
				legJewelRight.texOffs(45, 19).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
				ModelRenderer legProtectionRight = new ModelRenderer(this);
				legProtectionRight.setPos(-1.25F, 8.0F, -1.25F);
				setRotationAngle(legProtectionRight, 0.0F, 0.0F, -0.0873F);
				legProtectionRight.texOffs(88, 29).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 2.0F, -0.15F, false);

				this.leftLeg = new ModelRenderer(this);
				this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg.texOffs(0, 37).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 11.0F, 5.0F, -0.15F, true);
				this.legFeatherLeft = new ModelRenderer(this);
				this.legFeatherLeft.setPos(2.1F, 2.25F, 0.0F);
				setRotationAngle(this.legFeatherLeft, -0.7931F, 0.1231F, -0.124F);
				this.legFeatherLeft.texOffs(50, 23).addBox(0.3F, -2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 0.0F, true);
				ModelRenderer legProtectionLeft = new ModelRenderer(this);
				legProtectionLeft.setPos(1.25F, 8.0F, -1.25F);
				setRotationAngle(legProtectionLeft, 0.0F, 0.0F, 0.0873F);
				legProtectionLeft.texOffs(88, 29).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 2.0F, -0.15F, true);
				ModelRenderer legJewelLeft = new ModelRenderer(this);
				legJewelLeft.setPos(2.1F, 2.25F, 0.0F);
				setRotationAngle(legJewelLeft, -0.7854F, 0.0F, 0.0F);
				legJewelLeft.texOffs(45, 19).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, true);

				this.rightLeg.addChild(this.legFeatherRight);
				this.rightLeg.addChild(legJewelRight);
				this.rightLeg.addChild(legProtectionRight);

				this.leftLeg.addChild(this.legFeatherLeft);
				this.leftLeg.addChild(legJewelLeft);
				this.leftLeg.addChild(legProtectionLeft);
				break;

			default:
				break;
		}
	}

	@Override
	public void setupArmorAnim(T entityIn, float ageInTicks) {

		switch (this.slot) {
			case HEAD:
				float rh = -0.4363F + 0.1F * sinPI(ageInTicks / 40.0F);
				this.featherCrownLeft.xRot = rh;
				this.featherCrownRight.xRot = rh;
				this.featherCrownMiddle.xRot = rh;
				break;

			case CHEST:
				float rA = sinPI(ageInTicks / 40.0F);
				float rB = sinPI((ageInTicks - 15) / 40.0F);
				this.tail.zRot = 0.2182F + 0.1F * rA;
				this.tailTip.zRot = 0.1F * rB;
				this.armFeather.zRot = -0.05F * rB;
				this.armFeather.xRot = 0.1F * rB;
				this.neckStart.xRot = -0.3F + 0.2F * rA;
				this.neckStart.zRot = 0.2F + 0.04F * rB;
				this.neckEnd.xRot = 0.6F + 0.1F * rB;
				this.snakeHead.xRot = -0.2F * rB;
				this.jow.xRot = 0.13F + 0.1F * rA;
				this.snakeCrownRight.yRot = 0.3378F + 0.1F * rB;
				this.snakeCrownLeft.yRot = 0.3378F - 0.1F * rB;
				break;

			case LEGS:
				float f = Math.abs(0.05F + 1.02F * this.rightLeg.xRot) + 0.05F * (1 + sinPI(ageInTicks / 40.0F));
				this.underwearFront.xRot = -f;
				this.underwearBack.xRot = f;
				break;

			case FEET:
				float rf = -0.7931F + 0.2F * sinPI((ageInTicks + 10) / 40.0F);
				this.legFeatherRight.xRot = rf;
				this.legFeatherLeft.xRot = rf;
				break;

			default:
				break;
		}
	}
}