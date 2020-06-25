package com.fabbe50.client.layer;

import com.fabbe50.client.model.CreeperCorgiModel;
import com.fabbe50.corgis.entities.corgis.CreeperCorgiEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperCorgiChargeLayer extends EnergyLayer<CreeperCorgiEntity, CreeperCorgiModel<CreeperCorgiEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperCorgiModel<CreeperCorgiEntity> creeperCorgiModel = new CreeperCorgiModel<>();

    public CreeperCorgiChargeLayer(IEntityRenderer<CreeperCorgiEntity, CreeperCorgiModel<CreeperCorgiEntity>> p_i226038_1_) {
        super(p_i226038_1_);
    }

    @Override
    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01f;
    }

    @Override
    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    @Override
    protected EntityModel<CreeperCorgiEntity> func_225635_b_() {
        return this.creeperCorgiModel;
    }
}
