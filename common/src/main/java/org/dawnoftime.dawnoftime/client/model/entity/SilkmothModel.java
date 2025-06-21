package org.dawnoftime.dawnoftime.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.entity.SilkmothEntity;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal")
public class SilkmothModel<T extends SilkmothEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DoTBCommon.MOD_ID, "silkmoth"), "main");
    private final ModelPart root;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;
    private final ModelPart head;

    public SilkmothModel(ModelPart root) {
        this.root = root.getChild("root");

        ModelPart body = this.root.getChild("body");
        this.wingLeft = body.getChild("wingLeft");
        this.wingRight = body.getChild("wingRight");
        this.head = body.getChild("head");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -1.25F, -0.3F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition antennaLeft = head.addOrReplaceChild("antennaLeft", CubeListBuilder.create().texOffs(12, -7).addBox(-2.5F, -2.5F, -4.5F, 5.0F, 6.0F, 7.0F, new CubeDeformation(-2.5F)), PartPose.offsetAndRotation(-0.4F, -0.5F, -0.8F, -0.5236F, 1.0472F, 0.0F));

        PartDefinition antennaRight = head.addOrReplaceChild("antennaRight", CubeListBuilder.create().texOffs(12, -7).addBox(-2.5F, -2.5F, -4.5F, 5.0F, 6.0F, 7.0F, new CubeDeformation(-2.5F)), PartPose.offsetAndRotation(0.4F, -0.5F, -0.8F, -0.5236F, -1.0472F, 0.0F));

        PartDefinition wingLeft = body.addOrReplaceChild("wingLeft", CubeListBuilder.create().texOffs(-12, 20).mirror().addBox(-3.5F, -3.5F, -3.5F, 14.0F, 7.0F, 12.0F, new CubeDeformation(-3.5F)).mirror(false), PartPose.offsetAndRotation(0.3F, -0.8F, 0.5F, 0.0F, 0.2618F, 0.0F));

        PartDefinition wingRight = body.addOrReplaceChild("wingRight", CubeListBuilder.create().texOffs(-12, 20).addBox(-10.5F, -3.5F, -3.5F, 14.0F, 7.0F, 12.0F, new CubeDeformation(-3.5F)).mirror(false), PartPose.offsetAndRotation(-0.3F, -0.8F, 0.5F, 0.0F, -0.2618F, 0.0F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(9, 8).addBox(-2.5F, -2.28F, -1.55F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float flap = 5.0F;
        float d = Math.abs((ageInTicks % flap) / flap - 0.5F) ;
        this.wingLeft.zRot = (float) (Math.PI * (0.2F - d));
        this.wingRight.zRot = (float) (Math.PI * (-0.2F + d));
        this.head.xRot = headPitch * 0.018F;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}