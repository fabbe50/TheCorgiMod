package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.client.model.geom.ModelPart;

public class CorgiModel<T extends Corgi> extends AbstractCorgiModel<T> {
    public CorgiModel(ModelPart root) {
        super(root);
    }
}
