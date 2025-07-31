package com.fabbe50.corgimod.world.entity.ai.overrides;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class CustomSitOnBlockGoal extends MoveToBlockGoal {
    private final Corgi corgi;

    public CustomSitOnBlockGoal(Corgi corgi, double d) {
        super(corgi, d, 8);
        this.corgi = corgi;
    }

    public boolean canUse() {
        return this.corgi.isTame() && !this.corgi.isOrderedToSit() && super.canUse();
    }

    public void start() {
        super.start();
        this.corgi.setInSittingPose(false);
    }

    public void stop() {
        super.stop();
        this.corgi.setInSittingPose(false);
    }

    public void tick() {
        super.tick();
        this.corgi.setInSittingPose(this.isReachedTarget());
    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!level.isEmptyBlock(pos.above())) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.is(Blocks.CHEST)) {
                return ChestBlockEntity.getOpenCount(level, pos) < 1;
            } else {
                return blockState.is(Blocks.FURNACE) && blockState.getValue(FurnaceBlock.LIT) || blockState.is(BlockTags.BEDS, (state) -> state.getOptionalValue(BedBlock.PART).map(bedPart -> bedPart != BedPart.HEAD).orElse(true));
            }
        }
    }
}
