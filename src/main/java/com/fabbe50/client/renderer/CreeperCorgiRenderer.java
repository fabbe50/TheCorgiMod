package com.fabbe50.client.renderer;

import com.fabbe50.client.model.AbstractCorgiModel;
import com.fabbe50.client.model.CreeperCorgiModel;
import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.corgis.CreeperCorgiEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class CreeperCorgiRenderer extends AbstractCorgiRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/corgi/corgi_creeper.png");

    public CreeperCorgiRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CreeperCorgiModel());
    }

    @Override
    protected float handleRotationFloat(MobEntity livingBase, float partialTicks) {
        return ((CreeperCorgiEntity)livingBase).getTailRotation();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return TEXTURE;
    }
}
