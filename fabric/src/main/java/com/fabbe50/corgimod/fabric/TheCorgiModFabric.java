package com.fabbe50.corgimod.fabric;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.fabric.registry.Serializers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public final class TheCorgiModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EntityDataSerializers.registerSerializer(Serializers.CORGI_VARIANTS);

        TheCorgiMod.init();
    }
}
