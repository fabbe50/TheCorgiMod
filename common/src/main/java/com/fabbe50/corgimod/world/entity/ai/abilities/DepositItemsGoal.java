package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.world.block.util.ContainerUtils;
import com.fabbe50.corgimod.world.entity.ai.extended.ExtMoveToBlockGoal;
import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePackEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DepositItemsGoal<T extends TamableAnimalExtension & IFeaturePackEntity<T>> extends ExtMoveToBlockGoal<T> {
    private final T entity;

    public DepositItemsGoal(T entity, double d, int i, int j) {
        super(entity, d, i, j);
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if (!this.entity.getFeaturePack().hasInventory(this.entity)) {
            return false;
        }
        Container container = this.entity.getFeaturePack().getInventory(this.entity);
        if (container.isEmpty()) {
            return false;
        }
        return !this.entity.isOrderedToSit() && super.canUse();
    }

    @Override
    public double acceptedDistance() {
        return 3;
    }

    @Override
    protected BlockPos getTargetBlock() {
        return super.getTargetBlock();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) {
            BlockPos target = getMoveToTarget().below();
            Level level = this.entity.level();
            BlockState state = level.getBlockState(target);
            if (state.hasBlockEntity()) {
                BlockEntity blockEntity = level.getBlockEntity(target);
                if (blockEntity instanceof Container container) {
                    Container inventory = this.entity.getFeaturePack().getInventory(this.entity);
                    depositItems(inventory, container);
                }
            }
        }
    }

    public void depositItems(Container inventory, Container container) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack != null && !stack.isEmpty()) {
                int rest = ContainerUtils.insertItem(container, stack);
                stack.setCount(rest);
                inventory.setItem(i, stack);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        BlockState state = levelReader.getBlockState(blockPos);
        if (state.hasBlockEntity()) {
            BlockEntity blockEntity = levelReader.getBlockEntity(blockPos);
            return blockEntity instanceof Container;
        }
        return false;
    }
}
