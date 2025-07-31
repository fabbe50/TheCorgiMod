package com.fabbe50.corgimod.world.entity.ai.overrides;

import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CustomWaterAvoidingRandomStrollGoal<T extends TamableAnimal & IPet> extends WaterAvoidingRandomStrollGoal {
    private final T entity;
    private final int wanderingRange;

    public CustomWaterAvoidingRandomStrollGoal(T entity, double speedModifier, int wanderingRange) {
        this(entity, speedModifier, wanderingRange, 0.001f);
    }

    public CustomWaterAvoidingRandomStrollGoal(T entity, double speedModifier, int wanderingRange, float probability) {
        super(entity, speedModifier, probability);
        this.wanderingRange = wanderingRange;
        this.entity = entity;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (entity.isAskedToStay()) {
            BlockPos origin = entity.getOriginStay();
            if (!entity.isWithinRangeOfOrigin()) {
                return BlockPos.randomInCube(entity.getRandom(), 1, origin, wanderingRange).iterator().next().getBottomCenter();
            }
        }
        return super.getPosition();
    }
}
