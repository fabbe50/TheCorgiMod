package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.SunglassesCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.SunglassesCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SunglassesCorgiRenderer extends AbstractCorgiRenderer<SunglassesCorgi, SunglassesCorgiModel<SunglassesCorgi>> {
    public SunglassesCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_SUNGLASSES);
    }

    public SunglassesCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new SunglassesCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return Corgis.SUNGLASSES.getTextureLocation();
    }
}
