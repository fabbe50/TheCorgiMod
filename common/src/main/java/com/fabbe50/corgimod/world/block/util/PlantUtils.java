package com.fabbe50.corgimod.world.block.util;

import com.fabbe50.corgimod.registries.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class PlantUtils {
    public static boolean isPlantFullyGrown(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(state);
        }
        if (block instanceof NetherWartBlock) {
            if (state.hasProperty(NetherWartBlock.AGE)) {
                return state.getValue(NetherWartBlock.AGE) >= NetherWartBlock.MAX_AGE;
            }
        }
        if (block instanceof SweetBerryBushBlock) {
            if (state.hasProperty(SweetBerryBushBlock.AGE)) {
                return state.getValue(SweetBerryBushBlock.AGE) >= SweetBerryBushBlock.MAX_AGE;
            }
        }
        if (block instanceof CocoaBlock) {
            if (state.hasProperty(CocoaBlock.AGE)) {
                return state.getValue(CocoaBlock.AGE) >= CocoaBlock.MAX_AGE;
            }
        }
        if (state.is(ModTags.FARMER_BLOCKS_TO_BREAK)) {
            return true;
        }
        return false;
    }

    public static BlockState getPlantStateAfterHarvest(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock cropBlock) {
            return state.setValue(cropBlock.getAgeProperty(), 0);
        } else if (block instanceof SweetBerryBushBlock) {
            return state.setValue(SweetBerryBushBlock.AGE, 1);
        } else if (block instanceof CocoaBlock) {
            return state.setValue(CocoaBlock.AGE, 0);
        } else if (state.is(ModTags.FARMER_BLOCKS_TO_BREAK)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return block.defaultBlockState();
        }
    }

    public static void harvestPlant(ServerLevel level, BlockPos pos, BlockState state, Entity entity, ItemStack tool) {
        Item item = state.getBlock().getCloneItemStack(level, pos, state).getItem();
        Block.getDrops(state, level, pos, null, entity, tool).forEach(stack -> {
            if (item == stack.getItem() && !state.is(ModTags.FARMER_BLOCKS_TO_BREAK)) {
                stack.setCount(stack.getCount() - 1);
            }
            Block.popResource(level, pos, stack);
        });
        state.spawnAfterBreak(level, pos, tool, true);
    }
}
