package com.fabbe50.corgis.entities.registry;

import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.CorgiEntity;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    private static List<EntityType> entities = Lists.newArrayList();
    private static List<Item> spawmEggs = Lists.newArrayList();

    public static final EntityType<CorgiEntity> CORGI = createEntity(CorgiEntity.class, CorgiEntity::new, 0.6875f, 0.5625f, 0xE0BC8D, 0x89644E);

    private static <T extends Entity> EntityType<T> createEntity(Class<T> entityClass, EntityType.IFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, classToString(entityClass));
        EntityType<T> entity = EntityType.Builder.create(factory, EntityClassification.CREATURE).size(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        entity.setRegistryName(location);
        entities.add(entity);
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).group(ItemGroup.MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Reference.MOD_ID, classToString(entityClass) + "_spawn_egg"));
        spawmEggs.add(spawnEgg);
        return entity;
    }

    private static String classToString(Class<? extends Entity> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("_entity", "");
    }

    @SubscribeEvent
    public static void registerCorgis(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : entities) {
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
        }
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : spawmEggs) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }

    public static void addSpawningConditions() {
        EntitySpawnPlacementRegistry.register(CORGI, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorgiEntity::canSpawnHere);
    }

    public static void addSpawns() {
        addSpawn(CORGI, 2, 5, 5, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST);
    }

    public static void addSpawn(EntityType entityType, int min, int max, int weight, BiomeDictionary.Type... types) {
        if (weight > 0) {
            List<Biome> spawnableBiomes = Lists.newArrayList();
            for (BiomeDictionary.Type type : types) {
                spawnableBiomes.addAll(BiomeDictionary.getBiomes(type));
            }
            for (Biome biome : spawnableBiomes) {
                biome.getSpawns(entityType.getClassification()).add(new Biome.SpawnListEntry(entityType, weight, min, max));
            }
        }
    }
}
