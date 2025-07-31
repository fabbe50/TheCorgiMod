package com.fabbe50.corgimod.world.entity.feature.extras;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public interface IEvent {
    boolean handle(LivingEntity entity, ServerLevel level);
}
