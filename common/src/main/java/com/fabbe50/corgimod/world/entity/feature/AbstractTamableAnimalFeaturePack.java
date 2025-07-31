package com.fabbe50.corgimod.world.entity.feature;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;

public abstract class AbstractTamableAnimalFeaturePack<T extends TamableAnimal> extends AbstractAnimalFeaturePack<T> {
    @Override
    public void registerGoals(T entity, GoalSelector goalSelector) {
        super.registerGoals(entity, goalSelector);
        goalSelector.addGoal(2, new SitWhenOrderedToGoal(entity));
    }
}
