package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;

public abstract class AbstractConfigOption<T, R extends AbstractConfigListEntry<T>> implements IConfigOption<T, R> {
    private final String name;
    private final T defaultValue;
    private T value;

    public AbstractConfigOption(String name, T defaultValue) {
        this(name, defaultValue, defaultValue);
    }

    public AbstractConfigOption(String name, T defaultValue, T value) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
