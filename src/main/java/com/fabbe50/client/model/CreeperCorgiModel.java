package com.fabbe50.client.model;

import com.fabbe50.corgis.entities.corgis.CreeperCorgiEntity;
import net.minecraft.util.math.MathHelper;

public class CreeperCorgiModel<T extends CreeperCorgiEntity> extends AbstractCorgiModel<T> {
    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.Tail.rotateAngleY = 0.0F;
        this.Body.setRotationPoint(-3F, 16F, -8F);
        this.Body.rotateAngleX = (0);//(float)Math.PI / 22F);
        this.Tail.setRotationPoint(-1F, 16F, 7F);
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
}
