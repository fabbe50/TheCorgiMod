package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.List;

public class BreedGoalFix extends BreedGoal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    private final Corgi corgi;

    public BreedGoalFix(Corgi corgi, double loveTime) {
        super(corgi, loveTime);
        this.corgi = corgi;
    }

    @Override
    public boolean canUse() {
        if (!this.corgi.isInLove()) {
            return false;
        } else {
            this.partner = getFreePartner();
            return this.partner != null;
        }
    }

    private Corgi getFreePartner() {
        List<? extends Corgi> list = this.level.getNearbyEntities(Corgi.class, PARTNER_TARGETING, this.corgi, this.corgi.getBoundingBox().inflate(8.0D));
        double d = Double.MAX_VALUE;
        Corgi corgi1 = null;
        for (Corgi corgi2 : list) {
            if (this.corgi.canMate(corgi2) && this.corgi.distanceToSqr(corgi2) < d) {
                corgi1 = corgi2;
                d = this.corgi.distanceToSqr(corgi2);
            }
        }
        return corgi1;
    }
}
