package com.fabbe50.corgimod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static int ticksFromSecond(int seconds) {
        return seconds * 20;
    }

    public static int getTickTimeForSmeltingItem(int amount) {
        return amount * 200;
    }
}
