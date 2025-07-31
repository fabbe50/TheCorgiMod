package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;

import java.util.Properties;

public class BooleanOption extends AbstractConfigOption<Boolean, BooleanListEntry> {
    public BooleanOption(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public BooleanOption(String name, Boolean defaultValue, Boolean value) {
        super(name, defaultValue, value);
    }

    @Override
    public BooleanListEntry buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startBooleanToggle(getTranslation(), getValue())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setSaveConsumer(this::setValue)
                .build();
    }

    @Override
    public void readData(Properties properties) {
        setValue(Boolean.parseBoolean((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
