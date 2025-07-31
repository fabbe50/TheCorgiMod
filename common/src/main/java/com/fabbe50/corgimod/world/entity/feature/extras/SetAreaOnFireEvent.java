package com.fabbe50.corgimod.world.entity.feature.extras;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;

public class SetAreaOnFireEvent implements IEvent {
    @Override
    public boolean handle(LivingEntity entity, ServerLevel level) {
        return this.setAreaOnFire(entity, level, entity.getRandom().nextInt(2, 5));
    }

    private boolean setAreaOnFire(LivingEntity entity, ServerLevel level, int radius) {
        boolean flag = false;
        if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos posToSpawnFire = entity.blockPosition().offset(x, y, z);
                        if (level.getBlockState(posToSpawnFire).is(Blocks.AIR) && !level.getBlockState(posToSpawnFire.below()).is(Blocks.AIR)) {
                            if (level.setBlock(posToSpawnFire, Blocks.FIRE.defaultBlockState(), 10)) {
                                flag = true;
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }
}
