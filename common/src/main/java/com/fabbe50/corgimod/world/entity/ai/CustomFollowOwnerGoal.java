package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.IPet;
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
        return super.canUse() && !entity.isAskedToStay();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !entity.isAskedToStay();
    }
}
