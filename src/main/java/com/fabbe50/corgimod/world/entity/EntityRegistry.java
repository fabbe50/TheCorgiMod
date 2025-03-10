package com.fabbe50.corgimod.world.entity;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.*;
import com.fabbe50.corgimod.world.entity.monster.CreeperCorgi;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import com.fabbe50.corgimod.world.entity.monster.SkeletonCorgi;
import com.fabbe50.corgimod.world.entity.monster.ZombieCorgi;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CorgiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CorgiMod.MODID);

    public static final Supplier<EntityType<Corgi>> CORGI_NORMAL =                  DEFERRED_REGISTER.register("corgi_normal",      () -> EntityType.Builder.of(Corgi::new,             MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_normal"));
    public static final Supplier<EntityType<AntiCorgi>> CORGI_ANTI =                DEFERRED_REGISTER.register("corgi_anti",        () -> EntityType.Builder.of(AntiCorgi::new,         MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_anti"));
    public static final Supplier<EntityType<BodyguardCorgi>> CORGI_BODYGUARD =      DEFERRED_REGISTER.register("corgi_bodyguard",   () -> EntityType.Builder.of(BodyguardCorgi::new,    MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_bodyguard"));
    public static final Supplier<EntityType<BusinessCorgi>> CORGI_BUSINESS =        DEFERRED_REGISTER.register("corgi_business",    () -> EntityType.Builder.of(BusinessCorgi::new,     MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_business"));
    public static final Supplier<EntityType<CreeperCorgi>> CORGI_CREEPER =          DEFERRED_REGISTER.register("corgi_creeper",     () -> EntityType.Builder.of(CreeperCorgi::new,      MobCategory.MONSTER) .sized(0.8f, 0.6f).build("corgi_creeper"));
    public static final Supplier<EntityType<Fabbe50Corgi>> CORGI_FABBE50 =          DEFERRED_REGISTER.register("corgi_fabbe50",     () -> EntityType.Builder.of(Fabbe50Corgi::new,      MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_fabbe50"));
    public static final Supplier<EntityType<FarmerCorgi>> CORGI_FARMER =            DEFERRED_REGISTER.register("corgi_farmer",      () -> EntityType.Builder.of(FarmerCorgi::new,       MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_farmer"));
    public static final Supplier<EntityType<HeroCorgi>> CORGI_HERO =                DEFERRED_REGISTER.register("corgi_hero",        () -> EntityType.Builder.of(HeroCorgi::new,         MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_hero"));
    public static final Supplier<EntityType<LoveCorgi>> CORGI_LOVE =                DEFERRED_REGISTER.register("corgi_love",        () -> EntityType.Builder.of(LoveCorgi::new,         MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_love"));
    public static final Supplier<EntityType<MelonCorgi>> CORGI_MELON =              DEFERRED_REGISTER.register("corgi_melon",       () -> EntityType.Builder.of(MelonCorgi::new,        MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_melon"));
    public static final Supplier<EntityType<NerdCorgi>> CORGI_NERD =                DEFERRED_REGISTER.register("corgi_nerd",        () -> EntityType.Builder.of(NerdCorgi::new,         MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_nerd"));
    public static final Supplier<EntityType<PirateCorgi>> CORGI_PIRATE =            DEFERRED_REGISTER.register("corgi_pirate",      () -> EntityType.Builder.of(PirateCorgi::new,       MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_pirate"));
    public static final Supplier<EntityType<RadioactiveCorgi>> CORGI_RADIOACTIVE =  DEFERRED_REGISTER.register("corgi_radioactive", () -> EntityType.Builder.of(RadioactiveCorgi::new,  MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_radioactive"));
    public static final Supplier<EntityType<SkeletonCorgi>> CORGI_SKELETON =        DEFERRED_REGISTER.register("corgi_skeleton",    () -> EntityType.Builder.of(SkeletonCorgi::new,     MobCategory.MONSTER) .sized(0.8f, 0.6f).build("corgi_skeleton"));
    public static final Supplier<EntityType<SpyCorgi>> CORGI_SPY =                  DEFERRED_REGISTER.register("corgi_spy",         () -> EntityType.Builder.of(SpyCorgi::new,          MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_spy"));
    public static final Supplier<EntityType<SunglassesCorgi>> CORGI_SUNGLASSES =    DEFERRED_REGISTER.register("corgi_sunglasses",  () -> EntityType.Builder.of(SunglassesCorgi::new,   MobCategory.CREATURE).sized(0.8f, 0.6f).build("corgi_sunglasses"));
    public static final Supplier<EntityType<ZombieCorgi>> CORGI_ZOMBIE =            DEFERRED_REGISTER.register("corgi_zombie",      () -> EntityType.Builder.of(ZombieCorgi::new,       MobCategory.MONSTER) .sized(0.8f, 0.6f).build("corgi_zombie"));

    public static <T extends Mob> void registerSpawn(Supplier<EntityType<T>> entityType, SpawnPlacements.Type type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        SpawnPlacements.register(entityType.get(), type, types, spawnPredicate);
    }

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        registerSpawn(CORGI_NORMAL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Corgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_ANTI, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AntiCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_BODYGUARD, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodyguardCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_BUSINESS, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BusinessCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_CREEPER, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CreeperCorgi::checkMonsterSpawnRules);
        registerSpawn(CORGI_FABBE50, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fabbe50Corgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_FARMER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FarmerCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_HERO, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HeroCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_LOVE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LoveCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_MELON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MelonCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_NERD, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NerdCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_PIRATE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PirateCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_RADIOACTIVE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RadioactiveCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_SKELETON, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkeletonCorgi::checkMonsterSpawnRules);
        registerSpawn(CORGI_SPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_SUNGLASSES, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SunglassesCorgi::checkCorgiSpawnRules);
        registerSpawn(CORGI_ZOMBIE, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieCorgi::checkMonsterSpawnRules);
        event.put(CORGI_NORMAL.get(), Corgi.createAttributes().build());
        event.put(CORGI_ANTI.get(), AntiCorgi.createAttributes().build());
        event.put(CORGI_BODYGUARD.get(), BodyguardCorgi.createAttributes().build());
        event.put(CORGI_BUSINESS.get(), BusinessCorgi.createAttributes().build());
        event.put(CORGI_CREEPER.get(), CreeperCorgi.createAttributes().build());
        event.put(CORGI_FABBE50.get(), Fabbe50Corgi.createAttributes().build());
        event.put(CORGI_FARMER.get(), FarmerCorgi.createAttributes().build());
        event.put(CORGI_HERO.get(), HeroCorgi.createAttributes().build());
        event.put(CORGI_LOVE.get(), LoveCorgi.createAttributes().build());
        event.put(CORGI_MELON.get(), MelonCorgi.createAttributes().build());
        event.put(CORGI_NERD.get(), NerdCorgi.createAttributes().build());
        event.put(CORGI_PIRATE.get(), PirateCorgi.createAttributes().build());
        event.put(CORGI_RADIOACTIVE.get(), RadioactiveCorgi.createAttributes().build());
        event.put(CORGI_SKELETON.get(), SkeletonCorgi.createAttributes().build());
        event.put(CORGI_SPY.get(), SpyCorgi.createAttributes().build());
        event.put(CORGI_SUNGLASSES.get(), SunglassesCorgi.createAttributes().build());
        event.put(CORGI_ZOMBIE.get(), ZombieCorgi.createAttributes().build());
    }
}
