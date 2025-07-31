package com.fabbe50.corgimod.world.entity.monster;

import com.fabbe50.corgimod.registries.EntityRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HuskCorgi extends ZombieCorgi {
    public HuskCorgi(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    protected boolean isSunSensitive() {
        return false;
    }

    protected @NotNull SoundEvent getAmbientSound() {
        return SoundEvents.HUSK_AMBIENT;
    }

    protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HUSK_HURT;
    }

    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
    }

    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.HUSK_STEP;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean bl = super.doHurtTarget(entity);
        if (bl && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)f), this);
        }
        return bl;
    }

    protected void doUnderWaterConversion() {
        this.convertToZombieType(EntityRegistry.ZOMBIE_CORGI.get());
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1041, this.blockPosition(), 0);
        }
    }
}
