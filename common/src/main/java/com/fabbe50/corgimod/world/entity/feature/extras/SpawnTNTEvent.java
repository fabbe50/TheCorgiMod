package com.fabbe50.corgimod.world.entity.feature.extras;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.GameRules;

public class SpawnTNTEvent implements IEvent {
    @Override
    public boolean handle(LivingEntity entity, ServerLevel level) {
        if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            PrimedTnt tnt = new PrimedTnt(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            tnt.setFuse(30);
            return level.addFreshEntity(tnt);
        }
        return false;
    }
}
