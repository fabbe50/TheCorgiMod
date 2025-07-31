package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.SkeletonCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.layer.SkeletonCorgiHeldItemLayer;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.ISkeletonCorgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.jetbrains.annotations.NotNull;

public class SkeletonCorgiRenderer<T extends AbstractSkeleton & ISkeletonCorgi> extends MobRenderer<T, SkeletonCorgiModel<T>> {
    public SkeletonCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_SKELETON);
    }

    public SkeletonCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new SkeletonCorgiModel<>(context.bakeLayer(model)), 0.5f);
        this.addLayer(new SkeletonCorgiHeldItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    protected float getBob(T corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T corgi) {
        return Corgis.getCorgiFromEntity(corgi).getTextureLocation();
    }

    @Override
    protected boolean isShaking(T corgi) {
        return corgi.isShaking();
    }
}
