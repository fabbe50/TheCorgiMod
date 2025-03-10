package com.fabbe50.corgimod.client.renderer.curios;

import com.fabbe50.corgimod.CorgiMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SunglassesCurioRenderer implements ICurioRenderer {
    private final ResourceLocation SUNGLASSES_RESOURCE = new ResourceLocation(CorgiMod.MODID, "textures/models/curios/sunglasses_layer_1full.png");

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int i, float v, float v1, float v2, float v3, float v4, float v5) {
        EntityModel<T> entityModel = renderLayerParent.getModel();
        if (entityModel instanceof HumanoidModel<T> humanoidModel) {
            poseStack.pushPose();
            ICurioRenderer.followHeadRotations(slotContext.entity(), humanoidModel.head);
            VertexConsumer vertex = multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(SUNGLASSES_RESOURCE));
            poseStack.scale(1.1f, 1.1f, 1.1f);
            poseStack.translate(0, 0.025f, 0);
            humanoidModel.renderToBuffer(poseStack, vertex, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            if (itemStack.hasFoil()) {
                humanoidModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.armorEntityGlint()), i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
            poseStack.popPose();
        }
    }
}
