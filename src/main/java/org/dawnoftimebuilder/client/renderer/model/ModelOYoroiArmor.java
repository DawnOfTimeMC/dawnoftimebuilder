package org.dawnoftimebuilder.client.renderer.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelOYoroiArmor extends ModelBiped {
	
	private final EntityEquipmentSlot slot;
	
	//Helmet
    public ModelRenderer headLeftSide;
    public ModelRenderer headFront;
    public ModelRenderer headBack;
    public ModelRenderer headRightSide;
    public ModelRenderer helmet;
    public ModelRenderer headLeft;
    public ModelRenderer headRight;
    public ModelRenderer helmetHorn;
    public ModelRenderer helmetJewel;
    
	//Chest
    public ModelRenderer chestSub;
    public ModelRenderer chestProtTop;
    public ModelRenderer chestProtBot;
	public ModelRenderer bodyBreast;
	public ModelRenderer bodyBreastProt;
    public ModelRenderer armLeftSub;
    public ModelRenderer armLeftTop;
    public ModelRenderer armLeftMid;
    public ModelRenderer armLeftBot;
    public ModelRenderer armLeftShoulder;
    public ModelRenderer armRightSub;
    public ModelRenderer armRightBot;
    public ModelRenderer armRightMid;
    public ModelRenderer armRightTop;
    public ModelRenderer armRightShoulder;
    
	//Leggings
    public ModelRenderer thighFront;
    public ModelRenderer thighFrontSub;
    public ModelRenderer thighBack;
    public ModelRenderer thighBackSub;
    public ModelRenderer thighRight;
    public ModelRenderer thighRightSub;
    public ModelRenderer thighLeft;
    public ModelRenderer thighLeftSub;
    
	//Boots
    public ModelRenderer legLeftProt;
    public ModelRenderer legLeftSub;
    public ModelRenderer legRightProt;
    public ModelRenderer legRightSub;
    
    public ModelOYoroiArmor(float scale, EntityEquipmentSlot slot, boolean isSteve) {
    	super(scale, 0, 64, 64);
        
    	this.slot = slot;
    	
        switch (slot) {
            case HEAD:
                this.helmet = new ModelRenderer(this, 28, 21);
                this.helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.helmet.addBox(-4.5F, -8.5F, -4.5F, 9, 4, 9, -0.1F);
                this.headFront = new ModelRenderer(this, 38, 17);
                this.headFront.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headFront.addBox(-4.5F, -7.3F, -2.0F, 9, 1, 3, -0.1F);
                this.setRotateAngle(headFront, 0.6981317007977318F, 0.0F, 0.0F);
                this.helmetHorn = new ModelRenderer(this, 46, 0);
                this.helmetHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.helmetHorn.addBox(2.2F, -6.0F, 2.2F, 6, 0, 6, 0.0F);
                this.setRotateAngle(helmetHorn, 1.4311699866353502F, -0.13962634015954636F, -0.7853981633974483F);
                this.helmetJewel = new ModelRenderer(this, 52, 6);
                this.helmetJewel.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.helmetJewel.addBox(-1.0F, -5.0F, -6.8F, 2, 2, 1, 0.0F);
                this.setRotateAngle(helmetJewel, -0.2617993877991494F, 0.0F, 0.0F);
                this.headRight = new ModelRenderer(this, 11, 20);
                this.headRight.mirror = true;
                this.headRight.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headRight.addBox(0.0F, -6.5F, -4.5F, 4, 1, 9, -0.1F);
                this.setRotateAngle(headRight, 0.0F, 0.0F, 0.6981317007977318F);
                this.headRightSide = new ModelRenderer(this, 52, 9);
                this.headRightSide.mirror = true;
                this.headRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headRightSide.addBox(0.0F, -8.3F, -4.5F, 3, 2, 1, -0.1F);
                this.setRotateAngle(headRightSide, 0.0F, 0.0F, 0.6981317007977318F);
                this.headLeft = new ModelRenderer(this, 11, 20);
                this.headLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headLeft.addBox(-4.0F, -6.5F, -4.5F, 4, 1, 9, -0.1F);
                this.setRotateAngle(headLeft, 0.0F, 0.0F, -0.6981317007977318F);
                this.headLeftSide = new ModelRenderer(this, 52, 9);
                this.headLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headLeftSide.addBox(-3.0F, -8.3F, -4.5F, 3, 2, 1, -0.1F);
                this.setRotateAngle(headLeftSide, 0.0F, 0.0F, -0.6981317007977318F);
                this.headBack = new ModelRenderer(this, 36, 12);
                this.headBack.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headBack.addBox(-4.5F, -6.5F, 0.0F, 9, 1, 4, -0.1F);
                this.setRotateAngle(headBack, -0.6981317007977318F, 0.0F, 0.0F);
                
                this.bipedHead = helmet;
                this.bipedHead.addChild(headLeftSide);
                this.bipedHead.addChild(headFront);
                this.bipedHead.addChild(headBack);
                this.bipedHead.addChild(headRightSide);
                this.bipedHead.addChild(headLeft);
                this.bipedHead.addChild(headRight);
                this.bipedHead.addChild(helmetHorn);
                this.bipedHead.addChild(helmetJewel);
            	break;
            	
            case CHEST:
                this.chestSub = new ModelRenderer(this, 0, 0);
                this.chestSub.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.chestSub.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.3F);
                this.chestProtBot = new ModelRenderer(this, 24, 0);
                this.chestProtBot.setRotationPoint(0.0F, 0.0F, -0.5F);
                this.chestProtBot.addBox(-4.5F, 4.0F, -2.0F, 9, 7, 5, 0.1F);
				if(isSteve){
					this.chestProtTop = new ModelRenderer(this, 19, 12);
					this.chestProtTop.setRotationPoint(0.0F, 0.0F, -0.5F);
					this.chestProtTop.addBox(-3.0F, 1.0F, -2.0F, 6, 2, 5, 0.1F);

					this.armRightSub = new ModelRenderer(this, 0, 30);
					this.armRightSub.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRightSub.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armRightBot = new ModelRenderer(this, 38, 34);
					this.armRightBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightBot.addBox(-3.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armRightMid = new ModelRenderer(this, 27, 36);
					this.armRightMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightMid.addBox(-3.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armRightTop = new ModelRenderer(this, 16, 30);
					this.armRightTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightTop.addBox(-3.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armRightShoulder = new ModelRenderer(this, 46, 35);
					this.armRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulder.addBox(-4.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					this.setRotateAngle(armRightShoulder, 0.0F, 0.0F, 0.3490658503988659F);

					this.armLeftSub = new ModelRenderer(this, 0, 30);
					this.armLeftSub.mirror = true;
					this.armLeftSub.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armLeftSub.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armLeftBot = new ModelRenderer(this, 38, 34);
					this.armLeftBot.mirror = true;
					this.armLeftBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftBot.addBox(1.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armLeftMid = new ModelRenderer(this, 27, 36);
					this.armLeftMid.mirror = true;
					this.armLeftMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftMid.addBox(0.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armLeftTop = new ModelRenderer(this, 16, 30);
					this.armLeftTop.mirror = true;
					this.armLeftTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftTop.addBox(0.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armLeftShoulder = new ModelRenderer(this, 46, 35);
					this.armLeftShoulder.mirror = true;
					this.armLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulder.addBox(3.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					this.setRotateAngle(armLeftShoulder, 0.0F, 0.0F, -0.3490658503988659F);
				}else{
					this.chestProtTop = new ModelRenderer(this, 24, 12);
					this.chestProtTop.setRotationPoint(0.0F, 0.0F, -0.5F);
					this.chestProtTop.addBox(-3.0F, 1.0F, 2.0F, 6, 2, 1, 0.1F);
					this.bodyBreast = new ModelRenderer(this, 27, 47);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, -0.1F);
					this.setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.bodyBreastProt = new ModelRenderer(this, 24, 15);
					this.bodyBreastProt.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreastProt.addBox(-3.0F, 0.8F, -0.1F, 6, 2, 1, 0.1F);
					this.setRotateAngle(bodyBreastProt, -0.5759586531581287F, 0.0F, 0.0F);

					this.armRightSub = new ModelRenderer(this, 0, 30);
					this.armRightSub.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRightSub.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armRightBot = new ModelRenderer(this, 38, 34);
					this.armRightBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightBot.addBox(-2.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armRightMid = new ModelRenderer(this, 27, 36);
					this.armRightMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightMid.addBox(-2.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armRightTop = new ModelRenderer(this, 16, 30);
					this.armRightTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightTop.addBox(-2.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armRightShoulder = new ModelRenderer(this, 46, 35);
					this.armRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRightShoulder.addBox(-3.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					this.setRotateAngle(armRightShoulder, 0.0F, 0.0F, 0.2617993877991494F);

					this.armLeftSub = new ModelRenderer(this, 0, 30);
					this.armLeftSub.mirror = true;
					this.armLeftSub.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armLeftSub.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armLeftBot = new ModelRenderer(this, 38, 34);
					this.armLeftBot.mirror = true;
					this.armLeftBot.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftBot.addBox(0.5F, 8.5F, -2.5F, 2, 2, 5, 0.2F);
					this.armLeftMid = new ModelRenderer(this, 27, 36);
					this.armLeftMid.mirror = true;
					this.armLeftMid.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftMid.addBox(-0.5F, 4.5F, -2.5F, 3, 3, 5, 0.1F);
					this.armLeftTop = new ModelRenderer(this, 16, 30);
					this.armLeftTop.mirror = true;
					this.armLeftTop.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftTop.addBox(-0.5F, -2.5F, -2.5F, 3, 6, 5, 0.2F);
					this.armLeftShoulder = new ModelRenderer(this, 46, 35);
					this.armLeftShoulder.mirror = true;
					this.armLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armLeftShoulder.addBox(2.5F, -5.5F, -3.0F, 1, 8, 6, 0.0F);
					this.setRotateAngle(armLeftShoulder, 0.0F, 0.0F, -0.2617993877991494F);
				}

                this.bipedBody = chestSub;
                this.bipedBody.addChild(chestProtTop);
                this.bipedBody.addChild(chestProtBot);
                if(!isSteve){
                	this.bipedBody.addChild(bodyBreast);
					this.bipedBody.addChild(bodyBreastProt);
				}

                this.bipedLeftArm = armLeftSub;
                this.bipedLeftArm.addChild(armLeftTop);
                this.bipedLeftArm.addChild(armLeftMid);
                this.bipedLeftArm.addChild(armLeftBot);
                this.bipedLeftArm.addChild(armLeftShoulder);

                this.bipedRightArm = armRightSub;
                this.bipedRightArm.addChild(armRightBot);
                this.bipedRightArm.addChild(armRightMid);
                this.bipedRightArm.addChild(armRightTop);
                this.bipedRightArm.addChild(armRightShoulder);
            	break;
            	
            case LEGS:
                this.thighFrontSub = new ModelRenderer(this, 4, 21);
                this.thighFrontSub.setRotationPoint(0.0F, 11.0F, -2.5F);
                this.thighFrontSub.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 0, 0.0F);
                this.thighFront = new ModelRenderer(this, 0, 16);
                this.thighFront.setRotationPoint(0.0F, 11.0F, -2.5F);
                this.thighFront.addBox(-4.0F, 1.0F, -0.5F, 8, 4, 1, 0.0F);
                this.thighBackSub = new ModelRenderer(this, 4, 21);
                this.thighBackSub.setRotationPoint(0.0F, 11.0F, 2.5F);
                this.thighBackSub.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 0, 0.0F);
                this.thighBack = new ModelRenderer(this, 0, 16);
                this.thighBack.setRotationPoint(0.0F, 11.0F, 2.5F);
                this.thighBack.addBox(-4.0F, 1.0F, -0.5F, 8, 4, 1, 0.0F);
                this.thighRightSub = new ModelRenderer(this, 12, 18);
                this.thighRightSub.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.thighRightSub.addBox(-4.5F, 11.0F, -2.0F, 0, 1, 4, 0.0F);
                this.thighRight = new ModelRenderer(this, 0, 22);
                this.thighRight.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.thighRight.addBox(-5.0F, 12.0F, -2.0F, 1, 4, 4, 0.0F);
                this.thighLeftSub = new ModelRenderer(this, 12, 18);
                this.thighLeftSub.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.thighLeftSub.addBox(4.5F, 11.0F, -2.0F, 0, 1, 4, 0.0F);
                this.thighLeft = new ModelRenderer(this, 0, 22);
                this.thighLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.thighLeft.addBox(4.0F, 12.0F, -2.0F, 1, 4, 4, 0.0F);
                
                this.bipedBody = thighRight;
                this.bipedBody.addChild(thighRightSub);
                this.bipedBody.addChild(thighLeft);
                this.bipedBody.addChild(thighLeftSub);
                this.bipedBody.addChild(thighFront);
                this.bipedBody.addChild(thighFrontSub);
                this.bipedBody.addChild(thighBack);
                this.bipedBody.addChild(thighBackSub);
            	break;
            	
            case FEET:
                this.legRightSub = new ModelRenderer(this, 0, 46);
                this.legRightSub.setRotationPoint(-1.9F, 12.0F, 0.0F);
                this.legRightSub.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
                this.legRightProt = new ModelRenderer(this, 12, 42);
                this.legRightProt.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.legRightProt.addBox(-2.5F, 5.5F, -2.5F, 5, 4, 4, 0.1F);
                this.legLeftSub = new ModelRenderer(this, 0, 46);
                this.legLeftSub.mirror = true;
                this.legLeftSub.setRotationPoint(1.9F, 12.0F, 0.0F);
                this.legLeftSub.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
                this.legLeftProt = new ModelRenderer(this, 12, 42);
                this.legLeftProt.mirror = true;
                this.legLeftProt.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.legLeftProt.addBox(-2.5F, 5.5F, -2.5F, 5, 4, 4, 0.1F);
                
                this.bipedLeftLeg = legLeftSub;
                this.bipedLeftLeg.addChild(legLeftProt);
                
                this.bipedRightLeg = legRightSub;
                this.bipedRightLeg.addChild(legRightProt);
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

		if (this.slot == EntityEquipmentSlot.LEGS) {
			float f = 1.0F;
			if (entityIn instanceof EntityLivingBase) {
				if (((EntityLivingBase) entityIn).getTicksElytraFlying() > 4) {
					f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
					f = f / 0.2F;
					f = f * f * f;
					if (f < 1.0F) f = 1.0F;
				}
			}

			f = MathHelper.abs(MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f);
			this.thighBack.rotateAngleX = f;
			this.thighBackSub.rotateAngleX = f;
			this.thighFront.rotateAngleX = -f;
			this.thighFrontSub.rotateAngleX = -f;

			if (this.isRiding) {
				this.thighBack.rotateAngleX += 1.0F;
				this.thighBackSub.rotateAngleX += 1.0F;
				this.thighFront.rotateAngleX += -1.0F;
				this.thighFrontSub.rotateAngleX += -1.0F;
			}

			if (this.isSneak) {
				this.thighBack.rotateAngleX += -0.5F;
				this.thighBackSub.rotateAngleX += -0.5F;
				this.thighFront.rotateAngleX += -0.5F;
				this.thighFrontSub.rotateAngleX += -0.5F;
				this.thighFront.rotationPointY = 8.0F;
				this.thighFrontSub.rotationPointY = 8.0F;
			} else {
				this.thighFront.rotationPointY = 11.0F;
				this.thighFrontSub.rotationPointY = 11.0F;
			}
		}

		//Fix the "breathing" and wrong head rotation on ArmorStands
		if (entityIn instanceof EntityArmorStand) {
			EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
			this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
			this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
			this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
			this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
			copyModelAngles(this.bipedHead, this.bipedHeadwear);
		}
    }
    
    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
