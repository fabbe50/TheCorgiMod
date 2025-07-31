package com.fabbe50.corgimod.world.entity;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.fabbe50.corgimod.world.entity.feature.*;
import com.fabbe50.corgimod.world.entity.feature.corgis.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Corgis {
    // TAMABLE CORGIS
    NORMAL(0, "normal", "Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_normal.png"), CorgiVariants.NORMAL, new BaseCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("normal")),
    ANTI(1, "anti", "Anti Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_anti.png"), CorgiVariants.ANTI, new AntiCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("anti")),
    BODYGUARD(2, "bodyguard", "Bodyguard Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_bodyguard.png"), CorgiVariants.BODYGUARD, new BodyguardCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("bodyguard")),
    BUSINESS(3, "business", "Business Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_business.png"), CorgiVariants.BUSINESS, new BaseCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("business")),
    FABBE50(5, "fabbe50", "fabbe50 Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_fabbe50.png"), CorgiVariants.FABBE50, new fabbe50CorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("fabbe50")),
    FARMER(6, "farmer", "Farmer Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_farmer.png"), CorgiVariants.FARMER, new FarmerCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("farmer")),
    HERO(7, "hero", "Hero Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_hero.png"), CorgiVariants.HERO, new HeroCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("hero")),
    LOVE(8, "love", "Love Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_love.png"), CorgiVariants.LOVE, new LoveCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("love")),
    MELON(9, "melon", "Melon Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_melon.png"), CorgiVariants.MELON, new MelonCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("melon")),
    NERD(10, "nerd", "Nerd Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_nerd.png"), CorgiVariants.NERD, new BaseCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("nerd")),
    PIRATE(11, "pirate", "Pirate Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_pirate.png"), CorgiVariants.PIRATE, new PirateCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("pirate")),
    RADIOACTIVE(12, "radioactive", "Radioactive Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_radioactive.png"), CorgiVariants.RADIOACTIVE, new RadioactiveCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("radioactive")),
    SPY(14, "spy", "Spy Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_spy.png"), CorgiVariants.SPY, new SpyCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("spy")),
    SUNGLASSES(15, "sunglasses", "Sunglasses Corgi", TheCorgiMod.location("textures/entity/corgi/corgi_sunglasses.png"), CorgiVariants.SUNGLASSES, new SunglassesCorgiFeaturePack(), EntityRegistry.CORGI.get(), false, registerLootTable("sunglasses")),

    // HOSTILE CORGIS
    BOGGED(19, "bogged", "Bogged Corgi", TheCorgiMod.location("textures/entity/bogged_corgi.png"), null, null, EntityRegistry.BOGGED_CORGI.get(), true, null),
    CREEPER(4, "creeper", "Creeper Corgi", TheCorgiMod.location("textures/entity/creeper_corgi.png"), null, null, EntityRegistry.CREEPER_CORGI.get(), true, null),
    DROWNED(23, "drowned", "Drowned Corgi", TheCorgiMod.location("textures/entity/drowned_corgi.png"), null, null, EntityRegistry.DROWNED_CORGI.get(), true, null),
    ENDER(17, "ender", "Ender Corgi", TheCorgiMod.location("textures/entity/ender_corgi.png"), null, null, EntityRegistry.ENDER_CORGI.get(), true, null),
    HUSK(22, "husk", "Husk Corgi", TheCorgiMod.location("textures/entity/husk_corgi.png"), null, null, EntityRegistry.HUSK_CORGI.get(), true, null),
    SKELETON(13, "skeleton", "Skeleton Corgi", TheCorgiMod.location("textures/entity/skeleton_corgi.png"), null, null, EntityRegistry.SKELETON_CORGI.get(), true, null),
    SPIDER(18, "spider", "Spider Corgi", TheCorgiMod.location("textures/entity/spider_corgi.png"), null, null, EntityRegistry.SPIDER_CORGI.get(), true, null),
    STRAY(20, "stray", "Stray Corgi", TheCorgiMod.location("textures/entity/stray_corgi.png"), null, null, EntityRegistry.STRAY_CORGI.get(), true, null),
    WITHER_SKELETON(21, "wither_skeleton", "Wither Skeleton Corgi", TheCorgiMod.location("textures/entity/wither_skeleton_corgi.png"), null, null, EntityRegistry.WITHER_SKELETON_CORGI.get(), true, null),
    ZOMBIE(16, "zombie", "Zombie Corgi", TheCorgiMod.location("textures/entity/zombie_corgi.png"), null, null, EntityRegistry.ZOMBIE_CORGI.get(), true, null)
    ;


    private final int id;
    private final String name;
    private final String formattedName;
    private final ResourceLocation textureLocation;
    private final ResourceKey<CorgiVariant> corgiVariant;
    private final IFeaturePack<Corgi> corgiFeaturePack;
    private final EntityType<? extends net.minecraft.world.entity.Entity> entityType;
    private final boolean hostile;
    private final ResourceKey<LootTable> defaultLootTable;
    Corgis(int id, String name, String formattedName, ResourceLocation textureLocation, ResourceKey<CorgiVariant> corgiVariant, IFeaturePack<Corgi> corgiFeaturePack, EntityType<? extends net.minecraft.world.entity.Entity> entityType, boolean hostile, ResourceKey<LootTable> defaultLootTable) {
        this.id = id;
        this.name = name;
        this.formattedName = formattedName;
        this.textureLocation = textureLocation;
        this.corgiVariant = corgiVariant;
        this.corgiFeaturePack = corgiFeaturePack;
        this.entityType = entityType;
        this.hostile = hostile;
        this.defaultLootTable = defaultLootTable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public static List<Corgis> getCorgis() {
        return Arrays.stream(Corgis.values()).toList();
    }

    public ResourceKey<CorgiVariant> getCorgiVariant() {
        return corgiVariant;
    }

    public IFeaturePack<Corgi> getCorgiFeaturePack() {
        return corgiFeaturePack;
    }

    public EntityType<? extends net.minecraft.world.entity.Entity> getEntityType() {
        return entityType;
    }

    public ResourceKey<LootTable> getDefaultLootTable() {
        return defaultLootTable;
    }

    public static Corgi getOffspringFromParents(ServerLevel level, Corgi parent1, Corgi parent2) {
        Corgi child;
        if (parent1.getRandom().nextBoolean()) {
            child = (Corgi) parent1.getVariantType().getEntityType().create(level);
            if (child != null) {
                child.setVariant(parent1.getVariant());
            }
        } else {
            child = (Corgi) parent2.getVariantType().getEntityType().create(level);
            if (child != null) {
                child.setVariant(parent2.getVariant());
            }
        }
        if (child != null) {
            if (parent1.isTame()) {
                child.setOwnerUUID(parent1.getOwnerUUID());
                child.setTame(true, true);
                if (parent1.getRandom().nextBoolean()) {
                    child.setCollarColor(parent1.getCollarColor());
                } else {
                    child.setCollarColor(parent2.getCollarColor());
                }
            }
        }
        return child;
    }

    public static Corgi getRandomOffspring(ServerLevel level, Corgi parent) {
        List<Corgis> tamableCorgis = getNonHostileCorgis();
        Corgis randomCorgi = tamableCorgis.get(level.getRandom().nextInt(0, tamableCorgis.size()));
        Corgi child = (Corgi) randomCorgi.getEntityType().create(level);
        if (child != null) {
            if (parent.isTame()) {
                child.setOwnerUUID(parent.getOwnerUUID());
                child.setTame(true, true);
            }
        }
        return child;
    }

    public static Corgis getCorgiFromID(int id) {
        for (Corgis corgis : getCorgis()) {
            if (corgis.getId() == id) {
                return corgis;
            }
        }
        return Corgis.NORMAL;
    }

    public static Corgis getCorgiFromVariant(ResourceKey<CorgiVariant> corgiVariant) {
        for (Corgis corgis : getCorgis()) {
            if (corgis.getCorgiVariant() != null && corgis.getCorgiVariant().equals(corgiVariant)) {
                return corgis;
            }
        }
        return NORMAL;
    }

    public static Corgis getCorgiFromEntity(Entity entity) {
        for (Corgis corgi : values()) {
            if (corgi.getEntityType().tryCast(entity) != null) {
                return corgi;
            }
        }
        return NORMAL;
    }

    public static List<Corgis> getNonHostileCorgis() {
        List<Corgis> tmp = new ArrayList<>();
        for (Corgis corgi : Corgis.values()) {
            if (!corgi.hostile)
                tmp.add(corgi);
        }
        return tmp;
    }

    private static ResourceKey<LootTable> registerLootTable(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, TheCorgiMod.location("entities/corgis/" + name));
    }
}
