package org.dawnoftimebuilder.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HolyArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {

	//Helmet
	public ModelRenderer armorHead;
	public ModelRenderer headWingLeft;
	public ModelRenderer headWingRight;

	//Chest
	public ModelRenderer chest;
	public ModelRenderer chestEffectBig;
	public ModelRenderer chestEffectBigRotated;
	public ModelRenderer chestEffectMiddle;
	public ModelRenderer chestEffectMiddleRotated;
	public ModelRenderer chestEffectSmall;
	public ModelRenderer chestEffectSmallRotated;
	public ModelRenderer chestBack;
	public ModelRenderer chestPecRight;
	public ModelRenderer chestPecLeft;
	public ModelRenderer chestCore;
	public ModelRenderer chestEffectFrontA;
	public ModelRenderer chestEffectFrontB;
	public ModelRenderer abs;
	public ModelRenderer armRight;
	public ModelRenderer armRightShoulderA;
	public ModelRenderer armRightShoulderB;
	public ModelRenderer armRightElbow;
	public ModelRenderer armRightWing;
	public ModelRenderer armRightGlove;
	public ModelRenderer armRightEffect;
	public ModelRenderer armRightEffectRotated;
	public ModelRenderer armLeft;
	public ModelRenderer armLeftShoulderA;
	public ModelRenderer armLeftShoulderB;
	public ModelRenderer armLeftElbow;
	public ModelRenderer armLeftWing;
	public ModelRenderer armLeftGlove;
	public ModelRenderer armLeftEffect;
	public ModelRenderer armLeftEffectRotated;

	//Leggings
	public ModelRenderer hipsLeft;
	public ModelRenderer hipsRight;

	//Boots
	public ModelRenderer armorLegLeft;
	public ModelRenderer legLeftWing;
	public ModelRenderer armorLegRight;
	public ModelRenderer legRightWing;

	public HolyArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 128, 128);

		switch (slot) {
			case HEAD:
				this.armorHead = new ModelRenderer(this);
				this.armorHead.setPos(0.0F, 0.0F, 0.0F);
				this.armorHead.texOffs(0, 0).addBox(-4.5F, -9.0F, -4.5F, 9, 10, 9, 0.1F, false);
				this.armorHead.texOffs(0, 0).addBox(-6.0F, -6.0F, -1.0F, 2, 2, 2, 0.0F, true);
				this.armorHead.texOffs(0, 0).addBox(4.0F, -6.0F, -1.0F, 2, 2, 2, 0.0F, false);
				this.armorHead.texOffs(36, 0).addBox(-1.0F, -9.5F, -3.0F, 2, 7, 8, 0.0F, false);
				this.headWingLeft = new ModelRenderer(this);
				this.headWingLeft.setPos(5.5F, -5.5F, 0.0F);
				this.headWingLeft.texOffs(36, 7).addBox(0.0F, -2.5F, -2.0F, 0, 5, 8, 0.0F, true);
				setRotateAngle(this.headWingLeft, 0.3491F, 0.4363F, 0.0F);
				this.headWingRight = new ModelRenderer(this);
				this.headWingRight.setPos(-5.5F, -5.5F, 0.0F);
				this.headWingRight.texOffs(36, 7).addBox(0.0F, -2.5F, -2.0F, 0, 5, 8, 0.0F, false);
				setRotateAngle(headWingRight, 0.3491F, -0.4363F, 0.0F);

				this.head = this.armorHead;
				this.armorHead.addChild(this.headWingLeft);
				this.armorHead.addChild(this.headWingRight);
				break;

			case CHEST:
				this.chest = new ModelRenderer(this);
				this.chest.setPos(0.0F, 0.0F, 0.0F);
				this.chest.texOffs(0, 28).addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, -0.2F, false);
				this.chestEffectBig = new ModelRenderer(this);
				this.chestEffectBig.setPos(0.0F, -2.5F, 7.0F);
				this.chestEffectBig.texOffs(66, 0).addBox(-15.5F, -15.5F, 0.0F, 31, 31, 0, 0.0F, false);
				this.chestEffectBigRotated = new ModelRenderer(this);
				this.chestEffectBigRotated.setPos(0.0F, 0.0F, 0.0F);
				this.chestEffectBigRotated.texOffs(90, 31).addBox(-9.5F, -9.5F, 0.0F, 19, 19, 0, 0.0F, false);
				setRotateAngle(this.chestEffectBigRotated, 0.0F, 0.0F, 0.7854F);
				this.chestEffectMiddle = new ModelRenderer(this);
				this.chestEffectMiddle.setPos(0.0F, -2.5F, 8.0F);
				this.chestEffectMiddle.texOffs(0, 63).addBox(-8.5F, -8.5F, 0.01F, 17, 17, 0, 0.0F, false);
				this.chestEffectMiddleRotated = new ModelRenderer(this);
				this.chestEffectMiddleRotated.setPos(0.0F, -2.5F, 8.0F);
				this.chestEffectMiddleRotated.texOffs(0, 80).addBox(-6.5F, -6.5F, 0.0F, 13, 13, 0, 0.0F, false);
				this.chestEffectSmall = new ModelRenderer(this);
				this.chestEffectSmall.setPos(0.0F, -2.5F, 9.0F);
				this.chestEffectSmall.texOffs(83, 50).addBox(-4.5F, -4.5F, 0.0F, 9, 9, 0, 0.0F, false);
				this.chestEffectSmallRotated = new ModelRenderer(this);
				this.chestEffectSmallRotated.setPos(0.0F, -2.5F, 9.0F);
				this.chestEffectSmallRotated.texOffs(101, 50).addBox(-4.5F, -4.5F, 0.01F, 9, 9, 0, 0.0F, false);
				this.chestBack = new ModelRenderer(this);
				this.chestBack.setPos(0.0F, 6.2F, 2.5F);
				this.chestBack.texOffs(0, 46).addBox(-3.5F, -6.5F, -1.7F, 7, 12, 2, 0.0F, false);
				setRotateAngle(this.chestBack, -0.0873F, 0.0F, 0.0F);
				if(isSteve){
					this.chestPecRight = new ModelRenderer(this);
					this.chestPecRight.setPos(0.5F, 4.0F, -0.5F);
					this.chestPecRight.texOffs(37, 45).addBox(-4.0F, -5.0F, -2.9F, 4, 5, 4, 0.0F, true);
					setRotateAngle(this.chestPecRight, -0.2007F, 0.0F, -0.2618F);
					this.chestPecLeft = new ModelRenderer(this);
					this.chestPecLeft.setPos(-0.5F, 4.0F, -0.5F);
					this.chestPecLeft.texOffs(37, 45).addBox(0.0F, -5.0F, -2.9F, 4, 5, 4, 0.0F, false);
					setRotateAngle(this.chestPecLeft, -0.2007F, 0.0F, 0.2618F);
					this.chestCore = new ModelRenderer(this);
					this.chestCore.setPos(0.0F, 4.5F, -3.6F);
					this.chestCore.texOffs(0, 19).addBox(-1.0F, -3.5F, 0.0F, 2, 3, 1, 0.0F, false);
					setRotateAngle(this.chestCore, -0.1745F, 0.0F, 0.0F);
					this.chestEffectFrontB = new ModelRenderer(this);
					this.chestEffectFrontB.setPos(0.0F, -1.0F, 0.3F);
					this.chestEffectFrontB.texOffs(34, 75).addBox(-2.5F, -2.5F, 0.0F, 5, 5, 0, 0.0F, false);
					this.chestEffectFrontA = new ModelRenderer(this);
					this.chestEffectFrontA.setPos(0.0F, -1.0F, 0.3F);
					this.chestEffectFrontA.texOffs(34, 70).addBox(-2.5F, -2.5F, 0.01F, 5, 5, 0, 0.0F, false);
					this.abs = new ModelRenderer(this);
					this.abs.setPos(0.0F, 4.7F, -3.3F);
					this.abs.texOffs(18, 48).addBox(-3.5F, -1.0F, 0.3F, 7, 8, 1, 0.0F, false);
					setRotateAngle(this.abs, 0.0873F, 0.0F, 0.0F);
					this.armRight = new ModelRenderer(this);
					this.armRight.setPos(-5.0F, 2.0F, 0.0F);
					this.armRight.texOffs(46, 28).addBox(-3.5F, -2.0F, -2.5F, 5, 12, 5, -0.19F, true);
					this.armRightShoulderA = new ModelRenderer(this);
					this.armRightShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.armRightShoulderA.texOffs(25, 21).addBox(-4.0F, -3.5F, -3.5F, 6, 5, 7, 0.0F, true);
					setRotateAngle(this.armRightShoulderA, 0.0F, 0.0F, 0.1745F);
					this.armRightShoulderB = new ModelRenderer(this);
					this.armRightShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.armRightShoulderB.texOffs(0, 19).addBox(-6.2F, -2.0F, -4.0F, 8, 1, 8, 0.0F, true);
					setRotateAngle(this.armRightShoulderB, 0.0F, 0.0F, 0.6109F);
					this.armRightElbow = new ModelRenderer(this);
					this.armRightElbow.setPos(0.0F, 0.0F, 0.0F);
					this.armRightElbow.texOffs(44, 20).addBox(-3.5F, 3.3F, -3.0F, 5, 2, 6, 0.0F, true);
					this.armRightElbow.texOffs(0, 6).addBox(-4.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, true);
					this.armRightWing = new ModelRenderer(this);
					this.armRightWing.setPos(-3.401F, 4.3F, -1.6F);
					this.armRightWing.texOffs(0, 0).addBox(-0.099F, -1.0F, 0.0F, 0, 2, 4, 0.0F, true);
					setRotateAngle(this.armRightWing, 0.1745F, -0.3054F, 0.0F);
					this.armRightGlove = new ModelRenderer(this);
					this.armRightGlove.setPos(-1.0F, 9.0F, -3.0F);
					this.armRightGlove.texOffs(47, 48).addBox(-2.5F, -1.5F, 0.0F, 5, 3, 6, 0.01F, true);
					setRotateAngle(this.armRightGlove, 0.0F, 0.0F, -0.0873F);
					this.armRightEffect = new ModelRenderer(this);
					this.armRightEffect.setPos(-3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armRightEffect, 0.0F, 0.0F, -1.0F);
					this.armRightEffect.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true);
					this.armRightEffectRotated = new ModelRenderer(this);
					this.armRightEffectRotated.setPos(-3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armRightEffectRotated, -0.7854F, 0.0F, -1.0F);
					this.armRightEffectRotated.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true);
					this.armLeft = new ModelRenderer(this);
					this.armLeft.setPos(5.0F, 2.0F, 0.0F);
					this.armLeft.texOffs(46, 28).addBox(-1.5F, -2.0F, -2.5F, 5, 12, 5, -0.19F, false);
					this.armLeftShoulderA = new ModelRenderer(this);
					this.armLeftShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderA.texOffs(25, 21).addBox(-2.0F, -3.5F, -3.5F, 6, 5, 7, 0.0F, false);
					setRotateAngle(this.armLeftShoulderA, 0.0F, 0.0F, -0.1745F);
					this.armLeftShoulderB = new ModelRenderer(this);
					this.armLeftShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderB.texOffs(0, 19).addBox(-1.8F, -2.0F, -4.0F, 8, 1, 8, 0.0F, false);
					setRotateAngle(this.armLeftShoulderB, 0.0F, 0.0F, -0.6109F);
					this.armLeftElbow = new ModelRenderer(this);
					this.armLeftElbow.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftElbow.texOffs(44, 20).addBox(-1.5F, 3.3F, -3.0F, 5, 2, 6, 0.0F, false);
					this.armLeftElbow.texOffs(0, 6).addBox(3.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, false);
					this.armLeftWing = new ModelRenderer(this);
					this.armLeftWing.setPos(3.401F, 4.3F, -1.6F);
					this.armLeftWing.texOffs(0, 0).addBox(0.099F, -1.0F, 0.0F, 0, 2, 4, 0.0F, false);
					setRotateAngle(this.armLeftWing, 0.1745F, 0.3054F, 0.0F);
					this.armLeftGlove = new ModelRenderer(this);
					this.armLeftGlove.setPos(1.0F, 9.0F, -3.0F);
					this.armLeftGlove.texOffs(47, 48).addBox(-2.5F, -1.5F, 0.0F, 5, 3, 6, 0.01F, false);
					setRotateAngle(this.armLeftGlove, 0.0F, 0.0F, 0.0873F);
					this.armLeftEffectRotated = new ModelRenderer(this);
					this.armLeftEffectRotated.setPos(3.0F, 5.0F, 0.0F);
					setRotateAngle(this.armLeftEffectRotated, -0.7854F, 0.0F, 1.0F);
					this.armLeftEffectRotated.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false);
					this.armLeftEffect = new ModelRenderer(this);
					this.armLeftEffect.setPos(3.0F, 5.0F, 0.0F);
					setRotateAngle(armLeftEffect, 0.0F, 0.0F, 1.0F);
					this.armLeftEffect.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false);
				}else{
					this.chestPecRight = new ModelRenderer(this);
					this.chestPecRight.setPos(0.5F, 4.0F, -0.5F);
					this.chestPecRight.texOffs(37, 45).addBox(-4.0F, -4.0F, -3.5F, 4, 5, 4, 0.0F, true);
					setRotateAngle(this.chestPecRight, -0.3752F, 0.0F, -0.1745F);
					this.chestPecLeft = new ModelRenderer(this);
					this.chestPecLeft.setPos(-0.5F, 4.0F, -0.5F);
					this.chestPecLeft.texOffs(37, 45).addBox(0.0F, -4.0F, -3.5F, 4, 5, 4, 0.0F, false);
					setRotateAngle(this.chestPecLeft, -0.3752F, 0.0F, 0.1745F);
					this.chestCore = new ModelRenderer(this);
					this.chestCore.setPos(0.0F, 4.5F, -3.6F);
					this.chestCore.texOffs(0, 19).addBox(-1.0F, -3.5F, -0.7F, 2, 3, 1, 0.0F, false);
					setRotateAngle(this.chestCore, -0.1745F, 0.0F, 0.0F);
					this.chestEffectFrontB = new ModelRenderer(this);
					this.chestEffectFrontB.setPos(0.0F, -0.5F, 0.3F);
					this.chestEffectFrontB.texOffs(34, 75).addBox(-2.5F, -2.5F, 0.0F, 5, 5, 0, 0.0F, false);
					this.chestEffectFrontA = new ModelRenderer(this);
					this.chestEffectFrontA.setPos(0.0F, -0.5F, 0.3F);
					this.chestEffectFrontA.texOffs(34, 70).addBox(-2.5F, -2.5F, 0.01F, 5, 5, 0, 0.0F, false);
					this.abs = new ModelRenderer(this);
					this.abs.setPos(0.0F, 4.7F, -3.3F);
					this.abs.texOffs(18, 48).addBox(-3.5F, -1.0F, 0.8F, 7, 8, 1, 0.0F, false);
					this.armRight = new ModelRenderer(this);
					this.armRight.setPos(-5.0F, 2.0F, 0.0F);
					this.armRight.texOffs(46, 28).addBox(-2.5F, -2.0F, -2.5F, 4, 12, 5, -0.19F, true);
					this.armRightShoulderA = new ModelRenderer(this);
					this.armRightShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.armRightShoulderA.texOffs(25, 21).addBox(-3.0F, -3.5F, -3.5F, 5, 5, 7, 0.0F, true);
					setRotateAngle(this.armRightShoulderA, 0.0F, 0.0F, 0.1745F);
					this.armRightShoulderB = new ModelRenderer(this);
					this.armRightShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.armRightShoulderB.texOffs(0, 19).addBox(-5.2F, -2.0F, -4.0F, 7, 1, 8, 0.0F, true);
					setRotateAngle(this.armRightShoulderB, 0.0F, 0.0F, 0.6109F);
					this.armRightElbow = new ModelRenderer(this);
					this.armRightElbow.setPos(0.0F, 0.0F, 0.0F);
					this.armRightElbow.texOffs(44, 20).addBox(-2.5F, 3.3F, -3.0F, 4, 2, 6, 0.0F, true);
					this.armRightElbow.texOffs(0, 6).addBox(-3.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, true);
					this.armRightWing = new ModelRenderer(this);
					this.armRightWing.setPos(-3.401F, 4.3F, -1.6F);
					this.armRightWing.texOffs(0, 0).addBox(0.901F, -1.0F, 0.0F, 0, 2, 4, 0.0F, true);
					setRotateAngle(this.armRightWing, 0.1745F, -0.3054F, 0.0F);
					this.armRightGlove = new ModelRenderer(this);
					this.armRightGlove.setPos(-1.0F, 9.0F, -3.0F);
					this.armRightGlove.texOffs(47, 48).addBox(-1.5F, -1.5F, 0.0F, 4, 3, 6, 0.01F);
					setRotateAngle(this.armRightGlove, 0.0F, 0.0F, -0.0873F);
					this.armLeft = new ModelRenderer(this);
					this.armLeft.setPos(5.0F, 2.0F, 0.0F);
					this.armLeft.texOffs(46, 28).addBox(-1.5F, -2.0F, -2.5F, 4, 12, 5, -0.19F, false);
					this.armLeftShoulderA = new ModelRenderer(this);
					this.armLeftShoulderA.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderA.texOffs(25, 21).addBox(-2.0F, -3.5F, -3.5F, 5, 5, 7, 0.0F, false);
					setRotateAngle(this.armLeftShoulderA, 0.0F, 0.0F, -0.1745F);
					this.armLeftShoulderB = new ModelRenderer(this);
					this.armLeftShoulderB.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftShoulderB.texOffs(0, 19).addBox(-1.8F, -2.0F, -4.0F, 7, 1, 8, 0.0F, false);
					setRotateAngle(this.armLeftShoulderB, 0.0F, 0.0F, -0.6109F);
					this.armLeftElbow = new ModelRenderer(this);
					this.armLeftElbow.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftElbow.texOffs(44, 20).addBox(-1.5F, 3.3F, -3.0F, 4, 2, 6, 0.0F, false);
					armLeftElbow.texOffs(0, 6).addBox(2.0F, 3.8F, -1.1F, 1, 1, 1, 0.0F, false);
					this.armLeftWing = new ModelRenderer(this);
					this.armLeftWing.setPos(3.401F, 4.3F, -1.6F);
					this.armLeftWing.texOffs(0, 0).addBox(-0.901F, -1.0F, 0.0F, 0, 2, 4, 0.0F, false);
					setRotateAngle(this.armLeftWing, 0.1745F, 0.3054F, 0.0F);
					this.armLeftGlove = new ModelRenderer(this);
					this.armLeftGlove.setPos(1.0F, 9.0F, -3.0F);
					this.armLeftGlove.texOffs(47, 48).addBox(-2.5F, -1.5F, 0.0F, 4, 3, 6, 0.01F, false);
					setRotateAngle(this.armLeftGlove, 0.0F, 0.0F, 0.0873F);
				}
				this.armRightEffect = new ModelRenderer(this);
				this.armRightEffect.setPos(-3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armRightEffect, 0.0F, 0.0F, -1.0F);
				this.armRightEffect.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true);
				this.armRightEffectRotated = new ModelRenderer(this);
				this.armRightEffectRotated.setPos(-3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armRightEffectRotated, -0.7854F, 0.0F, -1.0F);
				this.armRightEffectRotated.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, true);
				this.armLeftEffectRotated = new ModelRenderer(this);
				this.armLeftEffectRotated.setPos(3.0F, 5.0F, 0.0F);
				setRotateAngle(this.armLeftEffectRotated, -0.7854F, 0.0F, 1.0F);
				this.armLeftEffectRotated.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false);
				this.armLeftEffect = new ModelRenderer(this);
				this.armLeftEffect.setPos(3.0F, 5.0F, 0.0F);
				setRotateAngle(armLeftEffect, 0.0F, 0.0F, 1.0F);
				this.armLeftEffect.texOffs(34, 56).addBox(0.0F, -3.5F, -3.5F, 0, 7, 7, 0.01F, false);

				this.body = this.chest;
				this.body.addChild(this.chestBack);
				this.body.addChild(this.chestPecRight);
				this.body.addChild(this.chestPecLeft);
				this.body.addChild(this.chestCore);
				this.chestCore.addChild(this.chestEffectFrontA);
				this.chestCore.addChild(this.chestEffectFrontB);
				this.body.addChild(this.abs);
				this.body.addChild(this.chestEffectBig);
				this.chestEffectBig.addChild(this.chestEffectBigRotated);
				this.chest.addChild(this.chestEffectMiddle);
				this.chest.addChild(this.chestEffectMiddleRotated);
				this.chest.addChild(this.chestEffectSmall);
				this.chest.addChild(this.chestEffectSmallRotated);

				this.leftArm = this.armLeft;
				this.leftArm.addChild(this.armLeftShoulderA);
				this.leftArm.addChild(this.armLeftShoulderB);
				this.leftArm.addChild(this.armLeftGlove);
				this.leftArm.addChild(this.armLeftElbow);
				this.armLeftElbow.addChild(this.armLeftWing);
				this.armLeft.addChild(this.armLeftEffectRotated);
				this.armLeft.addChild(this.armLeftEffect);

				this.rightArm = this.armRight;
				this.rightArm.addChild(this.armRightShoulderA);
				this.rightArm.addChild(this.armRightShoulderB);
				this.rightArm.addChild(this.armRightGlove);
				this.rightArm.addChild(this.armRightElbow);
				this.armRightElbow.addChild(this.armRightWing);
				this.armRight.addChild(this.armRightEffect);
				this.armRight.addChild(this.armRightEffectRotated);
				break;

			case LEGS:
				this.chest = new ModelRenderer(this);
				this.chest.setPos(0.0F, 0.0F, 0.0F);
				this.chest.texOffs(0, 23).addBox(-1.0F, 9.5F, -3.3F, 2, 2, 1, 0.0F, false);
				this.hipsLeft = new ModelRenderer(this);
				this.hipsLeft.setPos(0.0F, 10.0F, 0.0F);
				this.hipsLeft.texOffs(28, 54).addBox(0.0F, 0.0F, -3.0F, 4, 3, 6, 0.0F, false);
				setRotateAngle(this.hipsLeft, 0.0F, 0.0F, -0.2618F);
				this.hipsRight = new ModelRenderer(this);
				this.hipsRight.setPos(0.0F, 10.0F, 0.0F);
				this.hipsRight.texOffs(28, 54).addBox(-4.0F, 0.0F, -3.0F, 4, 3, 6, 0.0F, true);
				setRotateAngle(this.hipsRight, 0.0F, 0.0F, 0.2618F);
				this.armorLegLeft = new ModelRenderer(this);
				this.armorLegLeft.setPos(1.9F, 12.0F, 0.0F);
				this.armorLegLeft.texOffs(28, 33).addBox(-1.5F, -0.3F, -2.5F, 4, 5, 5, 0.0F, false);
				this.armorLegLeft.texOffs(63, 51).addBox(-1.2598F, -0.1375F, -3.1F, 4, 3, 6, -0.05F, false);
				this.armorLegRight = new ModelRenderer(this);
				this.armorLegRight.setPos(-1.9F, 12.0F, 0.0F);
				this.armorLegRight.texOffs(28, 33).addBox(-2.5F, -0.3F, -2.5F, 4, 5, 5, 0.0F, true);
				this.armorLegRight.texOffs(63, 51).addBox(-2.7402F, -0.1375F, -3.1F, 4, 3, 6, -0.05F, true);

				this.body = this.chest;
				this.body.addChild(this.hipsLeft);
				this.body.addChild(this.hipsRight);

				this.leftLeg = this.armorLegLeft;

				this.rightLeg = this.armorLegRight;
				break;

			case FEET:
				this.armorLegLeft = new ModelRenderer(this);
				this.armorLegLeft.setPos(1.9F, 12.0F, 0.0F);
				this.armorLegLeft.texOffs(66, 31).addBox(-2.5F, -0.7F, -2.5F, 5, 13, 5, -0.2F, false);
				this.armorLegLeft.texOffs(48, 57).addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, 0.0F, false);
				this.armorLegLeft.texOffs(25, 43).addBox(-2.5F, 5.3F, -2.6F, 5, 2, 3, 0.0F, false);
				this.armorLegLeft.texOffs(0, 6).addBox(2.0F, 5.8F, -1.1F, 1, 1, 1, 0.0F, false);
				this.legLeftWing = new ModelRenderer(this);
				this.legLeftWing.setPos(2.501F, 5.3F, -1.6F);
				this.legLeftWing.texOffs(0, 55).addBox(0.0F, -1.0F, 0.0F, 0, 3, 5, 0.0F, false);
				setRotateAngle(this.legLeftWing, -0.3F, 0.3054F, 0.0F);
				this.armorLegRight = new ModelRenderer(this);
				this.armorLegRight.setPos(-1.9F, 12.0F, 0.0F);
				this.armorLegRight.texOffs(66, 31).addBox(-2.5F, -0.7F, -2.5F, 5, 13, 5, -0.2F, true);
				this.armorLegRight.texOffs(48, 57).addBox(-2.5F, 10.3F, -2.5F, 5, 2, 5, 0.0F, true);
				this.armorLegRight.texOffs(25, 43).addBox(-2.5F, 5.3F, -2.6F, 5, 2, 3, 0.0F, true);
				this.armorLegRight.texOffs(0, 6).addBox(-3.0F, 5.8F, -1.1F, 1, 1, 1, 0.0F, true);
				this.legRightWing = new ModelRenderer(this);
				this.legRightWing.setPos(-2.501F, 5.3F, -1.6F);
				this.legRightWing.texOffs(0, 55).addBox(0.0F, -1.0F, 0.0F, 0, 3, 5, 0.0F, true);
				setRotateAngle(this.legRightWing, -0.3F, -0.3054F, 0.0F);

				this.leftLeg = this.armorLegLeft;
				this.leftLeg.addChild(this.legLeftWing);

				this.rightLeg = this.armorLegRight;
				this.rightLeg.addChild(this.legRightWing);
				break;

			default:
				break;
		}
		this.setAllVisible(false);
		switch (this.slot) {
			case HEAD:
				this.head.visible = true;
				break;

			case CHEST:
				this.body.visible = true;
				this.leftArm.visible = true;
				this.rightArm.visible = true;
				break;

			case LEGS:
				this.body.visible = true;
				this.leftLeg.visible = true;
				this.rightLeg.visible = true;
				break;

			case FEET:
				this.leftLeg.visible = true;
				this.rightLeg.visible = true;
				break;

			default:
				break;
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		super.renderToBuffer(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		switch (this.slot) {
			case HEAD:
				this.headWingLeft.xRot = 0.3491F + 0.15F * sinPI(ageInTicks / 40.0F);
				this.headWingRight.xRot = 0.3491F + 0.15F * sinPI(ageInTicks / 40.0F);
				break;

			case CHEST:
				float dA = ageInTicks / 40.0F;
				float rotation = dA % (2.0F * (float)Math.PI);
				float rotationD = (dA + (float)Math.PI / 4.0F) % (2.0F * (float)Math.PI);
				this.chestEffectFrontA.zRot = rotation;
				this.chestEffectFrontB.zRot = rotationD;
				this.chestEffectMiddle.zRot = rotation;
				this.chestEffectMiddleRotated.zRot = rotationD;
				this.armLeftEffect.xRot = -rotation;
				this.armLeftEffectRotated.xRot = -rotationD;
				this.armRightEffect.xRot = -rotation;
				this.armRightEffectRotated.xRot = -rotationD;
				dA = 0.1F * sinPI(dA);
				this.armLeftWing.xRot = 0.1745F + dA;
				this.armRightWing.xRot = 0.1745F + dA;
				dA = 0.06F * sinPI(ageInTicks / 80.0F);
				this.chestEffectBig.y = dA;
				this.chestEffectMiddle.y = dA;
				this.chestEffectMiddleRotated.y = dA;
				this.chestEffectSmall.y = dA;
				this.chestEffectSmallRotated.y = dA;
				dA = ageInTicks / 20.0F;
				rotation = dA % (2.0F * (float)Math.PI);
				rotationD = (dA + (float)Math.PI / 4.0F) % (2.0F * (float)Math.PI);
				this.chestEffectSmall.zRot = -rotation;
				this.chestEffectSmallRotated.zRot = -rotationD;
				break;

			case FEET:
				this.legLeftWing.xRot = -0.1745F + 0.1F * sinPI(ageInTicks / 40.0F);
				this.legRightWing.xRot = -0.1745F + 0.1F * sinPI(ageInTicks / 40.0F);
				break;

			default:
				break;
		}
	}
}