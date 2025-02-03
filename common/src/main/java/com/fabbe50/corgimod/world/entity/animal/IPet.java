package com.fabbe50.corgimod.world.entity.animal;

import net.minecraft.core.BlockPos;

public interface IPet {
    void setAskedToStay(boolean stay);

    boolean isAskedToStay();

    void setOriginStay(BlockPos origin);

    BlockPos getOriginStay();
}
