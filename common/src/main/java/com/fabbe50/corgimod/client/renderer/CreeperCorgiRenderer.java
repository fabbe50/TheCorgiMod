package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.CreeperCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.layer.CreeperCorgiPowerLayer;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.CreeperCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CreeperCorgiRenderer extends MobRenderer<CreeperCorgi, CreeperCorgiModel<CreeperCorgi>> {
    public CreeperCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_CREEPER);
    }

    public CreeperCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new CreeperCorgiModel<>(context.bakeLayer(model)), 0.5f);
        this.addLayer(new CreeperCorgiPowerLayer(this, context.getModelSet()));
    }

    protected void scale(CreeperCorgi corgi, PoseStack poseStack, float v) {
        float f = corgi.getSwelling(v);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(CreeperCorgi corgi, float v) {
        float f = corgi.getSwelling(v);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    protected float getBob(CreeperCorgi corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CreeperCorgi corgi) {
        return Corgis.CREEPER.getTextureLocation();
    }
}
