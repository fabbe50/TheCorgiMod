package com.fabbe50.corgimod.world.entity.ai.overrides;

import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class CustomFollowOwnerGoal<T extends TamableAnimal & IPet> extends FollowOwnerGoal {
    private final T entity;

    public CustomFollowOwnerGoal(T entity, double d, float f, float g) {
        super(entity, d, f, g);
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !entity.isAskedToStay() && !entity.isSleeping();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !entity.isAskedToStay() && !entity.isSleeping();
    }
}
