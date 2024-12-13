package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.SpyCorgi;
import net.minecraft.client.model.geom.ModelPart;

public class SpyCorgiModel<T extends SpyCorgi> extends AbstractCorgiModel<T> {
    public SpyCorgiModel(ModelPart root) {
        super(root);
    }
}
