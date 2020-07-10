package com.fabbe50.corgis.entities.corgis;

import com.fabbe50.corgis.entities.interfaces.ICorgi;
import com.fabbe50.corgis.entities.event.ZombieCorgiEvent;
import com.fabbe50.corgis.entities.registry.EntityRegistry;
import com.fabbe50.corgis.event.ModEventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class ZombieCorgiEntity extends MonsterEntity implements ICorgi {
    private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(ZombieCorgiEntity.class, DataSerializers.BOOLEAN);
    private static final Predicate<Difficulty> HARD_DIFFICULTY_PREDICATE = (p_213697_0_) -> p_213697_0_ == Difficulty.HARD;
    private final BreakDoorGoal breakDoor = new BreakDoorGoal(this, HARD_DIFFICULTY_PREDICATE);
    private boolean isBreakDoorsTaskSet;

    public ZombieCorgiEntity(EntityType<? extends ZombieCorgiEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ZombieCorgiEntity(World world) {
        this(EntityRegistry.ZOMBIE_CORGI, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new ZombieCorgiEntity.AttackTurtleEggGoal(this, 1.0D, 3));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::isBreakDoorsTaskSet));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.2D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(ZombifiedPiglinEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.TARGET_DRY_BABY));
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233819_b_, 35.0D).func_233815_a_(Attributes.field_233821_d_, (double)0.23F).func_233815_a_(Attributes.field_233823_f_, 3.0D).func_233815_a_(Attributes.field_233826_i_, 2.0D).func_233814_a_(Attributes.field_233829_l_);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(IS_CHILD, false);
    }

    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }

    public void setBreakDoorsAItask(boolean enabled) {
        if (this.canBreakDoors()) {
            if (this.isBreakDoorsTaskSet != enabled) {
                this.isBreakDoorsTaskSet = enabled;
                ((GroundPathNavigator)this.getNavigator()).setBreakDoors(enabled);
                if (enabled) {
                    this.goalSelector.addGoal(1, this.breakDoor);
                } else {
                    this.goalSelector.removeGoal(this.breakDoor);
                }
            }
        } else if (this.isBreakDoorsTaskSet) {
            this.goalSelector.removeGoal(this.breakDoor);
            this.isBreakDoorsTaskSet = false;
        }
    }

    protected boolean canBreakDoors() {
        return true;
    }

    public boolean isChild() {
        return this.getDataManager().get(IS_CHILD);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        if (this.isChild())
            this.experienceValue = (int)((float)this.experienceValue * 3F);

        return super.getExperiencePoints(player);
    }

    public void setChild(boolean child) {
        this.getDataManager().set(IS_CHILD, child);
        if (this.world != null && !this.world.isRemote) {
            ModifiableAttributeInstance attributeInstance = this.getAttribute(Attributes.field_233821_d_);
            attributeInstance.removeModifier(BABY_SPEED_BOOST);
            if (child)
                attributeInstance.func_233767_b_(BABY_SPEED_BOOST);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (IS_CHILD.equals(key))
            this.recalculateSize();

        super.notifyDataManagerChange(key);
    }

    @Override
    public void livingTick() {
        if (this.isAlive()) {
            boolean flag = this.shouldBurnInDay() && this.isInDaylight();
            if (flag)
                this.setFire(8);
        }

        super.livingTick();
    }

    protected void transferDataInConversion(EntityType<? extends ZombieCorgiEntity> type) {
        if (this.removed) {
            ZombieCorgiEntity entity = type.create(this.world);
            entity.copyLocationAndAnglesFrom(this);
            entity.setBreakDoorsAItask(entity.canBreakDoors() && this.isBreakDoorsTaskSet());
            entity.applyAttributeBonuses(entity.world.getDifficultyForLocation(new BlockPos(entity.getPositionUnderneath().up())).getClampedAdditionalDifficulty());
            entity.setChild(this.isChild());
            entity.setNoAI(this.isAIDisabled());

            if (this.hasCustomName()) {
                entity.setCustomName(this.getCustomName());
                entity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired()) {
                entity.enablePersistence();
            }

            entity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(entity);
            this.remove();
        }
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (item instanceof SpawnEggItem && ((SpawnEggItem)item).hasType(itemStack.getTag(), this.getType())) {
            if (this.world.isRemote) {
                ZombieCorgiEntity entity = (ZombieCorgiEntity) this.getType().create(this.world);
                if (entity != null) {
                    entity.setChild(true);
                    entity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0f, 0.0f);
                    this.world.addEntity(entity);
                    if (itemStack.hasDisplayName())
                        entity.setCustomName(itemStack.getDisplayName());
                    if (!player.abilities.isCreativeMode)
                        itemStack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;
        } else
            return super.func_230254_b_(player, hand);
    }

    protected boolean shouldBurnInDay() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount)) {
            LivingEntity livingEntity = this.getAttackTarget();
            if (livingEntity == null && source.getTrueSource() instanceof LivingEntity)
                livingEntity = (LivingEntity)source.getTrueSource();

            int i = MathHelper.floor(this.getPosX());
            int j = MathHelper.floor(this.getPosY());
            int k = MathHelper.floor(this.getPosZ());

            ZombieCorgiEvent.SummonAidEvent event = ModEventFactory.fireZombieSummonAid(this, world, i, j, k, livingEntity, this.getAttribute(Attributes.field_233829_l_).getValue());
            if (event.getResult() == Event.Result.DENY) return true;
            if (event.getResult() == Event.Result.ALLOW || livingEntity != null && this.world.getDifficulty() == Difficulty.HARD && (double)this.rand.nextFloat() < this.getAttribute(Attributes.field_233829_l_).getValue() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                ZombieCorgiEntity entity = event.getCustomSummonedAid() != null && event.getResult() == Event.Result.ALLOW ? event.getCustomSummonedAid() : EntityRegistry.ZOMBIE_CORGI.create(this.world);

                for (int l = 0; l < 50; l++) {
                    int i1 = i + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                    int j1 = j + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                    int k1 = k + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                    BlockPos pos = new BlockPos(i1, j1 - 1, k1);
                    EntityType<?> entitytype = entity.getType();
                    EntitySpawnPlacementRegistry.PlacementType entityspawnplacementregistry$placementtype = EntitySpawnPlacementRegistry.getPlacementType(entitytype);
                    if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(entityspawnplacementregistry$placementtype, this.world, pos, entitytype) && EntitySpawnPlacementRegistry.func_223515_a(entitytype, this.world, SpawnReason.REINFORCEMENT, pos, this.world.rand)) {
                        entity.setPosition((double)i1, (double)j1, (double)k1);
                        if (!this.world.isPlayerWithin((double)i1, (double)j1, (double)k1, 7.0D) && this.world.checkNoEntityCollision(entity) && this.world.hasNoCollisions(entity) && !this.world.containsAnyLiquid(entity.getBoundingBox())) {
                            this.world.addEntity(entity);
                            if (livingEntity != null)
                                entity.setAttackTarget(livingEntity);
                            entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(entity.func_233580_cy_()), SpawnReason.REINFORCEMENT, null, null);
                            this.getAttribute(Attributes.field_233829_l_).func_233769_c_(new AttributeModifier("Reinforcement caller charge", (double)-0.05f, AttributeModifier.Operation.ADDITION));
                            entity.getAttribute(Attributes.field_233829_l_).func_233769_c_(new AttributeModifier("Reinforcement callee charge", (double)-0.05f, AttributeModifier.Operation.ADDITION));
                            break;
                        }
                    }
                }
            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            float f = this.world.getDifficultyForLocation(this.func_233580_cy_()).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3f) {
                entityIn.setFire(2 * (int)f);
            }
        }

        return flag;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_GROWL;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_WOLF_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getTailRotation() {
        return ((float)Math.PI / 5F);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.isChild())
            compound.putBoolean("IsBaby", true);

        compound.putBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.getBoolean("IsBaby"))
            this.setChild(true);

        this.setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
    }

    @Override
    public void onKillEntity(LivingEntity entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        if ((this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) && entityLivingIn instanceof VillagerEntity) {
            if (this.world.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean())
                return;

            VillagerEntity villager = (VillagerEntity)entityLivingIn;
            ZombieVillagerEntity zombieVillager = EntityType.ZOMBIE_VILLAGER.create(this.world);
            zombieVillager.copyLocationAndAnglesFrom(villager);
            villager.remove();
            zombieVillager.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(zombieVillager.func_233580_cy_())), SpawnReason.CONVERSION, new ZombieCorgiEntity.GroupData(false), null);
            zombieVillager.setVillagerData(villager.getVillagerData());
            zombieVillager.setGossips(villager.getGossip().func_234058_a_(NBTDynamicOps.INSTANCE).getValue());
            zombieVillager.setOffers(villager.getOffers().write());
            zombieVillager.setEXP(villager.getXp());
            zombieVillager.setChild(villager.isChild());
            zombieVillager.setNoAI(villager.isAIDisabled());
            if (villager.hasCustomName()) {
                zombieVillager.setCustomName(villager.getCustomName());
                zombieVillager.setCustomNameVisible(villager.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired())
                zombieVillager.enablePersistence();

            zombieVillager.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(zombieVillager);
            if (!this.isSilent())
                this.world.playEvent(null, 1026, new BlockPos(this.func_233580_cy_()), 0);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? 0.2f : 0.3f; //TODO: No idea if these numbers are correct.
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        float f = difficultyIn.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
        if (spawnDataIn == null) {
            spawnDataIn = new ZombieCorgiEntity.GroupData(worldIn.getRandom().nextFloat() < ForgeConfig.SERVER.zombieBabyChance.get());
        }
        if (spawnDataIn instanceof ZombieCorgiEntity.GroupData) {
            ZombieCorgiEntity.GroupData entity$groupdata = (ZombieCorgiEntity.GroupData)spawnDataIn;
            if (entity$groupdata.isChild) {
                this.setChild(true);
            }

            this.setBreakDoorsAItask(this.canBreakDoors() && this.rand.nextFloat() < f * 0.1f);
        }
        this.applyAttributeBonuses(f);
        return spawnDataIn;
    }

    protected void applyAttributeBonuses(float difficulty) {
        this.getAttribute(Attributes.field_233820_c_).func_233769_c_(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.5D, AttributeModifier.Operation.ADDITION));
        double d0 = this.rand.nextDouble() * 1.5D * (double)difficulty;
        if (d0 > 1.0D)
            this.getAttribute(Attributes.field_233819_b_).func_233769_c_(new AttributeModifier("Random extra-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));

        if (this.rand.nextFloat() < difficulty * 0.05f) {
            this.getAttribute(Attributes.field_233829_l_).func_233769_c_(new AttributeModifier("Leader bonus", this.rand.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
            this.getAttribute(Attributes.field_233818_a_).func_233769_c_(new AttributeModifier("Leader bonus", this.rand.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            this.setBreakDoorsAItask(this.canBreakDoors());
        }
    }

    @Override
    public double getYOffset() {
        return this.isChild() ? 0.0D : -0.45D;
    }

    class AttackTurtleEggGoal extends BreakBlockGoal {
        AttackTurtleEggGoal(CreatureEntity creatureIn, double speed, int yMax) {
            super(Blocks.TURTLE_EGG, creatureIn, speed, yMax);
        }

        public void playBreakingSound(IWorld worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, 0.5F, 0.9F + ZombieCorgiEntity.this.rand.nextFloat() * 0.2F);
        }

        public void playBrokenSound(World worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
        }

        public double getTargetDistanceSq() {
            return 1.14D;
        }
    }

    public class GroupData implements ILivingEntityData {
        public final boolean isChild;

        private GroupData(boolean isChildIn) {
            this.isChild = isChildIn;
        }
    }
}
