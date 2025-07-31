package com.fabbe50.corgimod.world.entity.feature.corgis.monster;

import com.fabbe50.corgimod.world.entity.feature.corgis.BaseCorgiFeaturePack;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class ZombieCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public List<EntityType<?>> getPreyTargets() {
        return List.of();
    }


}
