package com.fabbe50.corgis.entities.ai.traits;

import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import com.fabbe50.corgis.utils.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldReader;

import java.util.Random;

public class MelonTrait extends MoveToBlockGoal {
    private final CorgiEntity corgi;
    private Random random = new Random();

    public MelonTrait(CorgiEntity corgi, double speedIn) {
        super(corgi, speedIn, 8);
        this.corgi = corgi;
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.isTamed() && !this.corgi.func_233684_eK_() && this.corgi.getCorgiType().equals(CorgiType.MELON) && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.corgi.func_233686_v_(false);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.corgi.func_233686_v_(false);
    }

    private int timeSitting = 20;
    private int ticks = 0;
    @Override
    public void tick() {
        super.tick();
        this.corgi.func_233686_v_(this.getIsAboveDestination());
        if (timeSitting == 0)
            timeSitting = this.random.nextInt(10) + 10;
        if (this.getIsAboveDestination() && this.corgi.func_233684_eK_() && this.corgi.world.getBlockState(this.corgi.func_233580_cy_().down()).getBlock() == Blocks.MELON &&
                this.corgi.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && Corgis.config.getMelonCorgi().getShouldBreakMelons()) {
            if (ticks % 20 == 0) {
                timeSitting--;
                if (timeSitting == 0) {
                    this.corgi.func_233686_v_(false);
                    Utilities.destroyBlock(this.corgi.world, this.corgi.func_233580_cy_().down(), true, false, true);
                    timeSitting = 20;
                    ticks = 0;
                }
            }
            ticks++;
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
