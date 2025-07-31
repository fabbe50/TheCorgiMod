package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.model.EnderCorgiModel;
import com.fabbe50.corgimod.world.entity.interfaces.model.ICorgiLike;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.monster.Monster;
import org.jetbrains.annotations.NotNull;

public class EnderCorgiEyesLayer<T extends Monster & ICorgiLike> extends EyesLayer<T, EnderCorgiModel<T>> {
    private static final RenderType ENDER_CORGI_EYES = RenderType.eyes(TheCorgiMod.location("textures/entity/ender_corgi_eyes.png"));

    public EnderCorgiEyesLayer(RenderLayerParent<T, EnderCorgiModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public @NotNull RenderType renderType() {
        return ENDER_CORGI_EYES;
    }
}
