package com.fabbe50.corgimod.world.entity.ai.extended;

import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;

public abstract class ExtMoveToBlockGoal<T extends TamableAnimal & IPet> extends MoveToBlockGoal {
    private final T pet;
    private boolean reachedTarget;

    public ExtMoveToBlockGoal(T pet, double speedModifier, int horizontalScanRange, int verticalScanRange) {
        super(pet, speedModifier, horizontalScanRange, verticalScanRange);
        this.pet = pet;
    }

    @Override
    public boolean canUse() {
        if (this.pet.isTame() && !this.pet.isOrderedToSit()) {
            return super.canUse();
        }
        return false;
    }

    protected BlockPos getTargetBlock() {
        return getMoveToTarget().below();
    }

    @Override
    protected void moveMobToBlock() {
        BlockPos blockPos = this.getTargetBlock();
        this.mob.getNavigation().moveTo(blockPos.getX() + 0.5F, this.blockPos.getY(), this.blockPos.getZ() + 0.5F, this.speedModifier);
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.getTargetBlock();
        if (!blockPos.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
            this.reachedTarget = false;
            this.tryTicks++;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo((double)blockPos.getX() + (double)0.5F, blockPos.getY(), (double)blockPos.getZ() + (double)0.5F, this.speedModifier);
            }
        } else {
            this.reachedTarget = true;
            this.tryTicks--;
        }
    }

    @Override
    protected boolean isReachedTarget() {
        return this.reachedTarget;
    }

    public T getPet() {
        return pet;
    }
}
