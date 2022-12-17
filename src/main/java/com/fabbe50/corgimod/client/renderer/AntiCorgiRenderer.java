package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.AntiCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.animal.AntiCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AntiCorgiRenderer extends MobRenderer<AntiCorgi, AntiCorgiModel<AntiCorgi>> {
    public AntiCorgiRenderer(EntityRendererProvider.Context context) {
        super(context, new AntiCorgiModel<>(context.bakeLayer(ModelLayers.CORGI_ANTI)), 0.5f);
    }

    @Override
    protected void scale(AntiCorgi corgi, PoseStack poseStack, float p_115316_) {
        super.scale(corgi, poseStack, p_115316_);
        poseStack.scale(0.8F, 0.8F, 0.8F);
    }

    @Override
    protected void setupRotations(AntiCorgi corgi, PoseStack poseStack, float p_115319_, float p_115320_, float p_115321_) {
        super.setupRotations(corgi, poseStack, p_115319_, p_115320_, p_115321_);
        float f = corgi.getLieDownAmount(p_115321_);
        if (f > 0.0F) {
            poseStack.translate((double)(0.4F * f), (double)(0.15F * f), (double)(0.1F * f));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp(f, 0.0F, 90.0F)));
            BlockPos blockpos = corgi.blockPosition();

            for(Player player : corgi.level.getEntitiesOfClass(Player.class, (new AABB(blockpos)).inflate(2.0D, 2.0D, 2.0D))) {
                if (player.isSleeping()) {
                    poseStack.translate((double)(0.15F * f), 0.0D, 0.0D);
                    break;
                }
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(AntiCorgi corgi) {
        return Corgis.ANTI.getTextureLocation();
    }
}
