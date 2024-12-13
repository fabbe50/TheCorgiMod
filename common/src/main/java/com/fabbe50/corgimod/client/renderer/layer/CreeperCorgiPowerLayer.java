package com.fabbe50.corgimod.client.renderer.layer;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.client.model.CreeperCorgiModel;
import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.world.entity.animal.CreeperCorgi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CreeperCorgiPowerLayer extends EnergySwirlLayer<CreeperCorgi, CreeperCorgiModel<CreeperCorgi>> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperCorgiModel<CreeperCorgi> model;

    public CreeperCorgiPowerLayer(RenderLayerParent<CreeperCorgi, CreeperCorgiModel<CreeperCorgi>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new CreeperCorgiModel<>(entityModelSet.bakeLayer(ModelLayers.CORGI_CREEPER_ARMOR));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(4, 4).addBox(-3.5F, -8.5F, -8.5F, 7.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(83, 10).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.5F, -6.5F, -3.6667F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(27, 3).addBox(-3.5F, -9.5F, -1.6667F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 3).addBox(0.5F, -9.5F, -1.6667F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));

        PartDefinition rb_leg = partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));

        PartDefinition rf_leg = partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));

        PartDefinition lb_leg = partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));

        PartDefinition lf_leg = partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    protected float xOffset(float p_116968_) {
        return p_116968_ * 0.01f;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    @Override
    protected EntityModel<CreeperCorgi> model() {
        return this.model;
    }
}
