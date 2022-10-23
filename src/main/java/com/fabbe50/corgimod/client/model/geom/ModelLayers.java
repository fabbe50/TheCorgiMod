package com.fabbe50.corgimod.client.model.geom;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.AntiCorgiModel;
import com.fabbe50.corgimod.client.model.BodyguardCorgiModel;
import com.fabbe50.corgimod.client.model.CorgiModel;
import com.google.common.collect.Sets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

import java.util.Set;

public class ModelLayers {
    private static final String DEFAULT_LAYER = "main";

    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();


    public static final ModelLayerLocation CORGI_NORMAL = createLocation("corgi_normal", "main");
    public static final ModelLayerLocation CORGI_ANTI = createLocation("corgi_anti", "main");
    public static final ModelLayerLocation CORGI_BODYGUARD = createLocation("corgi_bodyguard", "main");


    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CORGI_NORMAL, CorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_ANTI, AntiCorgiModel::createBodyLayer);
        event.registerLayerDefinition(CORGI_BODYGUARD, BodyguardCorgiModel::createBodyLayer);
    }


    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(CorgiMod.MODID, model), layer);
    }
}
