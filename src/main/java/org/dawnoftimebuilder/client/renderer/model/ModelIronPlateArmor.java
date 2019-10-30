package org.dawnoftimebuilder.client.renderer.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIronPlateArmor extends ModelBiped {

	private final EntityEquipmentSlot slot;

	//Helmet
	public ModelRenderer baseHelmet;
	public ModelRenderer earMiddle;
	public ModelRenderer leftEarBottom;
	public ModelRenderer leftHead;
	public ModelRenderer middleBottom;
	public ModelRenderer middleCraneC;
	public ModelRenderer middleFeatherA;
	public ModelRenderer middleFeatherB;
	public ModelRenderer middleHeadA;
	public ModelRenderer middleHeadB;
	public ModelRenderer rightHead;
	public ModelRenderer rightEarBottom;
	public ModelRenderer topHead;

	//Chest
	public ModelRenderer backRightBody;
	public ModelRenderer backLeftBody;
	public ModelRenderer bodyBase;
	public ModelRenderer leftHand;
	public ModelRenderer leftShoulderA;
	public ModelRenderer leftShoulderB;
	public ModelRenderer leftShoulderC;
	public ModelRenderer leftTopBody;
	public ModelRenderer middleTopBody;
	public ModelRenderer miscA;
	public ModelRenderer rightHand;
	public ModelRenderer rightShoulderA;
	public ModelRenderer rightShoulderB;
	public ModelRenderer rightShoulderC;
	public ModelRenderer rightTopBody;

	//Leggings
	public ModelRenderer beltA;
	public ModelRenderer beltB;
	public ModelRenderer leftLegging;
	public ModelRenderer miscB;
	public ModelRenderer rightLegging;

	//Boots
	public ModelRenderer leftLeg;
	public ModelRenderer rightLeg;

    public ModelIronPlateArmor(float scale, EntityEquipmentSlot slot) {
    	super(scale, 0, 128, 64);
    	
    	this.slot = slot;
    	
	    switch (slot) {
	        case HEAD:
				this.baseHelmet = new ModelRenderer(this, 62, 11);
				this.baseHelmet.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.baseHelmet.addBox(-4.5F, -8.2F, -4.5F, 9, 9, 9, 0.1F);
				this.earMiddle = new ModelRenderer(this, 57, 49);
				this.earMiddle.setRotationPoint(0.0F, -4.0F, 0.4F);
				this.earMiddle.addBox(-5.5F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
				this.setRotateAngle(earMiddle, -0.31869712141416456F, 0.0F, 0.0F);
				this.leftEarBottom = new ModelRenderer(this, 0, 34);
				this.leftEarBottom.mirror = true;
				this.leftEarBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftEarBottom.addBox(4.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.leftHead = new ModelRenderer(this, 52, 31);
				this.leftHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				this.setRotateAngle(leftHead, 0.0F, 0.0F, 0.27314402793711257F);
				this.middleBottom = new ModelRenderer(this, 0, 30);
				this.middleBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleBottom.addBox(-4.0F, -7.5F, -1.5F, 8, 1, 3, 0.0F);
				this.setRotateAngle(middleBottom, 1.5707963267948966F, 0.0F, 0.0F);
				this.middleCraneC = new ModelRenderer(this, 45, 28);
				this.middleCraneC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleCraneC.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				this.setRotateAngle(middleCraneC, -0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadA = new ModelRenderer(this, 45, 28);
				this.middleHeadA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleHeadA.addBox(-1.0F, -10.6F, -2.0F, 2, 2, 4, 0.0F);
				this.setRotateAngle(middleHeadA, 0.41887902047863906F, 0.0F, 0.0F);
				this.middleHeadB = new ModelRenderer(this, 44, 19);
				this.middleHeadB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.middleHeadB.addBox(-1.0F, -10.5F, -2.5F, 2, 2, 5, 0.0F);
				this.middleFeatherA = new ModelRenderer(this, 89, -8);
				this.middleFeatherA.setRotationPoint(0.0F, -8.0F, 5.0F);
				this.middleFeatherA.addBox(-2.0F, -4.5F, -1.5F, 4, 9, 9, -2.0F);
				this.setRotateAngle(middleFeatherA, -0.19052597169560997F, 0.0F, 0.0F);
				this.middleFeatherB = new ModelRenderer(this, 89, 2);
				this.middleFeatherB.setRotationPoint(0.0F, 0.0F, 4.5F);
				this.middleFeatherB.addBox(-2.0F, -4.5F, -2.0F, 4, 9, 9, -2.0F);
				this.setRotateAngle(middleFeatherB, -0.1187509959883641F, 0.0F, 0.0F);
				this.rightEarBottom = new ModelRenderer(this, 0, 34);
				this.rightEarBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightEarBottom.addBox(-5.0F, -1.5F, -7.5F, 1, 3, 9, 0.0F);
				this.rightHead = new ModelRenderer(this, 52, 31);
				this.rightHead.mirror = true;
				this.rightHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightHead.addBox(-2.0F, -9.1F, -4.5F, 4, 1, 9, 0.0F);
				this.setRotateAngle(rightHead, 0.0F, 0.0F, -0.27314402793711257F);
				this.topHead = new ModelRenderer(this, 28, 5);
				this.topHead.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.topHead.addBox(-5.0F, -8.2F, -5.0F, 10, 3, 10, -0.3F);

				this.bipedHead = baseHelmet;
				this.bipedHead.addChild(this.earMiddle);
				this.earMiddle.addChild(this.leftEarBottom);
				this.earMiddle.addChild(this.middleBottom);
				this.earMiddle.addChild(this.rightEarBottom);
				this.bipedHead.addChild(this.leftHead);
				this.bipedHead.addChild(this.middleCraneC);
				this.bipedHead.addChild(this.middleFeatherA);
				this.middleFeatherA.addChild(this.middleFeatherB);
				this.bipedHead.addChild(this.middleHeadA);
				this.bipedHead.addChild(this.middleHeadB);
				this.bipedHead.addChild(this.rightHead);
				this.bipedHead.addChild(this.topHead);
	        	break;
	        	
	        case CHEST:
				this.backLeftBody = new ModelRenderer(this, 32, 27);
				this.backLeftBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.backLeftBody.addBox(-1.45F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				this.setRotateAngle(backLeftBody, 0.0F, 0.31869712141416456F, 0.0F);
				this.backRightBody = new ModelRenderer(this, 32, 27);
				this.backRightBody.mirror = true;
				this.backRightBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.backRightBody.addBox(-3.55F, 1.0F, 3.4F, 5, 8, 1, 0.0F);
				this.setRotateAngle(backRightBody, 0.0F, -0.31869712141416456F, 0.0F);
				this.bodyBase = new ModelRenderer(this, 0, 4);
				this.bodyBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.bodyBase.addBox(-4.5F, -0.1F, -2.5F, 9, 13, 5, 0.0F);
				this.leftHand = new ModelRenderer(this, 112, 48);
				this.leftHand.setRotationPoint(5.0F, 2.0F, 0.0F);
				this.leftHand.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
				this.leftShoulderA = new ModelRenderer(this, 0, 54);
				this.leftShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftShoulderA.addBox(-1.4F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
				this.leftShoulderB = new ModelRenderer(this, 0, 46);
				this.leftShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftShoulderB.addBox(-0.4F, -1.3F, -2.5F, 6, 3, 5, 0.2F);
				this.setRotateAngle(leftShoulderB, 0.0F, 0.0F, 0.39269908169872414F);
				this.leftShoulderC = new ModelRenderer(this, 0, 22);
				this.leftShoulderC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftShoulderC.addBox(-0.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				this.setRotateAngle(leftShoulderC, 0.0F, 0.0F, -0.13962634015954636F);
				this.leftTopBody = new ModelRenderer(this, 33, 18);
				this.leftTopBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftTopBody.addBox(-2.9F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				this.setRotateAngle(leftTopBody, 0.36425021489121656F, -0.4553564018453205F, -0.36425021489121656F);
				this.middleTopBody = new ModelRenderer(this, 54, 57);
				this.middleTopBody.setRotationPoint(-1.5F, 9.2F, -2.4F);
				this.middleTopBody.addBox(0.0F, -7.0F, 0.0F, 3, 7, 0, 0.0F);
				this.setRotateAngle(middleTopBody, 0.3490658503988659F, 0.0F, 0.0F);
				this.miscA = new ModelRenderer(this, 85, 35);
				this.miscA.setRotationPoint(0.0F, 8.6F, 3.3F);
				this.miscA.addBox(-3.5F, 0.2F, 0.0F, 7, 12, 0, 0.0F);
				this.setRotateAngle(miscA, 0.045553093477052F, 0.0F, 0.0F);
				this.rightHand = new ModelRenderer(this, 112, 48);
				this.rightHand.mirror = true;
				this.rightHand.setRotationPoint(-5.0F, 2.0F, 0.0F);
				this.rightHand.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
				this.rightShoulderA = new ModelRenderer(this, 0, 54);
				this.rightShoulderA.mirror = true;
				this.rightShoulderA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightShoulderA.addBox(-5.0F, -3.2F, -2.5F, 6, 5, 5, 0.0F);
				this.rightShoulderB = new ModelRenderer(this, 0, 46);
				this.rightShoulderB.mirror = true;
				this.rightShoulderB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightShoulderB.addBox(-5.8F, -1.4F, -2.5F, 6, 3, 5, 0.2F);
				this.setRotateAngle(rightShoulderB, 0.0F, 0.0F, -0.39269908169872414F);
				this.rightShoulderC = new ModelRenderer(this, 0, 22);
				this.rightShoulderC.mirror = true;
				this.rightShoulderC.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightShoulderC.addBox(-1.5F, -1.3F, -3.0F, 2, 2, 6, 0.0F);
				this.setRotateAngle(rightShoulderC, 0.0F, 0.0F, 0.13962634015954636F);
				this.rightTopBody = new ModelRenderer(this, 33, 18);
				this.rightTopBody.mirror = true;
				this.rightTopBody.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightTopBody.addBox(-1.1F, 0.1F, -5.3F, 4, 8, 1, 0.0F);
				this.setRotateAngle(rightTopBody, 0.36425021489121656F, 0.4553564018453205F, 0.36425021489121656F);

				this.bipedBody = bodyBase;
				this.bipedBody.addChild(this.backLeftBody);
				this.bipedBody.addChild(this.backRightBody);
				this.bipedBody.addChild(this.leftTopBody);
				this.bipedBody.addChild(this.middleTopBody);
				this.bipedBody.addChild(this.miscA);
				this.bipedBody.addChild(this.rightTopBody);

				this.bipedLeftArm = leftHand;
				this.bipedLeftArm.addChild(this.leftShoulderA);
				this.leftShoulderA.addChild(this.leftShoulderB);
				this.leftShoulderA.addChild(this.leftShoulderC);

				this.bipedRightArm = rightHand;
				this.bipedRightArm.addChild(this.rightShoulderA);
				this.rightShoulderA.addChild(this.rightShoulderB);
				this.rightShoulderA.addChild(this.rightShoulderC);
	        	break;
	        	
	        case LEGS:
				this.beltA = new ModelRenderer(this, 84, 57);
				this.beltA.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.beltA.addBox(-4.51F, 10.0F, -2.4F, 9, 2, 5, 0.3F);
				this.beltB = new ModelRenderer(this, 60, 54);
				this.beltB.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.beltB.addBox(-3.0F, 8.9F, -3.7F, 6, 4, 6, -0.8F);
				this.bodyBase = new ModelRenderer(this, 0, 0);
				this.bodyBase.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftLeg = new ModelRenderer(this, 0, 0);
				this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.leftLegging = new ModelRenderer(this, 32, 36);
				this.leftLegging.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.leftLegging.addBox(-0.3F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				this.setRotateAngle(leftLegging, 0.0F, 0.0F, -0.136659280431156F);
				this.miscB = new ModelRenderer(this, 50, 44);
				this.miscB.setRotationPoint(0.0F, 12.1F, -3.0F);
				this.miscB.addBox(-1.5F, -0.1F, 0.2F, 3, 8, 0, 0.0F);
				this.setRotateAngle(miscB, -0.045553093477052F, 0.0F, 0.0F);
				this.rightLeg = new ModelRenderer(this, 0, 0);
				this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.rightLegging = new ModelRenderer(this, 32, 36);
				this.rightLegging.mirror = true;
				this.rightLegging.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.rightLegging.addBox(-2.7F, -0.2F, -3.0F, 3, 6, 6, 0.1F);
				this.setRotateAngle(rightLegging, 0.0F, 0.0F, 0.136659280431156F);

				this.bipedBody = bodyBase;
				this.bipedBody.addChild(this.miscB);
				this.bipedBody.addChild(this.beltA);
				this.bipedBody.addChild(this.beltB);

	            this.bipedLeftLeg = leftLeg;
				this.bipedLeftLeg.addChild(this.leftLegging);

				this.bipedRightLeg = rightLeg;
				this.bipedRightLeg.addChild(this.rightLegging);
	        	break;
	        	
	        case FEET:
				this.rightLeg = new ModelRenderer(this, 32, 48);
				this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.leftLeg = new ModelRenderer(this, 32, 48);
				this.leftLeg.mirror = true;
				this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

	            this.bipedLeftLeg = leftLeg;

	            this.bipedRightLeg = rightLeg;
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
		if (entityIn instanceof EntityLivingBase){
			if(((EntityLivingBase)entityIn).getTicksElytraFlying() > 4) {
				f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
				f = f / 0.2F;
				f = f * f * f;
				if (f < 1.0F) f = 1.0F;
			}
		}

		f = MathHelper.abs(MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f);

        switch (this.slot) {
			case HEAD:
				this.middleFeatherA.rotateAngleX = -0.15F - f * 0.1F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);
				this.middleFeatherB.rotateAngleX = -0.15F - f * 0.1F + 0.1F * sinPI((ageInTicks - 15)/ 60.0F + 1.0F);
				break;

			case CHEST:
				this.miscA.rotateAngleX = 0.05F + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F);

				if (this.isRiding) this.miscA.rotateAngleX += 1.0F;

				if (this.isSneak) this.miscA.rotateAngleX += -0.18F;
				else this.miscA.rotateAngleX += f;

				break;

			case LEGS:
				this.miscB.rotateAngleX = -(f + 0.05F * sinPI(ageInTicks / 60.0F + 1.0F));

				if (this.isRiding) this.miscB.rotateAngleX += -1.0F;

				if (this.isSneak) {
					this.beltA.rotationPointY = -3.0F;
					this.beltB.rotationPointY = -3.0F;
					this.miscB.rotationPointY = 8.5F;
					this.miscB.rotateAngleX += -0.6F;
				}else{
					this.beltA.rotationPointY = 0.0F;
					this.beltB.rotationPointY = 0.0F;
					this.miscB.rotationPointY = 12.1F;
				}
				break;

			default:
				break;
		}
    }

	private float sinPI(float f) {
		return MathHelper.sin(f * (float)Math.PI);
	}
    
    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
