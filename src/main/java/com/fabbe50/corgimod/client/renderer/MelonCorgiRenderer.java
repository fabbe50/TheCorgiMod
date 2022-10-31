package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.MelonCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.MelonCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MelonCorgiRenderer extends AbstractCorgiRenderer<MelonCorgi, MelonCorgiModel<MelonCorgi>> {
    public MelonCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_MELON);
    }

    public MelonCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new MelonCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public ResourceLocation getTextureLocation(Corgi corgi) {
        return Corgis.MELON.getTextureLocation();
    }
}
