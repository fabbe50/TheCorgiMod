package com.fabbe50.client.renderer;

import com.fabbe50.client.layer.CreeperCorgiChargeLayer;
import com.fabbe50.client.model.AbstractCorgiModel;
import com.fabbe50.client.model.CreeperCorgiModel;
import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.corgis.CreeperCorgiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CreeperCorgiRenderer extends AbstractCorgiRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/corgi/corgi_creeper.png");

    public CreeperCorgiRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CreeperCorgiModel());
        this.addLayer(new CreeperCorgiChargeLayer(this));
    }

    @Override
    protected void preRenderCallback(LivingEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = ((CreeperCorgiEntity)entitylivingbaseIn).getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0f + MathHelper.sin(f * 100f) * f * 0.01f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f = f * f;
        f = f * f;
        float f2 = (1.0f + f * 0.4f) * f1;
        float f3 = (1.0f + f * 0.1f) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    @Override
    protected float getOverlayProgress(LivingEntity livingEntityIn, float partialTicks) {
        float f = ((CreeperCorgiEntity)livingEntityIn).getCreeperFlashIntensity(partialTicks);
        return (int)(f * 10f) % 2 == 0 ? 0f : MathHelper.clamp(f, 0.5f, 1.0f);
    }

    @Override
    protected float handleRotationFloat(MobEntity livingBase, float partialTicks) {
        return ((CreeperCorgiEntity)livingBase).getTailRotation();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return TEXTURE;
    }
}
