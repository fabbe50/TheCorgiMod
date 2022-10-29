package com.fabbe50.corgimod;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = CorgiMod.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("General")
    public General general = new General();

    public static class General {
        public boolean showCorgiDefaultNames = false;
    }
}