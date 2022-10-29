package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.ZombieCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.ZombieCorgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ZombieCorgiRenderer extends MobRenderer<ZombieCorgi, ZombieCorgiModel<ZombieCorgi>> {
    public ZombieCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_ZOMBIE);
    }

    public ZombieCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new ZombieCorgiModel<>(context.bakeLayer(model)), 0.5f);
    }

    @Override
    protected float getBob(ZombieCorgi corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ZombieCorgi corgi) {
        return Corgis.ZOMBIE.getTextureLocation();
    }
}
