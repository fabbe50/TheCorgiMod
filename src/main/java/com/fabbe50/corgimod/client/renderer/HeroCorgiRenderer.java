package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.HeroCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.HeroCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HeroCorgiRenderer extends AbstractCorgiRenderer<HeroCorgi, HeroCorgiModel<HeroCorgi>> {
    public HeroCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_HERO);
    }

    public HeroCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new HeroCorgiModel<>(context.bakeLayer(model)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return Corgis.HERO.getTextureLocation();
    }
}
