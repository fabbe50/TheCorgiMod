package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.corgis.CreeperCorgiEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CreeperSwellDecoyGoal extends Goal {
    private final CreeperCorgiEntity corgiEntity;
    private LivingEntity creeperAttackTarget;

    public CreeperSwellDecoyGoal(CreeperCorgiEntity corgiEntity) {
        this.corgiEntity = corgiEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.corgiEntity.getAttackTarget();
        return this.corgiEntity.getCreeperState() > 0 || livingentity != null && this.corgiEntity.getDistanceSq(livingentity) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.corgiEntity.getNavigator().clearPath();
        this.creeperAttackTarget = this.corgiEntity.getAttackTarget();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.creeperAttackTarget = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.creeperAttackTarget == null) {
            this.corgiEntity.setCreeperState(-1);
        } else if (this.corgiEntity.getDistanceSq(this.creeperAttackTarget) > 49.0D) {
            this.corgiEntity.setCreeperState(-1);
        } else if (!this.corgiEntity.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.corgiEntity.setCreeperState(-1);
        } else {
            this.corgiEntity.setCreeperState(1);
        }
    }
}
