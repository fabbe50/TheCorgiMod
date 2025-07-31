package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelPart;

public class CorgiModel<T extends Corgi> extends BaseCorgiModel<T> {
    public CorgiModel(ModelPart root) {
        super(root);
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
        if (entity.isAngry()) {
            this.tail.yRot = 0.0F;
        } else {
            super.prepareMobModel(entity, limbSwing, limbSwingAmount, ageInTicks);
        }
        this.head.zRot = entity.getHeadRollAngle(ageInTicks) + entity.getBodyRollAngle(ageInTicks, 0.0F);
        this.body.zRot = entity.getBodyRollAngle(ageInTicks, -0.16F);
        this.tail.zRot = entity.getBodyRollAngle(ageInTicks, -0.2F);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInSittingPose()) {
            setSittingPose(entity);
            setHeadAndTailRotation(ageInTicks, netHeadYaw, headPitch);
            setClosedMouth();
        } else {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
        if (entity.isInterested()) {
            setOpenMouth(entity);
        } else {
            setClosedMouth();
        }
    }
}
