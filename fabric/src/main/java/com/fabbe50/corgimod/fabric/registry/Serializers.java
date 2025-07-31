package com.fabbe50.corgimod.fabric.registry;

import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public class Serializers {
    public final static EntityDataSerializer<Holder<CorgiVariant>> CORGI_VARIANTS = EntityDataSerializer.forValueType(CorgiVariant.STREAM_CODEC);
}
