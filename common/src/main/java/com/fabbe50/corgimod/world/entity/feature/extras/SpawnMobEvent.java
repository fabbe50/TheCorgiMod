package com.fabbe50.corgimod.world.entity.feature.extras;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SpawnMobEvent implements IEvent {
    private final EntityType<?> summonable;
    private final EntityType<?> summonable2;

    public SpawnMobEvent(EntityType<?> summonable) {
        this.summonable = summonable;
        this.summonable2 = null;
    }

    public SpawnMobEvent(EntityType<?> summonable, EntityType<?> summonable2) {
        this.summonable = summonable;
        this.summonable2 = summonable2;
    }

    @Override
    public boolean handle(LivingEntity entity, ServerLevel level) {
        boolean flag = false;
        for (int a = 0; a < entity.getRandom().nextInt(5, 15); a++) {
            if (summonable2 != null && entity.getRandom().nextInt(5) == 0) {
                Entity newEntity = summonable2.create(level);
                if (this.summonEntityRandomNearby(entity, level, newEntity)) {
                    flag = true;
                }
            } else {
                Entity newEntity = summonable.create(level);
                if (this.summonEntityRandomNearby(entity, level, newEntity)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private boolean summonEntityRandomNearby(LivingEntity entity, ServerLevel level, Entity newEntity) {
        if (newEntity != null) {
            Vec3 position = entity.position().offsetRandom(entity.getRandom(), 5);
            position = position.add(0, 5, 0);
            newEntity.setPos(position);
            return level.addFreshEntity(newEntity);
        }
        return false;
    }
}
