package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.TheCorgiMod;
import dev.architectury.registry.registries.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.Optional;

public class CorgiVariants {
    public static Registrar<CorgiVariant> CORGI_VARIANTS_REGISTRY = TheCorgiMod.MANAGER.get().<CorgiVariant>builder(TheCorgiMod.location("corgi_variants")).syncToClients().build();

    public static final ResourceKey<CorgiVariant> NORMAL = create("corgi_normal");
    public static final ResourceKey<CorgiVariant> ANTI = create("corgi_anti");
    public static final ResourceKey<CorgiVariant> BODYGUARD = create("corgi_bodyguard");
    public static final ResourceKey<CorgiVariant> BUSINESS = create("corgi_business");
    public static final ResourceKey<CorgiVariant> FABBE50 = create("corgi_fabbe50");
    public static final ResourceKey<CorgiVariant> FARMER = create("corgi_farmer");
    public static final ResourceKey<CorgiVariant> HERO = create("corgi_hero");
    public static final ResourceKey<CorgiVariant> LOVE = create("corgi_love");
    public static final ResourceKey<CorgiVariant> MELON = create("corgi_melon");
    public static final ResourceKey<CorgiVariant> NERD = create("corgi_nerd");
    public static final ResourceKey<CorgiVariant> PIRATE = create("corgi_pirate");
    public static final ResourceKey<CorgiVariant> RADIOACTIVE = create("corgi_radioactive");
    public static final ResourceKey<CorgiVariant> SPY = create("corgi_spy");
    public static final ResourceKey<CorgiVariant> SUNGLASSES = create("corgi_sunglasses");

    public static void init() {}

    private static ResourceKey<CorgiVariant> create(String string) {
        ResourceKey<CorgiVariant> variant = ResourceKey.create(CORGI_VARIANTS_REGISTRY.key(), TheCorgiMod.location(string));
        register(string);
        return variant;
    }

    private static void register(String variantName) {
        ResourceLocation wildTexture = TheCorgiMod.location("entity/corgi/" + variantName);
        ResourceLocation tameTexture = TheCorgiMod.location("entity/corgi/" + variantName + "_tame");
        ResourceLocation angryTexture = TheCorgiMod.location("entity/corgi/" + variantName + "_angry");
        CORGI_VARIANTS_REGISTRY.register(TheCorgiMod.location(variantName), () -> new CorgiVariant(wildTexture, tameTexture, angryTexture));
    }

    public static Holder<CorgiVariant> getRandomVariant(RegistryAccess registryAccess) {
        Registry<CorgiVariant> registry = registryAccess.registryOrThrow(CORGI_VARIANTS_REGISTRY.key());
        Optional<Holder.Reference<CorgiVariant>> optionalVariant = registry.holders().findAny().or(() -> registry.getHolder(NORMAL));
        Objects.requireNonNull(registry);
        return optionalVariant.or(registry::getAny).orElseThrow();
    }

    public static Holder<CorgiVariant> getVariantHolder(ResourceKey<CorgiVariant> corgiVariant) {
        return CORGI_VARIANTS_REGISTRY.getHolder(corgiVariant);
    }
}
