package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.HeroCorgi;
import net.minecraft.client.model.geom.ModelPart;

public class HeroCorgiModel<T extends HeroCorgi> extends AbstractCorgiModel<T> {
    public HeroCorgiModel(ModelPart root) {
        super(root);
    }
}
