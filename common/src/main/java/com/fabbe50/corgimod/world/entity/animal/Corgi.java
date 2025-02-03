package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.registries.*;
import com.fabbe50.corgimod.world.entity.ai.CustomBegGoal;
import com.fabbe50.corgimod.world.entity.ai.CustomFollowOwnerGoal;
import com.fabbe50.corgimod.world.entity.ai.CustomWaterAvoidingRandomStrollGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class Corgi extends TamableAnimal implements NeutralMob, VariantHolder<Holder<CorgiVariant>>, ICorgi {
    private static final EntityDataAccessor<Holder<CorgiVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Corgi.class, ModEntitySerializers.CORGI_VARIANT);
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);;
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);;
    private static final EntityDataAccessor<Boolean> ASKED_TO_STAY = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> ORIGIN_STAY = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> DATA_HAS_BEEN_FED = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Long> DATA_TIME_WHEN_FED = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.LONG);

    public final Predicate<LivingEntity> PREY_SELECTOR;
    private float interestedAngle;
    private float interestedAngleO;
    private boolean isWet;
    private boolean isShaking;
    private float shakeAnim;
    private float shakeAnimO;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;

    public Corgi(EntityType<? extends Corgi> entityType, Level level) {
        super(entityType, level);
        PREY_SELECTOR = livingEntity -> {
            EntityType<?> preyEntityType = livingEntity.getType();
            return this.getPreyTargets().contains(preyEntityType);
        };
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5, 1.5));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new CustomFollowOwnerGoal<>(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new CustomWaterAvoidingRandomStrollGoal<>(this, 1.0, ModConfig.INSTANCE.maxWanderingDistance));
        this.goalSelector.addGoal(9, new CustomBegGoal<>(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.30000001192092896).add(Attributes.MAX_HEALTH, 12.0).add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            float health = ModConfig.INSTANCE.tamedCorgiMaxHealth;
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            this.setHealth(health);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(ModConfig.INSTANCE.tamedCorgiAttackDamage);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(ModConfig.INSTANCE.wildCorgiMaxHealth);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(ModConfig.INSTANCE.wildCorgiAttackDamage);
        }
    }

    public ResourceLocation getTexture() {
        CorgiVariant corgiVariant = this.getVariant().value();
        if (this.isTame()) {
            return corgiVariant.tameTexture();
        } else {
            return this.isAngry() ? corgiVariant.angryTexture() : corgiVariant.wildTexture();
        }
    }

    @Override
    public void setVariant(Holder<CorgiVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    public @NotNull Holder<CorgiVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setCollarColor(DyeColor color) {
        this.entityData.set(DATA_COLLAR_COLOR, color.getId());
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    @Override
    public void setIsInterested(boolean interested) {
        this.entityData.set(DATA_INTERESTED_ID, interested);
    }

    @Override
    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID);
    }

    @Override
    public void setAskedToStay(boolean stay) {
        this.entityData.set(ASKED_TO_STAY, stay);
    }

    @Override
    public boolean isAskedToStay() {
        return this.entityData.get(ASKED_TO_STAY);
    }

    @Override
    public void setOriginStay(BlockPos origin) {
        this.entityData.set(ORIGIN_STAY, origin);
    }

    @Override
    public BlockPos getOriginStay() {
        return this.entityData.get(ORIGIN_STAY);
    }

    @Override
    public void setHasBeenFed(boolean hasBeenFed) {
        this.entityData.set(DATA_HAS_BEEN_FED, hasBeenFed);
    }

    @Override
    public boolean hasBeenFed() {
        return this.entityData.get(DATA_HAS_BEEN_FED);
    }

    @Override
    public void setTimeWhenFed(long timeWhenFed) {
        this.entityData.set(DATA_TIME_WHEN_FED, timeWhenFed);
    }

    @Override
    public long getTimeWhenFed() {
        return this.entityData.get(DATA_TIME_WHEN_FED);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        RegistryAccess registryAccess = this.registryAccess();
        Registry<CorgiVariant> registry = registryAccess.registryOrThrow(ModRegistries.CORGI_VARIANT);
        builder.define(DATA_VARIANT_ID, registry.getHolder(CorgiVariants.NORMAL).or(registry::getAny).orElseThrow());
        builder.define(DATA_INTERESTED_ID, false);
        builder.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(ASKED_TO_STAY, false);
        builder.define(DATA_HAS_BEEN_FED, false);
        builder.define(DATA_TIME_WHEN_FED, 0L);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.getVariant().unwrapKey().ifPresent(resourceKey -> compoundTag.putString("variant", resourceKey.location().toString()));
        compoundTag.putInt("collarColor", this.getCollarColor().getId());
        compoundTag.putBoolean("askedToStay", this.isAskedToStay());
        compoundTag.putIntArray("stayOrigin", Utilities.getIntArrayFromBlockPos(this.getOriginStay()));
        compoundTag.putBoolean("hasBeenFed", this.hasBeenFed());
        compoundTag.putLong("timeWhenFed", this.getTimeWhenFed());
        this.addPersistentAngerSaveData(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant")))
                .map(location -> ResourceKey.create(ModRegistries.CORGI_VARIANT, location))
                .flatMap(resourceKey -> this.registryAccess().registryOrThrow(ModRegistries.CORGI_VARIANT).getHolder(resourceKey))
                .ifPresent(this::setVariant);
        if (compoundTag.contains("collarColor", 99)) {
            this.setCollarColor(DyeColor.byId(compoundTag.getInt("collarColor")));
        }
        if (compoundTag.contains("askedToStay", 99)) {
            this.setAskedToStay(compoundTag.getBoolean("askedToStay"));
        }
        if (compoundTag.contains("stayOrigin", 99)) {
            BlockPos pos = Utilities.getBlockPosFromIntArray(compoundTag.getIntArray("stayOrigin"));
            if (pos != null) {
                this.setOriginStay(pos);
            }
        }
        if (compoundTag.contains("hasBeenFed", 99)) {
            this.setHasBeenFed(compoundTag.getBoolean("hasBeenFed"));
        }
        if (compoundTag.contains("timeWhenFed", 99)) {
            this.setTimeWhenFed(compoundTag.getLong("timeWhenFed"));
        }
        this.readPersistentAngerSaveData(this.level(), compoundTag);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        Holder<CorgiVariant> corgiVariant = CorgiVariants.getRandomVariant(this.registryAccess());
        this.setVariant(corgiVariant);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public static boolean checkCorgiSpawnRules(EntityType<Corgi> corgi, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random) {
        return levelAccessor.getBlockState(blockPos.below()).is(BlockTags.CORGIS_SPAWNABLE_ON) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Corgi child = EntityRegistry.CORGI.get().create(serverLevel);
        if (ModConfig.INSTANCE.breedingMode.equals(ModConfig.BreedingMode.PARENTS)) {
            if (child != null && ageableMob instanceof Corgi parent2) {
                if (this.random.nextBoolean()) {
                    child.setVariant(this.getVariant());
                } else {
                    child.setVariant(parent2.getVariant());
                }

                if (this.isTame()) {
                    child.setOwnerUUID(this.getOwnerUUID());
                    child.setTame(true, true);
                    if (this.random.nextBoolean()) {
                        child.setCollarColor(this.getCollarColor());
                    } else {
                        child.setCollarColor(parent2.getCollarColor());
                    }
                }
            } else {
                child.setVariant(this.getVariant());
                if (this.isTame()) {
                    child.setOwnerUUID(this.getOwnerUUID());
                    child.setTame(true, true);
                    child.setCollarColor(this.getCollarColor());
                }
            }
        } else {
            child.setVariant(CorgiVariants.getRandomVariant(this.registryAccess()));
            if (this.isTame()) {
                child.setOwnerUUID(this.getOwnerUUID());
                child.setTame(true, true);
            }
        }
        return child;
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (this.isInSittingPose()) {
            return false;
        } else if (animal instanceof Corgi corgi) {
            if (!corgi.isTame()) {
                return false;
            } else if (corgi.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && animal.isInLove();
            }
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isWet && !this.isShaking && !this.isPathFinding() && this.onGround()) {
            this.isShaking = true;
            this.shakeAnim = 0.0f;
            this.shakeAnimO = 0.0f;
            this.level().broadcastEntityEvent(this, (byte)0);
        }
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.interestedAngleO = this.interestedAngle;
            if (this.isInterested()) {
                this.interestedAngle += (1 - this.interestedAngle) * 0.4f;
            } else {
                this.interestedAngle += (0 - this.interestedAngle) * 0.4f;
            }

            if (this.isInWaterRainOrBubble()) {
                this.isWet = true;
                if (this.isShaking && !this.level().isClientSide) {
                    this.level().broadcastEntityEvent(this, (byte) 56);
                    this.cancelShake();
                }
            } else if ((this.isWet || this.isShaking) && this.isShaking) {
                if (this.shakeAnim == 0.0F) {
                    this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1);
                    this.gameEvent(GameEvent.ENTITY_ACTION);
                }

                this.shakeAnimO = this.shakeAnim;
                this.shakeAnim += 0.05F;
                if (this.shakeAnimO >= 2) {
                    this.isWet = false;
                    this.isShaking = false;
                    this.shakeAnimO = 0;
                    this.shakeAnim = 0;
                }

                if (this.shakeAnim > 0.4F) {
                    float f = (float)this.getY();
                    int i = (int)(Mth.sin((this.shakeAnim - 0.4F) * 3.1415927F) * 7);
                    Vec3 vec3 = this.getDeltaMovement();

                    for(int j = 0; j < i; ++j) {
                        float g = (this.random.nextFloat() * 2 - 1) * this.getBbWidth() * 0.5F;
                        float h = (this.random.nextFloat() * 2 - 1) * this.getBbWidth() * 0.5F;
                        this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double)g, f + 0.8F, this.getZ() + (double)h, vec3.x, vec3.y, vec3.z);
                    }
                }
            }
        }
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof Creeper) && !(target instanceof Ghast) && !(target instanceof ArmorStand)) {
            if (target instanceof Corgi corgi) {
                return !corgi.isTame() || corgi.getOwner() != owner;
            } else {
                if (target instanceof Player player) {
                    if (owner instanceof Player ownerPlayer) {
                        if (!ownerPlayer.canHarmPlayer(player)) {
                            return false;
                        }
                    }
                }
                if (target instanceof AbstractHorse horse) {
                    if (horse.isTamed()) {
                        return false;
                    }
                }
                if (target instanceof TamableAnimal tamable) {
                    if (tamable.isTame()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (this.level().isClientSide && (!this.isBaby() || !this.isFood(stack))) {
            boolean result = this.isOwnedBy(player) || this.isTame() || isTamingItem(stack) || !this.isTame() && !this.isAngry();
            return result ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
                stack.consume(1, player);
                FoodProperties foodProperties = stack.get(DataComponents.FOOD);
                float f = foodProperties != null ? foodProperties.nutrition() : 1;
                this.heal(2 * f);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                if (item instanceof DyeItem dyeItem) {
                    if (this.isOwnedBy(player)) {
                        DyeColor color = dyeItem.getDyeColor();
                        if (color != this.getCollarColor()) {
                            this.setCollarColor(color);
                            stack.consume(1, player);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    return super.mobInteract(player, hand);
                }
            }
        } else if (isTamingItem(stack) && !this.isAngry()) {
            stack.consume(1, player);
            this.tryToTame(player);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 8) {
            this.isShaking = true;
            this.shakeAnim = 0.0F;
            this.shakeAnimO = 0.0F;
        } else if (b == 56) {
            this.cancelShake();
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.CORGI_FOOD);
    }

    @Override
    public boolean isTamingItem(ItemStack stack) {
        return stack.is(ItemTags.CORGI_TAMING_ITEMS);
    }

    @Override
    public boolean playerHoldingInteresting(Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return this.isTamingItem(main) || this.isTamingItem(off) || this.isFood(main) || this.isFood(off);
    }

    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    protected @NotNull Vec3 getLeashOffset() {
        return new Vec3(0, (0.6f * this.getEyeHeight()), this.getBbWidth() * 0.4f);
    }

    private void cancelShake() {
        this.isShaking = false;
        this.shakeAnim = 0.0F;
        this.shakeAnimO = 0.0F;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }
            return super.hurt(damageSource, f);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        this.isWet = false;
        cancelShake();
        super.die(damageSource);
    }

    public boolean isWet() {
        return isWet;
    }

    public float getWetShade(float f) {
        return Math.min(0.75f + Mth.lerp(f, this.shakeAnimO, this.shakeAnim) / 2 * 0.25f, 1);
    }

    public float getBodyRollAngle(float f, float g) {
        float h = (Mth.lerp(f, this.shakeAnimO, this.shakeAnim) + g) / 1.8F;
        if (h < 0.0F) {
            h = 0.0F;
        } else if (h > 1.0F) {
            h = 1.0F;
        }
        return Mth.sin(h * 3.1415927F) * Mth.sin(h * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
    }

    public float getHeadRollAngle(float f) {
        return Mth.lerp(f, this.interestedAngleO, this.interestedAngle) * 0.15F * 3.1415927F;
    }

    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    public float getTailAngle() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else if (this.isTame()) {
            float f = this.getMaxHealth();
            float g = (f - this.getHealth()) / f;
            return (0.55F - g * 0.4F) * 3.1415927F;
        } else {
            return 0.62831855F;
        }
    }

    public boolean canUseSlot(EquipmentSlot equipmentSlot) {
        return true;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, i);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15f, 1.0f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        } else if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < ModConfig.INSTANCE.tamedCorgiMaxHealth / 2 ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WOLF_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
}
