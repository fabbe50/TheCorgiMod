package com.fabbe50.corgimod.world.entity.feature;

import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractAnimalFeaturePack<T extends Animal> implements IFeaturePack<T> {
    @Override
    public void registerGoals(T entity, GoalSelector goalSelector) {
        goalSelector.addGoal(1, new FloatGoal(entity));
        goalSelector.addGoal(23, new LookAtPlayerGoal(entity, Player.class, 8.0F));
        goalSelector.addGoal(23, new RandomLookAroundGoal(entity));
    }
}
