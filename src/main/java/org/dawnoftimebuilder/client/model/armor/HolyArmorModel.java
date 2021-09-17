package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HolyArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public RendererModel head;
	public RendererModel headWingLeft;
	public RendererModel headWingRight;

	//Chest
	public RendererModel chest;
	public RendererModel chestEffectBig;
	public RendererModel chestEffectBigRotated;
	public RendererModel chestEffectMiddle;
	public RendererModel chestEffectMiddleRotated;
	public RendererModel chestEffectSmall;
	public RendererModel chestEffectSmallRotated;
	public RendererModel chestBack;
	public RendererModel chestPecRight;
	public RendererModel chestPecLeft;
	public RendererModel chestCore;
	public RendererModel chestEffectFrontA;
	public RendererModel chestEffectFrontB;
	public RendererModel abs;
	public RendererModel armRight;
	public RendererModel armRightShoulderA;
	public RendererModel armRightShoulderB;
	public RendererModel armRightElbow;
	public RendererModel armRightWing;
	public RendererModel armRightGlove;
	public RendererModel armRightEffect;
	public RendererModel armRightEffectRotated;
	public RendererModel armLeft;
	public RendererModel armLeftShoulderA;
	public RendererModel armLeftShoulderB;
	public RendererModel armLeftElbow;
	public RendererModel armLeftWing;
	public RendererModel armLeftGlove;
	public RendererModel armLeftEffect;
	public RendererModel armLeftEffectRotated;

	//Leggings
	public RendererModel hipsLeft;
	public RendererModel hipsRight;

	//Boots
	public RendererModel legLeft;
	public RendererModel legLeftWing;
	public RendererModel legRight;
	public RendererModel legRightWing;

	public HolyArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 128, 128);

		switch (slot) {
			case HEAD:
				this.head = new RendererModel(this);
				this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.head.cubeList.add(new ModelBox(this.head, 0, 0, -4.5F, -9.0F, -4.5F, 9, 10, 9, 0.1F, false));
				this.head.cubeList.add(new ModelBox(this.head, 0, 0, -6.0F, -6.0F, -1.0F, 2, 2, 2, 0.0F, true));
				this.head.cubeList.add(new ModelBox(this.head, 0, 0, 4.0F, -6.0F, -1.0F, 2, 2, 2, 0.0F, false));
				this.head.cubeList.add(new ModelBox(this.head, 36, 0, -1.0F, -9.5F, -3.0F, 2, 7, 8, 0.0F, false));
				this.headWingLeft = new RendererModel(this);
				this.headWingLeft.setRotationPoint(5.5F, -5.5F, 0.0F);
				this.headWingLeft.cubeList.add(new ModelBox(this.headWingLeft, 36, 7, 0.0F, -2.5F, -2.0F, 0, 5, 8, 0.0F, true));
				setRotateAngle(this.headWingLeft, 0.3491F, 0.4363F, 0.0F);
				this.headWingRight = new RendererModel(this);
				this.headWingRight.setRotationPoint(-5.5F, -5.5F, 0.0F);
				this.headWingRight.cubeList.add(new ModelBox(this.headWingRight, 36, 7, 0.0F, -2.5F, -2.0F, 0, 5, 8, 0.0F, false));
				setRotateAngle(headWingRight, 0.3491F, -0.4363F, 0.0F);

				this.bipedHead = this.head;
				this.bipedHead.addChild(this.headWingLeft);
				this.bipedHead.addChild(this.headWingRight);
				break;

			case CHEST:
				this.chest = new RendererModel(this);
				this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chest.cubeList.add(new ModelBox(this.chest, 0, 28, -4.5F, -0.5F, -2.5F, 9, 13, 5, -0.2F, false));
				this.chestEffectBig = new RendererModel(this);
				this.chestEffectBig.setRotationPoint(0.0F, -2.5F, 7.0F);
				this.chestEffectBig.cubeList.add(new ModelBox(this.chestEffectBig, 66, 0, -15.5F, -15.5F, 0.0F, 31, 31, 0, 0.0F, false));
				this.chestEffectBigRotated = new RendererModel(this);
				this.chestEffectBigRotated.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chestEffectBigRotated.cubeList.add(new ModelBox(this.chestEffectBigRotated, 90, 31, -9.5F, -9.5F, 0.0F, 19, 19, 0, 0.0F, false));
				setRotateAngle(this.chestEffectBigRotated, 0.0F, 0.0F, 0.7854F);
				this.chestEffectMiddle = new RendererModel(this);
				this.chestEffectMiddle.setRotationPoint(0.0F, -2.5F, 8.0F);
				this.chestEffectMiddle.cubeList.add(new ModelBox(this.chestEffectMiddle, 0, 63, -8.5F, -8.5F, 0.01F, 17, 17, 0, 0.0F, false));
				this.chestEffectMiddleRotated = new RendererModel(this);
				this.chestEffectMiddleRotated.setRotationPoint(0.0F, -2.5F, 8.0F);
				this.chestEffectMiddleRotated.cubeList.add(new ModelBox(this.chestEffectMiddleRotated, 0, 80, -6.5F, -6.5F, 0.0F, 13, 13, 0, 0.0F, false));
				this.chestEffectSmall = new RendererModel(this);
				this.chestEffectSmall.setRotationPoint(0.0F, -2.5F, 9.0F);
				this.chestEffectSmall.cubeList.add(new ModelBox(this.chestEffectSmall, 83, 50, -4.5F, -4.5F, 0.0F, 9, 9, 0, 0.0F, false));
				this.chestEffectSmallRotated = new RendererModel(this);
				this.chestEffectSmallRotated.setRotationPoint(0.0F, -2.5F, 9.0F);
				this.chestEffectSmallRotated.cubeList.add(new ModelBox(this.chestEffectSmallRotated, 101, 50, -4.5F, -4.5F, 0.01F, 9, 9, 0, 0.0F, false));
				this.chestBack = new RendererModel(this);
				this.chestBack.setRotationPoint(0.0F, 6.2F, 2.5F);
				this.chestBack.cubeList.add(new ModelBox(this.chestBack, 0, 46, -3.5F, -6.5F, -1.7F, 7, 12, 2, 0.0F, false));
				setRotateAngle(this.chestBack, -0.0873F, 0.0F, 0.0F);
				if(isSteve){
					this.chestPecRight = new RendererModel(this);
					this.chestPecRight.setRotationPoint(0.5F, 4.0F, -0.5F);
					this.chestPecRight.cubeList.add(new ModelBox(this.chestPecRight, 37, 45, -4.0F, -5.0F, -2.9F, 4, 5, 4, 0.0F, true));
					setRotateAngle(this.chestPecRight, -0.2007F, 0.0F, -0.2618F);
					this.chestPecLeft = new RendererModel(this);
					this.chestPecLeft.setRotationPoint(-0.5F, 4.0F, -0.5F);
					this.chestPecLeft.cubeList.add(new ModelBox(this.chestPecLeft, 37, 45, 0.0F, -5.0F, -2.9F, 4, 5, 4, 0.0F, false));
					setRotateAngle(this.chestPecLeft, -0.2007F, 0.0F, 0.2618F);
					this.chestCore = new RendererModel(this);
					this.chestCore.setRotationPoint(0.0F, 4.5F, -3.6F);
					this.chestCore.cubeList.add(new ModelBox(this.chestCore, 0, 19, -1.0F, -3.5F, 0.0F, 2, 3, 1, 0.0F, false));
					setRotateAngle(this.chestCore, -0.1745F, 0.0F, 0.0F);
					this.chestEffectFrontB = new RendererModel(this);
					this.chestEffectFrontB.setRotationPoint(0.0F, -1.0F, 0.3F);
					this.chestEffectFrontB.cubeList.add(new ModelBox(this.chestEffectFrontB, 34, 75, -2.5F, -2.5F, 0.0F, 5, 5, 0, 0.0F, false));
					this.chestEffectFrontA = new RendererModel(this);
					this.chestEffectFrontA.setRotationPoint(0.0F, -1.0F, 0.3F);
					this.chestEffectFrontA.cubeList.add(new ModelBox(this.chestEffectFrontA, 34, 70, -2.5F, -2.5F, 0.01F, 5, 5, 0, 0.0F, false));
					this.abs = new RendererModel(this);
					this.abs.setRotationPoint(0.0F, 4.7F, -3.3F);
					this.abs.cubeList.add(new ModelBox(this.abs, 18, 48, -3.5F, -1.0F, 0.3F, 7, 8, 1, 0.0F, false));
					setRotateAngle(this.abs, 0.0873F, 0.0F, 0.0F);
					this.armRight = new RendererModel(this);
					this.armRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRight.cubeList.add(new ModelBox(this.armRight, 46, 28, -3.5F, -2.0F, -2.5F, 5, 12, 5, -0.19F, true));
					this.armRightShoulderA = new RendererModel(this);
					this.armRightShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulderA.cubeList.add(new ModelBox(this.armRightShoulderA, 25, 21, -4.0F, -3.5F, -3.5F, 6, 5, 7, 0.0F, true));
					setRotateAngle(this.armRightShoulderA, 0.0F, 0.0F, 0.1745F);
					this.armRightShoulderB = new RendererModel(this);
					this.armRightShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulderB.cubeList.add(new ModelBox(this.armRightShoulderB, 0, 19, -6.2F, -2.0F, -4.0F, 8, 1, 8, 0.0F, true));
					setRotateAngle(this.armRightShoulderB, 0.0F, 0.0F, 0.6109F);
					this.armRightElbow = new RendererModel(this);
					this.armRightElbow.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightElbow.cubeList.add(new ModelBox(this.armRightElbow, 44, 20, -3.5F, 3.3F, -3.0F, 5, 2, 6, 0.0F, true));
					this.armRightElbow.cubeList.add(new ModelBox(this.armRightElbow, 0, 6, -4.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, true));
					this.armRightWing = new RendererModel(this);
					this.armRightWing.setRotationPoint(-3.401F, 4.3F, -1.6F);
					this.armRightWing.cubeList.add(new ModelBox(this.armRightWing, 0, 0, -0.099F, -1.0F, 0.0F, 0, 2, 4, 0.0F, true));
					setRotateAngle(this.armRightWing, 0.1745F, -0.3054F, 0.0F);
					this.armRightGlove = new RendererModel(this);
					this.armRightGlove.setRotationPoint(-1.0F, 9.0F, -3.0F);
					this.armRightGlove.cubeList.add(new ModelBox(this.armRightGlove, 47, 48, -2.5F, -1.5F, 0.0F, 5, 3, 6, 0.01F, true));
					setRotateAngle(this.armRightGlove, 0.0F, 0.0F, -0.0873F);
					this.armRightEffect = new RendererModel(this);
					this.armRightEffect.setRotationPoint(-3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armRightEffect, 0.0F, 0.0F, -1.0F);
					this.armRightEffect.cubeList.add(new ModelBox(this.armRightEffect, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true));
					this.armRightEffectRotated = new RendererModel(this);
					this.armRightEffectRotated.setRotationPoint(-3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armRightEffectRotated, -0.7854F, 0.0F, -1.0F);
					this.armRightEffectRotated.cubeList.add(new ModelBox(this.armRightEffectRotated, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true));
					this.armLeft = new RendererModel(this);
					this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeft.cubeList.add(new ModelBox(this.armLeft, 46, 28, -1.5F, -2.0F, -2.5F, 5, 12, 5, -0.19F, false));
					this.armLeftShoulderA = new RendererModel(this);
					this.armLeftShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderA.cubeList.add(new ModelBox(this.armLeftShoulderA, 25, 21, -2.0F, -3.5F, -3.5F, 6, 5, 7, 0.0F, false));
					setRotateAngle(this.armLeftShoulderA, 0.0F, 0.0F, -0.1745F);
					this.armLeftShoulderB = new RendererModel(this);
					this.armLeftShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderB.cubeList.add(new ModelBox(this.armLeftShoulderB, 0, 19, -1.8F, -2.0F, -4.0F, 8, 1, 8, 0.0F, false));
					setRotateAngle(this.armLeftShoulderB, 0.0F, 0.0F, -0.6109F);
					this.armLeftElbow = new RendererModel(this);
					this.armLeftElbow.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftElbow.cubeList.add(new ModelBox(this.armLeftElbow, 44, 20, -1.5F, 3.3F, -3.0F, 5, 2, 6, 0.0F, false));
					this.armLeftElbow.cubeList.add(new ModelBox(this.armLeftElbow, 0, 6, 3.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, false));
					this.armLeftWing = new RendererModel(this);
					this.armLeftWing.setRotationPoint(3.401F, 4.3F, -1.6F);
					this.armLeftWing.cubeList.add(new ModelBox(this.armLeftWing, 0, 0, 0.099F, -1.0F, 0.0F, 0, 2, 4, 0.0F, false));
					setRotateAngle(this.armLeftWing, 0.1745F, 0.3054F, 0.0F);
					this.armLeftGlove = new RendererModel(this);
					this.armLeftGlove.setRotationPoint(1.0F, 9.0F, -3.0F);
					this.armLeftGlove.cubeList.add(new ModelBox(this.armLeftGlove, 47, 48, -2.5F, -1.5F, 0.0F, 5, 3, 6, 0.01F, false));
					setRotateAngle(this.armLeftGlove, 0.0F, 0.0F, 0.0873F);
					this.armLeftEffectRotated = new RendererModel(this);
					this.armLeftEffectRotated.setRotationPoint(3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armLeftEffectRotated, -0.7854F, 0.0F, 1.0F);
					this.armLeftEffectRotated.cubeList.add(new ModelBox(this.armLeftEffectRotated, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false));
					this.armLeftEffect = new RendererModel(this);
					this.armLeftEffect.setRotationPoint(3.0F, 5.0F, 0.0F);
					setRotateAngle(armLeftEffect, 0.0F, 0.0F, 1.0F);
					this.armLeftEffect.cubeList.add(new ModelBox(this.armLeftEffect, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false));
				}else{
					this.chestPecRight = new RendererModel(this);
					this.chestPecRight.setRotationPoint(0.5F, 4.0F, -0.5F);
					this.chestPecRight.cubeList.add(new ModelBox(this.chestPecRight, 37, 45, -4.0F, -4.0F, -3.5F, 4, 5, 4, 0.0F, true));
					setRotateAngle(this.chestPecRight, -0.3752F, 0.0F, -0.1745F);
					this.chestPecLeft = new RendererModel(this);
					this.chestPecLeft.setRotationPoint(-0.5F, 4.0F, -0.5F);
					this.chestPecLeft.cubeList.add(new ModelBox(this.chestPecLeft, 37, 45, 0.0F, -4.0F, -3.5F, 4, 5, 4, 0.0F, false));
					setRotateAngle(this.chestPecLeft, -0.3752F, 0.0F, 0.1745F);
					this.chestCore = new RendererModel(this);
					this.chestCore.setRotationPoint(0.0F, 4.5F, -3.6F);
					this.chestCore.cubeList.add(new ModelBox(this.chestCore, 0, 19, -1.0F, -3.5F, -0.7F, 2, 3, 1, 0.0F, false));
					setRotateAngle(this.chestCore, -0.1745F, 0.0F, 0.0F);
					this.chestEffectFrontB = new RendererModel(this);
					this.chestEffectFrontB.setRotationPoint(0.0F, -0.5F, 0.3F);
					this.chestEffectFrontB.cubeList.add(new ModelBox(this.chestEffectFrontB, 34, 75, -2.5F, -2.5F, 0.0F, 5, 5, 0, 0.0F, false));
					this.chestEffectFrontA = new RendererModel(this);
					this.chestEffectFrontA.setRotationPoint(0.0F, -0.5F, 0.3F);
					this.chestEffectFrontA.cubeList.add(new ModelBox(this.chestEffectFrontA, 34, 70, -2.5F, -2.5F, 0.01F, 5, 5, 0, 0.0F, false));
					this.abs = new RendererModel(this);
					this.abs.setRotationPoint(0.0F, 4.7F, -3.3F);
					this.abs.cubeList.add(new ModelBox(this.abs, 18, 48, -3.5F, -1.0F, 0.8F, 7, 8, 1, 0.0F, false));
					this.armRight = new RendererModel(this);
					this.armRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRight.cubeList.add(new ModelBox(this.armRight, 46, 28, -2.5F, -2.0F, -2.5F, 4, 12, 5, -0.19F, true));
					this.armRightShoulderA = new RendererModel(this);
					this.armRightShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulderA.cubeList.add(new ModelBox(this.armRightShoulderA, 25, 21, -3.0F, -3.5F, -3.5F, 5, 5, 7, 0.0F, true));
					setRotateAngle(this.armRightShoulderA, 0.0F, 0.0F, 0.1745F);
					this.armRightShoulderB = new RendererModel(this);
					this.armRightShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulderB.cubeList.add(new ModelBox(this.armRightShoulderB, 0, 19, -5.2F, -2.0F, -4.0F, 7, 1, 8, 0.0F, true));
					setRotateAngle(this.armRightShoulderB, 0.0F, 0.0F, 0.6109F);
					this.armRightElbow = new RendererModel(this);
					this.armRightElbow.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightElbow.cubeList.add(new ModelBox(this.armRightElbow, 44, 20, -2.5F, 3.3F, -3.0F, 4, 2, 6, 0.0F, true));
					this.armRightElbow.cubeList.add(new ModelBox(this.armRightElbow, 0, 6, -3.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, true));
					this.armRightWing = new RendererModel(this);
					this.armRightWing.setRotationPoint(-3.401F, 4.3F, -1.6F);
					this.armRightWing.cubeList.add(new ModelBox(this.armRightWing, 0, 0, 0.901F, -1.0F, 0.0F, 0, 2, 4, 0.0F, true));
					setRotateAngle(this.armRightWing, 0.1745F, -0.3054F, 0.0F);
					this.armRightGlove = new RendererModel(this);
					this.armRightGlove.setRotationPoint(-1.0F, 9.0F, -3.0F);
					this.armRightGlove.cubeList.add(new ModelBox(this.armRightGlove, 47, 48, -1.5F, -1.5F, 0.0F, 4, 3, 6, 0.01F));
					setRotateAngle(this.armRightGlove, 0.0F, 0.0F, -0.0873F);
					this.armLeft = new RendererModel(this);
					this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeft.cubeList.add(new ModelBox(this.armLeft, 46, 28, -1.5F, -2.0F, -2.5F, 4, 12, 5, -0.19F, false));
					this.armLeftShoulderA = new RendererModel(this);
					this.armLeftShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderA.cubeList.add(new ModelBox(this.armLeftShoulderA, 25, 21, -2.0F, -3.5F, -3.5F, 5, 5, 7, 0.0F, false));
					setRotateAngle(this.armLeftShoulderA, 0.0F, 0.0F, -0.1745F);
					this.armLeftShoulderB = new RendererModel(this);
					this.armLeftShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderB.cubeList.add(new ModelBox(this.armLeftShoulderB, 0, 19, -1.8F, -2.0F, -4.0F, 7, 1, 8, 0.0F, false));
					setRotateAngle(this.armLeftShoulderB, 0.0F, 0.0F, -0.6109F);
					this.armLeftElbow = new RendererModel(this);
					this.armLeftElbow.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftElbow.cubeList.add(new ModelBox(this.armLeftElbow, 44, 20, -1.5F, 3.3F, -3.0F, 4, 2, 6, 0.0F, false));
					armLeftElbow.cubeList.add(new ModelBox(this.armLeftElbow, 0, 6, 2.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, false));
					this.armLeftWing = new RendererModel(this);
					this.armLeftWing.setRotationPoint(3.401F, 4.3F, -1.6F);
					this.armLeftWing.cubeList.add(new ModelBox(this.armLeftWing, 0, 0, -0.901F, -1.0F, 0.0F, 0, 2, 4, 0.0F, false));
					setRotateAngle(this.armLeftWing, 0.1745F, 0.3054F, 0.0F);
					this.armLeftGlove = new RendererModel(this);
					this.armLeftGlove.setRotationPoint(1.0F, 9.0F, -3.0F);
					this.armLeftGlove.cubeList.add(new ModelBox(this.armLeftGlove, 47, 48, -2.5F, -1.5F, 0.0F, 4, 3, 6, 0.01F, false));
					setRotateAngle(this.armLeftGlove, 0.0F, 0.0F, 0.0873F);
				}
				this.armRightEffect = new RendererModel(this);
				this.armRightEffect.setRotationPoint(-3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armRightEffect, 0.0F, 0.0F, -1.0F);
				this.armRightEffect.cubeList.add(new ModelBox(this.armRightEffect, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true));
				this.armRightEffectRotated = new RendererModel(this);
				this.armRightEffectRotated.setRotationPoint(-3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armRightEffectRotated, -0.7854F, 0.0F, -1.0F);
				this.armRightEffectRotated.cubeList.add(new ModelBox(this.armRightEffectRotated, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true));
				this.armLeftEffectRotated = new RendererModel(this);
				this.armLeftEffectRotated.setRotationPoint(3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armLeftEffectRotated, -0.7854F, 0.0F, 1.0F);
				this.armLeftEffectRotated.cubeList.add(new ModelBox(this.armLeftEffectRotated, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false));
				this.armLeftEffect = new RendererModel(this);
				this.armLeftEffect.setRotationPoint(3.0F, 5.0F, 0.0F);
				setRotateAngle(armLeftEffect, 0.0F, 0.0F, 1.0F);
				this.armLeftEffect.cubeList.add(new ModelBox(this.armLeftEffect, 34, 56, 0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false));

				this.bipedBody = this.chest;
				this.bipedBody.addChild(this.chestBack);
				this.bipedBody.addChild(this.chestPecRight);
				this.bipedBody.addChild(this.chestPecLeft);
				this.bipedBody.addChild(this.chestCore);
				this.chestCore.addChild(this.chestEffectFrontA);
				this.chestCore.addChild(this.chestEffectFrontB);
				this.bipedBody.addChild(this.abs);
				this.bipedBody.addChild(this.chestEffectBig);
				this.chestEffectBig.addChild(this.chestEffectBigRotated);
				this.chest.addChild(this.chestEffectMiddle);
				this.chest.addChild(this.chestEffectMiddleRotated);
				this.chest.addChild(this.chestEffectSmall);
				this.chest.addChild(this.chestEffectSmallRotated);

				this.bipedLeftArm = this.armLeft;
				this.bipedLeftArm.addChild(this.armLeftShoulderA);
				this.bipedLeftArm.addChild(this.armLeftShoulderB);
				this.bipedLeftArm.addChild(this.armLeftGlove);
				this.bipedLeftArm.addChild(this.armLeftElbow);
				this.armLeftElbow.addChild(this.armLeftWing);
				this.armLeft.addChild(this.armLeftEffectRotated);
				this.armLeft.addChild(this.armLeftEffect);

				this.bipedRightArm = this.armRight;
				this.bipedRightArm.addChild(this.armRightShoulderA);
				this.bipedRightArm.addChild(this.armRightShoulderB);
				this.bipedRightArm.addChild(this.armRightGlove);
				this.bipedRightArm.addChild(this.armRightElbow);
				this.armRightElbow.addChild(this.armRightWing);
				this.armRight.addChild(this.armRightEffect);
				this.armRight.addChild(this.armRightEffectRotated);
				break;

			case LEGS:
				this.chest = new RendererModel(this);
				this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.chest.cubeList.add(new ModelBox(this.chest, 0, 23, -1.0F, 9.5F, -3.3F, 2, 2, 1, 0.0F, false));
				this.hipsLeft = new RendererModel(this);
				this.hipsLeft.setRotationPoint(0.0F, 10.0F, 0.0F);
				this.hipsLeft.cubeList.add(new ModelBox(this.hipsLeft, 28, 54, 0.0F, 0.0F, -3.0F, 4, 3, 6, 0.0F, false));
				setRotateAngle(this.hipsLeft, 0.0F, 0.0F, -0.2618F);
				this.hipsRight = new RendererModel(this);
				this.hipsRight.setRotationPoint(0.0F, 10.0F, 0.0F);
				this.hipsRight.cubeList.add(new ModelBox(this.hipsRight, 28, 54, -4.0F, 0.0F, -3.0F, 4, 3, 6, 0.0F, true));
				setRotateAngle(this.hipsRight, 0.0F, 0.0F, 0.2618F);
				this.legLeft = new RendererModel(this);
				this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 28, 33, -1.5F, -0.3F, -2.5F, 4, 5, 5, 0.0F, false));
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 63, 51, -1.2598F, -0.1375F, -3.1F, 4, 3, 6, -0.05F, false));
				this.legRight = new RendererModel(this);
				this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRight.cubeList.add(new ModelBox(this.legRight, 28, 33, -2.5F, -0.3F, -2.5F, 4, 5, 5, 0.0F, true));
				this.legRight.cubeList.add(new ModelBox(this.legRight, 63, 51, -2.7402F, -0.1375F, -3.1F, 4, 3, 6, -0.05F, true));

				this.bipedBody = this.chest;
				this.bipedBody.addChild(this.hipsLeft);
				this.bipedBody.addChild(this.hipsRight);

				this.bipedLeftLeg = this.legLeft;

				this.bipedRightLeg = this.legRight;
				break;

			case FEET:
				this.legLeft = new RendererModel(this);
				this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 66, 31, -2.5F, -0.7F, -2.5F, 5, 13, 5, -0.2F, false));
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 48, 57, -2.5F, 10.3F, -2.5F, 5, 2, 5, 0.0F, false));
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 25, 43, -2.5F, 5.3F, -2.6F, 5, 2, 3, 0.0F, false));
				this.legLeft.cubeList.add(new ModelBox(this.legLeft, 0, 6, 2.0F, 5.8F, -1.1F, 1, 1, 1, 0.0F, false));
				this.legLeftWing = new RendererModel(this);
				this.legLeftWing.setRotationPoint(2.501F, 5.3F, -1.6F);
				this.legLeftWing.cubeList.add(new ModelBox(this.legLeftWing, 0, 55, 0.0F, -1.0F, 0.0F, 0, 3, 5, 0.0F, false));
				setRotateAngle(this.legLeftWing, -0.3F, 0.3054F, 0.0F);
				this.legRight = new RendererModel(this);
				this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRight.cubeList.add(new ModelBox(this.legRight, 66, 31, -2.5F, -0.7F, -2.5F, 5, 13, 5, -0.2F, true));
				this.legRight.cubeList.add(new ModelBox(this.legRight, 48, 57, -2.5F, 10.3F, -2.5F, 5, 2, 5, 0.0F, true));
				this.legRight.cubeList.add(new ModelBox(this.legRight, 25, 43, -2.5F, 5.3F, -2.6F, 5, 2, 3, 0.0F, true));
				this.legRight.cubeList.add(new ModelBox(this.legRight, 0, 6, -3.0F, 5.8F, -1.1F, 1, 1, 1, 0.0F, true));
				this.legRightWing = new RendererModel(this);
				this.legRightWing.setRotationPoint(-2.501F, 5.3F, -1.6F);
				this.legRightWing.cubeList.add(new ModelBox(this.legRightWing, 0, 55, 0.0F, -1.0F, 0.0F, 0, 3, 5, 0.0F, true));
				setRotateAngle(this.legRightWing, -0.3F, -0.3054F, 0.0F);

				this.bipedLeftLeg = this.legLeft;
				this.bipedLeftLeg.addChild(this.legLeftWing);

				this.bipedRightLeg = this.legRight;
				this.bipedRightLeg.addChild(this.legRightWing);
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

		switch (this.slot) {
			case HEAD:
				this.headWingLeft.rotateAngleX = 0.3491F + 0.15F * sinPI(ageInTicks / 40.0F);
				this.headWingRight.rotateAngleX = 0.3491F + 0.15F * sinPI(ageInTicks / 40.0F);
				break;

			case CHEST:
				float dA = ageInTicks / 40.0F;
				float rotation = dA % (2.0F * (float)Math.PI);
				float rotationD = (dA + (float)Math.PI / 4.0F) % (2.0F * (float)Math.PI);
				this.chestEffectFrontA.rotateAngleZ = rotation;
				this.chestEffectFrontB.rotateAngleZ = rotationD;
				this.chestEffectMiddle.rotateAngleZ = rotation;
				this.chestEffectMiddleRotated.rotateAngleZ = rotationD;
				this.armLeftEffect.rotateAngleX = -rotation;
				this.armLeftEffectRotated.rotateAngleX = -rotationD;
				this.armRightEffect.rotateAngleX = -rotation;
				this.armRightEffectRotated.rotateAngleX = -rotationD;
				dA = 0.1F * sinPI(dA);
				this.armLeftWing.rotateAngleX = 0.1745F + dA;
				this.armRightWing.rotateAngleX = 0.1745F + dA;
				dA = 0.06F * sinPI(ageInTicks / 80.0F);
				this.chestEffectBig.offsetY = dA;
				this.chestEffectMiddle.offsetY = dA;
				this.chestEffectMiddleRotated.offsetY = dA;
				this.chestEffectSmall.offsetY = dA;
				this.chestEffectSmallRotated.offsetY = dA;
				dA = ageInTicks / 20.0F;
				rotation = dA % (2.0F * (float)Math.PI);
				rotationD = (dA + (float)Math.PI / 4.0F) % (2.0F * (float)Math.PI);
				this.chestEffectSmall.rotateAngleZ = -rotation;
				this.chestEffectSmallRotated.rotateAngleZ = -rotationD;
				break;

			case FEET:
				this.legLeftWing.rotateAngleX = -0.1745F + 0.1F * sinPI(ageInTicks / 40.0F);
				this.legRightWing.rotateAngleX = -0.1745F + 0.1F * sinPI(ageInTicks / 40.0F);
				break;

			default:
				break;
		}
	}
}