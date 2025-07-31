package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.model.CorgiModel;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CorgiCollarLayer<T extends Corgi> extends RenderLayer<T, CorgiModel<T>> {
    private static final ResourceLocation CORGI_COLLAR_TEXTURE = TheCorgiMod.location("textures/entity/corgi/corgi_collar.png");
    private static final ResourceLocation CORGI_COLLAR_TEXTURE_EMISSIVE = TheCorgiMod.location("textures/entity/corgi/corgi_collar_e.png");

    public CorgiCollarLayer(RenderLayerParent<T, CorgiModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        if (entity.isTame() && !entity.isInvisible()) {
            int color = entity.getCollarColor().getTextureDiffuseColor();
            VertexConsumer vertexConsumer;
            if (entity.isCollarGlow()) {
                vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(CORGI_COLLAR_TEXTURE_EMISSIVE));
            } else {
                vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(CORGI_COLLAR_TEXTURE));
            }
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, color);
        }
    }
}
