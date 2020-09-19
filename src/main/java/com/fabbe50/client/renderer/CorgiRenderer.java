package com.fabbe50.client.renderer;

import com.fabbe50.client.model.CorgiModel;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class CorgiRenderer extends AbstractCorgiRenderer {
    public CorgiRenderer(EntityRendererManager manager) {
        super(manager, new CorgiModel<>());
    }

    private int rotationAngle;
    @Override
    public void render(MobEntity entity, float p_225623_2_, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLightIn) {
        super.render(entity, p_225623_2_, partialTicks, matrixStack, renderTypeBuffer, packedLightIn);
        if (entity instanceof CorgiEntity) {
            if (((CorgiEntity) entity).isAskedToStay() && entity == this.renderManager.pointedEntity) {
                matrixStack.push();
                matrixStack.scale(0.3f, 0.3f, 0.3f);
                matrixStack.rotate(new Quaternion(0, rotationAngle++, 0, true));
                matrixStack.translate(-0.5, 2, -0.5);
                drawWireFrame(matrixStack, renderTypeBuffer, Color.WHITE);
                matrixStack.pop();
            }
        }
    }

    @Override
    protected float handleRotationFloat(MobEntity livingBase, float partialTicks) {
        return ((CorgiEntity)livingBase).getTailRotation();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return ((CorgiEntity)entity).getCorgiType().getResourceLocation();
    }

    private static void drawWireFrame(MatrixStack matrixStack, IRenderTypeBuffer renderBuffers, Color color) {
        final Vec3d [] BASE_VERTICES = {
                new Vec3d(0, 1, 0),
                new Vec3d(1, 1, 0),
                new Vec3d(1, 1, 1),
                new Vec3d(0, 1, 1),
        };
        final Vec3d APEX_VERTEX = new Vec3d(0.5, 0, 0.5);

        IVertexBuilder vertexBuilderLines = renderBuffers.getBuffer(RenderType.LINES);
        Matrix4f matrixPos = matrixStack.getLast().getMatrix();
        for (int i = 1; i < BASE_VERTICES.length; ++i) {
            drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[i-1], BASE_VERTICES[i]);
        }
        drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[BASE_VERTICES.length - 1], BASE_VERTICES[0]);

        for (Vec3d baseVertex : BASE_VERTICES) {
            drawLine(matrixPos, vertexBuilderLines, color, APEX_VERTEX, baseVertex);
        }
    }

    private static void drawLine(Matrix4f matrixPos, IVertexBuilder renderBuffer, Color color, Vec3d startVertex, Vec3d endVertex) {
        renderBuffer.pos(matrixPos, (float) startVertex.x, (float) startVertex.y, (float) startVertex.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .endVertex();
        renderBuffer.pos(matrixPos, (float) endVertex.x, (float) endVertex.y, (float) endVertex.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .endVertex();
    }
}
