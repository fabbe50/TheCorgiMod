package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpyCorgi extends Corgi {
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(CorgiMod.config.corgiAbilities.spyCorgiRange).ignoreLineOfSight();
    public SpyCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.SPY.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getLevel().getGameTime() % Utils.ticksFromSecond(30) == 0 && this.isTame()) {
            List<LivingEntity> entities = this.getLevel().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(CorgiMod.config.corgiAbilities.spyCorgiRange));
            for (LivingEntity entity : entities) {
                if (entity != null && entity.isAlive() && entity instanceof Enemy) {
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, Utils.ticksFromSecond(CorgiMod.config.corgiAbilities.spyCorgiExposeTime)));
                }
            }
        }
    }
}
