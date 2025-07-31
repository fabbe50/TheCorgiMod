package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.entity.ai.HungryTamedAnimalGoesToEatGoal;
import com.fabbe50.corgimod.world.entity.ai.overrides.CustomBegGoal;
import com.fabbe50.corgimod.world.entity.ai.overrides.CustomWaterAvoidingRandomStrollGoal;
import com.fabbe50.corgimod.world.entity.ai.NonTameRandomTargetHungryGoal;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.feature.AbstractTamableAnimalFeaturePack;
import com.fabbe50.corgimod.world.item.CorgiHolderItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BaseCorgiFeaturePack extends AbstractTamableAnimalFeaturePack<Corgi> {
    public List<EntityType<?>> getPreyTargets() {
        return List.of(EntityType.SHEEP, EntityType.RABBIT, EntityType.FOX);
    }

    public Predicate<LivingEntity> getPreySelector() {
        return livingEntity -> {
            EntityType<?> preyEntityType = livingEntity.getType();
            return this.getPreyTargets().contains(preyEntityType);
        };
    }

    @Override
    public void addAdditionalSaveData(Corgi entity, CompoundTag tag) {

    }

    @Override
    public void readAdditionalSaveData(Corgi entity, CompoundTag tag) {

    }

    @Override
    public <R> R getCustomData(Corgi entity, EntityDataAccessor<R> accessor) {
        return entity.getEntityData().get(accessor);
    }

    @Override
    public <R> void setCustomData(Corgi entity, EntityDataAccessor<R> accessor, R value) {
        entity.getEntityData().set(accessor, value);
    }

    @Override
    public void registerGoals(Corgi entity, GoalSelector goalSelector) {
        super.registerGoals(entity, goalSelector);
        goalSelector.addGoal(3, new AvoidEntityGoal<>(entity, Llama.class, 24.0F, 1.5, 1.5));
        goalSelector.addGoal(4, new LeapAtTargetGoal(entity, 0.4F));
        goalSelector.addGoal(5, new MeleeAttackGoal(entity, 1.0, true));
        goalSelector.addGoal(20, new BreedGoal(entity, 1.0));
        goalSelector.addGoal(21, new CustomWaterAvoidingRandomStrollGoal<>(entity, 1.0, ModConfig.<Integer>getValue("maxWanderingDistance").getValue()));
        goalSelector.addGoal(22, new CustomBegGoal<>(entity, 8.0F));
    }

    @Override
    public void registerTargets(Corgi entity, GoalSelector targetSelector) {
        targetSelector.addGoal(1, new OwnerHurtByTargetGoal(entity));
        targetSelector.addGoal(2, new OwnerHurtTargetGoal(entity));
        targetSelector.addGoal(3, (new HurtByTargetGoal(entity)).setAlertOthers());
        targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity, Player.class, 10, true, false, entity::isAngryAt));
        targetSelector.addGoal(5, new NonTameRandomTargetHungryGoal<>(entity, Animal.class, false, getPreySelector()));
        targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(entity, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(entity, AbstractSkeleton.class, false));
        targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(entity, true));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.CORGI_FOOD);
    }

    @Override
    public boolean isTamingItem(ItemStack stack) {
        return stack.is(ModTags.CORGI_TAMING_ITEMS);
    }

    @Override
    public boolean isItemOfInterest(Corgi entity, ItemStack stack) {
        return isFood(stack) || isTamingItem(stack);
    }

    @Override
    public double getMovementSpeed() {
        return ModConfig.<Float>getValue("wildCorgiMovementSpeed").getValue();
    }

    @Override
    public double getTamedMovementSpeed() {
        return ModConfig.<Float>getValue("tamedCorgiMovementSpeed").getValue();
    }

    @Override
    public double getMaxHealth() {
        return ModConfig.<Float>getValue("wildCorgiMaxHealth").getValue();
    }

    @Override
    public double getTamedMaxHealth() {
        return ModConfig.<Float>getValue("tamedCorgiMaxHealth").getValue();
    }

    @Override
    public double getAttackDamage() {
        return ModConfig.<Float>getValue("wildCorgiAttackDamage").getValue();
    }

    @Override
    public double getTamedAttackDamage() {
        return ModConfig.<Float>getValue("tamedCorgiAttackDamage").getValue();
    }

    @Override
    public void onTame(Corgi entity, GoalSelector goalSelector) {
        Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(getTamedMaxHealth());
        entity.setHealth((float) getTamedMaxHealth());
        Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(getTamedAttackDamage());
    }

    @Override
    public InteractionResult onInteractWith(Corgi entity, Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void aiStep(Corgi entity) {

    }

    @Override
    public void onTick(Corgi entity) {
        entity.interestedAngleO = entity.interestedAngle;
        if (entity.isInterested()) {
            entity.interestedAngle += (1 - entity.interestedAngle) * 0.4f;

        } else {
            entity.interestedAngle += (0 - entity.interestedAngle) * 0.4f;
        }

        if (entity.isInWaterRainOrBubble()) {
            entity.setWet(true);
            if (entity.isShaking() && !entity.level().isClientSide) {
                entity.level().broadcastEntityEvent(entity, (byte) 56);
                entity.cancelShake();
            }
        } else if ((entity.isWet() || entity.isShaking()) && entity.isShaking()) {
            if (entity.shakeAnim == 0.0F) {
                entity.playSound(Sounds.SHAKE.getSound(), entity.getSoundVolume(), (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1);
                entity.gameEvent(GameEvent.ENTITY_ACTION);
            }

            entity.shakeAnimO = entity.shakeAnim;
            entity.shakeAnim += 0.05F;
            if (entity.shakeAnimO >= 2) {
                entity.setWet(false);
                entity.setShaking(false);
                entity.shakeAnimO = 0;
                entity.shakeAnim = 0;
            }

            if (entity.shakeAnim > 0.4F) {
                float f = (float)entity.getY();
                int i = (int)(Mth.sin((entity.shakeAnim - 0.4F) * 3.1415927F) * 7);
                Vec3 vec3 = entity.getDeltaMovement();

                for(int j = 0; j < i; ++j) {
                    float g = (entity.getRandom().nextFloat() * 2 - 1) * entity.getBbWidth() * 0.5F;
                    float h = (entity.getRandom().nextFloat() * 2 - 1) * entity.getBbWidth() * 0.5F;
                    entity.level().addParticle(ParticleTypes.SPLASH, entity.getX() + (double)g, f + 0.8F, entity.getZ() + (double)h, vec3.x, vec3.y, vec3.z);
                }
            }
        }
    }

    @Override
    public boolean doHurtTarget(Corgi entity, net.minecraft.world.entity.LivingEntity target) {
        return true;
    }

    @Override
    public HurtType onHurt(Corgi entity, DamageSource source, float f) {
        return HurtType.PASS;
    }

    @Override
    public void onDeath(Corgi entity, DamageSource source) {

    }

    @Override
    public void dropCustomDeathLoot(Corgi entity, ServerLevel level, DamageSource damageSource, boolean ignoreDropChance) {
        if (entity.isTame()) {
            ItemStack soulStack = new ItemStack(ModRegistries.CORGI_SOUL.get());
            CorgiHolderItem soulItem = (CorgiHolderItem) soulStack.getItem();
            soulItem.addCorgi(soulStack, entity);
            this.dropItem(entity, soulStack);
        }
    }

    protected void dropItem(Corgi entity, ItemLike item) {
        ItemStack dropStack = new ItemStack(item, entity.getRandom().nextInt(1, 4));
        dropItem(entity, dropStack);
    }

    protected void dropItem(Corgi entity, ItemStack stack) {
        entity.spawnAtLocation(stack);
    }

    @Override
    public boolean hasInventory(Corgi entity) {
        return false;
    }

    @Override
    public Container getInventory(Corgi entity) {
        throw new RuntimeException("Tried to access inventory on entity without inventory.");
    }

    @Override
    public void onItemPickup(Corgi entity, ItemEntity itemEntity) {

    }

    @Override
    public void onHandleParticles(Corgi entity, RandomSource random) {

    }

    @Override
    public SoundEvent onAmbientSound(Corgi entity) {
        if (entity.isAngry()) {
            return Sounds.GROWL.getSound();
        } else if (entity.getRandom().nextInt(3) == 0) {
            return entity.isTame() && entity.getHealth() < getTamedMaxHealth() / 2 ? Sounds.WHINE.getSound() : Sounds.PANT.getSound();
        } else {
            return Sounds.AMBIENT.getSound();
        }
    }

    public enum Sounds implements ISounds {
        SHAKE {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_SHAKE;
            }
        },
        HOWL {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_HOWL;
            }
        },
        HURT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_HURT;
            }
        },
        DEATH {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_DEATH;
            }
        },
        STEP {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_STEP;
            }
        },
        AMBIENT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_AMBIENT;
            }
        },
        WHINE {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_WHINE;
            }
        },
        PANT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_PANT;
            }
        },
        GROWL {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.WOLF_GROWL;
            }
        },
        EAT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.GENERIC_EAT;
            }
        };

        @Override
        public SoundEvent getSound() {
            return SoundEvents.EMPTY;
        }
    }
}
