package org.dawnoftimebuilder.client.renderer.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RaijinArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public RendererModel head;
	public RendererModel headHornLeftA;
	public RendererModel headHornLeftB;
	public RendererModel headHornLeftC;
	public RendererModel headHornLeftD;
	public RendererModel headHornRightA;
	public RendererModel headHornRightB;
	public RendererModel headHornRightC;
	public RendererModel headHornRightD;

	//Set
	public RendererModel body;
	public RendererModel flyA;
	public RendererModel flyB;
	public RendererModel flyC;
	public RendererModel flyD;
	public RendererModel flyE;
	public RendererModel flyF;

	//Chest
	public RendererModel armRingLeftA;
	public RendererModel armRingLeftB;
	public RendererModel armRingRightB;
	public RendererModel armRingRightA;
	public RendererModel chestNecklace;
	public RendererModel bodyBreast;
	public RendererModel bodyBreastNecklace;
	public RendererModel chestScarfTop;
	public RendererModel chestScarfLeftA;
	public RendererModel chestScarfLeftB;
	public RendererModel chestScarfLeftC;
	public RendererModel chestScarfRightA;
	public RendererModel chestScarfRightB;
	public RendererModel chestScarfRightC;
	public RendererModel chestScarfTopLeftA;
	public RendererModel chestScarfTopLeftB;
	public RendererModel chestScarfTopRightA;
	public RendererModel chestScarfTopRightB;

	//Leggings
	public RendererModel chestPant;
	public RendererModel legLeftPantBot;
	public RendererModel legLeftPantTop;
	public RendererModel legRightPantBot;
	public RendererModel legRightPantTop;
	public RendererModel chestBeltJewel;
	public RendererModel chestBeltHangA;
	public RendererModel chestBeltHangB;
	public RendererModel chestBelt;
	public RendererModel chestPantLeftBack;
	public RendererModel chestPantRightBack;
	public RendererModel chestPantRightFront;
	public RendererModel chestPantLeftFront;
	public RendererModel chestPantRight;
	public RendererModel chestPantLeft;

	//Boots
	public RendererModel legRingLeftA;
	public RendererModel legRingLeftB;
	public RendererModel legRingRightA;
	public RendererModel legRingRightB;

	public RaijinArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 64, 64);

		switch (slot) {
			case HEAD:
				this.head = new RendererModel(this, 0, 0);
				this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornLeftA = new RendererModel(this, 0, 0);
				this.headHornLeftA.setRotationPoint(2.5F, -6.5F, -5.0F);
				this.headHornLeftA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
				setRotateAngle(headHornLeftA, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
				this.headHornLeftB = new RendererModel(this, 15, 0);
				this.headHornLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornLeftB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
				setRotateAngle(headHornLeftB, -1.2217304763960306F, 0.0F, 0.0F);
				this.headHornLeftC = new RendererModel(this, 30, 0);
				this.headHornLeftC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornLeftC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
				setRotateAngle(headHornLeftC, 1.0471975511965976F, 0.0F, 0.0F);
				this.headHornLeftD = new RendererModel(this, 0, 0);
				this.headHornLeftD.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornLeftD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
				setRotateAngle(headHornLeftD, -0.5235987755982988F, 0.0F, 0.0F);
				this.headHornRightA = new RendererModel(this, 0, 0);
				this.headHornRightA.setRotationPoint(-2.5F, -6.5F, -5.0F);
				this.headHornRightA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
				setRotateAngle(headHornRightA, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
				this.headHornRightB = new RendererModel(this, 15, 0);
				this.headHornRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornRightB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
				setRotateAngle(headHornRightB, -1.2217304763960306F, 0.0F, 0.0F);
				this.headHornRightC = new RendererModel(this, 30, 0);
				this.headHornRightC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornRightC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
				setRotateAngle(headHornRightC, 1.0471975511965976F, 0.0F, 0.0F);
				this.headHornRightD = new RendererModel(this, 0, 0);
				this.headHornRightD.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.headHornRightD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
				setRotateAngle(headHornRightD, -0.5235987755982988F, 0.0F, 0.0F);

				this.bipedHead = head;
				this.bipedHead.addChild(headHornLeftA);
				this.headHornLeftA.addChild(this.headHornLeftB);
				this.headHornLeftA.addChild(this.headHornLeftC);
				this.headHornLeftA.addChild(this.headHornLeftD);
				this.bipedHead.addChild(headHornRightA);
				this.headHornRightA.addChild(this.headHornRightB);
				this.headHornRightA.addChild(this.headHornRightC);
				this.headHornRightA.addChild(this.headHornRightD);

				this.body = new RendererModel(this, 0, 0);
				this.body.setRotationPoint(0.0F, 0.0F, 0.0F);

				this.flyA = new RendererModel(this, 45, 0);
				this.flyA.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyB = new RendererModel(this, 45, 0);
				this.flyB.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyC = new RendererModel(this, 45, 0);
				this.flyC.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyD = new RendererModel(this, 45, 0);
				this.flyD.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyE = new RendererModel(this, 45, 0);
				this.flyE.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyF = new RendererModel(this, 45, 0);
				this.flyF.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				if(isSteve){
					this.flyA.setRotationPoint(-5.0F, -15.0F, 3.0F);
					this.flyB.setRotationPoint(5.0F, -15.0F, 3.0F);
					this.flyC.setRotationPoint(-11.0F, -7.5F, 3.0F);
					this.flyD.setRotationPoint(11.0F, -7.5F, 3.0F);
					this.flyE.setRotationPoint(-9.0F, 2.0F, 3.0F);
					this.flyF.setRotationPoint(9.0F, 2.0F, 3.0F);
				}else{
					this.flyA.setRotationPoint(-4.5F, -15.0F, 3.0F);
					this.flyB.setRotationPoint(4.5F, -15.0F, 3.0F);
					this.flyC.setRotationPoint(-10.0F, -7.5F, 3.0F);
					this.flyD.setRotationPoint(10.0F, -7.5F, 3.0F);
					this.flyE.setRotationPoint(-8.0F, 2.0F, 3.0F);
					this.flyF.setRotationPoint(8.0F, 2.0F, 3.0F);
				}

				this.bipedBody = body;
				this.bipedBody.addChild(this.flyA);
				this.bipedBody.addChild(this.flyB);
				this.bipedBody.addChild(this.flyC);
				this.bipedBody.addChild(this.flyD);
				this.bipedBody.addChild(this.flyE);
				this.bipedBody.addChild(this.flyF);
				break;

			case CHEST:
				if(isSteve){
					this.armRingLeftA = new RendererModel(this, 0, 0);
					this.armRingLeftA.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingLeftB = new RendererModel(this, 15, 1);
					this.armRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightA = new RendererModel(this, 30, 0);
					this.armRingRightA.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRingRightA.addBox(-3.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightB = new RendererModel(this, 0, 0);
					this.armRingRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-3.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.chestNecklace = new RendererModel(this, 0, 7);
					this.chestNecklace.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.3F);
				}else{
					this.armRingLeftA = new RendererModel(this, 0, 33);
					this.armRingLeftA.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingLeftB = new RendererModel(this, 13, 34);
					this.armRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightA = new RendererModel(this, 21, 28);
					this.armRingRightA.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRingRightA.addBox(-2.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightB = new RendererModel(this, 0, 33);
					this.armRingRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-2.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.bodyBreast = new RendererModel(this, 26, 34);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, 0.2F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.chestNecklace = new RendererModel(this, 0, 7);
					this.chestNecklace.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.2F);
					this.bodyBreastNecklace = new RendererModel(this, 39, 23);
					this.bodyBreastNecklace.setRotationPoint(0.0F, -0.2F, -2.2F);
					this.bodyBreastNecklace.addBox(-4.0F, 0.2F, -5.2F, 8, 0, 5, 0.2F);
					setRotateAngle(bodyBreastNecklace, 1.0097597431827485F, 0.0F, 0.0F);
				}
				this.chestScarfTop = new RendererModel(this, 0, 29);
				this.chestScarfTop.setRotationPoint(0.0F, -4.5F, 6.5F);
				this.chestScarfTop.addBox(-5.0F, -0.5F, -1.5F, 10, 1, 3, 0.0F);
				setRotateAngle(chestScarfTop, -1.1344640137963142F, 0.0F, 0.0F);
				this.chestScarfTopLeftA = new RendererModel(this, 35, 18);
				this.chestScarfTopLeftA.setRotationPoint(5.0F, 0.0F, 0.0F);
				this.chestScarfTopLeftA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
				setRotateAngle(chestScarfTopLeftA, 0.0F, 0.0F, -2.0943951023931953F);
				this.chestScarfTopLeftB = new RendererModel(this, 40, 21);
				this.chestScarfTopLeftB.setRotationPoint(-6.0F, 0.0F, 0.0F);
				this.chestScarfTopLeftB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
				setRotateAngle(chestScarfTopLeftB, 0.0F, 0.0F, 1.0471975511965976F);
				this.chestScarfTopRightA = new RendererModel(this, 35, 18);
				this.chestScarfTopRightA.setRotationPoint(-5.0F, 0.0F, 0.0F);
				this.chestScarfTopRightA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
				setRotateAngle(chestScarfTopRightA, 0.0F, 0.0F, -1.0471975511965976F);
				this.chestScarfTopRightB = new RendererModel(this, 40, 21);
				this.chestScarfTopRightB.setRotationPoint(-6.0F, 0.0F, 0.0F);
				this.chestScarfTopRightB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
				setRotateAngle(chestScarfTopRightB, 0.0F, 0.0F, -1.0471975511965976F);
				this.chestScarfLeftA = new RendererModel(this, 50, 6);
				this.chestScarfLeftA.setRotationPoint(5.0F, 0.0F, -3.0F);
				this.chestScarfLeftA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
				setRotateAngle(chestScarfLeftA, 0.0F, 0.3490658503988659F, 0.17453292519943295F);
				this.chestScarfLeftB = new RendererModel(this, 58, 3);
				this.chestScarfLeftB.setRotationPoint(0.0F, 5.0F, 0.0F);
				this.chestScarfLeftB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
				setRotateAngle(chestScarfLeftB, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
				this.chestScarfLeftC = new RendererModel(this, 54, 5);
				this.chestScarfLeftC.setRotationPoint(0.0F, 8.0F, 0.0F);
				this.chestScarfLeftC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
				setRotateAngle(chestScarfLeftC, -0.8726646259971648F, -0.8726646259971648F, 0.0F);
				this.chestScarfRightA = new RendererModel(this, 50, 6);
				this.chestScarfRightA.setRotationPoint(-5.0F, 0.0F, -3.0F);
				this.chestScarfRightA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
				setRotateAngle(chestScarfRightA, 0.0F, -0.3490658503988659F, -0.17453292519943295F);
				this.chestScarfRightB = new RendererModel(this, 58, 3);
				this.chestScarfRightB.setRotationPoint(0.0F, 5.0F, 0.0F);
				this.chestScarfRightB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
				setRotateAngle(chestScarfRightB, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
				this.chestScarfRightC = new RendererModel(this, 54, 5);
				this.chestScarfRightC.setRotationPoint(0.0F, 8.0F, 0.0F);
				this.chestScarfRightC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
				setRotateAngle(chestScarfRightC, -0.8726646259971648F, 0.8726646259971648F, 0.0F);

				this.bipedBody = chestNecklace;
				if(!isSteve){
					this.bipedBody.addChild(bodyBreast);
					this.bipedBody.addChild(bodyBreastNecklace);
				}
				this.bipedBody.addChild(this.chestScarfTop);
				this.chestScarfTop.addChild(this.chestScarfTopLeftA);
				this.chestScarfTopLeftA.addChild(this.chestScarfTopLeftB);
				this.chestScarfTop.addChild(this.chestScarfTopRightA);
				this.chestScarfTopRightA.addChild(this.chestScarfTopRightB);
				this.bipedBody.addChild(this.chestScarfLeftA);
				this.chestScarfLeftA.addChild(this.chestScarfLeftB);
				this.chestScarfLeftB.addChild(this.chestScarfLeftC);
				this.bipedBody.addChild(this.chestScarfRightA);
				this.chestScarfRightA.addChild(this.chestScarfRightB);
				this.chestScarfRightB.addChild(this.chestScarfRightC);

				this.bipedLeftArm = armRingLeftA;
				this.bipedLeftArm.addChild(armRingLeftB);

				this.bipedRightArm = armRingRightA;
				this.bipedRightArm.addChild(armRingRightB);
				break;

			case LEGS:
				this.chestBelt = new RendererModel(this, 0, 16);
				this.chestBelt.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chestBelt.addBox(-4.5F, 9.0F, -2.5F, 9, 2, 5, 0.2F);
				this.chestBeltJewel = new RendererModel(this, 0, 23);
				this.chestBeltJewel.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chestBeltJewel.addBox(0.0F, 8.5F, -3.0F, 3, 3, 1, 0.0F);
				this.chestBeltHangA = new RendererModel(this, 14, 23);
				this.chestBeltHangA.setRotationPoint(0.0F, 11.2F, -2.5F);
				this.chestBeltHangA.addBox(0.0F, 0.0F, -0.5F, 2, 5, 1, -0.2F);
				this.chestBeltHangB = new RendererModel(this, 8, 23);
				this.chestBeltHangB.setRotationPoint(1.0F, 11.0F, -2.75F);
				this.chestBeltHangB.addBox(0.0F, 0.0F, -0.5F, 2, 4, 1, -0.3F);
				this.chestPant = new RendererModel(this, 20, 23);
				this.chestPant.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chestPant.addBox(-4.0F, 11.0F, -2.0F, 8, 1, 4, 0.1F);
				this.chestPantLeftFront = new RendererModel(this, 23, 18);
				this.chestPantLeftFront.setRotationPoint(0.0F, 9.0F, 2.0F);
				this.chestPantLeftFront.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantLeftFront, -0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantRightFront = new RendererModel(this, 23, 18);
				this.chestPantRightFront.setRotationPoint(0.0F, 9.0F, 2.0F);
				this.chestPantRightFront.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantRightFront, -0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantLeft = new RendererModel(this, 28, 16);
				this.chestPantLeft.setRotationPoint(4.0F, 9.0F, -2.5F);
				this.chestPantLeft.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
				setRotateAngle(chestPantLeft, 0.0F, 0.0F, 0.5235987755982988F);
				this.chestPantRight = new RendererModel(this, 28, 16);
				this.chestPantRight.setRotationPoint(-4.0F, 9.0F, -2.5F);
				this.chestPantRight.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
				setRotateAngle(chestPantRight, 0.0F, 0.0F, -0.5235987755982988F);
				this.chestPantLeftBack = new RendererModel(this, 23, 18);
				this.chestPantLeftBack.setRotationPoint(0.0F, 9.0F, -2.0F);
				this.chestPantLeftBack.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantLeftBack, 0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantRightBack = new RendererModel(this, 23, 18);
				this.chestPantRightBack.setRotationPoint(0.0F, 9.0F, -2.0F);
				this.chestPantRightBack.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantRightBack, 0.5235987755982988F, 0.0F, 0.0F);

				this.legLeftPantBot = new RendererModel(this, 24, 7);
				this.legLeftPantBot.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeftPantTop = new RendererModel(this, 35, 7);
				this.legLeftPantTop.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legRightPantBot = new RendererModel(this, 24, 7);
				this.legRightPantBot.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRightPantTop = new RendererModel(this, 35, 7);
				this.legRightPantTop.setRotationPoint(0.0F, 0.0F, 0.0F);

				if(isSteve){
					this.legLeftPantBot.addBox(-2.0F, 5.0F, -2.0F, 4, 1, 4, 0.2F);
					this.legLeftPantTop.addBox(-2.5F, -1.0F, -2.5F, 5, 6, 5, 0.0F);
					this.legRightPantBot.addBox(-2.0F, 5.0F, -2.0F, 4, 1, 4, 0.2F);
					this.legRightPantTop.addBox(-2.5F, -1.0F, -2.5F, 5, 6, 5, 0.0F);
				}else{
					this.legLeftPantBot.addBox(-2.0F, 3.0F, -2.0F, 4, 1, 4, 0.2F);
					this.legLeftPantTop.addBox(-2.5F, -1.0F, -2.5F, 5, 4, 5, 0.0F);
					this.legRightPantBot.addBox(-2.0F, 3.0F, -2.0F, 4, 1, 4, 0.2F);
					this.legRightPantTop.addBox(-2.5F, -1.0F, -2.5F, 5, 4, 5, 0.0F);
				}

				this.bipedBody = chestBelt;
				this.bipedBody.addChild(chestBeltJewel);
				this.bipedBody.addChild(chestBeltHangA);
				this.bipedBody.addChild(chestBeltHangB);
				this.bipedBody.addChild(chestPant);
				this.bipedBody.addChild(chestPantLeftFront);
				this.bipedBody.addChild(chestPantRightFront);
				this.bipedBody.addChild(chestPantLeft);
				this.bipedBody.addChild(chestPantRight);
				this.bipedBody.addChild(chestPantLeftBack);
				this.bipedBody.addChild(chestPantRightBack);

				this.bipedLeftLeg = legLeftPantBot;
				this.bipedLeftLeg.addChild(legLeftPantTop);

				this.bipedRightLeg = legRightPantBot;
				this.bipedRightLeg.addChild(legRightPantTop);
				break;

			case FEET:
				this.legRingLeftA = new RendererModel(this, 30, 0);
				this.legRingLeftA.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legRingLeftA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.0F);
				this.legRingLeftB = new RendererModel(this, 15, 1);
				this.legRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legRingLeftB.addBox(-2.5F, 7.0F, -2.5F, 5, 1, 5, 0.01F);
				this.legRingRightA = new RendererModel(this, 0, 0);
				this.legRingRightA.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRingRightA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.01F);
				this.legRingRightB = new RendererModel(this, 30, 0);
				this.legRingRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legRingRightB.addBox(-2.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);

				this.bipedLeftLeg = legRingLeftA;
				this.bipedLeftLeg.addChild(legRingLeftB);

				this.bipedRightLeg = legRingRightA;
				this.bipedRightLeg.addChild(legRingRightB);
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
				bipedBody.showModel = true;
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

		float f;
		float d = ageInTicks / 60.0F;
		switch (this.slot) {
			case HEAD:
				f = d % (2.0F * (float)Math.PI);
				this.flyA.offsetY = 0.05F * sinPI(d + 1.0F);
				this.flyA.rotateAngleZ = f;
				this.flyB.offsetY = 0.05F * sinPI(d + 1.333F);
				this.flyB.rotateAngleZ = f + 0.33F;
				this.flyC.offsetY = 0.05F * sinPI(d + 1.667F);
				this.flyC.rotateAngleZ = f + 0.67F;
				this.flyD.offsetY = 0.05F * sinPI(d + 0.333F);
				this.flyD.rotateAngleZ = f + 0.17F;
				this.flyE.offsetY = 0.05F * sinPI(d);
				this.flyE.rotateAngleZ = f + 0.83F;
				this.flyF.offsetY = 0.05F * sinPI(d + 0.667F);
				this.flyF.rotateAngleZ = f + 0.5F;
				break;

			case CHEST:
				f = 0.03F * sinPI(2 * d);
				d = (0.866F - sinPI(0.333F - f)) * 0.25F;
				this.chestScarfTop.offsetZ = d * 0.8659F;
				this.chestScarfTop.offsetY = -d * 0.75F;
				this.chestScarfRightA.offsetY = d * 0.5F;
				this.chestScarfRightB.rotateAngleX = 1.047F + f * (float)Math.PI;
				this.chestScarfTopRightA.rotateAngleZ = -1.047F - f * (float)Math.PI;
				this.chestScarfTopRightB.rotateAngleZ = -1.047F + 2 * f * (float)Math.PI;
				this.chestScarfLeftA.offsetY = d * 0.5F;
				this.chestScarfLeftB.rotateAngleX = 1.047F + f * (float)Math.PI;
				this.chestScarfTopLeftA.rotateAngleZ = -2.094F + f * (float)Math.PI;
				this.chestScarfTopLeftB.rotateAngleZ = 1.047F - 2 * f * (float)Math.PI;
				f = -0.872F + 0.03F * sinPI((ageInTicks - 15) / 30.0F) * (float)Math.PI;
				this.chestScarfRightC.rotateAngleX = f;
				this.chestScarfLeftC.rotateAngleX = f;
				break;

			case LEGS:
				f = this.bipedLeftLeg.rotateAngleX;
				if(f > 0.0F) {
					this.chestBeltHangA.rotateAngleX = f * 0.5F;
					this.chestBeltHangB.rotateAngleX = f * 0.5F;
				} else {
					this.chestBeltHangA.rotateAngleX = f;
					this.chestBeltHangA.rotateAngleZ = f * 0.25F;
					this.chestBeltHangB.rotateAngleX = f;
					this.chestBeltHangB.rotateAngleY = f * 0.25F;
					this.chestBeltHangB.rotateAngleZ = f * 0.5F;
				}

				if (this.isSitting) {
					this.chestBeltHangA.rotateAngleX += 3.0F;
					this.chestBeltHangB.rotateAngleX += 3.0F;
				}

				if (this.isSneak) {
					this.chestBeltHangA.rotateAngleX += -0.5F;
					this.chestBeltHangB.rotateAngleX += -0.5F;
					this.chestBeltHangA.rotationPointY = 8.2F;
					this.chestBeltHangB.rotationPointY = 8.0F;
				}else{
					this.chestBeltHangA.rotationPointY = 11.2F;
					this.chestBeltHangB.rotationPointY = 11.0F;
				}
				break;

			default:
				break;
		}
	}
}