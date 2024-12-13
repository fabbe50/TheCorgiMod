package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModEntitySerializers {
    public static final EntityDataSerializer<Holder<CorgiVariant>> CORGI_VARIANT = EntityDataSerializer.forValueType(CorgiVariant.STREAM_CODEC);
}
