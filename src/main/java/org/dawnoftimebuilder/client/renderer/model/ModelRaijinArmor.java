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
public class ModelRaijinArmor extends ModelBiped {
	
	private final EntityEquipmentSlot slot;
	
	//Helmet
    public ModelRenderer head;
    public ModelRenderer headHornLeftA;
    public ModelRenderer headHornLeftB;
    public ModelRenderer headHornLeftC;
    public ModelRenderer headHornLeftD;
    public ModelRenderer headHornRightA;
    public ModelRenderer headHornRightB;
    public ModelRenderer headHornRightC;
    public ModelRenderer headHornRightD;
	
    //Set
    public ModelRenderer body;
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

    public ModelRaijinArmor(float scale, EntityEquipmentSlot slot, boolean isSteve) {
    	super(scale, 0, 64, 64);
    	
    	this.slot = slot;
    	
	    switch (slot) {
	        case HEAD:
	            this.head = new ModelRenderer(this, 0, 0);
	            this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornLeftA = new ModelRenderer(this, 0, 0);
	            this.headHornLeftA.setRotationPoint(2.5F, -6.5F, -5.0F);
	            this.headHornLeftA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
	            this.setRotateAngle(headHornLeftA, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
	            this.headHornLeftB = new ModelRenderer(this, 15, 0);
	            this.headHornLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornLeftB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
	            this.setRotateAngle(headHornLeftB, -1.2217304763960306F, 0.0F, 0.0F);
	            this.headHornLeftC = new ModelRenderer(this, 30, 0);
	            this.headHornLeftC.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornLeftC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
	            this.setRotateAngle(headHornLeftC, 1.0471975511965976F, 0.0F, 0.0F);
	            this.headHornLeftD = new ModelRenderer(this, 0, 0);
	            this.headHornLeftD.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornLeftD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
	            this.setRotateAngle(headHornLeftD, -0.5235987755982988F, 0.0F, 0.0F);
	            this.headHornRightA = new ModelRenderer(this, 0, 0);
	            this.headHornRightA.setRotationPoint(-2.5F, -6.5F, -5.0F);
	            this.headHornRightA.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.2F);
	            this.setRotateAngle(headHornRightA, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
	            this.headHornRightB = new ModelRenderer(this, 15, 0);
	            this.headHornRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornRightB.addBox(-0.5F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
	            this.setRotateAngle(headHornRightB, -1.2217304763960306F, 0.0F, 0.0F);
	            this.headHornRightC = new ModelRenderer(this, 30, 0);
	            this.headHornRightC.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornRightC.addBox(-0.5F, -3.0F, 2.0F, 1, 2, 1, -0.1F);
	            this.setRotateAngle(headHornRightC, 1.0471975511965976F, 0.0F, 0.0F);
	            this.headHornRightD = new ModelRenderer(this, 0, 0);
	            this.headHornRightD.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.headHornRightD.addBox(-0.5F, -8.0F, 0.8F, 1, 3, 1, -0.1F);
	            this.setRotateAngle(headHornRightD, -0.5235987755982988F, 0.0F, 0.0F);
	            
	            this.bipedHead = head;
	            this.bipedHead.addChild(headHornLeftA);
	            this.headHornLeftA.addChild(this.headHornLeftB);
	            this.headHornLeftA.addChild(this.headHornLeftC);
	            this.headHornLeftA.addChild(this.headHornLeftD);
	            this.bipedHead.addChild(headHornRightA);
	            this.headHornRightA.addChild(this.headHornRightB);
	            this.headHornRightA.addChild(this.headHornRightC);
	            this.headHornRightA.addChild(this.headHornRightD);
	            
	            this.body = new ModelRenderer(this, 0, 0);
	            this.body.setRotationPoint(0.0F, 0.0F, 0.0F);

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
					this.armRingLeftA = new ModelRenderer(this, 0, 0);
					this.armRingLeftA.setRotationPoint(5.0F, 2.0F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingLeftB = new ModelRenderer(this, 15, 1);
					this.armRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightA = new ModelRenderer(this, 30, 0);
					this.armRingRightA.setRotationPoint(-5.0F, 2.0F, 0.0F);
					this.armRingRightA.addBox(-3.5F, 7.0F, -2.5F, 5, 1, 5, 0.0F);
					this.armRingRightB = new ModelRenderer(this, 0, 0);
					this.armRingRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-3.5F, 5.5F, -2.5F, 5, 1, 5, 0.0F);
					this.chestNecklace = new ModelRenderer(this, 0, 7);
					this.chestNecklace.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.3F);
				}else{
					this.armRingLeftA = new ModelRenderer(this, 0, 33);
					this.armRingLeftA.setRotationPoint(5.0F, 2.5F, 0.0F);
					this.armRingLeftA.addBox(-1.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingLeftB = new ModelRenderer(this, 13, 34);
					this.armRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingLeftB.addBox(-1.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightA = new ModelRenderer(this, 21, 28);
					this.armRingRightA.setRotationPoint(-5.0F, 2.5F, 0.0F);
					this.armRingRightA.addBox(-2.5F, 7.0F, -2.5F, 4, 1, 5, 0.0F);
					this.armRingRightB = new ModelRenderer(this, 0, 33);
					this.armRingRightB.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.armRingRightB.addBox(-2.5F, 5.5F, -2.5F, 4, 1, 5, 0.0F);
					this.bodyBreast = new ModelRenderer(this, 26, 34);
					this.bodyBreast.setRotationPoint(0.0F, 0.9F, -2.1F);
					this.bodyBreast.addBox(-3.5F, 0.0F, -3.65F, 7, 2, 3, 0.2F);
					this.setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
					this.chestNecklace = new ModelRenderer(this, 0, 7);
					this.chestNecklace.setRotationPoint(0.0F, 0.0F, 0.0F);
					this.chestNecklace.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.2F);
					this.bodyBreastNecklace = new ModelRenderer(this, 39, 23);
					this.bodyBreastNecklace.setRotationPoint(0.0F, -0.2F, -2.2F);
					this.bodyBreastNecklace.addBox(-4.0F, 0.2F, -5.2F, 8, 0, 5, 0.2F);
					this.setRotateAngle(bodyBreastNecklace, 1.0097597431827485F, 0.0F, 0.0F);
	        	}
	            this.chestScarfTop = new ModelRenderer(this, 0, 29);
                this.chestScarfTop.setRotationPoint(0.0F, -4.5F, 6.5F);
                this.chestScarfTop.addBox(-5.0F, -0.5F, -1.5F, 10, 1, 3, 0.0F);
                this.setRotateAngle(chestScarfTop, -1.1344640137963142F, 0.0F, 0.0F);
                this.chestScarfTopLeftA = new ModelRenderer(this, 35, 18);
                this.chestScarfTopLeftA.setRotationPoint(5.0F, 0.0F, 0.0F);
                this.chestScarfTopLeftA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
                this.setRotateAngle(chestScarfTopLeftA, 0.0F, 0.0F, -2.0943951023931953F);
	            this.chestScarfTopLeftB = new ModelRenderer(this, 40, 21);
	            this.chestScarfTopLeftB.setRotationPoint(-6.0F, 0.0F, 0.0F);
	            this.chestScarfTopLeftB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
	            this.setRotateAngle(chestScarfTopLeftB, 0.0F, 0.0F, 1.0471975511965976F);
	            this.chestScarfTopRightA = new ModelRenderer(this, 35, 18);
	            this.chestScarfTopRightA.setRotationPoint(-5.0F, 0.0F, 0.0F);
	            this.chestScarfTopRightA.addBox(-6.0F, -0.5F, -1.0F, 6, 1, 2, 0.0F);
	            this.setRotateAngle(chestScarfTopRightA, 0.0F, 0.0F, -1.0471975511965976F);
	            this.chestScarfTopRightB = new ModelRenderer(this, 40, 21);
	            this.chestScarfTopRightB.setRotationPoint(-6.0F, 0.0F, 0.0F);
	            this.chestScarfTopRightB.addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
	            this.setRotateAngle(chestScarfTopRightB, 0.0F, 0.0F, -1.0471975511965976F);
	            this.chestScarfLeftA = new ModelRenderer(this, 50, 6);
	            this.chestScarfLeftA.setRotationPoint(5.0F, 0.0F, -3.0F);
	            this.chestScarfLeftA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
	            this.setRotateAngle(chestScarfLeftA, 0.0F, 0.3490658503988659F, 0.17453292519943295F);
	            this.chestScarfLeftB = new ModelRenderer(this, 58, 3);
	            this.chestScarfLeftB.setRotationPoint(0.0F, 5.0F, 0.0F);
	            this.chestScarfLeftB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
	            this.setRotateAngle(chestScarfLeftB, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
	            this.chestScarfLeftC = new ModelRenderer(this, 54, 5);
	            this.chestScarfLeftC.setRotationPoint(0.0F, 8.0F, 0.0F);
	            this.chestScarfLeftC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
	            this.setRotateAngle(chestScarfLeftC, -0.8726646259971648F, -0.8726646259971648F, 0.0F);
	            this.chestScarfRightA = new ModelRenderer(this, 50, 6);
	            this.chestScarfRightA.setRotationPoint(-5.0F, 0.0F, -3.0F);
	            this.chestScarfRightA.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
	            this.setRotateAngle(chestScarfRightA, 0.0F, -0.3490658503988659F, -0.17453292519943295F);
	            this.chestScarfRightB = new ModelRenderer(this, 58, 3);
	            this.chestScarfRightB.setRotationPoint(0.0F, 5.0F, 0.0F);
	            this.chestScarfRightB.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, -0.1F);
	            this.setRotateAngle(chestScarfRightB, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
	            this.chestScarfRightC = new ModelRenderer(this, 54, 5);
	            this.chestScarfRightC.setRotationPoint(0.0F, 8.0F, 0.0F);
	            this.chestScarfRightC.addBox(-1.0F, 0.0F, -0.5F, 2, 6, 1, 0.0F);
	            this.setRotateAngle(chestScarfRightC, -0.8726646259971648F, 0.8726646259971648F, 0.0F);
	            
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
	            this.chestBelt = new ModelRenderer(this, 0, 16);
	            this.chestBelt.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.chestBelt.addBox(-4.5F, 9.0F, -2.5F, 9, 2, 5, 0.2F);
	            this.chestBeltJewel = new ModelRenderer(this, 0, 23);
	            this.chestBeltJewel.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.chestBeltJewel.addBox(0.0F, 8.5F, -3.0F, 3, 3, 1, 0.0F);
	            this.chestBeltHangA = new ModelRenderer(this, 14, 23);
	            this.chestBeltHangA.setRotationPoint(0.0F, 11.2F, -2.5F);
	            this.chestBeltHangA.addBox(0.0F, 0.0F, -0.5F, 2, 5, 1, -0.2F);
	            this.chestBeltHangB = new ModelRenderer(this, 8, 23);
	            this.chestBeltHangB.setRotationPoint(1.0F, 11.0F, -2.75F);
	            this.chestBeltHangB.addBox(0.0F, 0.0F, -0.5F, 2, 4, 1, -0.3F);
	            this.chestPant = new ModelRenderer(this, 20, 23);
	            this.chestPant.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.chestPant.addBox(-4.0F, 11.0F, -2.0F, 8, 1, 4, 0.1F);
	            this.chestPantLeftFront = new ModelRenderer(this, 23, 18);
	            this.chestPantLeftFront.setRotationPoint(0.0F, 9.0F, 2.0F);
	            this.chestPantLeftFront.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
	            this.setRotateAngle(chestPantLeftFront, -0.5235987755982988F, 0.0F, 0.0F);
	            this.chestPantRightFront = new ModelRenderer(this, 23, 18);
	            this.chestPantRightFront.setRotationPoint(0.0F, 9.0F, 2.0F);
	            this.chestPantRightFront.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
	            this.setRotateAngle(chestPantRightFront, -0.5235987755982988F, 0.0F, 0.0F);
	            this.chestPantLeft = new ModelRenderer(this, 28, 16);
	            this.chestPantLeft.setRotationPoint(4.0F, 9.0F, -2.5F);
	            this.chestPantLeft.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
	            this.setRotateAngle(chestPantLeft, 0.0F, 0.0F, 0.5235987755982988F);
	            this.chestPantRight = new ModelRenderer(this, 28, 16);
	            this.chestPantRight.setRotationPoint(-4.0F, 9.0F, -2.5F);
	            this.chestPantRight.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
	            this.setRotateAngle(chestPantRight, 0.0F, 0.0F, -0.5235987755982988F);
	            this.chestPantLeftBack = new ModelRenderer(this, 23, 18);
	            this.chestPantLeftBack.setRotationPoint(0.0F, 9.0F, -2.0F);
	            this.chestPantLeftBack.addBox(0.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
	            this.setRotateAngle(chestPantLeftBack, 0.5235987755982988F, 0.0F, 0.0F);
	            this.chestPantRightBack = new ModelRenderer(this, 23, 18);
	            this.chestPantRightBack.setRotationPoint(0.0F, 9.0F, -2.0F);
	            this.chestPantRightBack.addBox(-4.4F, -2.0F, -0.5F, 4, 2, 1, 0.0F);
	            this.setRotateAngle(chestPantRightBack, 0.5235987755982988F, 0.0F, 0.0F);

				this.legLeftPantBot = new ModelRenderer(this, 24, 7);
				this.legLeftPantBot.setRotationPoint(1.9F, 12.0F, 0.0F);
				this.legLeftPantTop = new ModelRenderer(this, 35, 7);
				this.legLeftPantTop.setRotationPoint(0.0F, 0.0F, 0.0F);
				this.legRightPantBot = new ModelRenderer(this, 24, 7);
				this.legRightPantBot.setRotationPoint(-1.9F, 12.0F, 0.0F);
				this.legRightPantTop = new ModelRenderer(this, 35, 7);
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
	            this.legRingLeftA = new ModelRenderer(this, 30, 0);
	            this.legRingLeftA.setRotationPoint(1.9F, 12.0F, 0.0F);
	            this.legRingLeftA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.0F);
	            this.legRingLeftB = new ModelRenderer(this, 15, 1);
	            this.legRingLeftB.setRotationPoint(0.0F, 0.0F, 0.0F);
	            this.legRingLeftB.addBox(-2.5F, 7.0F, -2.5F, 5, 1, 5, 0.01F);
	            this.legRingRightA = new ModelRenderer(this, 0, 0);
	            this.legRingRightA.setRotationPoint(-1.9F, 12.0F, 0.0F);
	            this.legRingRightA.addBox(-2.5F, 8.5F, -2.5F, 5, 1, 5, 0.01F);
	            this.legRingRightB = new ModelRenderer(this, 30, 0);
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
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadyaw, float headPitch, float scaleFactor) {
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
    	super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadyaw, headPitch, scaleFactor);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

    	float f = 1.0F;
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
            	this.chestScarfTop.offsetZ = d * MathHelper.sin(1.047F);
            	this.chestScarfTop.offsetY = -d * 1.5F * MathHelper.cos(1.047F);
            	this.chestScarfRightA.offsetY = d * MathHelper.cos(1.047F);
            	this.chestScarfRightB.rotateAngleX = 1.047F + f * (float)Math.PI;
            	this.chestScarfTopRightA.rotateAngleZ = -1.047F - f * (float)Math.PI;
            	this.chestScarfTopRightB.rotateAngleZ = -1.047F + 2 * f * (float)Math.PI;
            	this.chestScarfLeftA.offsetY = d * MathHelper.cos(1.047F);
            	this.chestScarfLeftB.rotateAngleX = 1.047F + f * (float)Math.PI;
            	this.chestScarfTopLeftA.rotateAngleZ = -2.094F + f * (float)Math.PI;
            	this.chestScarfTopLeftB.rotateAngleZ = 1.047F - 2 * f * (float)Math.PI;
            	f = -0.872F + 0.03F * sinPI((ageInTicks - 15) / 30.0F) * (float)Math.PI;
            	this.chestScarfRightC.rotateAngleX = f;
            	this.chestScarfLeftC.rotateAngleX = f;
        		break;
        		
        	case LEGS:
                if (entityIn instanceof EntityLivingBase){
                    if(((EntityLivingBase)entityIn).getTicksElytraFlying() > 4) {
	                    f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
	                    f = f / 0.2F;
	                    f = f * f * f;
	                    if (f < 1.0F) f = 1.0F;
                	}
                }
                
            	f = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
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
            	
                if (this.isRiding) {
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
    
    private float sinPI(float f) {
    	return MathHelper.sin(f * (float)Math.PI);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
