package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.utils.Utilities;
import com.fabbe50.corgimod.world.entity.ability.IAbility;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpyCorgi extends Corgi implements IAbility {
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(CorgiMod.config.corgiAbilities.spyCorgiRange).ignoreLineOfSight();
    public SpyCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void runAbilityWhileFed() {
        if (this.level().getGameTime() % Utilities.ticksFromSecond(30) == 0 && !this.isInSittingPose()) {
            List<LivingEntity> entities = this.level().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(CorgiMod.config.corgiAbilities.spyCorgiRange));
            for (LivingEntity entity : entities) {
                if (entity != null && entity.isAlive() && entity instanceof Enemy) {
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, Utilities.ticksFromSecond(CorgiMod.config.corgiAbilities.spyCorgiExposeTime)));
                }
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
            return Component.literal(Corgis.SPY.getFormattedName());
        return super.getDisplayName();
    }
}
