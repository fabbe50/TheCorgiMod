package com.fabbe50.corgimod.world.entity.ability;

public interface IAbility {
    default public void runAbilityAtEndOfFed() {};

    default public void runAbilityWhileFed() {};

    default public void runAbilityWhileHungry() {};
}
