package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.interfaces.model.ICorgiLike;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

public class HostileCorgiModel<T extends Monster & ICorgiLike> extends BaseCorgiModel<T> {
    public HostileCorgiModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.getTarget() != null) {
            this.setOpenMouth(entity);
        } else {
            this.setClosedMouth();
        }
    }
}
