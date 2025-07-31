package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.monster.SpiderCorgi;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SpiderCorgiModel<T extends SpiderCorgi> extends BaseCorgiModel<T> {
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleHindLeg;
    private final ModelPart leftMiddleHindLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public SpiderCorgiModel(ModelPart modelPart) {
        super(modelPart);
        this.rightHindLeg = modelPart.getChild("right_hind_leg");
        this.leftHindLeg = modelPart.getChild("left_hind_leg");
        this.rightMiddleHindLeg = modelPart.getChild("right_middle_hind_leg");
        this.leftMiddleHindLeg = modelPart.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = modelPart.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = modelPart.getChild("left_middle_front_leg");
        this.rightFrontLeg = modelPart.getChild("right_front_leg");
        this.leftFrontLeg = modelPart.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(getMesh(), 128, 64);
    }

    public static MeshDefinition getMesh() {
        MeshDefinition meshdefinition = BaseCorgiModel.getMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();

        CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(36, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        CubeListBuilder cubeListBuilder2 = CubeListBuilder.create().texOffs(36, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        partdefinition.addOrReplaceChild("right_hind_leg", cubeListBuilder, PartPose.offset(-3.0F, 15.0F, 2.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", cubeListBuilder2, PartPose.offset(3.0F, 15.0F, 2.0F));
        partdefinition.addOrReplaceChild("right_middle_hind_leg", cubeListBuilder, PartPose.offset(-3.0F, 15.0F, 1.0F));
        partdefinition.addOrReplaceChild("left_middle_hind_leg", cubeListBuilder2, PartPose.offset(3.0F, 15.0F, 1.0F));
        partdefinition.addOrReplaceChild("right_middle_front_leg", cubeListBuilder, PartPose.offset(-3.0F, 15.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_middle_front_leg", cubeListBuilder2, PartPose.offset(3.0F, 15.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_front_leg", cubeListBuilder, PartPose.offset(-3.0F, 15.0F, -1.0F));
        partdefinition.addOrReplaceChild("left_front_leg", cubeListBuilder2, PartPose.offset(3.0F, 15.0F, -1.0F));

        return meshdefinition;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, ageInTicks);
        this.body.setPos(0.0F, 20.0F, 0.0F);
        this.tail.setPos(0.0F, 13.0F, 7.0F);
        this.head.setPos(0.0F, 18.0F, -6.8333F);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightHindLeg.setPos(-2.5f, 15, 5);
        this.leftHindLeg.setPos(2.5f, 15, 5);
        this.rightMiddleHindLeg.setPos(-3, 15, 2);
        this.leftMiddleHindLeg.setPos(3, 15, 2);
        this.rightMiddleFrontLeg.setPos(-3, 15, -1);
        this.leftMiddleFrontLeg.setPos(3, 15, -1);
        this.rightFrontLeg.setPos(-2.5f, 15, -4);
        this.leftFrontLeg.setPos(2.5f, 15, -4);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.tail.xRot = ageInTicks / 10;
        float k = ((float)Math.PI / 4F);
        float m = ((float)Math.PI / 8F);
        this.rightHindLeg.zRot = -k;
        this.leftHindLeg.zRot = k;
        this.rightMiddleHindLeg.zRot = -0.58119464F;
        this.leftMiddleHindLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = -k;
        this.leftFrontLeg.zRot = k;
        this.rightHindLeg.yRot = k;
        this.leftHindLeg.yRot = -k;
        this.rightMiddleHindLeg.yRot = m;
        this.leftMiddleHindLeg.yRot = -m;
        this.rightMiddleFrontLeg.yRot = -m;
        this.leftMiddleFrontLeg.yRot = m;
        this.rightFrontLeg.yRot = -k;
        this.leftFrontLeg.yRot = k;
        float hindLegYRotation = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float middleHindLegYRotation = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float middleFrontLegYRotation = -(Mth.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float frontLegYRotation = -(Mth.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        float hindLegZRotation = Math.abs(Mth.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float middleHindLegZRotation = Math.abs(Mth.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float middleFrontLegZRotation = Math.abs(Mth.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float frontLegZRotation = Math.abs(Mth.sin(limbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        ModelPart currentLeg = this.rightHindLeg;
        currentLeg.yRot += hindLegYRotation;
        currentLeg = this.leftHindLeg;
        currentLeg.yRot -= hindLegYRotation;
        currentLeg = this.rightMiddleHindLeg;
        currentLeg.yRot += middleHindLegYRotation;
        currentLeg = this.leftMiddleHindLeg;
        currentLeg.yRot -= middleHindLegYRotation;
        currentLeg = this.rightMiddleFrontLeg;
        currentLeg.yRot += middleFrontLegYRotation;
        currentLeg = this.leftMiddleFrontLeg;
        currentLeg.yRot -= middleFrontLegYRotation;
        currentLeg = this.rightFrontLeg;
        currentLeg.yRot += frontLegYRotation;
        currentLeg = this.leftFrontLeg;
        currentLeg.yRot -= frontLegYRotation;
        currentLeg = this.rightHindLeg;
        currentLeg.zRot += hindLegZRotation;
        currentLeg = this.leftHindLeg;
        currentLeg.zRot -= hindLegZRotation;
        currentLeg = this.rightMiddleHindLeg;
        currentLeg.zRot += middleHindLegZRotation;
        currentLeg = this.leftMiddleHindLeg;
        currentLeg.zRot -= middleHindLegZRotation;
        currentLeg = this.rightMiddleFrontLeg;
        currentLeg.zRot += middleFrontLegZRotation;
        currentLeg = this.leftMiddleFrontLeg;
        currentLeg.zRot -= middleFrontLegZRotation;
        currentLeg = this.rightFrontLeg;
        currentLeg.zRot += frontLegZRotation;
        currentLeg = this.leftFrontLeg;
        currentLeg.zRot -= frontLegZRotation;
        if (entity.isBaby()) {
            this.head.xScale = 0.75F;
            this.head.yScale = 0.75F;
            this.head.zScale = 0.75F;
            this.head.setPos(0F, 14.5F, -5.75F);
        } else {
            this.head.xScale = 1F;
            this.head.yScale = 1F;
            this.head.zScale = 1F;
            this.head.setPos(0F, 16F, -6.75F);
        }
        this.setClosedMouth();
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftMiddleFrontLeg, this.rightMiddleFrontLeg, this.leftMiddleHindLeg, this.rightMiddleHindLeg, this.leftHindLeg, this.rightHindLeg, this.tail);
    }
}
