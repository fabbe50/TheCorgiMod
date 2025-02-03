package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.IBeggingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class CustomBegGoal<T extends Mob & IBeggingEntity> extends Goal {
    private final T entity;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public CustomBegGoal(T entity, float lookDistance) {
        this.entity = entity;
        this.level = entity.level();
        this.lookDistance = lookDistance;
        this.begTargeting = TargetingConditions.forNonCombat().range(lookDistance);
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.begTargeting, this.entity);
        return this.player != null && this.entity.playerHoldingInteresting(this.player);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.player != null && !this.player.isAlive()) {
            return false;
        } else if (this.entity.distanceToSqr(this.player) > (this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.entity.playerHoldingInteresting(player);
        }
    }

    @Override
    public void start() {
        this.entity.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.entity.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        this.entity.setIsInterested(false);
        this.player = null;
    }

    @Override
    public void tick() {
        this.entity.getLookControl().setLookAt(this.player, 10, this.entity.getMaxHeadXRot());
        --this.lookTime;
    }
}
