package com.fabbe50.client.model;

import com.fabbe50.corgis.entities.CorgiEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CorgiModel<T extends CorgiEntity> extends TintedAgeableModel<T> {
    private final ModelRenderer Tail;
    private final ModelRenderer Body;
    private final ModelRenderer LegFrontLeft;
    private final ModelRenderer LegFrontRight;
    private final ModelRenderer LegBackLeft;
    private final ModelRenderer LegBackRight;
    private final ModelRenderer Face;
    private final ModelRenderer EarLeft;
    private final ModelRenderer EarRight;
    private final ModelRenderer MouthUpper;
    private final ModelRenderer MouthLower;

    public CorgiModel() {
        textureWidth = 128;
        textureHeight = 64;

        Tail = new ModelRenderer(this, 83, 10);
        Tail.func_228301_a_(0F, 0F, 0F, 2, 2, 4, 0);
        Tail.setRotationPoint(-1F, 16F, 7F);
        Tail.setTextureSize(128, 64);
        Tail.mirror = true;
        setRotation(Tail, 0F, 0F, 0F);
        Body = new ModelRenderer(this, 36, 10);
        Body.func_228301_a_(0F, 0F, 0F, 6, 4, 16, 0);
        Body.setRotationPoint(-3F, 16F, -8F);
        Body.setTextureSize(128, 64);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        LegFrontLeft = new ModelRenderer(this, 36, 33);
        LegFrontLeft.func_228301_a_(0F, 0F, 0F, 2, 4, 2, 0);
        LegFrontLeft.setRotationPoint(1F, 20F, -7F);
        LegFrontLeft.setTextureSize(128, 64);
        LegFrontLeft.mirror = true;
        setRotation(LegFrontLeft, 0F, 0F, 0F);
        LegFrontRight = new ModelRenderer(this, 47, 33);
        LegFrontRight.func_228301_a_(0F, 0F, 0F, 2, 4, 2, 0);
        LegFrontRight.setRotationPoint(-3F, 20F, -7F);
        LegFrontRight.setTextureSize(128, 64);
        LegFrontRight.mirror = true;
        setRotation(LegFrontRight, 0F, 0F, 0F);
        LegBackLeft = new ModelRenderer(this, 61, 33);
        LegBackLeft.func_228301_a_(0F, 0F, 0F, 2, 4, 2, 0);
        LegBackLeft.setRotationPoint(1F, 20F, 5F);
        LegBackLeft.setTextureSize(128, 64);
        LegBackLeft.mirror = true;
        setRotation(LegBackLeft, 0F, 0F, 0F);
        LegBackRight = new ModelRenderer(this, 72, 33);
        LegBackRight.func_228301_a_(0F, 0F, 0F, 2, 4, 2, 0);
        LegBackRight.setRotationPoint(-3F, 20F, 5F);
        LegBackRight.setTextureSize(128, 64);
        LegBackRight.mirror = true;
        setRotation(LegBackRight, 0F, 0F, 0F);
        Face = new ModelRenderer(this, 15, 10);
        Face.func_228301_a_(-3F, -3F, -1.5F, 6, 6, 3, 0);
        Face.setRotationPoint(0F, 15F, -9.5F);
        Face.setTextureSize(128, 64);
        Face.mirror = true;
        setRotation(Face, 0F, 0F, 0F);
        EarLeft = new ModelRenderer(this, 18, 3);
        EarLeft.func_228301_a_(1F, -6F, 0.5F, 2, 3, 1, 0);
        EarLeft.setRotationPoint(0F, 15F, -9.5F);
        EarLeft.setTextureSize(128, 64);
        EarLeft.mirror = true;
        setRotation(EarLeft, 0F, 0F, 0F);
        EarRight = new ModelRenderer(this, 27, 3);
        EarRight.func_228301_a_(-3F, -6F, 0.5F, 2, 3, 1, 0);
        EarRight.setRotationPoint(0F, 15F, -9.5F);
        EarRight.setTextureSize(128, 64);
        EarRight.mirror = true;
        setRotation(EarRight, 0F, 0F, 0F);
        MouthUpper = new ModelRenderer(this, 0, 10);
        MouthUpper.func_228301_a_(-1.5F, -0.5F, -4.5F, 3, 2, 3, 0);
        MouthUpper.setRotationPoint(0F, 15F, -9.5F);
        MouthUpper.setTextureSize(128, 64);
        MouthUpper.mirror = true;
        setRotation(MouthUpper, 0F, 0F, 0F);
        MouthLower = new ModelRenderer(this, 0, 18);
        MouthLower.func_228301_a_(-1.5F, 1.5F, -4.5F, 3, 1, 3, 0);
        MouthLower.setRotationPoint(0F, 15F, -9.5F);
        MouthLower.setTextureSize(128, 64);
        MouthLower.mirror = true;
        setRotation(MouthLower, 0F, 0F, 0F);
    }

    @Override
    protected Iterable<ModelRenderer> func_225602_a_() {
        return ImmutableList.of(this.Face, this.EarRight, this.EarLeft, this.MouthUpper, this.MouthLower);
    }

    @Override
    protected Iterable<ModelRenderer> func_225600_b_() {
        return ImmutableList.of(this.Body, this.LegBackRight, this.LegBackLeft, this.LegFrontRight, this.LegFrontLeft, this.Tail);
    }


    /*public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        Tail.render(f5);
        Body.render(f5);
        LegFrontLeft.render(f5);
        LegFrontRight.render(f5);
        LegBackLeft.render(f5);
        LegBackRight.render(f5);
        Face.render(f5);
        EarLeft.render(f5);
        EarRight.render(f5);
        MouthUpper.render(f5);
        MouthLower.render(f5);
    }*/
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {

        if (entityIn.isAngry()) {
            this.Tail.rotateAngleY = 0.0F;
        } else {
            this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        if (entityIn.isSitting()) {
            this.Body.setRotationPoint(-3F, 19F, -6F);
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

        this.Face.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.EarRight.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.EarLeft.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.MouthLower.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.MouthUpper.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.Body.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.16F);
        this.Tail.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.2F);
    }

    @Override
    public void func_225597_a_(T p_225597_1_, float f, float f1, float f2, float f3, float f4) {
        this.Face.rotateAngleX = f4 * 0.017453292F;
        this.Face.rotateAngleY = f3 * 0.017453292F;
        this.EarLeft.rotateAngleX = f4 * 0.017453292F;
        this.EarLeft.rotateAngleY = f3 * 0.017453292F;
        this.EarRight.rotateAngleX = f4 * 0.017453292F;
        this.EarRight.rotateAngleY = f3 * 0.017453292F;
        this.MouthLower.rotateAngleX = f4 * 0.017453292F;
        this.MouthLower.rotateAngleY = f3 * 0.017453292F;
        this.MouthUpper.rotateAngleX = f4 * 0.017453292F;
        this.MouthUpper.rotateAngleY = f3 * 0.017453292F;
        this.Tail.rotateAngleX = f2;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
