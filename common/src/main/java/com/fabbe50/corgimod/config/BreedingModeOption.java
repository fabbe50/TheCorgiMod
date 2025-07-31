package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.EnumListEntry;

import java.util.Properties;

public class BreedingModeOption extends AbstractConfigOption<BreedingModeOption.BreedingMode, EnumListEntry<BreedingModeOption.BreedingMode>> {
    public BreedingModeOption(String name, BreedingMode defaultValue) {
        super(name, defaultValue);
    }

    public BreedingModeOption(String name, BreedingMode defaultValue, BreedingMode value) {
        super(name, defaultValue, value);
    }

    @Override
    public EnumListEntry<BreedingMode> buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startEnumSelector(getTranslation(), BreedingMode.class, getValue())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setSaveConsumer(this::setValue)
                .build();
    }

    public enum BreedingMode {
        PARENTS,
        RANDOM
    }

    @Override
    public void readData(Properties properties) {
        setValue(BreedingMode.valueOf((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
