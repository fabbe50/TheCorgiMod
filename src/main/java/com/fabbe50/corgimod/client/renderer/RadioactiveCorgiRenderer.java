package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.RadioactiveCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.RadioactiveCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RadioactiveCorgiRenderer extends AbstractCorgiRenderer<RadioactiveCorgi, RadioactiveCorgiModel<RadioactiveCorgi>> {
    public RadioactiveCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_RADIOACTIVE);
    }

    public RadioactiveCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new RadioactiveCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.RADIOACTIVE.getTextureLocation();
    }
}
