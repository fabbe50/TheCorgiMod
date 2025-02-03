package com.fabbe50.corgimod;


public class ModConfig {
    public static ModConfig INSTANCE = new ModConfig();

    public int maxWanderingDistance = 10; //Blocks
    public float tamedCorgiMaxHealth = 40;
    public float tamedCorgiAttackDamage = 8;
    public float wildCorgiMaxHealth = 40;
    public float wildCorgiAttackDamage = 4;

    public BreedingMode breedingMode = BreedingMode.PARENTS;

    public enum BreedingMode {
        PARENTS,
        RANDOM
    }
}
