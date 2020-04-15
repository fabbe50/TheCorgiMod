package com.fabbe50.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class AbstractCorgiModel<T extends LivingEntity> extends TintedAgeableModel<T> {
    final ModelRenderer Tail;
    final ModelRenderer Body;
    final ModelRenderer LegFrontLeft;
    final ModelRenderer LegFrontRight;
    final ModelRenderer LegBackLeft;
    final ModelRenderer LegBackRight;
    final ModelRenderer Face;
    final ModelRenderer EarLeft;
    final ModelRenderer EarRight;
    final ModelRenderer MouthUpper;
    final ModelRenderer MouthLower;

    protected AbstractCorgiModel() {
        textureWidth = 128;
        textureHeight = 64;

        Tail = new ModelRenderer(this, 83, 10);
        Tail.addBox(0F, 0F, 0F, 2, 2, 4, 0);
        Tail.setRotationPoint(-1F, 16F, 7F);
        Tail.setTextureSize(128, 64);
        Tail.mirror = true;
        setRotation(Tail, 0F, 0F, 0F);
        Body = new ModelRenderer(this, 36, 10);
        Body.addBox(0F, 0F, 0F, 6, 4, 16, 0);
        Body.setRotationPoint(-3F, 16F, -8F);
        Body.setTextureSize(128, 64);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        LegFrontLeft = new ModelRenderer(this, 36, 33);
        LegFrontLeft.addBox(0F, 0F, 0F, 2, 4, 2, 0);
        LegFrontLeft.setRotationPoint(1F, 20F, -7F);
        LegFrontLeft.setTextureSize(128, 64);
        LegFrontLeft.mirror = true;
        setRotation(LegFrontLeft, 0F, 0F, 0F);
        LegFrontRight = new ModelRenderer(this, 47, 33);
        LegFrontRight.addBox(0F, 0F, 0F, 2, 4, 2, 0);
        LegFrontRight.setRotationPoint(-3F, 20F, -7F);
        LegFrontRight.setTextureSize(128, 64);
        LegFrontRight.mirror = true;
        setRotation(LegFrontRight, 0F, 0F, 0F);
        LegBackLeft = new ModelRenderer(this, 61, 33);
        LegBackLeft.addBox(0F, 0F, 0F, 2, 4, 2, 0);
        LegBackLeft.setRotationPoint(1F, 20F, 5F);
        LegBackLeft.setTextureSize(128, 64);
        LegBackLeft.mirror = true;
        setRotation(LegBackLeft, 0F, 0F, 0F);
        LegBackRight = new ModelRenderer(this, 72, 33);
        LegBackRight.addBox(0F, 0F, 0F, 2, 4, 2, 0);
        LegBackRight.setRotationPoint(-3F, 20F, 5F);
        LegBackRight.setTextureSize(128, 64);
        LegBackRight.mirror = true;
        setRotation(LegBackRight, 0F, 0F, 0F);
        Face = new ModelRenderer(this, 15, 10);
        Face.addBox(-3F, -3F, -1.5F, 6, 6, 3, 0);
        Face.setRotationPoint(0F, 15F, -9.5F);
        Face.setTextureSize(128, 64);
        Face.mirror = true;
        setRotation(Face, 0F, 0F, 0F);
        EarLeft = new ModelRenderer(this, 18, 3);
        EarLeft.addBox(1F, -6F, 0.5F, 2, 3, 1, 0);
        EarLeft.setRotationPoint(0F, 15F, -9.5F);
        EarLeft.setTextureSize(128, 64);
        EarLeft.mirror = true;
        setRotation(EarLeft, 0F, 0F, 0F);
        EarRight = new ModelRenderer(this, 27, 3);
        EarRight.addBox(-3F, -6F, 0.5F, 2, 3, 1, 0);
        EarRight.setRotationPoint(0F, 15F, -9.5F);
        EarRight.setTextureSize(128, 64);
        EarRight.mirror = true;
        setRotation(EarRight, 0F, 0F, 0F);
        MouthUpper = new ModelRenderer(this, 0, 10);
        MouthUpper.addBox(-1.5F, -0.5F, -4.5F, 3, 2, 3, 0);
        MouthUpper.setRotationPoint(0F, 15F, -9.5F);
        MouthUpper.setTextureSize(128, 64);
        MouthUpper.mirror = true;
        setRotation(MouthUpper, 0F, 0F, 0F);
        MouthLower = new ModelRenderer(this, 0, 18);
        MouthLower.addBox(-1.5F, 1.5F, -4.5F, 3, 1, 3, 0);
        MouthLower.setRotationPoint(0F, 15F, -9.5F);
        MouthLower.setTextureSize(128, 64);
        MouthLower.mirror = true;
        setRotation(MouthLower, 0F, 0F, 0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            matrixStackIn.push();
            if (true) {
                float f = 1.5F / 2.0F;
                matrixStackIn.scale(f, f, f);
            }

            matrixStackIn.translate(0.0D, 0.6, 0.18);
            this.getHeadParts().forEach((p_228230_8_) -> {
                p_228230_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
            matrixStackIn.push();
            float f1 = 1.0F / 2;
            matrixStackIn.scale(f1, f1, f1);
            matrixStackIn.translate(0.0D, 1.5, 0.0D);
            this.getBodyParts().forEach((p_228229_8_) -> {
                p_228229_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        } else
            super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(T entity, float f, float f1, float f2, float f3, float f4) {
        this.Face.rotateAngleX = f4 * 0.017453292F;
        this.Face.rotateAngleY = f3 * 0.017453292F;
        this.EarLeft.rotateAngleX = f4 * 0.017453292F;
        this.EarLeft.rotateAngleY = f3 * 0.017453292F;
        this.EarRight.rotateAngleX = f4 * 0.017453292F;
        this.EarRight.rotateAngleY = f3 * 0.017453292F;
        this.MouthLower.rotateAngleX = f4 * 0.017453292F + 0.1f;
        this.MouthLower.rotateAngleY = f3 * 0.017453292F;
        this.MouthUpper.rotateAngleX = f4 * 0.017453292F;
        this.MouthUpper.rotateAngleY = f3 * 0.017453292F;
        this.Tail.rotateAngleX = f2;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.Face, this.EarRight, this.EarLeft, this.MouthUpper, this.MouthLower);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.Body, this.LegBackRight, this.LegBackLeft, this.LegFrontRight, this.LegFrontLeft, this.Tail);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
