package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.entity.PetBowlBlockEntity;
import com.fabbe50.corgimod.world.entity.ai.extended.ExtMoveToBlockGoal;
import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HungryTamedAnimalGoesToEatGoal<T extends TamableAnimalExtension & IPet> extends ExtMoveToBlockGoal<T> {
    public HungryTamedAnimalGoesToEatGoal(T pet, double speedModifier, int horizontalScanRange, int verticalScanRange) {
        super(pet, speedModifier, horizontalScanRange, verticalScanRange);
    }

    @Override
    public boolean canUse() {
        if (!getPet().isSleeping() && getPet().isHungry()) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return getPet().isHungry() && super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 3;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget() && getPet().isHungry()) {
            T pet = this.getPet();
            Level level = pet.level();
            BlockEntity blockEntity = level.getBlockEntity(getTargetBlock());
            if (blockEntity instanceof PetBowlBlockEntity petBowlBlockEntity) {
                if (!petBowlBlockEntity.isEmpty()) {
                    petBowlBlockEntity.getItem(0).shrink(1);
                    pet.setHungry(false);
                }
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        if (levelReader.isEmptyBlock(blockPos.above())) {
            BlockState state = levelReader.getBlockState(blockPos);
            if (state.is(ModTags.CORGI_BOWL_BLOCKS)) {
                return state.getValue(PetBowlBlock.FOOD_IN_BOWL);
            }
        }
        return false;
    }
}
