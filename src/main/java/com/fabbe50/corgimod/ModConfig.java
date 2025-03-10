package com.fabbe50.corgimod;

import com.fabbe50.corgimod.world.item.ItemRegistry;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

@Config(name = CorgiMod.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public General general = new General();
    @ConfigEntry.Gui.CollapsibleObject
    public CorgiAbilities corgiAbilities = new CorgiAbilities();

    public static class General {
        @ConfigEntry.Gui.Tooltip(count = 3)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public NamingMode namingMode;

        @ConfigEntry.Gui.Tooltip
        public boolean enableWorkInProgressFeatures = false;
        @ConfigEntry.Gui.Tooltip
        public boolean corgiParticleEffects = true;

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public BreedingMode breedingMode;

        @ConfigEntry.Gui.Tooltip
        public boolean allowUraniumTNTBoosting = true;

        @ConfigEntry.Gui.Tooltip
        public int passiveCorgiSpawnWeight = 5;

        @ConfigEntry.Gui.Tooltip
        public int creeperCorgiSpawnWeight = 25;

        @ConfigEntry.Gui.Tooltip
        public int skeletonCorgiSpawnWeight = 25;

        @ConfigEntry.Gui.Tooltip
        public int zombieCorgiSpawnWeight = 35;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int zombieCorgiJockeySpawnChance = 5;


        public General() {
            this.namingMode = NamingMode.RANDOM_NAMES;
            this.breedingMode = BreedingMode.PARENTS;
        }
    }

    public static class CorgiAbilities {
        @ConfigEntry.Gui.Tooltip
        public int loveCorgiEffectRange = 5;
        @ConfigEntry.Gui.Tooltip
        public int loveCorgiMaxEntityCount = 16;
        @ConfigEntry.Gui.Tooltip
        public float pirateCorgiBoatSpeed = 1.5f;
        @ConfigEntry.Gui.Tooltip
        public double spyCorgiRange = 16.0d;
        @ConfigEntry.Gui.Tooltip
        public int spyCorgiExposeTime = 5;
        @ConfigEntry.Gui.Tooltip
        public boolean fabbe50CorgiDoRandomDrops = true;
        @ConfigEntry.Gui.Tooltip
        public boolean fabbe50CorgiDoRandomDropEvents = true;
    }

    public enum NamingMode {
        OFF,
        DEFAULT_NAMES,
        RANDOM_NAMES;

        NamingMode() {}
    }

    public enum BreedingMode {
        RANDOM,
        PARENTS;

        BreedingMode() {}
    }
}