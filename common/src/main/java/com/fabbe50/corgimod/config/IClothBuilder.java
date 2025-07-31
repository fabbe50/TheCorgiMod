package com.fabbe50.corgimod.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

public interface IClothBuilder<R, T extends AbstractConfigListEntry<R>> {
    T buildClothEntry(ConfigEntryBuilder builder);
}
