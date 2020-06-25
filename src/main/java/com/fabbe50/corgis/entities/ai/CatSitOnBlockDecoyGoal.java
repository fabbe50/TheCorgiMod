package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.block.*;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatSitOnBlockDecoyGoal extends MoveToBlockGoal {
    private final CorgiEntity corgi;

    public CatSitOnBlockDecoyGoal(CorgiEntity corgi, double speedIn) {
        super(corgi, speedIn, 8);
        this.corgi = corgi;
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.isTamed() && !this.corgi.isSitting() && this.corgi.getCorgiType().equals(CorgiType.ANTI) && super.shouldExecute();
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
        if (!this.getIsAboveDestination())
            this.corgi.setSitting(false);
        else
            this.corgi.setSitting(true);
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up()))
            return false;
        else {
            BlockState blockState = worldIn.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block == Blocks.CHEST) {
                return ChestTileEntity.getPlayersUsing(worldIn, pos) < 1;
            } else if (block == Blocks.FURNACE && blockState.get(FurnaceBlock.LIT)) {
                return true;
            } else {
                return block.isIn(BlockTags.BEDS) && blockState.get(BedBlock.PART) != BedPart.HEAD;
            }
        }
    }
}
