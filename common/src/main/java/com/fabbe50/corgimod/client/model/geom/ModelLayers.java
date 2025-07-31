package com.fabbe50.corgimod.client.model.geom;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.model.*;
import com.fabbe50.corgimod.client.renderer.layer.CreeperCorgiPowerLayer;
import com.fabbe50.corgimod.client.renderer.layer.SkeletonCorgiHeldItemLayer;
import com.google.common.collect.Sets;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

import java.util.Set;

public class ModelLayers {
    private static final String DEFAULT_LAYER = "main";

    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();

    public static final ModelLayerLocation CORGI = register("corgi");
    public static final ModelLayerLocation CORGI_BOGGED = register("corgi_bogged");
    public static final ModelLayerLocation CORGI_CREEPER = register("corgi_creeper");
    public static final ModelLayerLocation CORGI_CREEPER_ARMOR = register("corgi_creeper", "armor");
    public static final ModelLayerLocation CORGI_DROWNED = register("corgi_drowned");
    public static final ModelLayerLocation CORGI_ENDER = register("corgi_ender");
    public static final ModelLayerLocation CORGI_ENDER_EYES = register("corgi_ender_eyes");
    public static final ModelLayerLocation CORGI_HUSK = register("corgi_husk");
    public static final ModelLayerLocation CORGI_SKELETON = register("corgi_skeleton");
    public static final ModelLayerLocation CORGI_SKELETON_HELD_ITEM = register("corgi_skeleton", "held_item");
    public static final ModelLayerLocation CORGI_SPIDER = register("corgi_spider");
    public static final ModelLayerLocation CORGI_STRAY = register("corgi_stray");
    public static final ModelLayerLocation CORGI_WITHER_SKELETON = register("corgi_wither_skeleton");
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
        return new ModelLayerLocation(TheCorgiMod.location(model), layer);
    }

    public static void registerDefinitions() {
        EntityModelLayerRegistry.register(CORGI, BaseCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_BOGGED, SkeletonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_CREEPER, BaseCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_CREEPER_ARMOR, CreeperCorgiPowerLayer::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_DROWNED, BaseCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_ENDER, EnderCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_ENDER_EYES, EnderCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_HUSK, BaseCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SKELETON, SkeletonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SKELETON_HELD_ITEM, SkeletonCorgiHeldItemLayer::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_SPIDER, SpiderCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_STRAY, SkeletonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_WITHER_SKELETON, SkeletonCorgiModel::createBodyLayer);
        EntityModelLayerRegistry.register(CORGI_ZOMBIE, BaseCorgiModel::createBodyLayer);
    }
}
