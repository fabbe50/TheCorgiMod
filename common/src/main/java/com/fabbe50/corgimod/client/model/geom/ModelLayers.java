package com.fabbe50.corgimod.client.model.geom;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.model.*;
import com.fabbe50.corgimod.client.renderer.layer.CreeperCorgiPowerLayer;
import com.fabbe50.corgimod.client.renderer.layer.SkeletonCorgiHeldItemLayer;
import com.google.common.collect.Sets;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class ModelLayers {
    private static final String DEFAULT_LAYER = "main";

    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();


    public static final ModelLayerLocation CORGI_NORMAL = register("corgi_normal");
    public static final ModelLayerLocation CORGI_ANTI = register("corgi_anti");
    public static final ModelLayerLocation CORGI_BODYGUARD = register("corgi_bodyguard");
    public static final ModelLayerLocation CORGI_BUSINESS = register("corgi_business");
    public static final ModelLayerLocation CORGI_CREEPER = register("corgi_creeper");
    public static final ModelLayerLocation CORGI_CREEPER_ARMOR = register("corgi_creeper", "armor");
    public static final ModelLayerLocation CORGI_FABBE50 = register("corgi_fabbe50");
    public static final ModelLayerLocation CORGI_FARMER = register("corgi_farmer");
    public static final ModelLayerLocation CORGI_HERO = register("corgi_hero");
    public static final ModelLayerLocation CORGI_LOVE = register("corgi_love");
    public static final ModelLayerLocation CORGI_MELON = register("corgi_melon");
    public static final ModelLayerLocation CORGI_NERD = register("corgi_nerd");
    public static final ModelLayerLocation CORGI_PIRATE = register("corgi_pirate");
    public static final ModelLayerLocation CORGI_RADIOACTIVE = register("corgi_radioactive");
    public static final ModelLayerLocation CORGI_SKELETON = register("corgi_skeleton");
    public static final ModelLayerLocation CORGI_SKELETON_HELD_ITEM = register("corgi_skeleton", "held_item");
    public static final ModelLayerLocation CORGI_SPY = register("corgi_spy");
    public static final ModelLayerLocation CORGI_SUNGLASSES = register("corgi_sunglasses");
    public static final ModelLayerLocation CORGI_ZOMBIE = register("corgi_zombie");

    private static ModelLayerLocation register(String name) {
        return register(name, DEFAULT_LAYER);
    }

    private static ModelLayerLocation register(String name, String layer) {
        ModelLayerLocation modelLayerLocation = createLocation(name, layer);
        if (!ALL_MODELS.add(modelLayerLocation)) {
            throw new IllegalStateException("Duplicate registration for " + modelLayerLocation);
        } else {
            return modelLayerLocation;
        }
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, model), layer);
    }

    public static void registerDefinitions() {
        EntityModelLayerRegistry.register(CORGI_NORMAL, CorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_ANTI, AntiCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_BODYGUARD, BodyguardCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_BUSINESS, BusinessCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_CREEPER, CreeperCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_CREEPER_ARMOR, CreeperCorgiPowerLayer::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_FABBE50, Fabbe50CorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_FARMER, FarmerCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_HERO, HeroCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_LOVE, LoveCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_MELON, MelonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_NERD, NerdCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_PIRATE, PirateCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_RADIOACTIVE, RadioactiveCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SKELETON, SkeletonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SKELETON_HELD_ITEM, SkeletonCorgiHeldItemLayer::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SPY, SpyCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SUNGLASSES, SunglassesCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_ZOMBIE, ZombieCorgiModel::createBodyLayer);
    }
}
