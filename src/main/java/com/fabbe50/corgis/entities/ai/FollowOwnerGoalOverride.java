package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.interfaces.ICorgi;
import com.fabbe50.corgis.entities.interfaces.ITameableCorgi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;

public class FollowOwnerGoalOverride extends FollowOwnerGoal {
    private TameableEntity entity;
    private LivingEntity owner;

    public FollowOwnerGoalOverride(TameableEntity entity, double speed, float minDist, float maxDist, boolean p_i225711_6_) {
        super(entity, speed, minDist, maxDist, p_i225711_6_);
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.entity.getOwner();
        if (livingentity != null)
            this.owner = livingentity;
        if (entity instanceof ITameableCorgi) {
            return !((ITameableCorgi) entity).isAskedToStay() && super.shouldExecute();
        }
        return super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (entity instanceof ITameableCorgi) {
            return !((ITameableCorgi) entity).isAskedToStay() && super.shouldContinueExecuting();
        }
        return super.shouldContinueExecuting();
    }

    @Override
    public void resetTask() {
        this.owner = null;
    }

    @Override
    public void tick() {
        if (this.owner != null)
            super.tick();
    }
}
