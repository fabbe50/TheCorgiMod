package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.world.entity.ai.extended.ExtMoveToBlockGoal;
import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StealMelonGoal<T extends TamableAnimalExtension> extends ExtMoveToBlockGoal<T> {
    private int timeSitting = 20;
    private int ticks = 0;

    public StealMelonGoal(T entity, double speedModifier, int searchRange) {
        this(entity, speedModifier, searchRange, 1);
    }

    public StealMelonGoal(T entity, double speedModifier, int searchRange, int verticalSearchRange) {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    @Override
    public boolean canUse() {
        return !this.getPet().isOrderedToSit() && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.getPet().setInSittingPose(false);
        this.timeSitting = this.getPet().getRandom().nextInt(10) + 10;
        this.ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.getPet().setInSittingPose(false);
    }

    @Override
    protected BlockPos getTargetBlock() {
        return getMoveToTarget();
    }

    @Override
    public void tick() {
        super.tick();
        this.getPet().setInSittingPose(this.isReachedTarget());
        if (this.isReachedTarget() && this.getPet().isInSittingPose()) {
            if (ticks % 20 == 0) {
                timeSitting--;
                if (timeSitting <= 0) {
                    this.getPet().setInSittingPose(false);
                    if (this.getPet().level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        this.getPet().level().destroyBlock(this.blockPos, this.getPet().isTame(), this.getPet(), 512);
                    }
                }
            }
            ticks++;
        }
    }

    @Override
    protected boolean isValidTarget(@NotNull LevelReader reader, @NotNull BlockPos pos) {
        if (reader.isEmptyBlock(pos.above())) {
            BlockState state = reader.getBlockState(pos);
            return state.is(Blocks.MELON);
        }
        return false;
    }
}
