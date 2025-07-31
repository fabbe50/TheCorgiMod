package com.fabbe50.corgimod.world.entity.interfaces;

import com.fabbe50.corgimod.TheCorgiMod;
import net.minecraft.resources.ResourceLocation;

public interface IVariant {
    default ResourceLocation fullTextureId(ResourceLocation resourceLocation) {
        return TheCorgiMod.location("textures/" + resourceLocation.getPath() + ".png");
    }
}
