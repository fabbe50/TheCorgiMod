package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.EnumListEntry;

import java.util.Properties;

public class NamingModeOption extends AbstractConfigOption<NamingModeOption.NamingMode, EnumListEntry<NamingModeOption.NamingMode>> {
    public NamingModeOption(String name, NamingMode defaultValue) {
        super(name, defaultValue);
    }

    public NamingModeOption(String name, NamingMode defaultValue, NamingMode value) {
        super(name, defaultValue, value);
    }

    @Override
    public EnumListEntry<NamingMode> buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startEnumSelector(getTranslation(), NamingMode.class, getValue())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setSaveConsumer(this::setValue)
                .build();
    }

    public enum NamingMode {
        NO_NAMES,
        DEFAULT_NAMES,
        RANDOM_NAMES
    }

    @Override
    public void readData(Properties properties) {
        setValue(NamingMode.valueOf((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
