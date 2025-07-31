package com.fabbe50.corgimod.world.entity.interfaces.pets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;

public interface ICollaredPet extends IPet {
    void setCollarColor(DyeColor color);

    DyeColor getCollarColor();

    void setCollarGlow(boolean glow);

    boolean isCollarGlow();

    default void saveCollarData(CompoundTag compoundTag) {
        compoundTag.putInt("collarColor", this.getCollarColor().getId());
        compoundTag.putBoolean("collarGlow", this.isCollarGlow());
    }

    default void readCollarData(CompoundTag compoundTag) {
        if (compoundTag.contains("collarColor")) {
            this.setCollarColor(DyeColor.byId(compoundTag.getInt("collarColor")));
        }
        if (compoundTag.contains("collarGlow")) {
            this.setCollarGlow(compoundTag.getBoolean("collarGlow"));
        }
    }
}
