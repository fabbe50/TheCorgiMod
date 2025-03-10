package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.client.model.EnderCorgiModel;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CorgiCarryBlockLayer extends RenderLayer<EnderCorgi, EnderCorgiModel<EnderCorgi>> {
    private final BlockRenderDispatcher blockRenderer;

    public CorgiCarryBlockLayer(RenderLayerParent<EnderCorgi, EnderCorgiModel<EnderCorgi>> renderLayerParent, BlockRenderDispatcher blockRenderer) {
        super(renderLayerParent);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, EnderCorgi entity, float f, float g, float h, float j, float k, float l) {
        BlockState state = entity.getCarriedBlock();
        if (state != null) {
            poseStack.pushPose();
            poseStack.translate(0, 0.475f, 0.10f);
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.translate(-0.25, -0.525, 0.4);
            float m = 0.5f;
            poseStack.scale(m, m, m);
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
            this.blockRenderer.renderSingleBlock(state, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
