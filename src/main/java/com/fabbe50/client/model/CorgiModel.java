package com.fabbe50.client.model;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CorgiModel<T extends CorgiEntity> extends AbstractCorgiModel<T> {
    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        if (entityIn.isAngry()) {
            this.Tail.rotateAngleY = 0.0F;
        } else {
            this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        if (entityIn.isSleeping()) {
            this.Body.setRotationPoint(-3F, 19F, -5.5F);
            this.Body.rotateAngleX = (0);
            this.Tail.setRotationPoint(-1F, 19F, 10F);
            this.LegFrontRight.setRotationPoint(-3F, 22F, -5F);
            this.LegFrontRight.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.LegFrontRight.rotateAngleY = 0.5f;
            this.LegFrontLeft.setRotationPoint(1F, 22F, -6F);
            this.LegFrontLeft.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.LegFrontLeft.rotateAngleY = -0.5f;
            this.LegBackRight.rotateAngleY = -0.5f;
            this.LegBackRight.rotateAngleX = ((float) Math.PI * -3F / 2F);
            this.LegBackRight.setRotationPoint(-3F, 24F, 9F);
            this.LegBackLeft.rotateAngleY = 0.5f;
            this.LegBackLeft.rotateAngleX = ((float) Math.PI * -3F / 2F);
            this.LegBackLeft.setRotationPoint(1F, 24F, 10F);
            this.Face.setRotationPoint(0F, 18F, -7.5F);
            this.EarLeft.setRotationPoint(0F, 18F, -7.5F);
            this.EarRight.setRotationPoint(0F, 18F, -7.5F);
            this.MouthUpper.setRotationPoint(0F, 18F, -7.5F);
            this.MouthLower.setRotationPoint(0F, 18F, -7.5F);
        } else {
            this.Body.setRotationPoint(-3F, 16F, -8F);
            this.Body.rotateAngleX = (0);//(float)Math.PI / 22F);
            this.Tail.setRotationPoint(-1F, 16F, 8F);
            this.LegFrontRight.setRotationPoint(-3F, 20F, -7F);
            this.LegFrontLeft.setRotationPoint(1F, 20F, -7F);
            this.LegBackRight.setRotationPoint(-3F, 20F, 5F);
            this.LegBackLeft.setRotationPoint(1F, 20F, 5F);
            this.LegFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.LegFrontRight.rotateAngleY = 0;
            this.LegFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.LegFrontLeft.rotateAngleY = 0;
            this.LegBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.LegBackRight.rotateAngleY = 0;
            this.LegBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.LegBackLeft.rotateAngleY = 0;
            this.Face.setRotationPoint(0F, 15F, -9.5F);
            this.EarLeft.setRotationPoint(0F, 15F, -9.5F);
            this.EarRight.setRotationPoint(0F, 15F, -9.5F);
            this.MouthUpper.setRotationPoint(0F, 15F, -9.5F);
            this.MouthLower.setRotationPoint(0F, 15F, -9.5F);
        }

        this.Face.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.EarRight.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.EarLeft.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.MouthLower.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.MouthUpper.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.Body.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.16F);
        this.Tail.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.2F);
    }

    @Override
    public void setRotationAngles(T entity, float f, float f1, float f2, float f3, float f4) {
        super.setRotationAngles(entity, f, f1, f2, f3, f4);
    }
}
