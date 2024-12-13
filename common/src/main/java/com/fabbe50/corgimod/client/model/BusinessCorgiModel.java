package com.fabbe50.corgimod.client.model;

import com.fabbe50.corgimod.world.entity.animal.BusinessCorgi;
import net.minecraft.client.model.geom.ModelPart;

public class BusinessCorgiModel<T extends BusinessCorgi> extends AbstractCorgiModel<T> {
    public BusinessCorgiModel(ModelPart root) {
        super(root);
    }
}
