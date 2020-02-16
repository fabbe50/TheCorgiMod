package com.fabbe50.corgis.entities;

import com.fabbe50.corgis.entities.ai.BegDecoyGoal;
import com.fabbe50.corgis.entities.data.CorgiType;
import com.fabbe50.corgis.entities.registry.CorgiRegistry;
import com.fabbe50.corgis.entities.registry.EntityRegistry;
import com.google.common.base.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

/**
 * Created by fabbe50 on 19/06/2016.
 */
public class CorgiEntity extends TameableEntity {
    private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLARCOLOR = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CORGITYPE = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);
    public static final Predicate<LivingEntity> TARGETS = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
    };

    private float headRotation;
    private float headRotationOld;

    private boolean isWet;

    private boolean isShaking;

    private float timeCorgiIsShaking;
    private float prevTimeCorgiIsShaking;

    public CorgiEntity(EntityType<? extends CorgiEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new BegDecoyGoal(this, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal(this, AnimalEntity.class, false, TARGETS));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));

    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

        if (this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        }

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);

        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        }
        else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }

    protected void updateAITasks() {
        this.dataManager.set(DATA_HEALTH_ID, this.getHealth());
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_HEALTH_ID, this.getHealth());
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(COLLARCOLOR, DyeColor.RED.getId());
        this.dataManager.register(CORGITYPE, CorgiType.byDamage(rand.nextInt(8)).getID());
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Angry", this.isAngry());
        compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
        compound.putByte("CorgiType", (byte)this.getCorgiType().getID());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAngry(compound.getBoolean("Angry"));

        if (compound.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(compound.getByte("CollarColor")));
        }
        if (compound.contains("CorgiType", 99)) {
            this.setCorgitype(CorgiType.byDamage(compound.getByte("CorgiType")));
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isAngry() ? SoundEvents.ENTITY_WOLF_GROWL : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataManager.get(DATA_HEALTH_ID) < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT) : SoundEvents.ENTITY_WOLF_AMBIENT);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return null;
    }

    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeCorgiIsShaking = 0.0F;
            this.prevTimeCorgiIsShaking = 0.0F;
            this.world.setEntityState(this, (byte)8);
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }
    }

    public void tick() {
        super.tick();
        this.headRotationOld = this.headRotation;

        if (this.isBegging()) {
            this.headRotation += (1.0F - this.headRotation) * 0.4F;
        }
        else {
            this.headRotation += (0.0F - this.headRotation) * 0.4F;
        }

        if (this.isWet()) {
            this.isWet = true;
            this.isShaking = false;
            this.timeCorgiIsShaking = 0.0F;
            this.prevTimeCorgiIsShaking = 0.0F;
        }
        else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeCorgiIsShaking == 0.0F) {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeCorgiIsShaking = this.timeCorgiIsShaking;
            this.timeCorgiIsShaking += 0.05F;

            if (this.prevTimeCorgiIsShaking >= 2.0F) {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeCorgiIsShaking = 0.0F;
                this.timeCorgiIsShaking = 0.0F;
            }

            if (this.timeCorgiIsShaking > 0.4F) {
                float f = (float)this.getBoundingBox().minY;
                int i = (int)(MathHelper.sin((this.timeCorgiIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
                Vec3d vec3d = this.getMotion();

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    this.world.addParticle(ParticleTypes.SPLASH, this.getPosition().getX() + (double)f1, (double)(f + 0.8F), this.getPosition().getZ() + (double)f2, vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeCorgiIsShaking = 0.0f;
        this.timeCorgiIsShaking = 0.0f;
        super.onDeath(cause);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCorgiWet() {
        return this.isWet;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float shade) {
        return 0.75F + (this.prevTimeCorgiIsShaking + (this.timeCorgiIsShaking - this.prevTimeCorgiIsShaking) * shade) / 2.0F * 0.25F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float f2, float f3) {
        float f = (this.prevTimeCorgiIsShaking + (this.timeCorgiIsShaking - this.prevTimeCorgiIsShaking) * f2 + f3) / 1.8F;

        if (f < 0.0F) {
            f = 0.0F;
        }
        else if (f > 1.0F) {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float)Math.PI) * MathHelper.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float angle) {
        return MathHelper.lerp(angle, this.headRotationOld, this.headRotation) * 0.15F * (float)Math.PI;
    }

    public float getStandingEyeHeight() {
        return this.getEyeHeight() * 0.9F;
    }

    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        else {
            Entity entity = source.getImmediateSource();

            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        }

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();

        if (this.isTamed()) {
            if (!stack.isEmpty()) {
                if (stack.isFood()) {
                    if (item == Items.BAKED_POTATO && (Float)this.dataManager.get(DATA_HEALTH_ID) < 60.0) {
                        if (!player.abilities.isCreativeMode) {
                            stack.setCount(stack.getCount() - 1);
                        }

                        this.heal((float)item.getFood().getHealing());
                        return true;
                    }
                }
                else if (item instanceof DyeItem) {
                    DyeColor enumdyecolor = ((DyeItem) item).getDyeColor();
                    if (enumdyecolor != this.getCollarColor()) {
                        this.setCollarColor(enumdyecolor);
                        if (!player.abilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        return true;
                    }
                }
            }
            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(stack)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((LivingEntity) null);
            }
        }
        else if (stack != null && stack.getItem() == Items.BAKED_POTATO && !this.isAngry()) {
            if (!player.abilities.isCreativeMode) {
                stack.setCount(stack.getCount() - 1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(60.0F);
                    this.setOwnerId(player.getUniqueID());
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public boolean isAngry() {
        return (this.dataManager.get(TAMED) & 2) != 0;
    }

    public void setAngry(boolean angry) {
        byte b0 = this.dataManager.get(TAMED);

        if (angry) {
            this.dataManager.set(TAMED, (byte) (b0 | 2));
        }
        else {
            this.dataManager.set(TAMED, (byte) (b0 & -3));
        }
    }

    public CorgiType getCorgiType() {
        return CorgiType.byDamage(this.dataManager.get(CORGITYPE));
    }

    public void setCorgitype(CorgiType corgitype) {
        this.dataManager.set(CORGITYPE, corgitype.getID());
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataManager.get(COLLARCOLOR) & 15);
    }

    public void setCollarColor(DyeColor collarcolor) {
        this.dataManager.set(COLLARCOLOR, collarcolor.getId());
    }

    public CorgiType getRandomCorgiType(Random rand) {
        int i = rand.nextInt(CorgiType.count());
        return CorgiType.byDamage(i);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT compound) {
        livingdata = super.onInitialSpawn(world, difficulty, reason, livingdata, compound);
        this.setCorgitype(getRandomCorgiType(this.world.rand));
        this.setCustomName(new StringTextComponent(upperCaseFirstLetter(this.getCorgiType().getName()) + " Corgi"));
        return livingdata;
    }

    public static String upperCaseFirstLetter(String str) {
        String s1 = str.substring(0, 1).toUpperCase();
        return s1 + str.substring(1);
    }

    @OnlyIn(Dist.CLIENT)
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.25F - (this.getMaxHealth() - this.dataManager.get(DATA_HEALTH_ID)) * 0.01F) * (float)Math.PI : ((float)Math.PI / 5F));
    }

    @SuppressWarnings("NullableProblems")
    public CorgiEntity createChild(AgeableEntity ageable) {
        CorgiEntity entitycorgi = CorgiRegistry.CORGI.create(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            entitycorgi.setOwnerId(uuid);
            entitycorgi.setTamed(true);
        }

        return entitycorgi;
    }

    public void setBegging(boolean beg) {
        this.dataManager.set(BEGGING, beg);
    }

    public boolean isBegging() {
        return this.dataManager.get(BEGGING);
    }

    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof CorgiEntity)) {
            return false;
        } else {
            CorgiEntity corgiEntity = (CorgiEntity)otherAnimal;
            return corgiEntity.isTamed() && (!corgiEntity.isSitting() && (this.isInLove() && corgiEntity.isInLove()));
        }
    }
}
