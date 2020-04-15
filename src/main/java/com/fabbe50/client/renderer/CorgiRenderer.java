package com.fabbe50.client.renderer;

import com.fabbe50.client.model.CorgiModel;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class CorgiRenderer extends AbstractCorgiRenderer {
    public CorgiRenderer(EntityRendererManager manager) {
        super(manager, new CorgiModel<>());
    }

    @Override
    protected float handleRotationFloat(MobEntity livingBase, float partialTicks) {
        return ((CorgiEntity)livingBase).getTailRotation();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return ((CorgiEntity)entity).getCorgiType().getResourceLocation();
    }
}
