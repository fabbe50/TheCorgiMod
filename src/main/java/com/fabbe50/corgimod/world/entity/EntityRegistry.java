package com.fabbe50.corgimod.world.entity;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CorgiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CorgiMod.MODID);

    public static final RegistryObject<EntityType<Corgi>> CORGI_NORMAL = DEFERRED_REGISTER.register("corgi_normal", () -> registerEntity(EntityType.Builder.of(Corgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_normal"));
    public static final RegistryObject<EntityType<AntiCorgi>> CORGI_ANTI = DEFERRED_REGISTER.register("corgi_anti", () -> registerEntity(EntityType.Builder.of(AntiCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_anti"));
    public static final RegistryObject<EntityType<BodyguardCorgi>> CORGI_BODYGUARD = DEFERRED_REGISTER.register("corgi_bodyguard", () -> registerEntity(EntityType.Builder.of(BodyguardCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_bodyguard"));
    public static final RegistryObject<EntityType<BusinessCorgi>> CORGI_BUSINESS = DEFERRED_REGISTER.register("corgi_business", () -> registerEntity(EntityType.Builder.of(BusinessCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_business"));
    public static final RegistryObject<EntityType<CreeperCorgi>> CORGI_CREEPER = DEFERRED_REGISTER.register("corgi_creeper", () -> registerEntity(EntityType.Builder.of(CreeperCorgi::new, MobCategory.MONSTER).sized(1f, 0.5f), "corgi_creeper"));
    public static final RegistryObject<EntityType<Fabbe50Corgi>> CORGI_FABBE50 = DEFERRED_REGISTER.register("corgi_fabbe50", () -> registerEntity(EntityType.Builder.of(Fabbe50Corgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_fabbe50"));
    public static final RegistryObject<EntityType<FarmerCorgi>> CORGI_FARMER = DEFERRED_REGISTER.register("corgi_farmer", () -> registerEntity(EntityType.Builder.of(FarmerCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_farmer"));
    public static final RegistryObject<EntityType<HeroCorgi>> CORGI_HERO = DEFERRED_REGISTER.register("corgi_hero", () -> registerEntity(EntityType.Builder.of(HeroCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_hero"));
    public static final RegistryObject<EntityType<LoveCorgi>> CORGI_LOVE = DEFERRED_REGISTER.register("corgi_love", () -> registerEntity(EntityType.Builder.of(LoveCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_love"));
    public static final RegistryObject<EntityType<MelonCorgi>> CORGI_MELON = DEFERRED_REGISTER.register("corgi_melon", () -> registerEntity(EntityType.Builder.of(MelonCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_melon"));
    public static final RegistryObject<EntityType<NerdCorgi>> CORGI_NERD = DEFERRED_REGISTER.register("corgi_nerd", () -> registerEntity(EntityType.Builder.of(NerdCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_nerd"));
    public static final RegistryObject<EntityType<PirateCorgi>> CORGI_PIRATE = DEFERRED_REGISTER.register("corgi_pirate", () -> registerEntity(EntityType.Builder.of(PirateCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_pirate"));
    public static final RegistryObject<EntityType<RadioactiveCorgi>> CORGI_RADIOACTIVE = DEFERRED_REGISTER.register("corgi_radioactive", () -> registerEntity(EntityType.Builder.of(RadioactiveCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_radioactive"));
    public static final RegistryObject<EntityType<SkeletonCorgi>> CORGI_SKELETON = DEFERRED_REGISTER.register("corgi_skeleton", () -> registerEntity(EntityType.Builder.of(SkeletonCorgi::new, MobCategory.MONSTER).sized(1f, 0.5f), "corgi_skeleton"));
    public static final RegistryObject<EntityType<SpyCorgi>> CORGI_SPY = DEFERRED_REGISTER.register("corgi_spy", () -> registerEntity(EntityType.Builder.of(SpyCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_spy"));
    public static final RegistryObject<EntityType<SunglassesCorgi>> CORGI_SUNGLASSES = DEFERRED_REGISTER.register("corgi_sunglasses", () -> registerEntity(EntityType.Builder.of(SunglassesCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_sunglasses"));
    public static final RegistryObject<EntityType<ZombieCorgi>> CORGI_ZOMBIE = DEFERRED_REGISTER.register("corgi_zombie", () -> registerEntity(EntityType.Builder.of(ZombieCorgi::new, MobCategory.MONSTER).sized(1f, 0.5f), "corgi_zombie"));

    private static EntityType registerEntity(EntityType.Builder<?> builder, String entityName) {
        return builder.build(entityName);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(CORGI_NORMAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Corgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_ANTI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AntiCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_BODYGUARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodyguardCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_BUSINESS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BusinessCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_CREEPER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CreeperCorgi::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_FABBE50.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fabbe50Corgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_FARMER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FarmerCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_HERO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HeroCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_LOVE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LoveCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_MELON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MelonCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_NERD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NerdCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_PIRATE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PirateCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_RADIOACTIVE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RadioactiveCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_SKELETON.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkeletonCorgi::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_SPY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_SUNGLASSES.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SunglassesCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CORGI_ZOMBIE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieCorgi::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
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
