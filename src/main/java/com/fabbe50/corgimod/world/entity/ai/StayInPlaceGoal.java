package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class StayInPlaceGoal extends Goal {
    private final Random random = new Random();
    private final Corgi corgi;
    private BlockPos pos;

    public StayInPlaceGoal(Corgi corgi) {
        this.corgi = corgi;
    }

    @Override
    public boolean canUse() {
        return corgi.isTame() && corgi.isAskedToStay();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        this.pos = corgi.getOnPos();
    }

    @Override
    public void tick() {
        if (this.corgi.distanceToSqr(this.pos.getX(), this.pos.getY(), this.pos.getZ()) > 64 && this.canUse()) {
            this.corgi.getNavigation().moveTo(this.pos.getX() + (random.nextInt(10) - 5), this.pos.getY(), this.pos.getZ() + (random.nextInt(10) - 5), 1.0f);
        }
    }
}
