package com.fabbe50.client.renderer;

import com.fabbe50.client.model.AbstractCorgiModel;
import com.fabbe50.corgis.Reference;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCorgiRenderer<T extends MobEntity, M extends AbstractCorgiModel> extends MobRenderer<T, AbstractCorgiModel<T>> {
    private static final ResourceLocation CORGI = new ResourceLocation(Reference.MOD_ID, "textures/entity/corgi/corgi_normal.png");

    public AbstractCorgiRenderer(EntityRendererManager rendermanagerIn, M model) {
        super(rendermanagerIn, model, 0.4f);
    }

    @Override
    protected float handleRotationFloat(T livingBase, float partialTicks) {
        return super.handleRotationFloat(livingBase, partialTicks);
    }

    public void render(T entity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int partialTicks) {
        if (entity instanceof CorgiEntity) {
            if (entity.isWet()) {
                float f = entity.getBrightness() * ((CorgiEntity) entity).getShadingWhileWet(partialTicks);
                this.entityModel.setTint(f, f, f);
            }
        }
        super.render(entity, p_225623_2_, p_225623_3_, matrixStack, renderTypeBuffer, partialTicks);
        if (entity.isWet()) {
            this.entityModel.setTint(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return CORGI;
    }
}
