package com.fabbe50.corgis.entities.registry;

import com.fabbe50.corgis.Config;
import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.Reference;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class SpawnRegistry {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome != null && (Corgis.config.getCorgis().biomeCategories.get().contains(biome.getCategory().getName()) || Corgis.config.getCorgis().biomes.get().contains(event.getName().toString()))) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntityRegistry.CORGI, Corgis.config.getCorgis().weight.get(), Corgis.config.getCorgis().min.get(), Corgis.config.getCorgis().max.get()));
        }
        if (biome != null && !event.getCategory().equals(Biome.Category.MUSHROOM)) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(EntityRegistry.CREEPER_CORGI, 80, 1, 2));
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(EntityRegistry.ZOMBIE_CORGI, 100, 1, 3));
        }
    }
}
