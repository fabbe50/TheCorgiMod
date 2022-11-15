package com.fabbe50.corgimod;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = CorgiMod.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public General general = new General();
    @ConfigEntry.Gui.CollapsibleObject
    public CorgiAbilities corgiAbilities = new CorgiAbilities();

    public static class General {
        public boolean showCorgiDefaultNames = false;
        public boolean enableWorkInProgressFeatures = false;
        public boolean corgiParticleEffects = true;
    }

    public static class CorgiAbilities {
        public int loveCorgiEffectRange = 5;
        public int loveCorgiMaxEntityCount = 16;
        public float pirateCorgiBoatSpeed = 1.5f;
    }
}