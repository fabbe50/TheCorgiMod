package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.client.model.SkeletonCorgiModel;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.ISkeletonCorgi;
import com.fabbe50.corgimod.world.entity.monster.SkeletonCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class SkeletonCorgiHeldItemLayer<T extends AbstractSkeleton & ISkeletonCorgi> extends RenderLayer<T, SkeletonCorgiModel<T>> {
    private final ItemInHandRenderer handRenderer;

    public SkeletonCorgiHeldItemLayer(RenderLayerParent<T, SkeletonCorgiModel<T>> renderLayerParent, ItemInHandRenderer handRenderer) {
        super(renderLayerParent);
        this.handRenderer = handRenderer;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, T corgi, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
        poseStack.pushPose();
        ItemStack itemstack = corgi.getItemBySlot(EquipmentSlot.MAINHAND);

        poseStack.translate((this.getParentModel()).head.x / 16.0F, (this.getParentModel()).head.y / 16.0F, (double)((this.getParentModel()).head.z / 16.0F));
        if (corgi.isBaby()) {
            float scale = 0.75f;
            poseStack.scale(scale, scale, scale);
            float headYOffset = 0.4125f;
            float headZOffset = 0.125f;
            poseStack.translate(0, headYOffset, headZOffset);
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(p_117015_));
        poseStack.mulPose(Axis.XP.rotationDegrees(p_117016_));
        if (itemstack.is(ItemTags.SWORDS)) {
            poseStack.translate(-0.1F, -0.07F, -0.2D);
            rotateTool(poseStack);
        } else if (itemstack.is(Items.BOW)) {
            poseStack.translate(0.09F, -0.07F, -0.2D);
            rotateTool(poseStack);
        } else if (itemstack.getItem() instanceof BlockItem){
            float scale = 0.4f;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0, -0.26F, -0.9D);
            poseStack.mulPose(Axis.YP.rotationDegrees(13));
            poseStack.mulPose(Axis.XP.rotationDegrees(16));
        }

        this.handRenderer.renderItem(corgi, itemstack, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, i);
        poseStack.popPose();
    }

    private void rotateTool(PoseStack poseStack) {
        poseStack.mulPose(Axis.YP.rotationDegrees(225F));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
    }
}
