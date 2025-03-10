package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.AbstractCorgiModel;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CorgiCollarLayer<T extends Corgi, M extends AbstractCorgiModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation CORGI_COLLAR_TEXTURE = new ResourceLocation(CorgiMod.MODID, "textures/entity/corgi/corgi_collar.png");
    private static final ResourceLocation CORGI_COLLAR_TEXTURE_EMISSIVE = new ResourceLocation(CorgiMod.MODID, "textures/entity/corgi/corgi_collar_e.png");

    public CorgiCollarLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull T entity, float v, float v1, float v2, float v3, float v4, float v5) {
        if (entity.isTame() && !entity.isInvisible()) {
            float[] colors = entity.getCollarColor().getTextureDiffuseColors();
            VertexConsumer vertexConsumer;
            if (entity.isCollarGlow()) {
                vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(CORGI_COLLAR_TEXTURE_EMISSIVE));
            } else {
                vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(CORGI_COLLAR_TEXTURE));
            }
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, colors[0], colors[1], colors[2], 1);
        }
    }
}
