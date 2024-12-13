package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

@SuppressWarnings("CodeBlock2Expr")
public class CorgiVariant {
    public static final Codec<CorgiVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(ResourceLocation.CODEC.fieldOf("corgi_normal").forGetter(variant -> {
            return variant.wild;
        }), ResourceLocation.CODEC.fieldOf("corgi_tame").forGetter(variant -> {
            return variant.tame;
        }), ResourceLocation.CODEC.fieldOf("corgi_angry").forGetter(variant -> {
            return variant.angry;
        }), RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(CorgiVariant::biomes)).apply(instance, CorgiVariant::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, CorgiVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<CorgiVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CorgiVariant>> STREAM_CODEC;

    private final ResourceLocation wild;
    private final ResourceLocation wildFull;
    private final ResourceLocation tame;
    private final ResourceLocation tameFull;
    private final ResourceLocation angry;
    private final ResourceLocation angryFull;

    private final HolderSet<Biome> biomes;

    public CorgiVariant(ResourceLocation wildTexture, ResourceLocation tameTexture, ResourceLocation angryTexture, HolderSet<Biome> biomes) {
        this.wild = wildTexture;
        this.wildFull = fullTextureId(wildTexture);
        this.tame = tameTexture;
        this.tameFull = fullTextureId(tameTexture);
        this.angry = angryTexture;
        this.angryFull = fullTextureId(angryTexture);
        this.biomes = biomes;
    }

    private static ResourceLocation fullTextureId(ResourceLocation resourceLocation) {
        return resourceLocation.withPath((string) -> {
            return "textures/" + string + ".png";
        });
    }

    public ResourceLocation wildTexture() {
        return this.wildFull;
    }

    public ResourceLocation tameTexture() {
        return this.tameFull;
    }

    public ResourceLocation angryTexture() {
        return this.angryFull;
    }

    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof CorgiVariant corgiVariant) {
            return Objects.equals(this.wild, corgiVariant.wild) && Objects.equals(this.tame, corgiVariant.tame) && Objects.equals(this.angry, corgiVariant.angry);
        }
        return false;
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.wild.hashCode();
        i = 31 * i + this.tame.hashCode();
        i = 31 * i + this.angry.hashCode();
        i = 31 * i + this.biomes.hashCode();
        return i;
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, CorgiVariant::wildTexture, ResourceLocation.STREAM_CODEC, CorgiVariant::tameTexture, ResourceLocation.STREAM_CODEC, CorgiVariant::angryTexture, ByteBufCodecs.holderSet(Registries.BIOME), CorgiVariant::biomes, CorgiVariant::new);
        CODEC = RegistryFileCodec.create(ModRegistries.CORGI_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(ModRegistries.CORGI_VARIANT, DIRECT_STREAM_CODEC);
    }
}
