package com.fabbe50.corgimod.world.entity.feature;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IFeaturePack<T extends PathfinderMob> {
    void addAdditionalSaveData(T entity, CompoundTag tag);

    void readAdditionalSaveData(T entity, CompoundTag tag);

    <R> R getCustomData(T entity, EntityDataAccessor<R> accessor);

    <R> void setCustomData(T entity, EntityDataAccessor<R> accessor, R value);

    void registerGoals(T entity, GoalSelector goalSelector);

    void registerTargets(T entity, GoalSelector targetSelector);

    boolean isFood(ItemStack stack);

    boolean isTamingItem(ItemStack stack);

    boolean isItemOfInterest(T entity, ItemStack stack);

    double getMovementSpeed();

    double getTamedMovementSpeed();

    double getMaxHealth();

    double getTamedMaxHealth();

    double getAttackDamage();

    double getTamedAttackDamage();

    void onTame(T entity, GoalSelector goalSelector);

    InteractionResult onInteractWith(T entity, Player player, InteractionHand hand);

    void aiStep(T entity);

    void onTick(T entity);

    boolean doHurtTarget(T entity, LivingEntity target);

    HurtType onHurt(T entity, DamageSource source, float f);

    void onDeath(T entity, DamageSource source);

    void dropCustomDeathLoot(T entity, ServerLevel level, DamageSource damageSource, boolean ignoreDropChance);

    boolean hasInventory(T entity);

    Container getInventory(T entity);

    void onItemPickup(T entity, ItemEntity itemEntity);

    void onHandleParticles(T entity, RandomSource random);

    SoundEvent onAmbientSound(T entity);

    // In the feature pack implementing IFeaturePack, make enum implementing ISounds.
    interface ISounds {
        SoundEvent getSound();
    }

    enum HurtType {
        NO_HURT,
        HURT,
        PASS;
    }
}
