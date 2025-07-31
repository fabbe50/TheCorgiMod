package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.interfaces.model.ICorgiLike;
import com.fabbe50.corgimod.world.entity.interfaces.model.ICorgiTail;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BaseCorgiModel<T extends LivingEntity & ICorgiLike> extends ColorableAgeableListModel<T> {
    public final ModelPart body;
    public final ModelPart body_fins;
    public final ModelPart wings;
    public final ModelPart right_wing;
    public final ModelPart left_wing;
    public final ModelPart tail;
    public final ModelPart head;
    public final ModelPart mouth;
    public final ModelPart head_fins;
    public final ModelPart rb_leg;
    public final ModelPart rf_leg;
    public final ModelPart lb_leg;
    public final ModelPart lf_leg;

    public BaseCorgiModel(ModelPart root) {
        this.body = root.getChild("body");
        this.body_fins = this.body.getChild("body_fins");
        this.wings = this.body.getChild("wings");
        this.right_wing = this.wings.getChild("right_wing");
        this.left_wing = this.wings.getChild("left_wing");
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.mouth = this.head.getChild("mouth");
        this.head_fins = this.head.getChild("head_fins");
        this.rb_leg = root.getChild("rb_leg");
        this.rf_leg = root.getChild("rf_leg");
        this.lb_leg = root.getChild("lb_leg");
        this.lf_leg = root.getChild("lf_leg");
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(getMesh(), 128, 64);
    }

    public static MeshDefinition getMesh() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 10).addBox(-3.0F, -7.9F, -8.0F, 6.0F, 4.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        body.addOrReplaceChild("body_fins", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -5.8F, -6.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(69, 25).addBox(2.9F, 1.0F, -7.0F, 5.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(69, 25).addBox(-7.9F, 1.0F, -7.0F, 5.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));
        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, -4.0F));
        wings.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 42).mirror().addBox(0.0F, 0.2F, 0.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F, 0.0F));
        wings.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 42).addBox(-14.0F, 0.2F, 0.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1745F, 0.0F));

        partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(83, 10).addBox(-1.0F, -1.9F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.0F, -5.9F, -3.1667F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(27, 3).addBox(-3.0F, -8.9F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 3).addBox(1.0F, -8.9F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));
        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(79, 48).addBox(-8.0F, -2.4F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(-3.0F))
                .texOffs(96, 0).addBox(-4.0F, -2.5F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(-0.9F)), PartPose.offset(0.0F, -5.9F, -1.6667F));
        head.addOrReplaceChild("head_fins", CubeListBuilder.create().texOffs(100, 10).mirror().addBox(3.0F, -5.9F, -1.6667F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 10).addBox(-7.0F, -5.9F, -1.6667F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, -1.232F, -3.2639F));
        mouth.addOrReplaceChild("top_mouth", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.9F, -3.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-2.0F, -0.782F, -3.9972F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F)), PartPose.offset(0.0F, -0.018F, 0.5972F));
        mouth.addOrReplaceChild("bottom_mouth", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.1F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-2.0F, -1.05F, -3.4972F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0F, -0.018F, 0.0972F, 0.3927F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(72, 33).addBox(-0.9F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));
        partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-0.9F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));
        partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(61, 33).addBox(-1.1F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));
        partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(47, 33).addBox(-1.1F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

        return meshdefinition;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
        this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setNormalPose(entity, limbSwing, limbSwingAmount);
        setHeadAndTailRotation(ageInTicks, netHeadYaw, headPitch);
        setClosedMouth();
    }

    public void setSittingPose(T entity) {
        this.body.setPos(0F, 26F, 0F);
        this.tail.setPos(0F, 19F, 7F);
        this.tail.xRot = -0.5f;
        this.rf_leg.setPos(-2F, 23F, -7F);
        this.rf_leg.xRot = ((float) Math.PI * 3F / 2F);
        this.rf_leg.yRot = 0.5f;
        this.lf_leg.setPos(2F, 23F, -7F);
        this.lf_leg.xRot = ((float) Math.PI * 3F / 2F);
        this.lf_leg.yRot = -0.5f;
        this.rb_leg.yRot = -2.5f;
        this.rb_leg.xRot = ((float) Math.PI * -3F / 2F);
        this.rb_leg.setPos(-2F, 23F, 7F);
        this.lb_leg.yRot = 2.5f;
        this.lb_leg.xRot = ((float) Math.PI * -3F / 2F);
        this.lb_leg.setPos(2F, 23F, 7F);
        if (entity.isBaby()) {
            this.head.xScale = 0.75F;
            this.head.yScale = 0.75F;
            this.head.zScale = 0.75F;
            this.head.setPos(0F, 18F, -5.75F);
        } else {
            this.head.xScale = 1F;
            this.head.yScale = 1F;
            this.head.zScale = 1F;
            this.head.setPos(0F, 21F, -6.75F);
        }
    }

    public void setNormalPose(T entity, float limbSwing, float limbSwingAmount) {
        this.body.setPos(0F, 24F, 0F);
        this.tail.setPos(0F, 17F, 7F);
        this.tail.xRot = 0;
        this.rf_leg.setPos(-2F, 20F, -7F);
        this.lf_leg.setPos(2F, 20F, -7F);
        this.rb_leg.setPos(-2F, 20F, 7F);
        this.lb_leg.setPos(2F, 20F, 7F);
        this.rf_leg.xRot = (float)Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rf_leg.yRot = 0;
        this.lf_leg.xRot = (float)Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.lf_leg.yRot = 0;
        this.rb_leg.xRot = (float)Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rb_leg.yRot = 0;
        this.lb_leg.xRot = (float)Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.lb_leg.yRot = 0;
        if (entity.isBaby()) {
            this.head.xScale = 0.75F;
            this.head.yScale = 0.75F;
            this.head.zScale = 0.75F;
            this.head.setPos(0F, 16.5F, -5.75F);
        } else {
            this.head.xScale = 1F;
            this.head.yScale = 1F;
            this.head.zScale = 1F;
            this.head.setPos(0F, 18F, -6.75F);
        }
    }

    public void setHeadAndTailRotation(float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.tail.xRot = ageInTicks / 10;
    }

    public void setOpenMouth(T entity) {
        this.getLowerMouth().xRot = entity.getLowerMouthAngle();
    }

    public void setClosedMouth() {
        this.getLowerMouth().xRot = 0;
    }

    public ModelPart getLowerMouth() {
        return this.head.getChild("mouth").getChild("bottom_mouth");
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
