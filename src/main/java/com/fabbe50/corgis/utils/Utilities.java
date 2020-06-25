package com.fabbe50.corgis.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class Utilities {
    public static boolean destroyBlock(World world, BlockPos pos, boolean dropBlock, boolean dropTrueBlock, boolean soundParticles) {
        BlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (block.isAir(iblockstate, world, pos))
            return false;
        else {
            if (soundParticles)
                world.playEvent(2001, pos, Block.getStateId(iblockstate));

            if (dropBlock && !dropTrueBlock)
                Block.spawnDrops(world.getBlockState(pos), world, pos);
            else if (dropTrueBlock)
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(block.asItem())));

            return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }
}
