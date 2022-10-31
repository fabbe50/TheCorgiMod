package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.NerdCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.NerdCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class NerdCorgiRenderer extends AbstractCorgiRenderer<NerdCorgi, NerdCorgiModel<NerdCorgi>> {
    public NerdCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_NERD);
    }

    public NerdCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new NerdCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.NERD.getTextureLocation();
    }
}
