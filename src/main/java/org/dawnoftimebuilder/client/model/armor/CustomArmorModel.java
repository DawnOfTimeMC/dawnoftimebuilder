package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CustomArmorModel<T extends LivingEntity> extends BipedModel<T> {

	public final EquipmentSlotType slot;

	public CustomArmorModel(EquipmentSlotType slot, int textureWidthIn, int textureHeightIn){
		super(0.0F, 0.0F, textureWidthIn, textureHeightIn);

		this.slot = slot;
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		//Fix the "breathing" and wrong head rotation on ArmorStands
		if (entityIn instanceof ArmorStandEntity) {
			ArmorStandEntity entityAS = (ArmorStandEntity) entityIn;
			float f = (float) Math.PI / 180F;
			this.bipedHead.rotateAngleX = f * entityAS.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = f * entityAS.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = f * entityAS.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			this.bipedBody.rotateAngleX = f * entityAS.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = f * entityAS.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = f * entityAS.getBodyRotation().getZ();
			this.bipedLeftArm.rotateAngleX = f * entityAS.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = f * entityAS.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = f * entityAS.getLeftArmRotation().getZ();
			this.bipedRightArm.rotateAngleX = f * entityAS.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = f * entityAS.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = f * entityAS.getRightArmRotation().getZ();
			this.bipedLeftLeg.rotateAngleX = f * entityAS.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = f * entityAS.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = f * entityAS.getLeftLegRotation().getZ();
			this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
			this.bipedRightLeg.rotateAngleX = f * entityAS.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = f * entityAS.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = f * entityAS.getRightLegRotation().getZ();
			this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			this.bipedHeadwear.copyModelAngles(this.bipedHead);
		}
	}

	public static void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}

	public static float sinPI(float f) {
		return MathHelper.sin(f * (float)Math.PI);
	}
}
