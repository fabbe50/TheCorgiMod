package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.AbstractCorgiModel;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.BodyguardCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractCorgiRenderer<T extends Corgi, M extends AbstractCorgiModel<T>> extends MobRenderer<T, M> {
    public AbstractCorgiRenderer(EntityRendererProvider.Context context, M model) {
        super(context, model, 0.5f);
    }

    @Override
    protected float getBob(Corgi corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public void render(T corgi, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_115460_) {
        if (corgi.isWet()) {
            float f = corgi.getWetShade(p_115457_);
            this.model.setColor(f, f, f);
        }

        super.render(corgi, p_115456_, p_115457_, poseStack, multiBufferSource, p_115460_);
        if (corgi.isWet()) {
            this.model.setColor(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.NORMAL.getTextureLocation();
    }
}
