package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.client.model.SkeletonCorgiModel;
import com.fabbe50.corgimod.world.entity.animal.SkeletonCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SkeletonCorgiHeldItemLayer extends RenderLayer<SkeletonCorgi, SkeletonCorgiModel<SkeletonCorgi>> {
    private final ItemInHandRenderer handRenderer;

    public SkeletonCorgiHeldItemLayer(RenderLayerParent<SkeletonCorgi, SkeletonCorgiModel<SkeletonCorgi>> renderLayerParent, ItemInHandRenderer handRenderer) {
        super(renderLayerParent);
        this.handRenderer = handRenderer;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, SkeletonCorgi corgi, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
        poseStack.pushPose();
        poseStack.translate((double)((this.getParentModel()).head.x / 16.0F), (double)((this.getParentModel()).head.y / 16.0F), (double)((this.getParentModel()).head.z / 16.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(p_117015_));
        poseStack.mulPose(Axis.XP.rotationDegrees(p_117016_));
        poseStack.translate((double)0.09F, (double)-0.07F, -0.2D);
        poseStack.mulPose(Axis.YP.rotationDegrees(225F));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        ItemStack itemstack = corgi.getItemBySlot(EquipmentSlot.MAINHAND);
        this.handRenderer.renderItem(corgi, itemstack, ItemTransforms.TransformType.GROUND, false, poseStack, multiBufferSource, i);
        poseStack.popPose();
    }
}
