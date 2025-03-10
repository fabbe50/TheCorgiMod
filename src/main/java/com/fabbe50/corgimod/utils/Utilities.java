package com.fabbe50.corgimod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class Utilities {
    private static final Random random = new Random();

    public static int ticksFromSecond(int seconds) {
        return seconds * 20;
    }

    public static int getTickTimeForSmeltingItem(int amount) {
        return amount * 200;
    }

    public static Vec3i vec3iFromVec3(Vec3 vec3) {
        return new Vec3i((int)vec3.x, (int)vec3.y, (int)vec3.z);
    }

    public static void fixPos(Entity entity, ServerLevel serverLevel, BlockPos blockPos, boolean bl, boolean bl2) {
        double d;
        if (bl) {
            entity.setPos((double)blockPos.getX() + (double)0.5F, blockPos.getY() + 1, (double)blockPos.getZ() + (double)0.5F);
            d = getYOffset(serverLevel, blockPos, bl2, entity.getBoundingBox());
        } else {
            d = 0.0F;
        }

        entity.moveTo((double)blockPos.getX() + (double)0.5F, (double)blockPos.getY() + d, (double)blockPos.getZ() + (double)0.5F, Mth.wrapDegrees(serverLevel.random.nextFloat() * 360.0F), 0.0F);
        if (entity instanceof Mob mob) {
            mob.yHeadRot = mob.getYRot();
            mob.yBodyRot = mob.getYRot();
            mob.playAmbientSound();
        }
    }

    protected static double getYOffset(LevelReader levelReader, BlockPos blockPos, boolean bl, AABB aABB) {
        AABB aABB2 = new AABB(blockPos);
        if (bl) {
            aABB2 = aABB2.expandTowards((double)0.0F, (double)-1.0F, (double)0.0F);
        }

        Iterable<VoxelShape> iterable = levelReader.getCollisions((Entity)null, aABB2);
        return (double)1.0F + Shapes.collide(Direction.Axis.Y, aABB, iterable, bl ? (double)-2.0F : (double)-1.0F);
    }

    public static double getPartialPos(double axisPosition) {
        return getPartialPos(axisPosition, 0);
    }

    public static double getPartialPos(double axisPosition, double offset) {
        return axisPosition + random.nextDouble() + offset;
    }

    public static double getPartialPosY(double axisPosition) {
        return getPartialPosY(axisPosition, 1);
    }

    public static double getPartialPosY(double axisPosition, double height) {
        return axisPosition + random.nextDouble(height);
    }
}
