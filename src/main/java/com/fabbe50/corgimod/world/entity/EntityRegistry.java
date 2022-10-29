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
    public static final RegistryObject<EntityType<LoveCorgi>> CORGI_LOVE = DEFERRED_REGISTER.register("corgi_love", () -> registerEntity(EntityType.Builder.of(LoveCorgi::new, MobCategory.CREATURE).sized(1f, 0.5f), "corgi_love"));

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
        event.register(CORGI_LOVE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LoveCorgi::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        event.put(CORGI_NORMAL.get(), Corgi.createAttributes().build());
        event.put(CORGI_ANTI.get(), AntiCorgi.createAttributes().build());
        event.put(CORGI_BODYGUARD.get(), BodyguardCorgi.createAttributes().build());
        event.put(CORGI_BUSINESS.get(), BusinessCorgi.createAttributes().build());
        event.put(CORGI_CREEPER.get(), CreeperCorgi.createAttributes().build());
        event.put(CORGI_LOVE.get(), LoveCorgi.createAttributes().build());
    }
}
