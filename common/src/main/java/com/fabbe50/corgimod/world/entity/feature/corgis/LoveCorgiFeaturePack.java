package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.world.entity.ai.abilities.SpreadLoveGoal;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.GoalSelector;

public class LoveCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public void onHandleParticles(Corgi entity, RandomSource random) {
        super.onHandleParticles(entity, random);
        entity.level().addParticle(ParticleTypes.HEART, entity.getX() + random.nextDouble() - 0.5D, entity.getY() + random.nextDouble(), entity.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
    }

    @Override
    public void registerGoals(Corgi entity, GoalSelector goalSelector) {
        super.registerGoals(entity, goalSelector);
        goalSelector.addGoal(10, new SpreadLoveGoal(entity));
    }
}
