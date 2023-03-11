package com.fabbe50.corgimod.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ItemRandomSpawnEgg extends ForgeSpawnEggItem {
    private final List<RegistryObject<? extends EntityType<? extends Mob>>> types;

    public ItemRandomSpawnEgg(Supplier<? extends EntityType<? extends Mob>> defaultType, List<RegistryObject<? extends EntityType<? extends Mob>>> types, Properties props) {
        super(defaultType, 0, 0, props);
        this.types = types;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag) {
        return types.get(new Random().nextInt(types.size())).get();
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

