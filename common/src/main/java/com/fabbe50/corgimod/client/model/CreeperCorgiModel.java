package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.monster.CreeperCorgi;
import net.minecraft.client.model.geom.ModelPart;

public class CreeperCorgiModel<T extends CreeperCorgi> extends HostileCorgiModel<T> {
	public CreeperCorgiModel(ModelPart root) {
        super(root);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (entity.getSwellDir() > 0) {
			setOpenMouth(entity);
		} else {
			setClosedMouth();
		}
	}
}