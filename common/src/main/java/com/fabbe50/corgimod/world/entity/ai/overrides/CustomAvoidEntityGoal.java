package com.fabbe50.corgimod.world.entity.ai.overrides;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public class CustomAvoidEntityGoal<R extends TamableAnimal, T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final R entity;
    public static final Predicate<LivingEntity> NO_CREATIVE_OR_SPECTATOR = (entity) -> !(entity instanceof Player) || !entity.isSpectator() && !((Player)entity).isCreative();

    public CustomAvoidEntityGoal(R entity, Class<T> avoidedEntityClass, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
        super(entity, avoidedEntityClass, maxDist, walkSpeedModifier, sprintSpeedModifier, NO_CREATIVE_OR_SPECTATOR);
        this.entity = entity;
    }

    public boolean canUse() {
        return !this.entity.isTame() && super.canUse();
    }

    public boolean canContinueToUse() {
        return !this.entity.isTame() && super.canContinueToUse();
    }
}
