package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;

public class EntityRegistry {

    public static final Registrar<CorgiVariant> CORGI_VARIANTS = TheCorgiMod.MANAGER.get().get(ModRegistries.CORGI_VARIANT);

}
