package com.fabbe50.corgis.entities.registry;

import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.CorgiEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class EntityRegistry {
    public static void init() {
        createEntity(Reference.modResourceLoc("corgi"), CorgiEntity.class, "corgi", Corgis.instance, 0, 64, 1, true, 0xedc67d, 0x8f6830);

        addSpawning(CorgiEntity.class, 20, 5,12, EnumCreatureType.CREATURE, Biomes.DEFAULT, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.ROOFED_FOREST);
    }

    private static void createEntity(ResourceLocation location, Class<? extends Entity> clazz, String name, Object modInstance, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
        Corgis.getLogger().info("Registered Entity: " + name);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(location, clazz, name, id, modInstance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
    }

    private static void addSpawning(Class <? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
        net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
    }
}
