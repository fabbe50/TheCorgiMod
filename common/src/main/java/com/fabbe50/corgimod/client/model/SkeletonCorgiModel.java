package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.interfaces.corgi.ISkeletonCorgi;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.jetbrains.annotations.NotNull;

public class SkeletonCorgiModel<T extends AbstractSkeleton & ISkeletonCorgi> extends HostileCorgiModel<T> {
    public SkeletonCorgiModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(getMesh(), 128, 64);
    }

    public static MeshDefinition getMesh() {
        MeshDefinition meshdefinition = BaseCorgiModel.getMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(44, 15).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        body.addOrReplaceChild("leg_bones_front", CubeListBuilder.create().texOffs(54, 15).addBox(1.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 15).addBox(1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 22).addBox(-3.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 22).addBox(-3.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(51, 26).addBox(-3.0F, -10.0F, -7.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("leg_bones_back", CubeListBuilder.create().texOffs(56, 17).addBox(1.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 16).addBox(1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 19).addBox(-3.0F, -8.0F, -8.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 17).addBox(-3.0F, -5.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 26).addBox(-3.0F, -10.0F, -7.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 25).addBox(-3.0F, -10.0F, -8.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

        PartDefinition ribs_left = body.addOrReplaceChild("ribs_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 6.0F));
        ribs_left.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(53, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));
        ribs_left.addOrReplaceChild("middle_left", CubeListBuilder.create().texOffs(54, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        ribs_left.addOrReplaceChild("back_left", CubeListBuilder.create().texOffs(52, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(2.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ribs_right = body.addOrReplaceChild("ribs_right", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 6.0F));
        ribs_right.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(53, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));
        ribs_right.addOrReplaceChild("middle_right", CubeListBuilder.create().texOffs(54, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        ribs_right.addOrReplaceChild("back_right", CubeListBuilder.create().texOffs(52, 14).addBox(1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -9.0F, -6.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(87, 12).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.75F, 8.0F, -0.6981F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(85, 12).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

        return meshdefinition;
    }

    @Override
    public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
        this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.body.xScale = 0.97f;
        this.body.yScale = 0.99f;
        this.body.zScale = 0.99f;
        this.rb_leg.xScale = 0.97f;
        this.rb_leg.zScale = 0.99f;
        this.lb_leg.xScale = 0.97f;
        this.lb_leg.zScale = 0.99f;
        this.rf_leg.xScale = 0.97f;
        this.rf_leg.zScale = 0.99f;
        this.lf_leg.xScale = 0.97f;
        this.lf_leg.zScale = 0.99f;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setNormalPose(entity, limbSwing, limbSwingAmount);
        this.rb_leg.setPos(-2.04f, 20, 7.03f);
        this.lb_leg.setPos(2.04f, 20, 7.03f);
        this.rf_leg.setPos(-2.04f, 20, -7.03f);
        this.lf_leg.setPos(2.04f, 20, -7.03f);
        setHeadAndTailRotation(ageInTicks, netHeadYaw, headPitch);
        this.tail.setPos(0.0F, 13.75F, 7.0F);
        if (entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            this.setClosedMouth();
        } else {
            this.setOpenMouth(entity);
        }
    }
}
