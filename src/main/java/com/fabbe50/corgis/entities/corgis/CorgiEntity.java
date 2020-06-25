package com.fabbe50.corgis.entities.corgis;

import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.entities.ai.CatLieOnBedDecoyGoal;
import com.fabbe50.corgis.entities.ai.CatSitOnBlockDecoyGoal;
import com.fabbe50.corgis.entities.ai.traits.LoveTrait;
import com.fabbe50.corgis.entities.ai.traits.RadioactiveTrait;
import com.fabbe50.corgis.entities.interfaces.ICorgi;
import com.fabbe50.corgis.entities.ai.BegDecoyGoal;
import com.fabbe50.corgis.entities.ai.traits.MelonTrait;
import com.fabbe50.corgis.entities.data.CorgiType;
import com.fabbe50.corgis.entities.registry.EntityRegistry;
import com.google.common.base.Predicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

/**
 * Created by fabbe50 on 19/06/2016.
 */
public class CorgiEntity extends TameableEntity implements ICorgi {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLARCOLOR = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CORGITYPE = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);
    public static final Predicate<LivingEntity> TARGETS = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
    };
    //Cat Params
    private static final DataParameter<Boolean> field_213428_bG = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> field_213429_bH = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.BOOLEAN);
    private net.minecraft.entity.ai.goal.TemptGoal temptGoal;
    private CorgiEntity.AvoidPlayerGoal<PlayerEntity> avoidPlayerGoal;
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON, Items.TROPICAL_FISH);

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

    public CorgiEntity(World world) {
        this(EntityRegistry.CORGI, world);
    }

    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.temptGoal = new CorgiEntity.TemptGoal(this, 0.6D, BREEDING_ITEMS, true);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new CorgiEntity.MorningGiftGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, this.temptGoal);
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new CatLieOnBedDecoyGoal(this, 1.1D, 8));
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(9, new CatSitOnBlockDecoyGoal(this, 0.8D));
        this.goalSelector.addGoal(15, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(16, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(17, new BegDecoyGoal(this, 8.0F));
        this.goalSelector.addGoal(18, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(18, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal(this, AnimalEntity.class, false, TARGETS));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
        this.registerTraits();
    }

    protected void registerTraits() {
        this.goalSelector.addGoal(7, new MelonTrait(this, 0.8f));
    }

    @Override
    protected void setupTamedAI() {
        if (this.avoidPlayerGoal == null) {
            this.avoidPlayerGoal = new CorgiEntity.AvoidPlayerGoal<>(this, PlayerEntity.class, 16F, 0.8D, 1.33D);
        }

        this.goalSelector.removeGoal(this.avoidPlayerGoal);
        if (!this.isTamed()) {
            this.goalSelector.addGoal(4, this.avoidPlayerGoal);
        }
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

    protected void registerData() {
        super.registerData();
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(COLLARCOLOR, DyeColor.RED.getId());
        this.dataManager.register(CORGITYPE, CorgiType.byDamage(rand.nextInt(8)).getID());

        this.dataManager.register(field_213428_bG, false);
        this.dataManager.register(field_213429_bH, false);
    }

    public void func_213419_u(boolean p_213419_1_) {
        this.dataManager.set(field_213428_bG, p_213419_1_);
    }

    public boolean func_213416_eg() {
        return this.dataManager.get(field_213428_bG);
    }

    public void func_213415_v(boolean p_213415_1_) {
        this.dataManager.set(field_213429_bH, p_213415_1_);
    }

    public boolean func_213409_eh() {
        return this.dataManager.get(field_213429_bH);
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

    public static boolean canSpawnHere(EntityType<CorgiEntity> corgi, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK && world.getLightSubtracted(pos, 0) > 8;
    }

    protected SoundEvent getAmbientSound() {
        if (this.getCorgiType().equals(CorgiType.ANTI)) {
            if (this.isAngry())
                return SoundEvents.ENTITY_CAT_HISS;
            else if (this.isTamed()) {
                if (this.isInLove())
                    return SoundEvents.ENTITY_CAT_PURR;
                else
                    return this.rand.nextInt(4) == 0 ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT;
            } else {
                return SoundEvents.ENTITY_CAT_STRAY_AMBIENT;
            }
        } else {
            if (this.isAngry()) {
                return SoundEvents.ENTITY_WOLF_GROWL;
            } else if (this.rand.nextInt(3) == 0) {
                return this.isTamed() && this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
            } else {
                return SoundEvents.ENTITY_WOLF_AMBIENT;
            }
        }
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return this.getCorgiType() == CorgiType.ANTI ? SoundEvents.ENTITY_CAT_HURT : SoundEvents.ENTITY_WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.getCorgiType() == CorgiType.ANTI ? SoundEvents.ENTITY_CAT_DEATH : SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void consumeItemFromStack(PlayerEntity player, ItemStack stack) {
        if (this.getCorgiType().equals(CorgiType.ANTI) && (stack.getItem() == Items.COD || stack.getItem() == Items.TROPICAL_FISH || stack.getItem() == Items.SALMON))
            this.playSound(SoundEvents.ENTITY_CAT_EAT, 1.0f, 1.0f);

        super.consumeItemFromStack(player, stack);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return this.getCorgiType() != CorgiType.ANTI;
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

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return this.getCorgiType().equals(CorgiType.ANTI) ? (stack.getItem() == Items.COD || stack.getItem() == Items.TROPICAL_FISH || stack.getItem() == Items.SALMON) : stack.getItem() == Items.BAKED_POTATO;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isWet() {
        return this.isWet;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float shade) {
        return 0.75F + MathHelper.lerp(shade, this.prevTimeCorgiIsShaking, this.timeCorgiIsShaking) / 2.0f * 0.25f;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float f2, float f3) {
        float f = (MathHelper.lerp(f2, this.prevTimeCorgiIsShaking, this.timeCorgiIsShaking) + f3) / 1.8F;

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
                    if (this.getCorgiType().equals(CorgiType.ANTI) && (item == Items.COD || item == Items.TROPICAL_FISH || item == Items.SALMON) && this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        this.heal((float)item.getFood().getHealing());
                        return true;
                    } else if (!this.getCorgiType().equals(CorgiType.ANTI) && item == Items.BAKED_POTATO && this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        this.heal((float)item.getFood().getHealing());
                        return true;
                    }
                } else if (item instanceof DyeItem) {
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
        } else if (stack != null && (!this.getCorgiType().equals(CorgiType.ANTI) && stack.getItem() == Items.BAKED_POTATO) || (this.getCorgiType().equals(CorgiType.ANTI) && (item == Items.COD || item == Items.TROPICAL_FISH || item == Items.SALMON)) && !this.isAngry()) {
            if (!player.abilities.isCreativeMode) {
                stack.setCount(stack.getCount() - 1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.sitGoal.setSitting(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else {
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

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.25F - (this.getMaxHealth() - this.getHealth() * 0.01F) * (float)Math.PI) : ((float)Math.PI / 5F));
    }

    @SuppressWarnings("NullableProblems")
    public CorgiEntity createChild(AgeableEntity ageable) {
        CorgiEntity entitycorgi = EntityRegistry.CORGI.create(this.world);
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

    static class AvoidPlayerGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final CorgiEntity corgi;

        public AvoidPlayerGoal(CorgiEntity p_i50440_1_, Class<T> p_i50440_2_, float p_i50440_3_, double p_i50440_4_, double p_i50440_6_) {
            super(p_i50440_1_, p_i50440_2_, p_i50440_3_, p_i50440_4_, p_i50440_6_, EntityPredicates.CAN_AI_TARGET::test);
            this.corgi = p_i50440_1_;
        }

        public boolean shouldExecute() {
            return !this.corgi.isTamed() && this.corgi.getCorgiType().equals(CorgiType.ANTI) && super.shouldExecute();
        }

        public boolean shouldContinueExecuting() {
            return !this.corgi.isTamed() && super.shouldContinueExecuting();
        }
    }

    static class MorningGiftGoal extends Goal {
        private final CorgiEntity corgi;
        private PlayerEntity owner;
        private BlockPos bedPos;
        private int tickCounter;

        public MorningGiftGoal(CorgiEntity corgi) {
            this.corgi = corgi;
        }

        public boolean shouldExecute() {
            if (!this.corgi.isTamed()) {
                return false;
            } else if (this.corgi.isSitting()) {
                return false;
            } else if (!this.corgi.getCorgiType().equals(CorgiType.ANTI)) {
                return false;
            } else {
                LivingEntity livingentity = this.corgi.getOwner();
                if (livingentity instanceof PlayerEntity) {
                    this.owner = (PlayerEntity)livingentity;
                    if (!livingentity.isSleeping()) {
                        return false;
                    }

                    if (this.corgi.getDistanceSq(this.owner) > 100.0D) {
                        return false;
                    }

                    BlockPos blockpos = new BlockPos(this.owner);
                    BlockState blockstate = this.corgi.world.getBlockState(blockpos);
                    if (blockstate.getBlock().isIn(BlockTags.BEDS)) {
                        Direction direction = blockstate.get(BedBlock.HORIZONTAL_FACING);
                        this.bedPos = new BlockPos(blockpos.getX() - direction.getXOffset(), blockpos.getY(), blockpos.getZ() - direction.getZOffset());
                        return !this.func_220805_g();
                    }
                }

                return false;
            }
        }

        private boolean func_220805_g() {
            for(CorgiEntity corgi : this.corgi.world.getEntitiesWithinAABB(CorgiEntity.class, (new AxisAlignedBB(this.bedPos)).grow(2.0D))) {
                if (corgi != this.corgi && (corgi.func_213416_eg() || corgi.func_213409_eh())) {
                    return true;
                }
            }

            return false;
        }


        public boolean shouldContinueExecuting() {
            return this.corgi.isTamed() && !this.corgi.isSitting() && this.owner != null && this.owner.isSleeping() && this.bedPos != null && !this.func_220805_g();
        }

        public void startExecuting() {
            if (this.bedPos != null) {
                this.corgi.getAISit().setSitting(false);
                this.corgi.getNavigator().tryMoveToXYZ((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ(), (double)1.1F);
            }

        }

        public void resetTask() {
            this.corgi.func_213419_u(false);
            float f = this.corgi.world.getCelestialAngle(1.0F);
            if (this.owner.getSleepTimer() >= 100 && (double)f > 0.77D && (double)f < 0.8D && (double)this.corgi.world.getRandom().nextFloat() < 0.7D) {
                this.func_220804_h();
            }

            this.tickCounter = 0;
            this.corgi.func_213415_v(false);
            this.corgi.getNavigator().clearPath();
        }

        private void func_220804_h() {
            Random random = this.corgi.getRNG();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            blockpos$mutable.setPos(this.corgi);
            this.corgi.attemptTeleport((double)(blockpos$mutable.getX() + random.nextInt(11) - 5), (double)(blockpos$mutable.getY() + random.nextInt(5) - 2), (double)(blockpos$mutable.getZ() + random.nextInt(11) - 5), false);
            blockpos$mutable.setPos(this.corgi);
            LootTable loottable = this.corgi.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_CAT_MORNING_GIFT);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.corgi.world)).withParameter(LootParameters.POSITION, blockpos$mutable).withParameter(LootParameters.THIS_ENTITY, this.corgi).withRandom(random);

            for(ItemStack itemstack : loottable.generate(lootcontext$builder.build(LootParameterSets.GIFT))) {
                this.corgi.world.addEntity(new ItemEntity(this.corgi.world, (double)((float)blockpos$mutable.getX() - MathHelper.sin(this.corgi.renderYawOffset * ((float)Math.PI / 180F))), (double)blockpos$mutable.getY(), (double)((float)blockpos$mutable.getZ() + MathHelper.cos(this.corgi.renderYawOffset * ((float)Math.PI / 180F))), itemstack));
            }

        }

        public void tick() {
            if (this.owner != null && this.bedPos != null) {
                this.corgi.getAISit().setSitting(false);
                this.corgi.getNavigator().tryMoveToXYZ((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ(), (double)1.1F);
                if (this.corgi.getDistanceSq(this.owner) < 2.5D) {
                    ++this.tickCounter;
                    if (this.tickCounter > 16) {
                        this.corgi.func_213419_u(true);
                        this.corgi.func_213415_v(false);
                    } else {
                        this.corgi.faceEntity(this.owner, 45.0F, 45.0F);
                        this.corgi.func_213415_v(true);
                    }
                } else {
                    this.corgi.func_213419_u(false);
                }
            }
        }
    }

    static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
        @Nullable
        private PlayerEntity temptingPlayer;
        private final CorgiEntity corgi;

        public TemptGoal(CorgiEntity corgi, double speedIn, Ingredient temptItemsIn, boolean p_i50438_5_) {
            super(corgi, speedIn, temptItemsIn, p_i50438_5_);
            this.corgi = corgi;
        }

        public void tick() {
            super.tick();
            if (this.temptingPlayer == null && this.creature.getRNG().nextInt(600) == 0) {
                this.temptingPlayer = this.closestPlayer;
            } else if (this.creature.getRNG().nextInt(500) == 0) {
                this.temptingPlayer = null;
            }

        }

        protected boolean isScaredByPlayerMovement() {
            return (this.temptingPlayer == null || !this.temptingPlayer.equals(this.closestPlayer)) && super.isScaredByPlayerMovement();
        }

        public boolean shouldExecute() {
            return super.shouldExecute() && !this.corgi.isTamed() && this.corgi.getCorgiType().equals(CorgiType.ANTI);
        }
    }
}
