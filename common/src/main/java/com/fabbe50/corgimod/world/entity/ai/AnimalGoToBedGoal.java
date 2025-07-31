package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.entity.ai.extended.ExtMoveToBlockGoal;
import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.LevelReader;

public class AnimalGoToBedGoal<T extends TamableAnimalExtension & IPet> extends ExtMoveToBlockGoal<T> {
    private final T pet;

    public AnimalGoToBedGoal(T pet, double speedModifier, int horizontalScanRange, int verticalScanRange) {
        super(pet, speedModifier, horizontalScanRange, verticalScanRange);
        this.pet = pet;
    }

    @Override
    public boolean canUse() {
        if (this.pet.level().isNight()) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.pet.isSleeping() || super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        this.pet.setSleeping(false);
        this.pet.setInSittingPose(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.pet.setSleeping(false);
        this.pet.setInSittingPose(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.pet.setSleeping(this.isReachedTarget());
        this.pet.setInSittingPose(this.isReachedTarget());
        if (this.isReachedTarget() && this.pet.isSleeping()) {
            if (this.pet.level().isDay()) {
                this.pet.setSleeping(false);
                this.pet.setInSittingPose(false);
                this.pet.setHungry(true);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        if (levelReader.isEmptyBlock(blockPos.above())) {
            return levelReader.getBlockState(blockPos).is(ModTags.CORGI_BED_BLOCKS);
        }
        return false;
    }
}
