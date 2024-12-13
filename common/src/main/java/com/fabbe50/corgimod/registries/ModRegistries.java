package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ModRegistries {
    public static final ResourceKey<Registry<CorgiVariant>> CORGI_VARIANT = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, "corgi_variant"));
}
