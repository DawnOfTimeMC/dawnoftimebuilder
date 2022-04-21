package org.dawnoftimebuilder.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal")
public class JapaneseLightArmorModel<T extends LivingEntity> extends CustomArmorModel<T> {
	
	//Helmet
	private ModelRenderer headBase;
	private ModelRenderer knotBase;
	private ModelRenderer ribbonA;
	private ModelRenderer ribbonB;

	//Chest
	private ModelRenderer baseBody;
	private ModelRenderer armLeftLayer;
	private ModelRenderer armLeftArmor;
	private ModelRenderer armRightLayer;
	private ModelRenderer armRightArmor;
	private ModelRenderer bodyBreast;

	//Leggings
	private ModelRenderer legLeftArmor;
	private ModelRenderer legRightArmor;

	//Boots
	private ModelRenderer legLeftLayer;
	private ModelRenderer legRightLayer;

	public JapaneseLightArmorModel(EquipmentSlotType slot, boolean isSteve) {
		super(slot, 64, 32);

		switch (slot) {
			case HEAD:
				this.headBase = new ModelRenderer(this, 26, 16);
				this.headBase.setPos(0.0F, 0.0F, 0.0F);
				this.headBase.addBox(-4.0F, -6.5F, -4.0F, 8, 2, 8, 0.4F);
				this.knotBase = new ModelRenderer(this, 56, 9);
				this.knotBase.setPos(0.0F, 0.0F, 0.0F);
				this.knotBase.addBox(2.9F, -4.9F, 3.7F, 2, 2, 1, 0.4F);
				setRotateAngle(knotBase, 0.0F, 0.0F, -0.785F);
				this.ribbonA = new ModelRenderer(this, 56, 0);
				this.ribbonA.setPos(0.0F, -5.0F, 4.2F);
				this.ribbonA.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
				setRotateAngle(ribbonA, 0.35F, 0.0F, 0.0F);
				this.ribbonB = new ModelRenderer(this, 60, 0);
				this.ribbonB.setPos(0.0F, -5.0F, 4.2F);
				this.ribbonB.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 0, 0.0F);
				setRotateAngle(ribbonB, 0.175F, 0.0F, 0.0F);

				this.head = headBase;
				this.head.addChild(knotBase);
				this.head.addChild(ribbonA);
				this.head.addChild(ribbonB);
				break;

			case CHEST:
				this.baseBody = new ModelRenderer(this, 0, 0);
				this.baseBody.setPos(0.0F, 0.0F, 0.0F);
				this.baseBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.45F);
				if(isSteve){
					this.armRightLayer = new ModelRenderer(this, 24, 0);
					this.armRightLayer.setPos(-5.0F, 2.0F, 0.0F);
					this.armRightLayer.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armRightArmor = new ModelRenderer(this, 12, 16);
					this.armRightArmor.mirror = true;
					this.armRightArmor.setPos(0.0F, 0.0F, 0.0F);
					this.armRightArmor.addBox(-3.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
					this.armLeftLayer = new ModelRenderer(this, 24, 0);
					this.armLeftLayer.mirror = true;
					this.armLeftLayer.setPos(5.0F, 2.0F, 0.0F);
					this.armLeftLayer.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3F);
					this.armLeftArmor = new ModelRenderer(this, 12, 16);
					this.armLeftArmor.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftArmor.addBox(1.5F, 3.0F, -2.5F, 2, 5, 5, 0.2F);
				}else{
					this.armRightLayer = new ModelRenderer(this, 24, 0);
					this.armRightLayer.setPos(-5.0F, 2.5F, 0.0F);
					this.armRightLayer.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armRightArmor = new ModelRenderer(this, 12, 16);
					this.armRightArmor.mirror = true;
					this.armRightArmor.setPos(0.0F, 0.0F, 0.0F);
					this.armRightArmor.addBox(-2.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);
					this.armLeftLayer = new ModelRenderer(this, 24, 0);
					this.armLeftLayer.mirror = true;
					this.armLeftLayer.setPos(5.0F, 2.5F, 0.0F);
					this.armLeftLayer.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.3F);
					this.armLeftArmor = new ModelRenderer(this, 12, 16);
					this.armLeftArmor.setPos(0.0F, 0.0F, 0.0F);
					this.armLeftArmor.addBox(0.5F, 3.0F, -2.5F, 2, 5, 5, 0.1F);
					this.bodyBreast = new ModelRenderer(this, 0, 26);
					this.bodyBreast.setPos(0.0F, 1.3F, -2.0F);
					this.bodyBreast.addBox(-3.0F, 0.0F, -3.65F, 6, 2, 3, 0.1F);
					setRotateAngle(bodyBreast, 0.9948376736367678F, 0.0F, 0.0F);
				}

				this.body = baseBody;
				if(!isSteve) this.body.addChild(bodyBreast);

				this.leftArm = armLeftLayer;
				this.armLeftLayer.addChild(armLeftArmor);

				this.rightArm = armRightLayer;
				this.armRightLayer.addChild(armRightArmor);
				break;

			case LEGS:
				this.legLeftArmor = new ModelRenderer(this, 0, 16);
				this.legLeftArmor.mirror = true;
				this.legLeftArmor.setPos(0.0F, 0.0F, 0.0F);
				this.legLeftArmor.addBox(0.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotateAngle(legLeftArmor, 0.0F, 0.0F, -0.2F);
				this.legRightArmor = new ModelRenderer(this, 0, 16);
				this.legRightArmor.setPos(-0.0F, 0.0F, 0.0F);
				this.legRightArmor.addBox(-2.4F, -0.2F, -2.0F, 2, 5, 4, 0.6F);
				setRotateAngle(legRightArmor, 0.0F, 0.0F, 0.2F);

				this.legLeftLayer = new ModelRenderer(this, 0, 0);
				this.legLeftLayer.setPos(1.9F, 12.0F, 0.0F);
				this.leftLeg = legLeftLayer;
				legLeftLayer.addChild(legLeftArmor);

				this.legRightLayer = new ModelRenderer(this, 0, 0);
				this.legRightLayer.setPos(-1.9F, 12.0F, 0.0F);
				this.rightLeg = legRightLayer;
				legRightLayer.addChild(legRightArmor);

				break;

			case FEET:
				this.legLeftLayer = new ModelRenderer(this, 40, 0);
				this.legLeftLayer.mirror = true;
				this.legLeftLayer.setPos(1.9F, 12.0F, 0.0F);
				this.legLeftLayer.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);
				this.legRightLayer = new ModelRenderer(this, 40, 0);
				this.legRightLayer.setPos(-1.9F, 12.0F, 0.0F);
				this.legRightLayer.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.3F);

				this.leftLeg = legLeftLayer;

				this.rightLeg = legRightLayer;
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
				break;

			case CHEST:
				body.visible = true;
				leftArm.visible = true;
				rightArm.visible = true;
				break;

			case LEGS:
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
	public void setupArmorAnim(T entityIn, float ageInTicks) {
		super.setupArmorAnim(entityIn, ageInTicks);

		if (this.slot == EquipmentSlotType.HEAD) {
			float f = 0.3F * sinPI(ageInTicks / 60.0F + 1.0F) + Math.abs(this.rightLeg.xRot);
			this.ribbonA.xRot = Math.max(0.35F + f * 0.15F - this.head.xRot, 0.2F);
			this.ribbonA.zRot = -0.1F + f * 0.2F;
			this.ribbonB.xRot = Math.max(0.175F + f * 0.1F - this.head.xRot, 0.075F);
			this.ribbonB.zRot = -f * 0.2F;
		}
	}
}