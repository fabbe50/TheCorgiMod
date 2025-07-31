package com.fabbe50.corgimod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class Utilities {
    private static final Random random = new Random();

    public static int[] getIntArrayFromBlockPos(BlockPos pos) {
        return new int[]{ pos.getX(), pos.getY(), pos.getZ() };
    }

    public static BlockPos getBlockPosFromIntArray(int[] pos) {
        if (pos.length == 3) {
            return new BlockPos(pos[0], pos[1], pos[2]);
        }
        return null;
    }

    public static int getColorIntegerFromRGB(float r, float g, float b) {
        return getColorIntegerFromRGB((int)r, (int)g, (int)b);
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
            aABB2 = aABB2.expandTowards(0.0F, -1.0F, 0.0F);
        }

        Iterable<VoxelShape> iterable = levelReader.getCollisions(null, aABB2);
        return (double)1.0F + Shapes.collide(Direction.Axis.Y, aABB, iterable, bl ? (double)-2.0F : (double)-1.0F);
    }

    public static double getPartialPos(double axisPosition) {
        return axisPosition + random.nextDouble() - 0.5D;
    }

    public static double getPartialPosY(double axisPosition) {
        return axisPosition + random.nextDouble();
    }

    public static int ticksFromSecond(int seconds) {
        return seconds * 20;
    }

    public static Random getRandom() {
        return random;
    }

    public static String capitalizeFirstInEveryWord(String input) {
        String[] words = input.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toTitleCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return result.toString().trim();
    }
}
