package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.monster.*;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayList;
import java.util.List;

public class EntityRegistry {
    public static final Registrar<EntityType<?>> ENTITY_TYPES = TheCorgiMod.MANAGER.get().get(Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Corgi>> CORGI = register("corgi", Suppliers.memoize(() -> EntityType.Builder.of(Corgi::new, MobCategory.CREATURE).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("corgi")));

    public static final RegistrySupplier<EntityType<BoggedCorgi>> BOGGED_CORGI                  = register("bogged_corgi", Suppliers.memoize(() -> EntityType.Builder.of(BoggedCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("bogged_corgi")));
    public static final RegistrySupplier<EntityType<CreeperCorgi>> CREEPER_CORGI                = register("creeper_corgi", Suppliers.memoize(() -> EntityType.Builder.of(CreeperCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("creeper_corgi")));
    public static final RegistrySupplier<EntityType<DrownedCorgi>> DROWNED_CORGI                = register("drowned_corgi", Suppliers.memoize(() -> EntityType.Builder.of(DrownedCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("drowned_corgi")));
    public static final RegistrySupplier<EntityType<EnderCorgi>> ENDER_CORGI                    = register("ender_corgi", Suppliers.memoize(() -> EntityType.Builder.of(EnderCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("ender_corgi")));
    public static final RegistrySupplier<EntityType<HuskCorgi>> HUSK_CORGI                      = register("husk_corgi", Suppliers.memoize(() -> EntityType.Builder.of(HuskCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("husk_corgi")));
    public static final RegistrySupplier<EntityType<SkeletonCorgi>> SKELETON_CORGI              = register("skeleton_corgi", Suppliers.memoize(() -> EntityType.Builder.of(SkeletonCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("skeleton_corgi")));
    public static final RegistrySupplier<EntityType<SpiderCorgi>> SPIDER_CORGI                  = register("spider_corgi", Suppliers.memoize(() -> EntityType.Builder.of(SpiderCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.8f).eyeHeight(0.4f).clientTrackingRange(10).build("spider_corgi")));
    public static final RegistrySupplier<EntityType<StrayCorgi>> STRAY_CORGI                    = register("stray_corgi", Suppliers.memoize(() -> EntityType.Builder.of(StrayCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("stray_corgi")));
    public static final RegistrySupplier<EntityType<WitherSkeletonCorgi>> WITHER_SKELETON_CORGI = register("wither_skeleton_corgi", Suppliers.memoize(() -> EntityType.Builder.of(WitherSkeletonCorgi::new, MobCategory.MONSTER).sized(0.96f, 0.6f).eyeHeight(0.48f).clientTrackingRange(10).build("wither_skeleton_corgi")));
    public static final RegistrySupplier<EntityType<ZombieCorgi>> ZOMBIE_CORGI                  = register("zombie_corgi", Suppliers.memoize(() -> EntityType.Builder.of(ZombieCorgi::new, MobCategory.MONSTER).sized(0.8f, 0.5f).eyeHeight(0.4f).clientTrackingRange(10).build("zombie_corgi")));

    private static <T extends net.minecraft.world.entity.LivingEntity> RegistrySupplier<EntityType<T>> register(String registryName, Supplier<EntityType<T>> entityTypeSupplier) {
        return ENTITY_TYPES.register(TheCorgiMod.location(registryName), entityTypeSupplier);
    }

    public static void init() {
        registerAttributes();
        registerSpawns();
        registerSpawnLocations();
    }

    public static void registerAttributes() {
        EntityAttributeRegistry.register(CORGI, Corgi::createAttributes);

        EntityAttributeRegistry.register(BOGGED_CORGI, BoggedCorgi::createAttributes);
        EntityAttributeRegistry.register(CREEPER_CORGI, CreeperCorgi::createAttributes);
        EntityAttributeRegistry.register(DROWNED_CORGI, DrownedCorgi::createAttributes);
        EntityAttributeRegistry.register(ENDER_CORGI, EnderCorgi::createAttributes);
        EntityAttributeRegistry.register(HUSK_CORGI, HuskCorgi::createAttributes);
        EntityAttributeRegistry.register(SKELETON_CORGI, SkeletonCorgi::createAttributes);
        EntityAttributeRegistry.register(SPIDER_CORGI, SpiderCorgi::createAttributes);
        EntityAttributeRegistry.register(STRAY_CORGI, StrayCorgi::createAttributes);
        EntityAttributeRegistry.register(WITHER_SKELETON_CORGI, WitherSkeletonCorgi::createAttributes);
        EntityAttributeRegistry.register(ZOMBIE_CORGI, ZombieCorgi::createAttributes);
    }

    public static void registerSpawns() {
        SpawnPlacementsRegistry.register(CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Corgi::checkCorgiSpawnRules);

        SpawnPlacementsRegistry.register(BOGGED_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoggedCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(CREEPER_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CreeperCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(DROWNED_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DrownedCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(ENDER_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnderCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(HUSK_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HuskCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(SKELETON_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkeletonCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(SPIDER_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpiderCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(STRAY_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StrayCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(WITHER_SKELETON_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WitherSkeletonCorgi::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(ZOMBIE_CORGI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieCorgi::checkMonsterSpawnRules);
    }

    public static void registerSpawnLocations() {
        BiomeModifications.addProperties(biomeContext -> biomeContext.hasTag(ModTags.CORGI_SPAWN_BIOMES), (biomeContext, mutable) -> {
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(CORGI.get(), ModConfig.<Integer>getValue("passiveCorgiSpawnWeight").getValue(), 3, 6));
        });
        BiomeModifications.addProperties(biomeContext -> biomeContext.hasTag(BiomeTags.IS_OVERWORLD), (biomeContext, mutable) -> {
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(CREEPER_CORGI.get(), ModConfig.<Integer>getValue("creeperCorgiSpawnWeight").getValue(), 1, 3));
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(ENDER_CORGI.get(), ModConfig.<Integer>getValue("enderCorgiSpawnWeight").getValue(), 1, 2));
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(SKELETON_CORGI.get(), ModConfig.<Integer>getValue("skeletonCorgiSpawnWeight").getValue(), 2, 4));
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(ZOMBIE_CORGI.get(), ModConfig.<Integer>getValue("zombieCorgiSpawnWeight").getValue(), 1, 2));
        });
        BiomeModifications.addProperties(biomeContext -> biomeContext.hasTag(BiomeTags.IS_END), (biomeContext, mutable) -> {
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(ENDER_CORGI.get(), ModConfig.<Integer>getValue("enderCorgiSpawnWeight").getValue() * 6, 2, 4));
        });
        BiomeModifications.addProperties(biomeContext -> biomeContext.hasTag(BiomeTags.HAS_NETHER_FOSSIL), (biomeContext, mutable) -> {
            biomeContext.getProperties().getSpawnProperties().getSpawners().get(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(SKELETON_CORGI.get(), ModConfig.<Integer>getValue("skeletonCorgiSpawnWeight").getValue(), 3, 4));
        });
    }
}
