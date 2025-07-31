package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;

import java.util.List;

public class SpyCorgiFeaturePack extends BaseCorgiFeaturePack {
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(ModConfig.<Double>getValue("spyCorgiRange").getValue()).ignoreLineOfSight();
    private int tick = 0;

    @Override
    public void aiStep(Corgi entity) {
        if (Utilities.ticksFromSecond(30) < tick++ && !entity.isInSittingPose() && entity.isTame()) {
            List<net.minecraft.world.entity.LivingEntity> entities = entity.level().getNearbyEntities(net.minecraft.world.entity.LivingEntity.class, TARGETING_CONDITIONS, entity, entity.getBoundingBox().inflate(ModConfig.<Double>getValue("spyCorgiRange").getValue()));
            for (net.minecraft.world.entity.LivingEntity target : entities) {
                if (target != null && target.isAlive() && target instanceof Enemy) {
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, Utilities.ticksFromSecond(ModConfig.<Integer>getValue("spyCorgiExposeTime").getValue())));
                }
            }
            tick = 0;
        }
    }
}
