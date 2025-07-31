package com.fabbe50.corgimod.world.block.state.properties;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public enum Color implements StringRepresentable {
    WHITE("white", DyeColor.WHITE),
    LIGHT_GRAY("light_gray", DyeColor.LIGHT_GRAY),
    GRAY("gray", DyeColor.GRAY),
    BLACK("black", DyeColor.BLACK),
    BROWN("brown", DyeColor.BROWN),
    RED("red", DyeColor.RED),
    ORANGE("orange", DyeColor.ORANGE),
    YELLOW("yellow", DyeColor.YELLOW),
    LIME("lime", DyeColor.LIME),
    GREEN("green", DyeColor.GREEN),
    CYAN("cyan", DyeColor.CYAN),
    LIGHT_BLUE("light_blue", DyeColor.LIGHT_BLUE),
    BLUE("blue", DyeColor.BLUE),
    PURPLE("purple", DyeColor.PURPLE),
    MAGENTA("magenta", DyeColor.MAGENTA),
    PINK("pink", DyeColor.PINK);

    final String name;
    final DyeColor dyeColor;
    Color(String name, DyeColor dyeColor) {
        this.name = name;
        this.dyeColor = dyeColor;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public String translationKey() {
        return "color." + name;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
}
