package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.CorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CorgiRenderer extends AbstractCorgiRenderer<Corgi, CorgiModel<Corgi>> {
    public CorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_NORMAL);
    }

    public CorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation corgi_layer) {
        super(context, new CorgiModel<>(context.bakeLayer(corgi_layer)));
    }
}
