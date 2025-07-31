package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;

public interface IRangedConfigOption<T extends Number, R extends AbstractConfigListEntry<T>> extends IConfigOption<T, R> {
    T min();
    T max();

    default boolean isWithingRange() {
        return min().doubleValue() < getValue().doubleValue() && getValue().doubleValue() < max().doubleValue();
    }
}
