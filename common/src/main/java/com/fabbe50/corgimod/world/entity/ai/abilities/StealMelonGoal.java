package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.world.entity.animal.IAbility;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StealMelonGoal<T extends TamableAnimal & IAbility> extends MoveToBlockGoal {
    private final T entity;
    private int timeSitting = 20;
    private int ticks = 0;

    public StealMelonGoal(T entity, double speedModifier, int searchRange) {
        super(entity, speedModifier, searchRange);
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return !this.entity.isOrderedToSit() && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.entity.setInSittingPose(false);
        this.timeSitting = this.entity.getRandom().nextInt(10) + 10;
        this.ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.entity.setInSittingPose(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.entity.setInSittingPose(this.isReachedTarget());
        if (this.isReachedTarget() && this.entity.isInSittingPose() && this.entity.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            if (ticks % 20 == 0) {
                timeSitting--;
                if (timeSitting <= 0) {
                    this.entity.setInSittingPose(false);
                    this.entity.level().destroyBlock(this.blockPos, this.entity.isTame(), this.entity, 512);
                }
            }
            ticks++;
        }
    }

    @Override
    protected boolean isValidTarget(@NotNull LevelReader reader, @NotNull BlockPos pos) {
        if (reader.isEmptyBlock(pos.above())) {
            BlockState blockState = reader.getBlockState(pos);
            Block block = blockState.getBlock();
            return block == Blocks.MELON;
        }
        return false;
    }
}
