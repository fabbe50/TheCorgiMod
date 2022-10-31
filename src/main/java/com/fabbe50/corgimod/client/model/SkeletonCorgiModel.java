package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.SkeletonCorgi;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class SkeletonCorgiModel <T extends SkeletonCorgi> extends ColorableAgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart tail;
    public final ModelPart head;
    private final ModelPart rb_leg;
    private final ModelPart rf_leg;
    private final ModelPart lb_leg;
    private final ModelPart lf_leg;

    public SkeletonCorgiModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.rb_leg = root.getChild("rb_leg");
        this.rf_leg = root.getChild("rf_leg");
        this.lb_leg = root.getChild("lb_leg");
        this.lf_leg = root.getChild("lf_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(44, 15).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg_bones_front = body.addOrReplaceChild("leg_bones_front", CubeListBuilder.create().texOffs(54, 15).addBox(1.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 15).addBox(1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 22).addBox(-3.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 22).addBox(-3.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(51, 26).addBox(-3.0F, -10.0F, -7.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_bones_back = body.addOrReplaceChild("leg_bones_back", CubeListBuilder.create().texOffs(56, 17).addBox(1.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 16).addBox(1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 19).addBox(-3.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 17).addBox(-3.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 26).addBox(-3.0F, -10.0F, -7.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 25).addBox(-3.0F, -10.0F, -8.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

        PartDefinition ribs_left = body.addOrReplaceChild("ribs_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 6.0F));

        PartDefinition front_left = ribs_left.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(53, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition middle_left = ribs_left.addOrReplaceChild("middle_left", CubeListBuilder.create().texOffs(54, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition back_left = ribs_left.addOrReplaceChild("back_left", CubeListBuilder.create().texOffs(52, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ribs_right = body.addOrReplaceChild("ribs_right", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 6.0F));

        PartDefinition front_right = ribs_right.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(53, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition middle_right = ribs_right.addOrReplaceChild("middle_right", CubeListBuilder.create().texOffs(54, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition back_right = ribs_right.addOrReplaceChild("back_right", CubeListBuilder.create().texOffs(52, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(87, 12).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.75F, 8.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition tail_2_r1 = tail.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(85, 12).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.0F, -6.0F, -3.1667F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -2.018F, -2.9028F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.232F, -3.2639F));

        PartDefinition bottom_mouth_r1 = mouth.addOrReplaceChild("bottom_mouth_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.018F, 0.0972F, 0.3927F, 0.0F, 0.0F));

        PartDefinition rb_leg = partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(72, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));

        PartDefinition rf_leg = partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));

        PartDefinition lb_leg = partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(61, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));

        PartDefinition lf_leg = partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(47, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rb_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rf_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        lb_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        lf_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.setPos(0F, 24F, 0F);
        this.rf_leg.setPos(-2F, 20F, -7F);
        this.lf_leg.setPos(2F, 20F, -7F);
        this.rb_leg.setPos(-2F, 20F, 7F);
        this.lb_leg.setPos(2F, 20F, 7F);
        this.rf_leg.xRot = (float) Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rf_leg.yRot = 0;
        this.lf_leg.xRot = (float) Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.lf_leg.yRot = 0;
        this.rb_leg.xRot = (float) Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rb_leg.yRot = 0;
        this.lb_leg.xRot = (float) Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.lb_leg.yRot = 0;
        this.head.setPos(0F, 18F, -6.75F);

        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
    }

    @Override
    protected @NotNull Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rf_leg, this.lf_leg, this.rb_leg, this.lb_leg, this.tail);
    }
}
