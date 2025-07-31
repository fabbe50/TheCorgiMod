package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;

import java.util.Properties;

public class DoubleOption extends AbstractRangedConfigOption<Double, DoubleListEntry> {
    public DoubleOption(String name, Double defaultValue, Double min, Double max) {
        super(name, defaultValue, min, max);
    }

    public DoubleOption(String name, Double defaultValue, Double value, Double min, Double max) {
        super(name, defaultValue, value, min, max);
    }

    @Override
    public DoubleListEntry buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startDoubleField(getTranslation(), getValue())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setSaveConsumer(this::setValue)
                .build();
    }

    @Override
    public void readData(Properties properties) {
        setValue(Double.parseDouble((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
