package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.interfaces.model.ICorgiLike;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

public class EnderCorgiModel<T extends Monster & ICorgiLike> extends HostileCorgiModel<T> {
	public boolean carrying;
	public boolean creepy;

	public EnderCorgiModel(ModelPart root) {
		super(root);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (this.creepy) {
			this.setOpenMouth(entity);
		} else {
			this.setClosedMouth();
		}
		if (entity instanceof EnderCorgi enderCorgi) {
			this.wings.visible = enderCorgi.hasWings();
		}
	}
}