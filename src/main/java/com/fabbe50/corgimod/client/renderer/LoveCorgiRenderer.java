package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.CorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LoveCorgiRenderer extends AbstractCorgiRenderer<Corgi, CorgiModel<Corgi>> {
    public LoveCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_LOVE);
    }

    public LoveCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation corgi_layer) {
        super(context, new CorgiModel<>(context.bakeLayer(corgi_layer)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return Corgis.LOVE.getTextureLocation();
    }
}
