package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.HostileCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.IZombieCorgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;

public class ZombieCorgiRenderer<T extends Zombie & IZombieCorgi> extends MobRenderer<T, HostileCorgiModel<T>> {
    public ZombieCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_ZOMBIE);
    }

    public ZombieCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new HostileCorgiModel<>(context.bakeLayer(model)), 0.5f);
    }

    @Override
    protected float getBob(T corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T corgi) {
        return Corgis.getCorgiFromEntity(corgi).getTextureLocation();
    }
}
