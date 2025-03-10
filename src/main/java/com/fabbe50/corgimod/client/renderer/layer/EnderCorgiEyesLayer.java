package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.EnderCorgiModel;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EnderCorgiEyesLayer<T extends EnderCorgi> extends EyesLayer<T, EnderCorgiModel<T>> {
    private static final RenderType ENDER_CORGI_EYES = RenderType.eyes(new ResourceLocation(CorgiMod.MODID, "textures/entity/corgi/corgi_ender_eyes.png"));

    public EnderCorgiEyesLayer(RenderLayerParent<T, EnderCorgiModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public @NotNull RenderType renderType() {
        return ENDER_CORGI_EYES;
    }
}
