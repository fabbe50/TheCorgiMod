package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.monster.WitherSkeletonCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WitherSkeletonCorgiRenderer extends SkeletonCorgiRenderer<WitherSkeletonCorgi> {
    public WitherSkeletonCorgiRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(WitherSkeletonCorgi livingEntity, PoseStack poseStack, float f) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WitherSkeletonCorgi corgi) {
        return Corgis.WITHER_SKELETON.getTextureLocation();
    }
}
