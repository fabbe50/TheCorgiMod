package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;

public class BodyguardCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public double getMaxHealth() {
        return ModConfig.<Integer>getValue("bodyguardCorgiMaxHealth").getValue();
    }

    @Override
    public double getTamedMaxHealth() {
        return ModConfig.<Integer>getValue("bodyguardCorgiTamedMaxHealth").getValue();
    }

    @Override
    public double getAttackDamage() {
        return ModConfig.<Integer>getValue("bodyguardCorgiAttackDamage").getValue();
    }

    @Override
    public double getTamedAttackDamage() {
        return ModConfig.<Integer>getValue("bodyguardCorgiTamedAttackDamage").getValue();
    }
}
