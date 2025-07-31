package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.block.util.PlantUtils;
import com.fabbe50.corgimod.world.entity.ai.extended.ExtMoveToBlockGoal;
import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePackEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class FarmerGoal<T extends TamableAnimalExtension & IFeaturePackEntity<T>> extends ExtMoveToBlockGoal<T> {
    public FarmerGoal(T entity, double speedModifier, int searchRange, int verticalSearchRange) {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public double acceptedDistance() {
        return 3;
    }

    @Override
    protected int nextStartTick(PathfinderMob pathfinderMob) {
        return reducedTickDelay(60 + pathfinderMob.getRandom().nextInt(100));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) {
            BlockPos target = getTargetBlock();
            Level level = this.getPet().level();
            BlockState state = level.getBlockState(target);
            PlantUtils.harvestPlant((ServerLevel) level, target, state, this.getPet(), ItemStack.EMPTY);
            level.setBlockAndUpdate(target, PlantUtils.getPlantStateAfterHarvest(state));
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        BlockState state = levelReader.getBlockState(blockPos);
        BlockState stateBelow = levelReader.getBlockState(blockPos.below());
        if (state.is(ModTags.FARMER_LEAVE_BOTTOM_BLOCK)) {
            return state.getBlock().equals(stateBelow.getBlock()) && PlantUtils.isPlantFullyGrown(state);
        }
        return PlantUtils.isPlantFullyGrown(state);
    }
}
