package com.fabbe50.corgis.entities;

import com.fabbe50.corgis.entities.ai.EntityAIBegDecoy;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

/**
 * Created by fabbe50 on 19/06/2016.
 */
public class CorgiEntity extends EntityTameable {
    private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLARCOLOR = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CORGITYPE = EntityDataManager.createKey(CorgiEntity.class, DataSerializers.VARINT);

    private float headRotation;
    private float headRotationOld;

    private boolean isWet;

    private boolean isShaking;

    private float timeCorgiIsShaking;
    private float prevTimeCorgiIsShaking;

    public CorgiEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6875f, 0.5625f);
        this.setTamed(false);
    }

    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIBegDecoy(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, (new EntityAIHurtByTarget(this, true)));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed<>(this, EntityAnimal.class, false, entity -> (entity instanceof EntitySheep || entity instanceof EntityRabbit)));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        }

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
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

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DATA_HEALTH_ID, this.getHealth());
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(COLLARCOLOR, EnumDyeColor.RED.getDyeDamage());
        this.dataManager.register(CORGITYPE, CorgiType.byDamage(rand.nextInt(8)).getID());
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Angry", this.isAngry());
        compound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
        compound.setByte("CorgiType", (byte)this.getCorgiType().getID());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAngry(compound.getBoolean("Angry"));

        if (compound.hasKey("CollarColor", 99)) {
            this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getByte("CollarColor")));
        }
        if (compound.hasKey("CorgiType", 99)) {
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

    public void onLivingUpdate() {
        super.onLivingUpdate();

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

    public void onUpdate() {
        super.onUpdate();
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
                float f = (float)this.getEntityBoundingBox().minY;
                int i = (int)(MathHelper.sin((this.timeCorgiIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.getPosition().getX() + (double)f1, (double)(f + 0.8F), this.getPosition().getZ() + (double)f2, this.motionX, this.motionY, this.motionZ, 0);
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

    @SideOnly(Side.CLIENT)
    public boolean isCorgiWet() {
        return this.isWet;
    }

    @SideOnly(Side.CLIENT)
    public float getShadingWhileWet(float shade) {
        return 0.75F + (this.prevTimeCorgiIsShaking + (this.timeCorgiIsShaking - this.prevTimeCorgiIsShaking) * shade) / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
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

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float angle) {
        return (this.headRotationOld + (this.headRotation - this.headRotationOld) * angle) * 0.15F * (float)Math.PI;
    }

    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        else {
            Entity entity = source.getImmediateSource();

            if (this.aiSit != null) {
                this.aiSit.setSitting(false);
            }

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        }

        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();

        if (this.isTamed()) {
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemFood) {
                    ItemFood itemFood = (ItemFood) stack.getItem();
                    if (item == Items.BAKED_POTATO && (Float)this.dataManager.get(DATA_HEALTH_ID) < 60.0) {
                        if (!player.capabilities.isCreativeMode) {
                            stack.setCount(stack.getCount() - 1);
                        }

                        this.heal((float)itemFood.getHealAmount(stack));
                        return true;
                    }
                }
                else if (item == Items.DYE) {
                    EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
                    if (enumdyecolor != this.getCollarColor()) {
                        this.setCollarColor(enumdyecolor);
                        if (!player.capabilities.isCreativeMode) {
                            stack.setCount(stack.getCount() - 1);
                        }

                        return true;
                    }
                }
            }
            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(stack)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        }
        else if (stack != null && stack.getItem() == Items.BAKED_POTATO && !this.isAngry()) {
            if (!player.capabilities.isCreativeMode) {
                stack.setCount(stack.getCount() - 1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
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

    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataManager.get(COLLARCOLOR) & 15);
    }

    public void setCollarColor(EnumDyeColor collarcolor) {
        this.dataManager.set(COLLARCOLOR, collarcolor.getDyeDamage());
    }

    public CorgiType getRandomCorgiType(Random rand) {
        int i = rand.nextInt(CorgiType.count());
        return CorgiType.byDamage(i);
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setCorgitype(getRandomCorgiType(this.world.rand));
        this.setCustomNameTag(upperCaseFirstLetter(this.getCorgiType().getName()) + " Corgi");
        return livingdata;
    }

    private String upperCaseFirstLetter(String str) {
        String s1 = str.substring(0, 1).toUpperCase();
        return s1 + str.substring(1);
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.25F - (this.getMaxHealth() - this.dataManager.get(DATA_HEALTH_ID)) * 0.01F) * (float)Math.PI : ((float)Math.PI / 5F));
    }

    @SuppressWarnings("NullableProblems")
    public CorgiEntity createChild(EntityAgeable ageable) {
        CorgiEntity entitycorgi = new CorgiEntity(this.world);
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

    public boolean canMateWith(EntityAnimal otherAnimal) {
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
