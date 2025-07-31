package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import static com.fabbe50.corgimod.world.entity.animal.Corgi.PLAYER_SAVING_COOLDOWN;

public class HeroCorgiFeaturePack extends BaseCorgiFeaturePack {
    public static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();

    public void setPlayerSavingCooldown(Corgi entity, int cooldown) {
        setCustomData(entity, PLAYER_SAVING_COOLDOWN, cooldown);
    }

    public int getPlayerSavingCooldown(Corgi entity) {
        return getCustomData(entity, PLAYER_SAVING_COOLDOWN);
    }

    @Override
    public InteractionResult onInteractWith(Corgi entity, Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(ModRegistries.URANIUM)) {
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, Utilities.ticksFromSecond(30), 0));
            player.getItemInHand(hand).consume(1, entity);
            return InteractionResult.SUCCESS;
        }
        return super.onInteractWith(entity, player, hand);
    }

    @Override
    public void onTick(Corgi entity) {
        super.onTick(entity);
        if (getPlayerSavingCooldown(entity) <= 0 && !entity.isHungry()) {
            if (entity.getRandom().nextInt(8) == 0) {
                entity.level().addParticle(ParticleTypes.GLOW, Utilities.getPartialPos(entity.getX()), Utilities.getPartialPosY(entity.getY()), Utilities.getPartialPos(entity.getZ()), 0, 0, 0);
            }
        } else if (entity.level().getGameTime() % 20 == 0) {
            setPlayerSavingCooldown(entity, getPlayerSavingCooldown(entity) - 20);
        }
    }

    @Override
    public HurtType onHurt(Corgi entity, DamageSource source, float f) {
        if (!entity.hasEffect(MobEffects.WEAKNESS)) {
            return HurtType.NO_HURT;
        }
        return super.onHurt(entity, source, f);
    }
}
