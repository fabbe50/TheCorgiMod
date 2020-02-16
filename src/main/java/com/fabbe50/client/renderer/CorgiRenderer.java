package com.fabbe50.client.renderer;

import com.fabbe50.client.model.CorgiModel;
import com.fabbe50.corgis.entities.CorgiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class CorgiRenderer extends MobRenderer<CorgiEntity, CorgiModel<CorgiEntity>> {
    public CorgiRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CorgiModel<>(), 0.4f);
    }

    protected float handleRotationFloat(CorgiEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    public void render(CorgiEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int partialTicks) {
        if (entity.isWet()) {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            this.entityModel.func_228253_a_(f, f, f);
        }

        super.render(entity, p_225623_2_, p_225623_3_, matrixStack, renderTypeBuffer, partialTicks);
        if (entity.isWet()) {
            this.entityModel.func_228253_a_(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(CorgiEntity entity) {
        return entity.getCorgiType().getResourceLocation();
    }
}
