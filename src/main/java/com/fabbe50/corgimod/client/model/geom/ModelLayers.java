package com.fabbe50.corgimod.client.model.geom;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.*;
import com.fabbe50.corgimod.client.renderer.CreeperCorgiPowerLayer;
import com.fabbe50.corgimod.world.entity.animal.ZombieCorgi;
import com.google.common.collect.Sets;
import net.minecraft.client.model.Model;
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
    public static final ModelLayerLocation CORGI_LOVE = register("corgi_love");
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
        event.registerLayerDefinition(CORGI_LOVE, LoveCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_ZOMBIE, ZombieCorgiModel::createBodyLayer);
    }
}
