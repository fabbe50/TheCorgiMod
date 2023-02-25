package com.fabbe50.corgimod.data;

import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.fabbe50.corgimod.world.entity.animal.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Corgis {
    NORMAL(0, "normal", "Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_normal.png"), EntityRegistry.CORGI_NORMAL.get(), Corgi.class, false),
    ANTI(1, "anti", "Anti Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_anti.png"), EntityRegistry.CORGI_ANTI.get(), AntiCorgi.class,  false),
    BODYGUARD(2, "bodyguard", "Bodyguard Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_bodyguard.png"), EntityRegistry.CORGI_BODYGUARD.get(), BodyguardCorgi.class, false),
    BUSINESS(3, "business", "Business Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_business.png"), EntityRegistry.CORGI_BUSINESS.get(), BusinessCorgi.class, false),
    CREEPER(4, "creeper", "Creeper Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_creeper.png"), EntityRegistry.CORGI_CREEPER.get(), CreeperCorgi.class, true),
    FABBE50(5, "fabbe50", "fabbe50 Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_fabbe50.png"), EntityRegistry.CORGI_FABBE50.get(), Fabbe50Corgi.class, false),
    FARMER(6, "farmer", "Farmer Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_farmer.png"), EntityRegistry.CORGI_FARMER.get(), FarmerCorgi.class, false),
    HERO(7, "hero", "Hero Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_hero.png"), EntityRegistry.CORGI_HERO.get(), HeroCorgi.class, false),
    LOVE(8, "love", "Love Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_love.png"), EntityRegistry.CORGI_LOVE.get(), LoveCorgi.class, false),
    MELON(9, "melon", "Melon Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_melon.png"), EntityRegistry.CORGI_MELON.get(), MelonCorgi.class, false),
    NERD(10, "nerd", "Nerd Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_nerd.png"), EntityRegistry.CORGI_NERD.get(), NerdCorgi.class, false),
    PIRATE(11, "pirate", "Pirate Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_pirate.png"), EntityRegistry.CORGI_PIRATE.get(), PirateCorgi.class, false),
    RADIOACTIVE(12, "radioactive", "Radioactive Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_radioactive.png"), EntityRegistry.CORGI_RADIOACTIVE.get(), RadioactiveCorgi.class, false),
    SKELETON(13, "skeleton", "Skeleton Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_skeleton.png"), EntityRegistry.CORGI_SKELETON.get(), SkeletonCorgi.class, true),
    SPY(14, "spy", "Spy Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_spy.png"), EntityRegistry.CORGI_SPY.get(), SpyCorgi.class, false),
    SUNGLASSES(15, "sunglasses", "Sunglasses Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_sunglasses.png"), EntityRegistry.CORGI_SUNGLASSES.get(), SunglassesCorgi.class, false),
    ZOMBIE(16, "zombie", "Zombie Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_zombie.png"), EntityRegistry.CORGI_ZOMBIE.get(), ZombieCorgi.class, true);

    private final int id;
    private final String name;
    private final String formattedName;
    private final ResourceLocation textureLocation;
    private final EntityType<?> corgiType;
    private final Class<? extends LivingEntity> extendingClass;
    private final boolean hostile;
    Corgis(int id, String name, String formattedName, ResourceLocation textureLocation, EntityType<?> corgiType, Class<? extends LivingEntity> extendingClass, boolean hostile) {
        this.id = id;
        this.name = name;
        this.formattedName = formattedName;
        this.textureLocation = textureLocation;
        this.corgiType = corgiType;
        this.extendingClass = extendingClass;
        this.hostile = hostile;
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

    public EntityType<?> getCorgiType() {
        return corgiType;
    }

    public Class<? extends LivingEntity> getCorgiClass() {
        return extendingClass;
    }

    public static List<Corgis> getCorgis() {
        return Arrays.stream(Corgis.values()).toList();
    }

    public static EntityType<?> getCorgiTypeFromParent(Corgi parent) {
        for (Corgis corgis : getCorgis()) {
            if (corgis.getCorgiClass().equals(parent.getClass())) {
                return corgis.getCorgiType();
            }
        }
        return NORMAL.getCorgiType();
    }

    public static Corgis getCorgiFromID(int id) {
        for (Corgis corgis : getCorgis()) {
            if (corgis.getId() == id) {
                return corgis;
            }
        }
        return Corgis.NORMAL;
    }

    public static List<Corgis> getNonHostileCorgis() {
        List<Corgis> tmp = new ArrayList<>();
        for (Corgis corgi : Corgis.values()) {
            if (!corgi.hostile)
                tmp.add(corgi);
        }
        return tmp;
    }

    public static List<Corgis> getTameWolfBasedCorgis() {
        List<Corgis> tmp = new ArrayList<>();
        for (Corgis corgi : getNonHostileCorgis()) {
            try {
                if (Class.forName(corgi.getCorgiClass().getSuperclass().getName()).isAssignableFrom(Wolf.class) || Class.forName(corgi.getCorgiClass().getSuperclass().getName()).isAssignableFrom(Corgi.class)) {
                    tmp.add(corgi);
                }
            } catch (ClassNotFoundException ignored) {}
        }
        return tmp;
    }
}
