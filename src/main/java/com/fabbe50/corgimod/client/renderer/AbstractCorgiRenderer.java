package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.AbstractCorgiModel;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

import java.awt.*;

public abstract class AbstractCorgiRenderer<T extends Corgi, M extends AbstractCorgiModel<T>> extends MobRenderer<T, M> {
    private int rotationAngle;
    private final EntityRendererProvider.Context context;

    public AbstractCorgiRenderer(EntityRendererProvider.Context context, M model) {
        super(context, model, 0.5f);
        this.context = context;
    }

    @Override
    protected float getBob(Corgi corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public void render(T corgi, float p_115456_, float ageInTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_115460_) {
        if (corgi.isWet()) {
            float f = corgi.getWetShade(ageInTicks);
            this.model.setColor(f, f, f);
        }

        super.render(corgi, p_115456_, ageInTicks, poseStack, multiBufferSource, p_115460_);
        if (corgi.isWet()) {
            this.model.setColor(1.0f, 1.0f, 1.0f);
        }
        if (corgi.isAskedToStay() && corgi == this.context.getEntityRenderDispatcher().crosshairPickEntity) {
            if (rotationAngle == 36000) {
                rotationAngle = 0;
            }
            poseStack.pushPose();
            poseStack.scale(0.3f, 0.3f, 0.3f);
            poseStack.mulPose(new Quaternionf(new AxisAngle4f(rotationAngle / 100F, 0, 1, 0)));
            rotationAngle++;
            poseStack.translate(-0.5, 2, -0.5);
            drawWireFrame(poseStack, multiBufferSource, Color.WHITE);
            poseStack.popPose();
        }
    }

    private void drawWireFrame(PoseStack poseStack, MultiBufferSource multiBufferSource, Color color) {
        final Vector3d[] BASE_VERTICES = {
                new Vector3d(0, 1, 0),
                new Vector3d(1, 1, 0),
                new Vector3d(1, 1, 1),
                new Vector3d(0, 1, 1)
        };
        final Vector3d APEX_VERTEX = new Vector3d(0.5, 0, 0.5);

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.LINES);
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        for (int i = 1; i < BASE_VERTICES.length; i++) {
            drawLine(matrix4f, matrix3f, vertexConsumer, color, BASE_VERTICES[i - 1], BASE_VERTICES[i]);
        }
        drawLine(matrix4f, matrix3f, vertexConsumer, color, BASE_VERTICES[BASE_VERTICES.length - 1], BASE_VERTICES[0]);
        for (Vector3d vertex : BASE_VERTICES) {
            drawLine(matrix4f, matrix3f, vertexConsumer, color, APEX_VERTEX, vertex);
        }
    }

    private void drawLine(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, Color color, Vector3d startVertex, Vector3d endVertex) {
        vertexConsumer.vertex(matrix4f, (float) startVertex.x, (float) startVertex.y, (float) startVertex.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .normal(matrix3f, (float) startVertex.x, (float) startVertex.y, (float) startVertex.z)
                .endVertex();
        vertexConsumer.vertex(matrix4f, (float) endVertex.x, (float) endVertex.y, (float) endVertex.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .normal(matrix3f, (float) endVertex.x, (float) endVertex.y, (float) endVertex.z)
                .endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return Corgis.NORMAL.getTextureLocation();
    }
}
