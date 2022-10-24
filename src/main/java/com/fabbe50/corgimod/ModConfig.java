package com.fabbe50.corgimod;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = CorgiMod.MODID)
public class ModConfig implements ConfigData {
    boolean showCorgiDefaultNames = false;
}