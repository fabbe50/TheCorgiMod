package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.SpyCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.SpyCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SpyCorgiRenderer extends AbstractCorgiRenderer<SpyCorgi, SpyCorgiModel<SpyCorgi>> {
    public SpyCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_SPY);
    }

    public SpyCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new SpyCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.SPY.getTextureLocation();
    }
}
