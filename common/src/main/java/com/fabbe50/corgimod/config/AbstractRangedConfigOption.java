package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;

public abstract class AbstractRangedConfigOption<T extends Number, R extends AbstractConfigListEntry<T>> extends AbstractConfigOption<T, R> implements IRangedConfigOption<T, R> {
    private final T min;
    private final T max;

    public AbstractRangedConfigOption(String name, T defaultValue, T min, T max) {
        this(name, defaultValue, defaultValue, min, max);
    }

    public AbstractRangedConfigOption(String name, T defaultValue, T value, T min, T max) {
        super(name, defaultValue, value);
        this.min = min;
        this.max = max;
    }

    @Override
    public T min() {
        return min;
    }

    @Override
    public T max() {
        return max;
    }
}
