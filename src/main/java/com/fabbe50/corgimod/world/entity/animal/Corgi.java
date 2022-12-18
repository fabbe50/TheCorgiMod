package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.ai.BreedGoalFix;
import com.fabbe50.corgimod.world.entity.ai.FollowOwnerGoalFix;
import com.fabbe50.corgimod.world.entity.ai.StayInPlaceGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class Corgi extends Wolf {
    private static final EntityDataAccessor<Boolean> ASKED_TO_STAY = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    public final float bobs;

    public Corgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        bobs = this.getRandom().nextFloat() * (float) Math.PI * 2.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Corgi.CorgiPanicGoal(1.5D));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new StayInPlaceGoal(this));
        this.goalSelector.addGoal(4, new Corgi.CorgiAvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new FollowOwnerGoalFix(this, 1.0d, 10.0f, 2.0f, false));
        this.goalSelector.addGoal(9, new BreedGoalFix(this, 1.0D));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ASKED_TO_STAY, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("AskedToStay", isAskedToStay());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAskedToStay(compoundTag.getBoolean("AskedToStay"));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(12.0D);
        }

        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(8.0D);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (this.isTame() && player.isCrouching() && !this.level.isClientSide) {
            this.setAskedToStay(!this.isAskedToStay());
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float v) {
        if (damageSource.getEntity() instanceof Player player) {
            if (this.getOwner() != null) {
                if (this.getOwner().is(player)) {
                    for (int i = 0; i < 4; i++) {
                        this.level.addParticle(ParticleTypes.HEART, this.getX() + random.nextDouble() - 0.5D, this.getY() + random.nextDouble(), this.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
                    }
                    return false;
                }
            }
        }
        return super.hurt(damageSource, v);
    }

    public static class CorgiEvents {
        @SubscribeEvent
        public static void disableCorgiDamage(AttackEntityEvent event) {
            if (event.getTarget() instanceof Corgi corgi) {
                if (corgi.getOwner() instanceof Player player) {
                    if (player.equals(event.getEntity())) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.NORMAL.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public Wolf getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        EntityType<Corgi> corgi = (EntityType<Corgi>) Corgis.getTameWolfBasedCorgis().get(this.random.nextInt(Corgis.getTameWolfBasedCorgis().size())).getCorgiType();
        Corgi corgi1 = corgi.create(level);
        UUID uuid1 = this.getOwnerUUID();
        if (corgi1 != null && uuid1 != null) {
            corgi1.setOwnerUUID(uuid1);
            corgi1.setTame(true);
        }
        return corgi1;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof Corgi corgi)) {
            return false;
        } else {
            if (!corgi.isTame()) {
                return false;
            } else if (corgi.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && corgi.isInLove();
            }
        }
    }

    public void setAskedToStay(boolean askedToStay) {
        this.entityData.set(ASKED_TO_STAY, askedToStay);
    }

    public boolean isAskedToStay() {
        return this.entityData.get(ASKED_TO_STAY);
    }

    class CorgiAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final Corgi corgi;

        public CorgiAvoidEntityGoal(Corgi corgi, Class<T> avoidClass, float maxDist, double walkModifier, double sprintModifier) {
            super(corgi, avoidClass, maxDist, walkModifier, sprintModifier);
            this.corgi = corgi;
        }

        public boolean canUse() {
            if (super.canUse() && this.toAvoid instanceof Llama) {
                return !this.corgi.isTame() && this.avoidLlama((Llama)this.toAvoid);
            } else {
                return false;
            }
        }

        private boolean avoidLlama(Llama llama) {
            return llama.getStrength() >= Corgi.this.random.nextInt(5);
        }

        public void start() {
            Corgi.this.setTarget(null);
            super.start();
        }

        public void tick() {
            Corgi.this.setTarget(null);
            super.tick();
        }
    }

    class CorgiPanicGoal extends PanicGoal {
        public CorgiPanicGoal(double p_203124_) {
            super(Corgi.this, p_203124_);
        }

        protected boolean shouldPanic() {
            return this.mob.isFreezing() || this.mob.isOnFire();
        }
    }
}
