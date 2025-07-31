package com.fabbe50.corgimod.world.block.state.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum WoodType implements StringRepresentable {
    OAK("oak"),
    SPRUCE("spruce"),
    BIRCH("birch"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    MANGROVE("mangrove"),
    CHERRY("cherry"),
    BAMBOO("bamboo"),
    CRIMSON("crimson"),
    WARPED("warped");

    final String name;
    WoodType(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public String translationKey() {
        return "wood_type." + name;
    }
}
