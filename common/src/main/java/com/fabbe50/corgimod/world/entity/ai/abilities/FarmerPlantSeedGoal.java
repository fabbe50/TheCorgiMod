package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.block.util.ContainerUtils;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FarmerPlantSeedGoal<T extends Corgi> extends MoveToBlockGoal {
    private final T entity;
    private ItemStack stack;

    public FarmerPlantSeedGoal(T entity, double d, int i, int j) {
        super(entity, d, i, j);
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return !this.entity.isOrderedToSit() && super.canUse();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget() && !this.stack.isEmpty()) {
            BlockPos target = getMoveToTarget();
            Level level = this.entity.level();
            Block block = ((BlockItem) this.stack.getItem()).getBlock();
            this.stack.shrink(1);
            level.setBlockAndUpdate(target, block.defaultBlockState());
            this.stack = ItemStack.EMPTY;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        BlockState state = levelReader.getBlockState(blockPos);
        if (!state.is(ModTags.FARMER_FARMLAND)) {
            return false;
        }
        if (!levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }
        this.stack = this.getRandomSeedFromInventory();
        if (this.stack.isEmpty()) {
            this.stack = ItemStack.EMPTY;
            return false;
        }
        if (!(this.stack.getItem() instanceof BlockItem)) {
            this.stack = ItemStack.EMPTY;
            return false;
        }
        return true;
    }

    private ItemStack getRandomSeedFromInventory() {
        if (this.entity.getFeaturePack().hasInventory(this.entity)) {
            Container inventory = this.entity.getFeaturePack().getInventory(this.entity);
            if (!inventory.isEmpty()) {
                return ContainerUtils.getRandomItemStack(inventory, ModTags.FARMER_SEEDS, ModTags.FARMER_SEEDS_BLACKLIST);
            }
        }
        return ItemStack.EMPTY;
    }
}
