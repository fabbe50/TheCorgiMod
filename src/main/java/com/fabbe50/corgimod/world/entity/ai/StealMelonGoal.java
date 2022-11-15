package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.MelonCorgi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StealMelonGoal extends MoveToBlockGoal {
    private final MelonCorgi corgi;
    private int timeSitting = 20;
    private int ticks = 0;

    public StealMelonGoal(MelonCorgi corgi, double speedModifier, int searchRange) {
        super(corgi, speedModifier, searchRange);
        this.corgi = corgi;
    }

    @Override
    public boolean canUse() {
        return !this.corgi.isOrderedToSit() && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.corgi.setInSittingPose(false);
        this.timeSitting = this.corgi.getRandom().nextInt(10) + 10;
        this.ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.corgi.setInSittingPose(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.corgi.setInSittingPose(this.isReachedTarget());
        if (this.isReachedTarget() && this.corgi.isInSittingPose() && this.corgi.getLevel().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            if (ticks % 20 == 0) {
                timeSitting--;
                if (timeSitting <= 0) {
                    this.corgi.setInSittingPose(false);
                    this.corgi.getLevel().destroyBlock(this.blockPos, this.corgi.isTame(), this.corgi, 512);
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
