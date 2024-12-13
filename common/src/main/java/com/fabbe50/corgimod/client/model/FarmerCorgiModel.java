package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.FarmerCorgi;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class FarmerCorgiModel<T extends FarmerCorgi> extends AbstractCorgiModel<T> {

    public FarmerCorgiModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 10).addBox(-3.0F, -7.9F, -8.0F, 6.0F, 4.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(83, 10).addBox(-1.0F, -1.9F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.0F, -5.9F, -3.1667F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));

        PartDefinition left_ear_r1 = head.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(18, 3).addBox(-1.0F, -2.65F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.0F, -0.6667F, 0.0F, 0.0F, 0.2618F));

        PartDefinition right_ear_r1 = head.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(27, 3).addBox(-1.0F, -2.65F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, -0.6667F, 0.0F, 0.0F, -0.2618F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.918F, -2.9028F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.232F, -3.2639F));

        PartDefinition bottom_mouth_r1 = mouth.addOrReplaceChild("bottom_mouth_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.1F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.018F, 0.0972F, 0.3927F, 0.0F, 0.0F));

        PartDefinition rb_leg = partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(72, 33).addBox(-0.9F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));

        PartDefinition rf_leg = partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-0.9F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));

        PartDefinition lb_leg = partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(61, 33).addBox(-1.1F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));

        PartDefinition lf_leg = partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(47, 33).addBox(-1.1F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(79, 48).addBox(-8.0F, -2.4F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(-3.0F))
                .texOffs(96, 0).addBox(-4.0F, -2.5F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(-0.9F)), PartPose.offset(0.0F, -5.9F, -1.6667F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }
}
