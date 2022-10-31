package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.PirateCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.PirateCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PirateCorgiRenderer extends AbstractCorgiRenderer<PirateCorgi, PirateCorgiModel<PirateCorgi>> {
    public PirateCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_PIRATE);
    }

    public PirateCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new PirateCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.PIRATE.getTextureLocation();
    }
}
