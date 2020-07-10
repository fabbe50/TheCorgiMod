package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

public class CatLieOnBedDecoyGoal extends MoveToBlockGoal {
    private final CorgiEntity corgi;

    public CatLieOnBedDecoyGoal(CorgiEntity corgi, double speed, int length) {
        super(corgi, speed, length, 6);
        this.corgi = corgi;
        this.field_203112_e = -2;
        this.setMutexFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.isTamed() && !this.corgi.func_233684_eK_() && !this.corgi.func_213416_eg() && this.corgi.getCorgiType().equals(CorgiType.ANTI) && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.corgi.func_233686_v_(false);
    }

    @Override
    protected int getRunDelay(CreatureEntity creatureIn) {
        return 40;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.corgi.func_213419_u(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.corgi.func_233686_v_(false);
        if (!this.getIsAboveDestination())
            this.corgi.func_213419_u(false);
        else if (!this.corgi.func_213416_eg()) {
            this.corgi.func_213419_u(true);
        }
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos).getBlock().isIn(BlockTags.BEDS);
    }
}
