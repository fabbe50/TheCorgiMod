package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.gameevent.GameEvent;

public class RadioactiveCorgiFeaturePack extends BaseCorgiFeaturePack {
    public int uraniumDropTime = Utilities.getRandom().nextInt(6000) + 6000;

    @Override
    public void aiStep(Corgi entity) {
        if (!entity.level().isClientSide && entity.isAlive() && entity.isTame()) {
            if (!entity.isHungry()) {
                runAbilityWhileFed(entity);
            }
        }
        super.aiStep(entity);
    }

    @Override
    public void onHandleParticles(Corgi entity, RandomSource random) {
        entity.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, (39 / 255f), (194 / 255f), (44 / 255f)), entity.getX() + random.nextDouble() - 0.5D, entity.getY() + random.nextDouble(), entity.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
    }

    public void runAbilityWhileFed(Corgi entity) {
        if (!entity.level().isClientSide && entity.isAlive() && !entity.isBaby() && --this.uraniumDropTime <= 0) {
            entity.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (Utilities.getRandom().nextFloat() - Utilities.getRandom().nextFloat()) * 0.2F + 1.0F);
            entity.spawnAtLocation(ModRegistries.URANIUM.get());
            entity.gameEvent(GameEvent.ENTITY_PLACE);
            this.uraniumDropTime = Utilities.getRandom().nextInt(6000) + 6000;
            if (entity.getRandom().nextInt(5) == 0) {
                entity.setHungry(true);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Corgi entity, net.minecraft.world.entity.LivingEntity target) {
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 10, 3));
        target.addEffect(new MobEffectInstance(MobEffects.HUNGER, 10, 3));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 3));
        return super.doHurtTarget(entity, target);
    }
}
