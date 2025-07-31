package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import net.minecraft.network.chat.Component;

import java.util.Properties;

public class IntegerOption extends AbstractRangedConfigOption<Integer, IntegerSliderEntry> {
    private final String textGetterValue;

    public IntegerOption(String name, Integer defaultValue, Integer min, Integer max) {
        this(name, defaultValue, min, max, "Value: %s");
    }

    public IntegerOption(String name, Integer defaultValue, Integer min, Integer max, String textGetterValue) {
        this(name, defaultValue, defaultValue, min, max, textGetterValue);
    }

    public IntegerOption(String name, Integer defaultValue, Integer value, Integer min, Integer max, String textGetterValue) {
        super(name, defaultValue, value, min, max);
        this.textGetterValue = textGetterValue;
    }

    @Override
    public IntegerSliderEntry buildClothEntry(ConfigEntryBuilder builder) {
        return builder.startIntSlider(getTranslation(), getValue(), min(), max())
                .setDefaultValue(getDefaultValue())
                .setTooltip(getTooltipTranslation())
                .setTextGetter(integer -> Component.translatable(textGetterValue, integer))
                .setSaveConsumer(this::setValue)
                .build();
    }

    @Override
    public void readData(Properties properties) {
        setValue(Integer.parseInt((String) properties.computeIfAbsent(getKey(), o -> String.valueOf(getDefaultValue()))));
    }
}
