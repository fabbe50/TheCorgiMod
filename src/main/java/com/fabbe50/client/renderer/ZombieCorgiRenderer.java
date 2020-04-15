package com.fabbe50.client.renderer;

import com.fabbe50.client.model.ZombieCorgiModel;
import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.corgis.ZombieCorgiEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class ZombieCorgiRenderer extends AbstractCorgiRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/corgi/corgi_zombie.png");

    public ZombieCorgiRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ZombieCorgiModel());
    }

    @Override
    protected float handleRotationFloat(MobEntity livingBase, float partialTicks) {
        return ((ZombieCorgiEntity)livingBase).getTailRotation();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return TEXTURE;
    }
}
