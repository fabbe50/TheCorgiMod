package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.BodyguardCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.BodyguardCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BodyguardCorgiRenderer extends AbstractCorgiRenderer<BodyguardCorgi, BodyguardCorgiModel<BodyguardCorgi>> {
    public BodyguardCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_BODYGUARD);
    }

    public BodyguardCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new BodyguardCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.BODYGUARD.getTextureLocation();
    }
}
