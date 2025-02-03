package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CorgiVariants {
    private static final List<ResourceKey<CorgiVariant>> CORGI_VARIANTS = new ArrayList<>();

    public static final ResourceKey<CorgiVariant> NORMAL = create("corgi_normal");

    private static ResourceKey<CorgiVariant> create(String string) {
        ResourceKey<CorgiVariant> variant = ResourceKey.create(ModRegistries.CORGI_VARIANT, ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, string));
        CORGI_VARIANTS.add(variant);
        return variant;
    }

    public static Holder<CorgiVariant> getRandomVariant(RegistryAccess registryAccess) {
        Registry<CorgiVariant> registry = registryAccess.registryOrThrow(ModRegistries.CORGI_VARIANT);
        Optional<Holder.Reference<CorgiVariant>> optionalVariant = registry.getAny();
        Objects.requireNonNull(registry);
        return optionalVariant.orElseThrow();
    }

    public static List<ResourceKey<CorgiVariant>> getCorgiVariants() {
        return CORGI_VARIANTS;
    }
}
