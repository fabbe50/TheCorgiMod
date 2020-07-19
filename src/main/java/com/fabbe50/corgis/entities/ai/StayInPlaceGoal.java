package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class StayInPlaceGoal extends Goal {
    private Random random = new Random();
    private CorgiEntity entity;
    private BlockPos pos;

    public StayInPlaceGoal(CorgiEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return entity.isTamed() && entity.isAskedToStay();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }

    @Override
    public void startExecuting() {
        this.pos = entity.func_233580_cy_();
    }

    @Override
    public void tick() {
        if (this.entity.getDistanceSq(this.pos.getX(), this.pos.getY(), this.pos.getZ()) > 64 && this.shouldExecute()) {
            this.entity.getNavigator().tryMoveToXYZ(this.pos.getX() + (random.nextInt(10) - 5), this.pos.getY(), this.pos.getZ() + (random.nextInt(10) - 5), 1.0f);
        }
    }
}
