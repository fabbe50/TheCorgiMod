package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.Fabbe50CorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Fabbe50Corgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class Fabbe50CorgiRenderer extends AbstractCorgiRenderer<Fabbe50Corgi, Fabbe50CorgiModel<Fabbe50Corgi>> {
    public Fabbe50CorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_FABBE50);
    }

    public Fabbe50CorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new Fabbe50CorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.FABBE50.getTextureLocation();
    }
}
