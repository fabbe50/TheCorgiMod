package com.fabbe50.corgimod.world.entity.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SkeletonCorgi extends AbstractSkeleton {

    public SkeletonCorgi(EntityType<? extends AbstractSkeleton> p_33570_, Level p_33571_) {
        super(p_33570_, p_33571_);
    }

    public float getTailAngle() {
        return ((float)Math.PI / 5F);
    }

    @Override
    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }
}
