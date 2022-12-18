package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.FarmerCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.FarmerCorgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FarmerCorgiRenderer extends AbstractCorgiRenderer<FarmerCorgi, FarmerCorgiModel<FarmerCorgi>> {
    public FarmerCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_FARMER);
    }

    public FarmerCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation corgi_layer) {
        super(context, new FarmerCorgiModel<>(context.bakeLayer(corgi_layer)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return Corgis.FARMER.getTextureLocation();
    }
}
