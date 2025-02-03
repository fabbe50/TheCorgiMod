package com.fabbe50.corgimod.world.entity.animal;

public interface IAbility {
    void setHasBeenFed(boolean hasBeenFed);

    boolean hasBeenFed();

    void setTimeWhenFed(long timeWhenFed);

    long getTimeWhenFed();
}
