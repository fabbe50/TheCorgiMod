package com.fabbe50.corgis.entities.ai.traits;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MelonTrait extends MoveToBlockGoal {
    private final CorgiEntity corgi;

    public MelonTrait(CorgiEntity corgi, double speedIn) {
        super(corgi, speedIn, 8);
        this.corgi = corgi;
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.isTamed() && !this.corgi.isSitting() && this.corgi.getCorgiType().equals(CorgiType.MELON) && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.corgi.getAISit().setSitting(false);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.corgi.setSitting(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.corgi.getAISit().setSitting(false);
        if (!this.getIsAboveDestination()) {
            this.corgi.setSitting(false);
        } else if (!this.corgi.isSitting()) {
            this.corgi.setSitting(true);
        }
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        } else {
            BlockState blockState = worldIn.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block == Blocks.MELON) {
                return true;
            }
            return false;
        }
    }
}
