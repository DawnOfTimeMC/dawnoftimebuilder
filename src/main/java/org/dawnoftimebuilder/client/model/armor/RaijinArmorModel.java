package org.dawnoftimebuilder.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RaijinArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public ModelRenderer headArmor;
	public ModelRenderer headHornLeftA;
	public ModelRenderer headHornLeftB;
	public ModelRenderer headHornLeftC;
	public ModelRenderer headHornLeftD;
	public ModelRenderer headHornRightA;
	public ModelRenderer headHornRightB;
	public ModelRenderer headHornRightC;
	public ModelRenderer headHornRightD;

	//Set
	public ModelRenderer bodyArmor;
	public ModelRenderer flyA;
	public ModelRenderer flyB;
	public ModelRenderer flyC;
	public ModelRenderer flyD;
	public ModelRenderer flyE;
	public ModelRenderer flyF;

	//Chest
	public ModelRenderer armRingLeftA;
	public ModelRenderer armRingLeftB;
	public ModelRenderer armRingRightB;
	public ModelRenderer armRingRightA;
	public ModelRenderer chestNecklace;
	public ModelRenderer bodyBreast;
	public ModelRenderer bodyBreastNecklace;
	public ModelRenderer chestScarfTop;
	public ModelRenderer chestScarfLeftA;
	public ModelRenderer chestScarfLeftB;
	public ModelRenderer chestScarfLeftC;
	public ModelRenderer chestScarfRightA;
	public ModelRenderer chestScarfRightB;
	public ModelRenderer chestScarfRightC;
	public ModelRenderer chestScarfTopLeftA;
	public ModelRenderer chestScarfTopLeftB;
	public ModelRenderer chestScarfTopRightA;
	public ModelRenderer chestScarfTopRightB;

	//Leggings
	public ModelRenderer chestPant;
	public ModelRenderer legLeftPantBot;
	public ModelRenderer legLeftPantTop;
	public ModelRenderer legRightPantBot;
	public ModelRenderer legRightPantTop;
	public ModelRenderer chestBeltJewel;
	public ModelRenderer chestBeltHangA;
	public ModelRenderer chestBeltHangB;
	public ModelRenderer chestBelt;
	public ModelRenderer chestPantLeftBack;
	public ModelRenderer chestPantRightBack;
	public ModelRenderer chestPantRightFront;
	public ModelRenderer chestPantLeftFront;
	public ModelRenderer chestPantRight;
	public ModelRenderer chestPantLeft;

	//Boots
	public ModelRenderer legRingLeftA;
	public ModelRenderer legRingLeftB;
	public ModelRenderer legRingRightA;
	public ModelRenderer legRingRightB;

	public RaijinArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 64, 64);

		switch (slot) {
			case HEAD:
				this.headArmor = new ModelRenderer(this, 0, 0);
				this.headArmor.setPos(0.0F, 0.0F, 0.0F);
				this.headHornLeftA = new ModelRenderer(this, 0, 0);
				this.headHornLeftA.setPos(2.5F, -6.5F, -5.0F);
				this.headHornLeftA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
				setRotateAngle(headHornLeftA, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
				this.headHornLeftB = new ModelRenderer(this, 15, 0);
				this.headHornLeftB.setPos(0.0F, 0.0F, 0.0F);
				this.headHornLeftB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
				setRotateAngle(headHornLeftB, -1.2217304763960306F, 0.0F, 0.0F);
				this.headHornLeftC = new ModelRenderer(this, 30, 0);
				this.headHornLeftC.setPos(0.0F, 0.0F, 0.0F);
				this.headHornLeftC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
				setRotateAngle(headHornLeftC, 1.0471975511965976F, 0.0F, 0.0F);
				this.headHornLeftD = new ModelRenderer(this, 0, 0);
				this.headHornLeftD.setPos(0.0F, 0.0F, 0.0F);
				this.headHornLeftD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
				setRotateAngle(headHornLeftD, -0.5235987755982988F, 0.0F, 0.0F);
				this.headHornRightA = new ModelRenderer(this, 0, 0);
				this.headHornRightA.setPos(-2.5F, -6.5F, -5.0F);
				this.headHornRightA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
				setRotateAngle(headHornRightA, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
				this.headHornRightB = new ModelRenderer(this, 15, 0);
				this.headHornRightB.setPos(0.0F, 0.0F, 0.0F);
				this.headHornRightB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
				setRotateAngle(headHornRightB, -1.2217304763960306F, 0.0F, 0.0F);
				this.headHornRightC = new ModelRenderer(this, 30, 0);
				this.headHornRightC.setPos(0.0F, 0.0F, 0.0F);
				this.headHornRightC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
				setRotateAngle(headHornRightC, 1.0471975511965976F, 0.0F, 0.0F);
				this.headHornRightD = new ModelRenderer(this, 0, 0);
				this.headHornRightD.setPos(0.0F, 0.0F, 0.0F);
				this.headHornRightD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
				setRotateAngle(headHornRightD, -0.5235987755982988F, 0.0F, 0.0F);

				this.head = headArmor;
				this.head.addChild(headHornLeftA);
				this.headHornLeftA.addChild(this.headHornLeftB);
				this.headHornLeftA.addChild(this.headHornLeftC);
				this.headHornLeftA.addChild(this.headHornLeftD);
				this.head.addChild(headHornRightA);
				this.headHornRightA.addChild(this.headHornRightB);
				this.headHornRightA.addChild(this.headHornRightC);
				this.headHornRightA.addChild(this.headHornRightD);

				this.bodyArmor = new ModelRenderer(this, 0, 0);
				this.bodyArmor.setPos(0.0F, 0.0F, 0.0F);

				this.flyA = new ModelRenderer(this, 45, 0);
				this.flyA.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyB = new ModelRenderer(this, 45, 0);
				this.flyB.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyC = new ModelRenderer(this, 45, 0);
				this.flyC.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyD = new ModelRenderer(this, 45, 0);
				this.flyD.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyE = new ModelRenderer(this, 45, 0);
				this.flyE.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				this.flyF = new ModelRenderer(this, 45, 0);
				this.flyF.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
				if(isSteve){
					this.flyA.setPos(-5.0F, -15.0F, 3.0F);
					this.flyB.setPos(5.0F, -15.0F, 3.0F);
					this.flyC.setPos(-11.0F, -7.5F, 3.0F);
					this.flyD.setPos(11.0F, -7.5F, 3.0F);
					this.flyE.setPos(-9.0F, 2.0F, 3.0F);
					this.flyF.setPos(9.0F, 2.0F, 3.0F);
				}else{
					this.flyA.setPos(-4.5F, -15.0F, 3.0F);
					this.flyB.setPos(4.5F, -15.0F, 3.0F);
					this.flyC.setPos(-10.0F, -7.5F, 3.0F);
					this.flyD.setPos(10.0F, -7.5F, 3.0F);
					this.flyE.setPos(-8.0F, 2.0F, 3.0F);
					this.flyF.setPos(8.0F, 2.0F, 3.0F);
				}

				this.body = bodyArmor;
				this.body.addChild(this.flyA);
				this.body.addChild(this.flyB);
				this.body.addChild(this.flyC);
				this.body.addChild(this.flyD);
				this.body.addChild(this.flyE);
				this.body.addChild(this.flyF);
				break;

			case CHEST:
				if(isSteve){
					this.armRingLeftA = new ModelRenderer(this, 0, 0);
					this.armRingLeftA.setPos(5.0F, 2.0F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingLeftB = new ModelRenderer(this, 15, 1);
					this.armRingLeftB.setPos(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightA = new ModelRenderer(this, 30, 0);
					this.armRingRightA.setPos(-5.0F, 2.0F, 0.0F);
					this.armRingRightA.addBox(-3.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightB = new ModelRenderer(this, 0, 0);
					this.armRingRightB.setPos(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-3.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.chestNecklace = new ModelRenderer(this, 0, 7);
					this.chestNecklace.setPos(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.3F);
				}else{
					this.armRingLeftA = new ModelRenderer(this, 0, 33);
					this.armRingLeftA.setPos(5.0F, 2.5F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingLeftB = new ModelRenderer(this, 13, 34);
					this.armRingLeftB.setPos(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightA = new ModelRenderer(this, 21, 28);
					this.armRingRightA.setPos(-5.0F, 2.5F, 0.0F);
					this.armRingRightA.addBox(-2.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightB = new ModelRenderer(this, 0, 33);
					this.armRingRightB.setPos(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-2.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.bodyBreast = new ModelRenderer(this, 26, 34);
					this.bodyBreast.setPos(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, 0.2F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.chestNecklace = new ModelRenderer(this, 0, 7);
					this.chestNecklace.setPos(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.2F);
					this.bodyBreastNecklace = new ModelRenderer(this, 39, 23);
					this.bodyBreastNecklace.setPos(0.0F, -0.2F, -2.2F);
					this.bodyBreastNecklace.addBox(-4.0F, 0.2F, -5.2F, 8, 0, 5, 0.2F);
					setRotateAngle(bodyBreastNecklace, 1.0097597431827485F, 0.0F, 0.0F);
				}
				this.chestScarfTop = new ModelRenderer(this, 0, 29);
				this.chestScarfTop.setPos(0.0F, -4.5F, 6.5F);
				this.chestScarfTop.addBox(-5.0F, -0.5F, -1.5F, 10, 1, 3, 0.0F);
				setRotateAngle(chestScarfTop, -1.1344640137963142F, 0.0F, 0.0F);
				this.chestScarfTopLeftA = new ModelRenderer(this, 35, 18);
				this.chestScarfTopLeftA.setPos(5.0F, 0.0F, 0.0F);
				this.chestScarfTopLeftA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
				setRotateAngle(chestScarfTopLeftA, 0.0F, 0.0F, -2.0943951023931953F);
				this.chestScarfTopLeftB = new ModelRenderer(this, 40, 21);
				this.chestScarfTopLeftB.setPos(-6.0F, 0.0F, 0.0F);
				this.chestScarfTopLeftB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
				setRotateAngle(chestScarfTopLeftB, 0.0F, 0.0F, 1.0471975511965976F);
				this.chestScarfTopRightA = new ModelRenderer(this, 35, 18);
				this.chestScarfTopRightA.setPos(-5.0F, 0.0F, 0.0F);
				this.chestScarfTopRightA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
				setRotateAngle(chestScarfTopRightA, 0.0F, 0.0F, -1.0471975511965976F);
				this.chestScarfTopRightB = new ModelRenderer(this, 40, 21);
				this.chestScarfTopRightB.setPos(-6.0F, 0.0F, 0.0F);
				this.chestScarfTopRightB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
				setRotateAngle(chestScarfTopRightB, 0.0F, 0.0F, -1.0471975511965976F);
				this.chestScarfLeftA = new ModelRenderer(this, 50, 6);
				this.chestScarfLeftA.setPos(5.0F, 0.0F, -3.0F);
				this.chestScarfLeftA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
				setRotateAngle(chestScarfLeftA, 0.0F, 0.3490658503988659F, 0.17453292519943295F);
				this.chestScarfLeftB = new ModelRenderer(this, 58, 3);
				this.chestScarfLeftB.setPos(0.0F, 5.0F, 0.0F);
				this.chestScarfLeftB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
				setRotateAngle(chestScarfLeftB, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
				this.chestScarfLeftC = new ModelRenderer(this, 54, 5);
				this.chestScarfLeftC.setPos(0.0F, 8.0F, 0.0F);
				this.chestScarfLeftC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
				setRotateAngle(chestScarfLeftC, -0.8726646259971648F, -0.8726646259971648F, 0.0F);
				this.chestScarfRightA = new ModelRenderer(this, 50, 6);
				this.chestScarfRightA.setPos(-5.0F, 0.0F, -3.0F);
				this.chestScarfRightA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
				setRotateAngle(chestScarfRightA, 0.0F, -0.3490658503988659F, -0.17453292519943295F);
				this.chestScarfRightB = new ModelRenderer(this, 58, 3);
				this.chestScarfRightB.setPos(0.0F, 5.0F, 0.0F);
				this.chestScarfRightB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
				setRotateAngle(chestScarfRightB, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
				this.chestScarfRightC = new ModelRenderer(this, 54, 5);
				this.chestScarfRightC.setPos(0.0F, 8.0F, 0.0F);
				this.chestScarfRightC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
				setRotateAngle(chestScarfRightC, -0.8726646259971648F, 0.8726646259971648F, 0.0F);

				this.body = chestNecklace;
				if(!isSteve){
					this.body.addChild(bodyBreast);
					this.body.addChild(bodyBreastNecklace);
				}
				this.body.addChild(this.chestScarfTop);
				this.chestScarfTop.addChild(this.chestScarfTopLeftA);
				this.chestScarfTopLeftA.addChild(this.chestScarfTopLeftB);
				this.chestScarfTop.addChild(this.chestScarfTopRightA);
				this.chestScarfTopRightA.addChild(this.chestScarfTopRightB);
				this.body.addChild(this.chestScarfLeftA);
				this.chestScarfLeftA.addChild(this.chestScarfLeftB);
				this.chestScarfLeftB.addChild(this.chestScarfLeftC);
				this.body.addChild(this.chestScarfRightA);
				this.chestScarfRightA.addChild(this.chestScarfRightB);
				this.chestScarfRightB.addChild(this.chestScarfRightC);

				this.leftArm = armRingLeftA;
				this.leftArm.addChild(armRingLeftB);

				this.rightArm = armRingRightA;
				this.rightArm.addChild(armRingRightB);
				break;

			case LEGS:
				this.chestBelt = new ModelRenderer(this, 0, 16);
				this.chestBelt.setPos(0.0F, 0.0F, 0.0F);
				this.chestBelt.addBox(-4.5F, 9.0F, -2.5F, 9, 2, 5, 0.2F);
				this.chestBeltJewel = new ModelRenderer(this, 0, 23);
				this.chestBeltJewel.setPos(0.0F, 0.0F, 0.0F);
				this.chestBeltJewel.addBox(0.0F, 8.5F, -3.0F, 3, 3, 1, 0.0F);
				this.chestBeltHangA = new ModelRenderer(this, 14, 23);
				this.chestBeltHangA.setPos(0.0F, 11.2F, -2.5F);
				this.chestBeltHangA.addBox(0.0F, 0.0F, -0.5F, 2, 5, 1, -0.2F);
				this.chestBeltHangB = new ModelRenderer(this, 8, 23);
				this.chestBeltHangB.setPos(1.0F, 11.0F, -2.75F);
				this.chestBeltHangB.addBox(0.0F, 0.0F, -0.5F, 2, 4, 1, -0.3F);
				this.chestPant = new ModelRenderer(this, 20, 23);
				this.chestPant.setPos(0.0F, 0.0F, 0.0F);
				this.chestPant.addBox(-4.0F, 11.0F, -2.0F, 8, 1, 4, 0.1F);
				this.chestPantLeftFront = new ModelRenderer(this, 23, 18);
				this.chestPantLeftFront.setPos(0.0F, 9.0F, 2.0F);
				this.chestPantLeftFront.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantLeftFront, -0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantRightFront = new ModelRenderer(this, 23, 18);
				this.chestPantRightFront.setPos(0.0F, 9.0F, 2.0F);
				this.chestPantRightFront.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantRightFront, -0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantLeft = new ModelRenderer(this, 28, 16);
				this.chestPantLeft.setPos(4.0F, 9.0F, -2.5F);
				this.chestPantLeft.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
				setRotateAngle(chestPantLeft, 0.0F, 0.0F, 0.5235987755982988F);
				this.chestPantRight = new ModelRenderer(this, 28, 16);
				this.chestPantRight.setPos(-4.0F, 9.0F, -2.5F);
				this.chestPantRight.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
				setRotateAngle(chestPantRight, 0.0F, 0.0F, -0.5235987755982988F);
				this.chestPantLeftBack = new ModelRenderer(this, 23, 18);
				this.chestPantLeftBack.setPos(0.0F, 9.0F, -2.0F);
				this.chestPantLeftBack.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantLeftBack, 0.5235987755982988F, 0.0F, 0.0F);
				this.chestPantRightBack = new ModelRenderer(this, 23, 18);
				this.chestPantRightBack.setPos(0.0F, 9.0F, -2.0F);
				this.chestPantRightBack.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
				setRotateAngle(chestPantRightBack, 0.5235987755982988F, 0.0F, 0.0F);

				this.legLeftPantBot = new ModelRenderer(this, 24, 7);
				this.legLeftPantBot.setPos(1.9F, 12.0F, 0.0F);
				this.legLeftPantTop = new ModelRenderer(this, 35, 7);
				this.legLeftPantTop.setPos(0.0F, 0.0F, 0.0F);
				this.legRightPantBot = new ModelRenderer(this, 24, 7);
				this.legRightPantBot.setPos(-1.9F, 12.0F, 0.0F);
				this.legRightPantTop = new ModelRenderer(this, 35, 7);
				this.legRightPantTop.setPos(0.0F, 0.0F, 0.0F);

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

				this.body = chestBelt;
				this.body.addChild(chestBeltJewel);
				this.body.addChild(chestBeltHangA);
				this.body.addChild(chestBeltHangB);
				this.body.addChild(chestPant);
				this.body.addChild(chestPantLeftFront);
				this.body.addChild(chestPantRightFront);
				this.body.addChild(chestPantLeft);
				this.body.addChild(chestPantRight);
				this.body.addChild(chestPantLeftBack);
				this.body.addChild(chestPantRightBack);

				this.leftLeg = legLeftPantBot;
				this.leftLeg.addChild(legLeftPantTop);

				this.rightLeg = legRightPantBot;
				this.rightLeg.addChild(legRightPantTop);
				break;

			case FEET:
				this.legRingLeftA = new ModelRenderer(this, 30, 0);
				this.legRingLeftA.setPos(1.9F, 12.0F, 0.0F);
				this.legRingLeftA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.0F);
				this.legRingLeftB = new ModelRenderer(this, 15, 1);
				this.legRingLeftB.setPos(0.0F, 0.0F, 0.0F);
				this.legRingLeftB.addBox(-2.5F, 7.0F, -2.5F, 5, 1, 5, 0.01F);
				this.legRingRightA = new ModelRenderer(this, 0, 0);
				this.legRingRightA.setPos(-1.9F, 12.0F, 0.0F);
				this.legRingRightA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.01F);
				this.legRingRightB = new ModelRenderer(this, 30, 0);
				this.legRingRightB.setPos(0.0F, 0.0F, 0.0F);
				this.legRingRightB.addBox(-2.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);

				this.leftLeg = legRingLeftA;
				this.leftLeg.addChild(legRingLeftB);

				this.rightLeg = legRingRightA;
				this.rightLeg.addChild(legRingRightB);
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
				body.visible = true;
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
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float f;
		float d = ageInTicks / 60.0F;
		switch (this.slot) {
			case HEAD:
				f = d % (2.0F * (float)Math.PI);
				this.flyA.y = 0.05F * sinPI(d + 1.0F);
				this.flyA.zRot = f;
				this.flyB.y = 0.05F * sinPI(d + 1.333F);
				this.flyB.zRot = f + 0.33F;
				this.flyC.y = 0.05F * sinPI(d + 1.667F);
				this.flyC.zRot = f + 0.67F;
				this.flyD.y = 0.05F * sinPI(d + 0.333F);
				this.flyD.zRot = f + 0.17F;
				this.flyE.y = 0.05F * sinPI(d);
				this.flyE.zRot = f + 0.83F;
				this.flyF.y = 0.05F * sinPI(d + 0.667F);
				this.flyF.zRot = f + 0.5F;
				break;

			case CHEST:
				f = 0.03F * sinPI(2 * d);
				d = (0.866F - sinPI(0.333F - f)) * 0.25F;
				this.chestScarfTop.z = d * 0.8659F;
				this.chestScarfTop.y = -d * 0.75F;
				this.chestScarfRightA.y = d * 0.5F;
				this.chestScarfRightB.xRot = 1.047F + f * (float)Math.PI;
				this.chestScarfTopRightA.zRot = -1.047F - f * (float)Math.PI;
				this.chestScarfTopRightB.zRot = -1.047F + 2 * f * (float)Math.PI;
				this.chestScarfLeftA.y = d * 0.5F;
				this.chestScarfLeftB.xRot = 1.047F + f * (float)Math.PI;
				this.chestScarfTopLeftA.zRot = -2.094F + f * (float)Math.PI;
				this.chestScarfTopLeftB.zRot = 1.047F - 2 * f * (float)Math.PI;
				f = -0.872F + 0.03F * sinPI((ageInTicks - 15) / 30.0F) * (float)Math.PI;
				this.chestScarfRightC.xRot = f;
				this.chestScarfLeftC.xRot = f;
				break;

			case LEGS:
				f = this.leftLeg.xRot;
				if(f > 0.0F) {
					this.chestBeltHangA.xRot = f * 0.5F;
					this.chestBeltHangB.xRot = f * 0.5F;
				} else {
					this.chestBeltHangA.xRot = f;
					this.chestBeltHangA.zRot = f * 0.25F;
					this.chestBeltHangB.xRot = f;
					this.chestBeltHangB.yRot = f * 0.25F;
					this.chestBeltHangB.zRot = f * 0.5F;
				}

				if (this.riding) {
					this.chestBeltHangA.xRot += 3.0F;
					this.chestBeltHangB.xRot += 3.0F;
				}

				if (this.crouching) {
                    this.chestBeltHangA.xRot -= 0.5F;
                    this.chestBeltHangB.xRot -= 0.5F;
					this.chestBeltHangA.y = 8.2F;
					this.chestBeltHangB.y = 8.0F;
				}else{
					this.chestBeltHangA.y = 11.2F;
					this.chestBeltHangB.y = 11.0F;
				}
				break;

			default:
				break;
		}
	}
}