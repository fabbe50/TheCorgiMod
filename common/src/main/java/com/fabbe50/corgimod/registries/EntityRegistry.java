package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registrar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {
    public static final Registrar<CorgiVariant> CORGI_VARIANTS = TheCorgiMod.MANAGER.get().get(ModRegistries.CORGI_VARIANT);

    public static final Supplier<EntityType<Corgi>> CORGI = Suppliers.memoize(() -> EntityType.Builder.of(Corgi::new, MobCategory.CREATURE).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("corgi"));
}
