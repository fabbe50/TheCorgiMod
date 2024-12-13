package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.NerdCorgi;
import net.minecraft.client.model.geom.ModelPart;

public class NerdCorgiModel<T extends NerdCorgi> extends AbstractCorgiModel<T> {
    public NerdCorgiModel(ModelPart root) {
        super(root);
    }
}
