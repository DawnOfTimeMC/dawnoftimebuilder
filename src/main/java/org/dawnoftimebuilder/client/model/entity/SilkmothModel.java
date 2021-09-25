package org.dawnoftimebuilder.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.entity.SilkmothEntity;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal")
public class SilkmothModel<T extends SilkmothEntity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer wingLeft;
	private final ModelRenderer wingRight;
	private final ModelRenderer legs;
	private final ModelRenderer antennaLeft;
	private final ModelRenderer antennaRight;

	public SilkmothModel(){
		this.texWidth = 32;
		this.texHeight = 32;

		this.body = new ModelRenderer(this, 0, 13);
		this.body.setPos(0.0F, 0.0F, 0.0F);
		this.body.addBox(-1.0F, -1.0F, -0.2F, 2, 2, 5, -0.2F);
		this.setRotateAngle(body, -0.175F, 0.0F, 0.0F);
		this.body.y = 1.4F;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 3, -1.0F);
		this.setRotateAngle(head, 0.35F, 0.0F, 0.0F);
		this.legs = new ModelRenderer(this, 9, 8);
		this.legs.setPos(0.0F, 0.0F, 0.0F);
		this.legs.addBox(-2.5F, -2.0F, -1.8F, 5, 5, 5, -1.6F);
		this.antennaLeft = new ModelRenderer(this, 12, -7);
		this.antennaLeft.setPos(0.4F, -0.5F, -0.8F);
		this.antennaLeft.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaLeft, -0.53F, -1.05F, 0.0F);
		this.antennaRight = new ModelRenderer(this, 12, -7);
		this.antennaRight.setPos(-0.4F, -0.5F, -0.8F);
		this.antennaRight.addBox(-2.5F, -2.5F, -4.5F, 5, 6, 7, -2.5F);
		this.setRotateAngle(antennaRight, -0.53F, 1.05F, 0.0F);
		this.wingLeft = new ModelRenderer(this, -12, 20);
		this.wingLeft.mirror = true;
		this.wingLeft.setPos(0.3F, -0.8F, 0.5F);
		this.wingLeft.addBox(-3.5F, -3.5F, -3.5F, 14, 7, 12, -3.5F);
		this.setRotateAngle(wingLeft, 0.0F, 0.26F, 0.0F);
		this.wingRight = new ModelRenderer(this, -12, 20);
		this.wingRight.setPos(-0.3F, -0.8F, 0.5F);
		this.wingRight.addBox(-10.5F, -3.5F, -3.5F, 14, 7, 12, -3.5F);
		this.setRotateAngle(wingRight, 0.0F, -0.26F, 0.0F);

		this.body.addChild(this.head);
		this.head.addChild(this.antennaLeft);
		this.head.addChild(this.antennaRight);
		this.body.addChild(this.legs);
		this.body.addChild(this.wingRight);
		this.body.addChild(this.wingLeft);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		matrixStack.scale(0.5F, 0.5F, 0.5F );
		this.body.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStack.popPose();
	}

	@Override
	public void setupAnim(SilkmothEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float flap = 5.0F;
		float d = Math.abs((ageInTicks % flap) / flap - 0.5F) ;
		this.wingLeft.zRot = (float) (Math.PI * (0.2F - d));
		this.wingRight.zRot = (float) (Math.PI * (-0.2F + d));
		this.head.xRot = headPitch * 0.018F;
	}

	private void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}
}