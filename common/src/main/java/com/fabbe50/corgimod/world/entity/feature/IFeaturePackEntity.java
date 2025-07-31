package com.fabbe50.corgimod.world.entity.feature;

import net.minecraft.world.entity.PathfinderMob;

public interface IFeaturePackEntity<T extends PathfinderMob> {
    IFeaturePack<T> getFeaturePack();
}
