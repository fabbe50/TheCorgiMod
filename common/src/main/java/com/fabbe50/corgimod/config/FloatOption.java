package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;

import java.util.Properties;

public class FloatOption extends AbstractRangedConfigOption<Float, FloatListEntry> {
    public FloatOption(String name, Float defaultValue, Float min, Float max) {
        super(name, defaultValue, min, max);
    }

    public FloatOption(String name, Float defaultValue, Float value, Float min, Float max) {
        super(name, defaultValue, value, min, max);
    }

    @Override
    public FloatListEntry buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startFloatField(getTranslation(), getValue())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setSaveConsumer(this::setValue)
                .build();
    }

    @Override
    public void readData(Properties properties) {
        setValue(Float.parseFloat((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
