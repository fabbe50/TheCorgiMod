package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.world.entity.interfaces.IVariant;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.VariantHolder;

import java.awt.*;
import java.util.Random;

public class RandomVariantSpawnEgg<T extends Mob & VariantHolder<Holder<V>>, V extends IVariant> extends VariantSpawnEggItem<T, V> {
    public RandomVariantSpawnEgg(RegistrySupplier<EntityType<T>> entityType, Properties properties, Registrar<V> variantRegistry) {
        super(entityType, 0, 0, properties, variantRegistry, null);
    }

    @Override
    public Holder.Reference<V> getVariant(T entity) {
        return getRegistry(entity).getRandom(entity.getRandom()).orElseThrow();
    }

    private final Random random = new Random();
    private Color backgroundColor = null;
    private Color foregroundColor = null;
    private long timeStamp;
    @Override
    public int getColor(int layer) {
        if ((backgroundColor == null || foregroundColor == null) || (timeStamp / 1000) < System.currentTimeMillis() / 1000) {
            timeStamp = System.currentTimeMillis();
            backgroundColor = new Color(random.nextInt(127), random.nextInt(127), random.nextInt(127));
            foregroundColor = new Color(random.nextInt(127) + 127, random.nextInt(127) + 127, random.nextInt(127) + 127);
        }
        return layer == 0 ? backgroundColor.getRGB() : foregroundColor.getRGB();
    }
}
