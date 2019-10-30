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
public class ModelJapaneseLightArmor extends ModelBiped {

    private final EntityEquipmentSlot slot;

    //Helmet
    private ModelRenderer headBase;
    private ModelRenderer knotBase;
    private ModelRenderer ribbonA;
    private ModelRenderer ribbonB;

    //Chest
    private ModelRenderer baseBody;
    private ModelRenderer armLeft;
    private ModelRenderer armLeftArmor;
    private ModelRenderer armRight;
    private ModelRenderer armRightArmor;

    //Leggings
    private ModelRenderer legLeftArmor;
    private ModelRenderer legRightArmor;

    //Boots
    private ModelRenderer legLeft;
    private ModelRenderer legRight;

    public ModelJapaneseLightArmor(float scale, EntityEquipmentSlot slot) {
        super(scale, 0, 64, 32);

        this.slot = slot;

        switch (slot) {
            case HEAD:
                this.headBase = new ModelRenderer(this, 0, -8);
                this.headBase.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.headBase.addBox(-4.0F, -6.5F, -4.0F, 8, 2, 8, 0.4F);
                this.knotBase = new ModelRenderer(this, 34, 0);
                this.knotBase.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.knotBase.addBox(2.9F, -4.9F, 3.7F, 2, 2, 1, 0.4F);
                this.setRotateAngle(knotBase, 0.0F, 0.0F, -0.785F);
                this.ribbonA = new ModelRenderer(this, 56, 0);
                this.ribbonA.setRotationPoint(0.0F, -5.0F, 4.2F);
                this.ribbonA.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
                this.setRotateAngle(ribbonA, 0.35F, 0.0F, 0.0F);
                this.ribbonB = new ModelRenderer(this, 60, 0);
                this.ribbonB.setRotationPoint(0.0F, -5.0F, 4.2F);
                this.ribbonB.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 0, 0.0F);
                this.setRotateAngle(ribbonB, 0.175F, 0.0F, 0.0F);

                this.bipedHead = headBase;
                this.bipedHead.addChild(knotBase);
                this.bipedHead.addChild(ribbonA);
                this.bipedHead.addChild(ribbonB);
                break;

            case CHEST:
                this.baseBody = new ModelRenderer(this, 0, 2);
                this.baseBody.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.baseBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.45F);
                this.armLeft = new ModelRenderer(this, 24, 3);
                this.armLeft.mirror = true;
                this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
                this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
                this.armLeftArmor = new ModelRenderer(this, 12, 18);
                this.armLeftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.armLeftArmor.addBox(1.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
                this.armRight = new ModelRenderer(this, 24, 3);
                this.armRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
                this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
                this.armRightArmor = new ModelRenderer(this, 12, 18);
                this.armRightArmor.mirror = true;
                this.armRightArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.armRightArmor.addBox(-3.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);

                this.bipedBody = baseBody;

                this.bipedLeftArm = armLeft;
                this.armLeft.addChild(armLeftArmor);

                this.bipedRightArm = armRight;
                this.armRight.addChild(armRightArmor);
                break;

            case LEGS:
                this.legLeftArmor = new ModelRenderer(this, 0, 18);
                this.legLeftArmor.mirror = true;
                this.legLeftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.legLeftArmor.addBox(0.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
                this.setRotateAngle(legLeftArmor, 0.0F, 0.0F, -0.2F);
                this.legRightArmor = new ModelRenderer(this, 0, 18);
                this.legRightArmor.setRotationPoint(-0.0F, 0.0F, 0.0F);
                this.legRightArmor.addBox(-2.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
                this.setRotateAngle(legRightArmor, 0.0F, 0.0F, 0.2F);

                this.legRight = new ModelRenderer(this, 0, 0);
                this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
                this.bipedRightLeg = legRight;
                legRight.addChild(legRightArmor);

                this.legLeft = new ModelRenderer(this, 0, 0);
                this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
                this.bipedLeftLeg = legLeft;
                legLeft.addChild(legLeftArmor);

                break;

            case FEET:
                this.legLeft = new ModelRenderer(this, 40, 7);
                this.legLeft.mirror = true;
                this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
                this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
                this.legRight = new ModelRenderer(this, 40, 7);
                this.legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
                this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

                this.bipedLeftLeg = legLeft;

                this.bipedRightLeg = legRight;
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

        switch (this.slot) {
            case HEAD:
                float f = 1.0F;
                if (entityIn instanceof EntityLivingBase){
                    if(((EntityLivingBase)entityIn).getTicksElytraFlying() > 4) {
                        f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
                        f = f / 0.2F;
                        f = f * f * f;
                        if (f < 1.0F) f = 1.0F;
                    }
                }

                f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + MathHelper.cos(limbSwing * 0.3331F + (float)Math.PI) * limbSwingAmount / f;
                this.ribbonA.rotateAngleX = 0.35F + f * 0.15F;
                this.ribbonA.rotateAngleZ = -0.1F + f * 0.2F;
                this.ribbonB.rotateAngleX = 0.175F + f * 0.1F;
                this.ribbonB.rotateAngleZ = - f * 0.2F;
                break;

            default:
                break;
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
