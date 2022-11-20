package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.utils.Utils;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeroCorgi extends Corgi {
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
    private static final EntityDataAccessor<Integer> PLAYER_SAVING_COOLDOWN = SynchedEntityData.defineId(HeroCorgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_BEEN_FED = SynchedEntityData.defineId(HeroCorgi.class, EntityDataSerializers.BOOLEAN);

    public HeroCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_SAVING_COOLDOWN, 0);
        this.entityData.define(HAS_BEEN_FED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("player_saving_cooldown", this.getPlayerSavingCooldown());
        compoundTag.putBoolean("has_been_fed", this.hasBeenFed());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPlayerSavingCooldown(compoundTag.getInt("player_saving_cooldown"));
        this.setHasBeenFed(compoundTag.getBoolean("has_been_fed"));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (this.isFood(player.getItemInHand(hand))) {
            if (!this.hasBeenFed()) {
                this.setHasBeenFed(true);
                return InteractionResult.SUCCESS;
            }
        } else if (player.getItemInHand(hand).is(ItemRegistry.URANIUM.get())) {
            this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, Utils.ticksFromSecond(30), 0));
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPlayerSavingCooldown() <= 0 && this.hasBeenFed()) {
            if (this.random.nextInt(8) == 0) {
                this.getLevel().addParticle(ParticleTypes.GLOW, this.getX() + this.random.nextDouble() - 0.5D, this.getY() + this.random.nextDouble(), this.getZ() + this.random.nextDouble() - 0.5D, 0, 0, 0);
            }
        } else if (this.getLevel().getGameTime() % 20 == 0) {
            this.setPlayerSavingCooldown(getPlayerSavingCooldown() - 20);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float v) {
        return this.hasEffect(MobEffects.WEAKNESS) && super.hurt(damageSource, v);
    }

    public void setPlayerSavingCooldown(int cooldown) {
        this.entityData.set(PLAYER_SAVING_COOLDOWN, cooldown);
    }

    public int getPlayerSavingCooldown() {
        return this.entityData.get(PLAYER_SAVING_COOLDOWN);
    }

    public void setHasBeenFed(boolean hasBeenFed) {
        this.entityData.set(HAS_BEEN_FED, hasBeenFed);
    }

    public boolean hasBeenFed() {
        return this.entityData.get(HAS_BEEN_FED);
    }

    public static class HeroCorgiEvents {
        @SubscribeEvent
        public void onPlayerDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player) {
                List<HeroCorgi> list = player.getLevel().getNearbyEntities(HeroCorgi.class, TARGETING_CONDITIONS, player, player.getBoundingBox().inflate(16.0D));
                for (HeroCorgi corgi : list) {
                    if (corgi.getOwner() != null && corgi.getOwner().is(player) && corgi.hasBeenFed() && corgi.getPlayerSavingCooldown() <= 0) {
                        player.setHealth(1);
                        event.setCanceled(true);
                        for (int i = 0; i < 8; i++) {
                            player.getLevel().addParticle(ParticleTypes.HEART, player.position().x + player.getRandom().nextDouble() - 0.5D, player.position().y + player.getRandom().nextInt(2) + player.getRandom().nextDouble() - 0.5D, player.position().z + player.getRandom().nextDouble() - 0.5D, 0, 0.1f, 0);
                        }
                        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Utils.ticksFromSecond(20), 2, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Utils.ticksFromSecond(5), 3, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Utils.ticksFromSecond(20), 2, false, true));
                        if (event.getSource().isFire()) {
                            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Utils.ticksFromSecond(20), 0, false, true));
                        }
                        if (event.getSource().equals(DamageSource.DROWN)) {
                            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, Utils.ticksFromSecond(20), 0, false, true));
                        }
                        corgi.setHasBeenFed(false);
                        corgi.setPlayerSavingCooldown(Utils.ticksFromSecond(corgi.getRandom().nextInt(600) + 2400));
                        break;
                    }
                }
            }
        }
    }
}
