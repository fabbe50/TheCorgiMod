package com.fabbe50.corgimod.world.entity;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(CORGI_NORMAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Corgi::checkAnimalSpawnRules);
        SpawnPlacements.register(CORGI_ANTI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AntiCorgi::checkAnimalSpawnRules);
        SpawnPlacements.register(CORGI_BODYGUARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodyguardCorgi::checkAnimalSpawnRules);
        SpawnPlacements.register(CORGI_BUSINESS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BusinessCorgi::checkAnimalSpawnRules);
        SpawnPlacements.register(CORGI_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CreeperCorgi::checkMonsterSpawnRules);

        event.put(CORGI_NORMAL.get(), Corgi.createAttributes().build());
        event.put(CORGI_ANTI.get(), AntiCorgi.createAttributes().build());
        event.put(CORGI_BODYGUARD.get(), BodyguardCorgi.createAttributes().build());
        event.put(CORGI_BUSINESS.get(), BusinessCorgi.createAttributes().build());
        event.put(CORGI_CREEPER.get(), CreeperCorgi.createAttributes().build());
    }
}
