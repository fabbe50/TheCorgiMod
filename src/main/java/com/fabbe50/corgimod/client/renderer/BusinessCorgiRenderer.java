package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.BusinessCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.BusinessCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BusinessCorgiRenderer extends AbstractCorgiRenderer<BusinessCorgi, BusinessCorgiModel<BusinessCorgi>> {
    public BusinessCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_BUSINESS);
    }

    public BusinessCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, new BusinessCorgiModel<>(context.bakeLayer(modelLayerLocation)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.BUSINESS.getTextureLocation();
    }
}
