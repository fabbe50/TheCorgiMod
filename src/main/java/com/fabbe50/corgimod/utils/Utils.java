package com.fabbe50.corgimod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static int ticksFromSecond(int seconds) {
        return seconds * 20;
    }

    public static int getTickTimeForSmeltingItem(int amount) {
        return amount * 200;
    }

    public static Vec3i vec3iFromVec3(Vec3 vec3) {
        return new Vec3i((int)vec3.x, (int)vec3.y, (int)vec3.z);
    }
}
