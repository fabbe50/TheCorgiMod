package com.fabbe50.corgimod.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AlignablePos implements StringRepresentable {
    FRONT("front"),
    BACK("back"),
    CENTER("center");

    final String name;
    AlignablePos(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
