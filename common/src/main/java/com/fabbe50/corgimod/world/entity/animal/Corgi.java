package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.Platform;
import com.fabbe50.corgimod.config.BreedingModeOption;
import com.fabbe50.corgimod.registries.*;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePackEntity;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.ITamableCorgi;
import com.fabbe50.corgimod.world.entity.feature.corgis.BaseCorgiFeaturePack.Sounds;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePack;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePack.HurtType;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Corgi extends TamableAnimalExtension implements NeutralMob, VariantHolder<Holder<CorgiVariant>>, ITamableCorgi, IFeaturePackEntity<Corgi> {
    private static final EntityDataAccessor<Holder<CorgiVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Corgi.class, Platform.getCorgiVariantSerializer());
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_COLLAR_GLOW = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> PLAYER_SAVING_COOLDOWN = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> HAS_TREASURE = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<BlockPos> TREASURE_POS = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BLOCK_POS);
    public static final EntityDataAccessor<Integer> TREASURE_COOLDOWN = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);

    public float interestedAngle;
    public float interestedAngleO;
    private boolean wet;
    private boolean shaking;
    public float shakeAnim;
    public float shakeAnimO;
    public float lieDownAmount;
    public float lieDownAmountO;
    public float lieDownAmountTail;
    public float lieDownAmountOTail;
    public float relaxStateOneAmount;
    public float relaxStateOneAmountO;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;
    public final float bobs;

    private SimpleContainer inventory;

    public Corgi(EntityType<? extends Corgi> entityType, Level level) {
        super(entityType, level);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.bobs = this.getRandom().nextFloat() * (float) Math.PI * 2.0F;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return net.minecraft.world.entity.LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, ModConfig.<Float>getValue("wildCorgiMovementSpeed").getValue())
                .add(Attributes.MAX_HEALTH, ModConfig.<Float>getValue("wildCorgiMaxHealth").getValue())
                .add(Attributes.ATTACK_DAMAGE, ModConfig.<Float>getValue("wildCorgiAttackDamage").getValue());
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            float health = (float) this.getFeaturePack().getTamedMaxHealth();
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
            this.setHealth(health);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(this.getFeaturePack().getTamedAttackDamage());
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.getFeaturePack().getTamedMovementSpeed());
            if (!this.hasCustomName() && ModConfig.<Boolean>getValue("nameOnTame").getValue()) {
                NameRegistry.nameCorgi(this);
            }
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(this.getFeaturePack().getMaxHealth());
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(this.getFeaturePack().getAttackDamage());
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.getFeaturePack().getMovementSpeed());
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

    public Corgis getVariantType() {
        return Corgis.getCorgiFromVariant(getVariant().unwrapKey().orElse(CorgiVariants.NORMAL));
    }

    public IFeaturePack<Corgi> getFeaturePack() {
        return this.getVariantType().getCorgiFeaturePack();
    }

    @Override
    public void setCollarColor(DyeColor color) {
        this.entityData.set(DATA_COLLAR_COLOR, color.getId());
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    @Override
    public void setCollarGlow(boolean glow) {
        this.entityData.set(DATA_COLLAR_GLOW, glow);
    }

    @Override
    public boolean isCollarGlow() {
        return this.entityData.get(DATA_COLLAR_GLOW);
    }

    @Override
    public void setIsInterested(boolean interested) {
        this.entityData.set(DATA_INTERESTED_ID, interested);
    }

    @Override
    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID);
    }

    public void setLying(boolean bl) {
        this.entityData.set(IS_LYING, bl);
    }

    public boolean isLying() {
        return this.entityData.get(IS_LYING);
    }

    public void setRelaxStateOne(boolean bl) {
        this.entityData.set(RELAX_STATE_ONE, bl);
    }

    public boolean isRelaxStateOne() {
        return this.entityData.get(RELAX_STATE_ONE);
    }

    public void createInventory() {
        this.inventory = new SimpleContainer(27);
        this.setInventory(this.inventory);
    }

    public void setInventory(SimpleContainer inventory) {
        this.inventory = inventory;
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        RegistryAccess registryAccess = this.registryAccess();
        Registry<CorgiVariant> registry = registryAccess.registryOrThrow(CorgiVariants.CORGI_VARIANTS_REGISTRY.key());
        Optional<Holder.Reference<CorgiVariant>> registryHolder = registry.getHolder(CorgiVariants.NORMAL);
        Objects.requireNonNull(registry);
        builder.define(DATA_VARIANT_ID, registryHolder.or(registry::getAny).orElseThrow());
        builder.define(DATA_INTERESTED_ID, false);
        builder.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
        builder.define(DATA_COLLAR_GLOW, false);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(IS_LYING, false);
        builder.define(RELAX_STATE_ONE, false);
        builder.define(PLAYER_SAVING_COOLDOWN, 0);
        builder.define(HAS_TREASURE, false);
        builder.define(TREASURE_POS, BlockPos.ZERO);
        builder.define(TREASURE_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.getVariant().unwrapKey().ifPresent(resourceKey -> compoundTag.putString("variant", resourceKey.location().toString()));
        this.saveCollarData(compoundTag);
        this.addPersistentAngerSaveData(compoundTag);
        this.getFeaturePack().addAdditionalSaveData(this, compoundTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant")))
                .map(location -> ResourceKey.create(CorgiVariants.CORGI_VARIANTS_REGISTRY.key(), location))
                .flatMap(resourceKey -> this.registryAccess().registryOrThrow(CorgiVariants.CORGI_VARIANTS_REGISTRY.key()).getHolder(resourceKey))
                .ifPresent(this::setVariant);
        this.readCollarData(compoundTag);
        this.readPersistentAngerSaveData(this.level(), compoundTag);
        this.getFeaturePack().readAdditionalSaveData(this, compoundTag);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (!mobSpawnType.equals(MobSpawnType.BREEDING) || this.getVariant() == null) {
            Holder<CorgiVariant> corgiVariant = CorgiVariants.getRandomVariant(this.registryAccess());
            this.setVariant(corgiVariant);
        }
        this.setFemale(this.getRandom().nextBoolean());
        this.applyTamingSideEffects();
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    public GoalSelector getGoalSelector() {
        return this.goalSelector;
    }

    public GoalSelector getTargetSelector() {
        return this.targetSelector;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public static boolean checkCorgiSpawnRules(EntityType<Corgi> corgi, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.CORGIS_SPAWNABLE_ON) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        if (ModConfig.<BreedingModeOption.BreedingMode>getValue("breedingMode").getValue().equals(BreedingModeOption.BreedingMode.PARENTS)) {
            if (ageableMob instanceof Corgi parent2) {
                return Corgis.getOffspringFromParents(serverLevel, this, parent2);
            }
            Corgi child = (Corgi) this.getVariantType().getEntityType().create(serverLevel);
            if (child != null) {
                child.setVariant(this.getVariant());
                if (this.isTame()) {
                    child.setOwnerUUID(this.getOwnerUUID());
                    child.setTame(true, true);
                    child.setCollarColor(this.getCollarColor());
                }
            }
            return child;
        }
        return Corgis.getRandomOffspring(serverLevel, this);
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
        if (this.isNoAi()) {
            return;
        }
        super.aiStep();
        if (!this.level().isClientSide && this.wet && !this.shaking && !this.isPathFinding() && this.onGround()) {
            this.shaking = true;
            this.shakeAnim = 0.0f;
            this.shakeAnimO = 0.0f;
            this.level().broadcastEntityEvent(this, (byte)0);
        }
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
        this.getFeaturePack().aiStep(this);
    }

    @Override
    protected void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double speedMod = this.getMoveControl().getSpeedModifier();
            if (speedMod == 0.6) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (speedMod == 1.33) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            if (ModConfig.<Boolean>getValue("doCorgiParticles").getValue() && !this.isHungry()) {
                if (random.nextInt(8) == 0) {
                    this.getFeaturePack().onHandleParticles(this, this.getRandom());
                }
            }

            this.getFeaturePack().onTick(this);
        }
    }

    @Override
    public boolean wantsToAttack(net.minecraft.world.entity.LivingEntity target, net.minecraft.world.entity.LivingEntity owner) {
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
    protected @NotNull ResourceKey<LootTable> getDefaultLootTable() {
        return this.getVariantType().getDefaultLootTable();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult featureResult = getFeaturePack().onInteractWith(this, player, hand);
        if (featureResult.consumesAction()) {
            return featureResult;
        }
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (!this.level().isClientSide || this.isBaby() && this.isFood(stack)) {
            if (this.isTame()) {
                if (item == ModRegistries.CORGI_CARRY_TOOL.get()) {
                    return InteractionResult.PASS;
                } else if (this.isFood(stack)) {
                    boolean completedTask = false;
                    if (this.isHungry()) {
                        this.setHungry(false);
                        this.level().playSound(this, this.getOnPos(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1, 1);
                        this.level().addParticle(ParticleTypes.HEART, this.getX() + random.nextDouble() - 0.5D, this.getY() + random.nextDouble(), this.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
                        completedTask = true;
                    }
                    if (this.getHealth() < this.getMaxHealth()) {
                        FoodProperties foodProperties = stack.get(DataComponents.FOOD);
                        this.heal(2 * (foodProperties != null ? foodProperties.nutrition() : 1));
                        completedTask = true;
                    }
                    if (completedTask) {
                        this.usePlayerItem(player, hand, stack);
                        return InteractionResult.sidedSuccess(this.level().isClientSide);
                    }
                } else {
                    if (item instanceof DyeItem dyeItem) {
                        if (this.isOwnedBy(player)) {
                            DyeColor color = dyeItem.getDyeColor();
                            if (color != this.getCollarColor()) {
                                this.setCollarColor(color);
                                this.usePlayerItem(player, hand, stack);
                                return InteractionResult.SUCCESS;
                            }
                        }
                        return super.mobInteract(player, hand);
                    } else if (item instanceof GlowInkSacItem) {
                        if (this.isOwnedBy(player)) {
                            if (!this.isCollarGlow()) {
                                this.setCollarGlow(true);
                                this.usePlayerItem(player, hand, stack);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    } else {
                        InteractionResult interactionResult = super.mobInteract(player, hand);
                        if (!interactionResult.consumesAction() && this.isOwnedBy(player)) {
                            if (player.isCrouching()) {
                                this.setOriginStay(this.blockPosition());
                                this.setAskedToStay(!this.isAskedToStay());
                            } else {
                                this.setOrderedToSit(!this.isOrderedToSit());
                                this.jumping = false;
                                this.navigation.stop();
                                this.setTarget(null);
                            }
                            return InteractionResult.SUCCESS_NO_ITEM_USED;
                        } else {
                            return interactionResult;
                        }
                    }
                }
            } else if (isTamingItem(stack) && !this.isAngry()) {
                this.usePlayerItem(player, hand, stack);
                this.tryToTame(player);
                return InteractionResult.SUCCESS;
            } else {
                return super.mobInteract(player, hand);
            }
        }
        boolean result = this.isOwnedBy(player) || this.isTame() || isTamingItem(stack) || !this.isTame() && !this.isAngry();
        return result ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand interactionHand, ItemStack stack) {
        if (this.isFood(stack)) {
            this.playSound(Sounds.EAT.getSound(), 1.0F, 1.0F);
        }
        super.usePlayerItem(player, interactionHand, stack);
    }

    @Override
    public void setTame(boolean bl, boolean bl2) {
        super.setTame(bl, bl2);
        this.getFeaturePack().onTame(this, this.goalSelector);
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
            this.shaking = true;
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
        return this.getFeaturePack().isFood(stack);
    }

    public boolean isTamingItem(ItemStack stack) {
        return this.getFeaturePack().isTamingItem(stack);
    }

    @Override
    public boolean playerHoldingInteresting(Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return this.getFeaturePack().isItemOfInterest(this, main) || this.getFeaturePack().isItemOfInterest(this, off);
    }

    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    protected @NotNull Vec3 getLeashOffset() {
        return new Vec3(0, (0.6f * this.getEyeHeight()), this.getBbWidth() * 0.4f);
    }

    public void cancelShake() {
        this.shaking = false;
        this.shakeAnim = 0.0F;
        this.shakeAnimO = 0.0F;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity entity) {
        if (entity instanceof net.minecraft.world.entity.LivingEntity livingEntity) {
            if (this.getFeaturePack().doHurtTarget(this, livingEntity)) {
                return super.doHurtTarget(entity);
            }
            return false;
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        HurtType hurtType = this.getFeaturePack().onHurt(this, damageSource, f);
        if (hurtType != HurtType.PASS) {
            return hurtType == HurtType.HURT;
        }

        if (damageSource.getEntity() instanceof Player) {
            return super.hurt(damageSource, f);
        }

        LivingEntity owner = this.getOwner();
        if (this.isTame() && owner != null && owner.is(damageSource.getEntity())) {
            return false;
        }
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
        this.getFeaturePack().onDeath(this, damageSource);
        this.wet = false;
        cancelShake();
        super.die(damageSource);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean ignoreDropChance) {
        this.getFeaturePack().dropCustomDeathLoot(this, serverLevel, damageSource, ignoreDropChance);
        super.dropCustomDeathLoot(serverLevel, damageSource, ignoreDropChance);
    }

    @Override
    protected @NotNull Vec3i getPickupReach() {
        return new Vec3i(3, 1, 3);
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        this.getFeaturePack().onItemPickup(this, itemEntity);
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }

    public boolean isWet() {
        return wet;
    }

    public void setShaking(boolean shaking) {
        this.shaking = shaking;
    }

    public boolean isShaking() {
        return shaking;
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

    @Override
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

    public float getLieDownAmount(float f) {
        return Mth.lerp(f, this.lieDownAmountO, this.lieDownAmount);
    }

    public float getLieDownAmountTail(float f) {
        return Mth.lerp(f, this.lieDownAmountOTail, this.lieDownAmountTail);
    }

    public float getRelaxStateOneAmount(float f) {
        return Mth.lerp(f, this.relaxStateOneAmountO, this.relaxStateOneAmount);
    }

    public boolean canUseSlot(EquipmentSlot equipmentSlot) {
        return true;
    }

    @Override
    public boolean isAngry() {
        return NeutralMob.super.isAngry() || getRemainingPersistentAngerTime() == -1;
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
        this.playSound(Sounds.STEP.getSound(), 0.15f, 1.0f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return getFeaturePack().onAmbientSound(this);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Sounds.HURT.getSound();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.DEATH.getSound();
    }

    @Override
    public float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected @NotNull Component getTypeName() {
        if (this.getVariant() != null) {
            return Component.translatable(this.getVariant().unwrapKey().get().location().toLanguageKey());
        }
        return super.getTypeName();
    }
}
