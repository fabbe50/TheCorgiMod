package com.fabbe50.corgimod.world.entity.ai.overrides;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class CustomLieOnBedGoal extends MoveToBlockGoal {
    private final Corgi corgi;

    public CustomLieOnBedGoal(Corgi corgi, double d, int i) {
        super(corgi, d, i, 6);
        this.corgi = corgi;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean canUse() {
        return this.corgi.isTame() && !this.corgi.isOrderedToSit() && !this.corgi.isLying() && super.canUse();
    }

    public void start() {
        super.start();
        this.corgi.setInSittingPose(false);
    }

    protected int nextStartTick(PathfinderMob arg) {
        return 40;
    }

    public void stop() {
        super.stop();
        this.corgi.setLying(false);
    }

    public void tick() {
        super.tick();
        this.corgi.setInSittingPose(false);
        if (!this.isReachedTarget()) {
            this.corgi.setLying(false);
        } else if (!this.corgi.isLying()) {
            this.corgi.setLying(true);
        }

    }

    protected boolean isValidTarget(LevelReader arg, BlockPos arg2) {
        return arg.isEmptyBlock(arg2.above()) && arg.getBlockState(arg2).is(BlockTags.BEDS);
    }
}
