package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.IPet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CustomWaterAvoidingRandomStrollGoal<T extends PathfinderMob & IPet> extends WaterAvoidingRandomStrollGoal {
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
            Vec3 origin = entity.getOriginStay().getBottomCenter();
            if (entity.position().distanceTo(origin) > wanderingRange) {
                return LandRandomPos.getPosTowards(entity, 10, 7, origin);
            }
        }
        return super.getPosition();
    }
}
