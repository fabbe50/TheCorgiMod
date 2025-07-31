package com.fabbe50.corgimod.neoforge.registry;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Serializers {
    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, TheCorgiMod.MOD_ID);

    public final static Supplier<EntityDataSerializer<Holder<CorgiVariant>>> CORGI_VARIANT = SERIALIZERS.register("corgi_variants", () -> EntityDataSerializer.forValueType(CorgiVariant.STREAM_CODEC));
}
