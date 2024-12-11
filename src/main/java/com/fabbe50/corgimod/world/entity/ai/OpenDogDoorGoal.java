package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.level.block.BlockRegistry;
import com.fabbe50.corgimod.world.level.block.DogDoorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class OpenDogDoorGoal extends Goal {
    private PathfinderMob entity;
    private BlockPos doorPosition;
    private BlockState blockState;
    private DogDoorBlock dogDoorBlock;
    private boolean flag = false;

    public OpenDogDoorGoal(PathfinderMob entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if (entity != null) {
            Level level = entity.level();
            if (isCloseToDoor(level, entity.getOnPos())) {
                blockState = level.getBlockState(doorPosition);
                if (blockState.is(BlockRegistry.OAK_DOG_DOOR.get())) {
                    dogDoorBlock = (DogDoorBlock) blockState.getBlock();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void tick() {
        if (!blockState.getValue(DogDoorBlock.OPEN) && checkNullStates()) {
            dogDoorBlock.setOpen(entity, entity.level(), blockState, doorPosition, true);
            flag = true;
        }
    }

    @Override
    public void stop() {
        if (flag) {
            dogDoorBlock.setOpen(entity, entity.level(), blockState, doorPosition, false);
            blockState = null;
            doorPosition = null;
            dogDoorBlock = null;
            flag = false;
        }
    }

    private boolean checkNullStates() {
        return entity != null && blockState != null && doorPosition != null && dogDoorBlock != null;
    }

    private boolean isCloseToDoor(Level level, BlockPos pos) {
        BlockPos origin = pos.offset(-1, 0, -1);
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < 3; x++) {
                BlockPos tempPos = origin.offset(x, 0, z);
                if (level.getBlockState(tempPos).is(BlockRegistry.OAK_DOG_DOOR.get())) {
                    doorPosition = tempPos;
                    return true;
                }
            }
        }
        return false;
    }
}
