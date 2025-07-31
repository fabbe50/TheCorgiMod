package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.SpiderCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.monster.SpiderCorgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpiderCorgiRenderer<T extends SpiderCorgi> extends MobRenderer<T, SpiderCorgiModel<T>> {
    public SpiderCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_SPIDER);
    }

    public SpiderCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new SpiderCorgiModel<>(context.bakeLayer(model)), 0.8f);
    }

    @Override
    protected float getBob(T corgi, float f) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T entity) {
        return Corgis.SPIDER.getTextureLocation();
    }
}
