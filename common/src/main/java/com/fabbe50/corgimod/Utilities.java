package com.fabbe50.corgimod;

import net.minecraft.core.BlockPos;

public class Utilities {
    public static int[] getIntArrayFromBlockPos(BlockPos pos) {
        return new int[]{ pos.getX(), pos.getY(), pos.getZ() };
    }

    public static BlockPos getBlockPosFromIntArray(int[] pos) {
        if (pos.length == 3) {
            return new BlockPos(pos[0], pos[1], pos[2]);
        }
        return null;
    }
}
