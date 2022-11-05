package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class FollowOwnerGoalFix extends FollowOwnerGoal {
    private final TamableAnimal entity;

    public FollowOwnerGoalFix(TamableAnimal entity, double speed, float minDist, float maxDist, boolean canFly) {
        super(entity, speed, minDist, maxDist, canFly);
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !((Corgi)entity).isAskedToStay();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !((Corgi)entity).isAskedToStay();
    }
}
