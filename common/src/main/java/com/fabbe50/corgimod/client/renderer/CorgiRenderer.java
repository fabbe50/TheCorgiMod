package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.client.model.CorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.layer.CorgiCollarLayer;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

import java.awt.*;

public class CorgiRenderer<T extends Corgi> extends MobRenderer<T, CorgiModel<T>> {
    private int rotationAngle;
    private final EntityRendererProvider.Context context;

    public CorgiRenderer(EntityRendererProvider.Context context) {
        super(context, new CorgiModel<>(context.bakeLayer(ModelLayers.CORGI)), 0.5f);
        this.addLayer(new CorgiCollarLayer<>(this));
        this.context = context;
    }

    @Override
    protected float getBob(T corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public void render(T corgi, float p_115456_, float ageInTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_115460_) {
        if (corgi.isWet()) {
            float f = corgi.getWetShade(ageInTicks);
            this.model.setColor(ARGB32.colorFromFloat(1.0f, f, f, f));
        }

        super.render(corgi, p_115456_, ageInTicks, poseStack, multiBufferSource, p_115460_);
        if (corgi.isWet()) {
            this.model.setColor(-1);
        }
        if (corgi.isAskedToStay() && corgi == this.context.getEntityRenderDispatcher().crosshairPickEntity) {
            if (rotationAngle == 36000) {
                rotationAngle = 0;
            }
            float y = 2;
            poseStack.pushPose();
            poseStack.scale(0.3f, 0.3f, 0.3f);
            poseStack.mulPose(new Quaternionf(new AxisAngle4f(rotationAngle / 100F, 0, 1, 0)));
            rotationAngle++;
            poseStack.translate(-0.5, Mth.sin((rotationAngle + ageInTicks / 20F + corgi.bobs) * 0.01F + 0.1F) / 3 + (corgi.getEyeHeight() + 1.5F), -0.5);
            drawWireFrame(poseStack, multiBufferSource, (!corgi.isWithinRangeOfOrigin() && ModConfig.<Boolean>getValue("redWireFrameWhenCorgiTooFarFromStayPosition").getValue()) ? Color.RED : Color.WHITE);
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
        for (int i = 1; i < BASE_VERTICES.length; i++) {
            drawLine(poseStack.last(), matrix4f, vertexConsumer, color, BASE_VERTICES[i - 1], BASE_VERTICES[i]);
        }
        drawLine(poseStack.last(), matrix4f, vertexConsumer, color, BASE_VERTICES[BASE_VERTICES.length - 1], BASE_VERTICES[0]);
        for (Vector3d vertex : BASE_VERTICES) {
            drawLine(poseStack.last(), matrix4f, vertexConsumer, color, APEX_VERTEX, vertex);
        }
    }

    private void drawLine(PoseStack.Pose pose, Matrix4f matrix4f, VertexConsumer vertexConsumer, Color color, Vector3d startVertex, Vector3d endVertex) {
        vertexConsumer.addVertex(matrix4f, (float) startVertex.x, (float) startVertex.y, (float) startVertex.z)
                .setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .setNormal(pose, (float) startVertex.x, (float) startVertex.y, (float) startVertex.z);
        vertexConsumer.addVertex(matrix4f, (float) endVertex.x, (float) endVertex.y, (float) endVertex.z)
                .setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .setNormal(pose, (float) endVertex.x, (float) endVertex.y, (float) endVertex.z);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Corgi corgi) {
        return corgi.getTexture();
    }
}
