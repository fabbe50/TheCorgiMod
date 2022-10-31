package com.fabbe50.corgimod.client.model.geom;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.*;
import com.fabbe50.corgimod.client.renderer.layer.CreeperCorgiPowerLayer;
import com.fabbe50.corgimod.client.renderer.layer.SkeletonCorgiHeldItemLayer;
import com.google.common.collect.Sets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

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
        return new ModelLayerLocation(new ResourceLocation(CorgiMod.MODID, model), layer);
    }

    public static void registerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CORGI_NORMAL, CorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_ANTI, AntiCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_BODYGUARD, BodyguardCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_BUSINESS, BusinessCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_CREEPER, CreeperCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_CREEPER_ARMOR, CreeperCorgiPowerLayer::createBodyLayer);
        event.registerLayerDefinition(CORGI_FABBE50, Fabbe50CorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_HERO, HeroCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_LOVE, LoveCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_MELON, MelonCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_NERD, NerdCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_PIRATE, PirateCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_RADIOACTIVE, RadioactiveCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_SKELETON, SkeletonCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_SKELETON_HELD_ITEM, SkeletonCorgiHeldItemLayer::createBodyLayer);
        event.registerLayerDefinition(CORGI_SPY, SpyCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_SUNGLASSES, SunglassesCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_ZOMBIE, ZombieCorgiModel::createBodyLayer);
    }
}
