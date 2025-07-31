package com.fabbe50.corgimod.config;

import com.fabbe50.corgimod.TheCorgiMod;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.network.chat.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public interface IConfigOption<T, R extends AbstractConfigListEntry<T>> extends IClothBuilder<T, R> {
    String getKey();

    default Component getTranslation() {
        return Component.translatable("option." + TheCorgiMod.MOD_ID + "." + getKey());
    }

    default Component getTooltipTranslation() {
        return Component.translatable("option." + TheCorgiMod.MOD_ID + "." + getKey() + ".tooltip");
    }

    void setValue(T value);

    T getValue();

    T getDefaultValue();

    default void writeData(FileOutputStream fos) throws IOException {
        fos.write((getKey() + "=" + getValue()).getBytes());
        fos.write("\n".getBytes());
    }

    void readData(Properties properties);
}
