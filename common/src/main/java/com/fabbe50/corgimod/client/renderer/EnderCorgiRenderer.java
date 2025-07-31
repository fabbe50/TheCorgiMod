package com.fabbe50.corgimod.client.renderer;

import com.fabbe50.corgimod.client.model.EnderCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.layer.CorgiCarryBlockLayer;
import com.fabbe50.corgimod.client.renderer.layer.EnderCorgiEyesLayer;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EnderCorgiRenderer extends MobRenderer<EnderCorgi, EnderCorgiModel<EnderCorgi>> {
    private final RandomSource random = RandomSource.create();

    public EnderCorgiRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.CORGI_ZOMBIE);
    }

    public EnderCorgiRenderer(EntityRendererProvider.Context context, ModelLayerLocation model) {
        super(context, new EnderCorgiModel<>(context.bakeLayer(model)), 0.5f);
        this.addLayer(new EnderCorgiEyesLayer<>(this));
        this.addLayer(new CorgiCarryBlockLayer(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public void render(EnderCorgi livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        BlockState state = livingEntity.getCarriedBlock();
        EnderCorgiModel<EnderCorgi> enderCorgiModel = this.getModel();
        enderCorgiModel.carrying = state != null;
        enderCorgiModel.creepy = livingEntity.isCreepy();
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(EnderCorgi entity, float f) {
        if (entity.isCreepy()) {
            double d = 0.02 * entity.getScale();
            return new Vec3(this.random.nextGaussian() * d, 0, this.random.nextGaussian() * d);
        } else {
            return super.getRenderOffset(entity, f);
        }
    }

    @Override
    protected float getBob(EnderCorgi corgi, float t) {
        return corgi.getTailAngle();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(EnderCorgi corgi) {
        return Corgis.ENDER.getTextureLocation();
    }
}
