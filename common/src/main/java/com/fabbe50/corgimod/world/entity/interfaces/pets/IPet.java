package com.fabbe50.corgimod.world.entity.interfaces.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;

public interface IPet {
    void setAskedToStay(boolean stay);

    boolean isAskedToStay();

    void setOriginStay(BlockPos origin);

    BlockPos getOriginStay();

    boolean isWithinRangeOfOrigin();

    void setSleeping(boolean sleeping);

    boolean isSleeping();
}
