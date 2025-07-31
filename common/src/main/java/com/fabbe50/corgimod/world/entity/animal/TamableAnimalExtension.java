package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.world.entity.ai.AnimalGoToBedGoal;
import com.fabbe50.corgimod.world.entity.ai.HungryTamedAnimalGoesToEatGoal;
import com.fabbe50.corgimod.world.entity.ai.overrides.CustomFollowOwnerGoal;
import com.fabbe50.corgimod.world.entity.interfaces.pets.IPet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TamableAnimalExtension extends TamableAnimal implements IPet {
    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(TamableAnimalExtension.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ASKED_TO_STAY = SynchedEntityData.defineId(TamableAnimalExtension.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> ORIGIN_STAY = SynchedEntityData.defineId(TamableAnimalExtension.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> FEMALE = SynchedEntityData.defineId(TamableAnimalExtension.class, EntityDataSerializers.BOOLEAN);
    private boolean sleeping = false;

    protected TamableAnimalExtension(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new HungryTamedAnimalGoesToEatGoal<>(this, 0.7f, 16, 3));
        this.goalSelector.addGoal(6, new AnimalGoToBedGoal<>(this, 0.6, 16, 5));
        this.goalSelector.addGoal(7, new CustomFollowOwnerGoal<>(this, 1.0, 10.0F, 2.0F));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HUNGRY, false);
        builder.define(ASKED_TO_STAY, false);
        builder.define(ORIGIN_STAY, BlockPos.ZERO);
        builder.define(FEMALE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("hungry", this.isHungry());
        compoundTag.putIntArray("stayOrigin", Utilities.getIntArrayFromBlockPos(this.getOriginStay()));
        compoundTag.putBoolean("askedToStay", this.isAskedToStay());
        compoundTag.putBoolean("female", this.isFemale());
        compoundTag.putBoolean("sleeping", this.isSleeping());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("hungry")) {
            this.setHungry(compoundTag.getBoolean("hungry"));
        }
        if (compoundTag.contains("stayOrigin")) {
            BlockPos pos = Utilities.getBlockPosFromIntArray(compoundTag.getIntArray("stayOrigin"));
            if (pos != null) {
                this.setOriginStay(pos);
            }
        }
        if (compoundTag.contains("askedToStay")) {
            this.setAskedToStay(compoundTag.getBoolean("askedToStay"));
        }
        if (compoundTag.contains("female")) {
            this.setFemale(compoundTag.getBoolean("female"));
        }
        if (compoundTag.contains("sleeping")) {
            this.setSleeping(compoundTag.getBoolean("sleeping"));
        }
        this.setInSittingPose(this.isOrderedToSit() || this.isSleeping());
    }

    public void setHungry(boolean hungry) {
        this.entityData.set(HUNGRY, hungry);
    }

    public boolean isHungry() {
        return this.entityData.get(HUNGRY);
    }

    public void setFemale(boolean female) {
        this.entityData.set(FEMALE, female);
    }

    public boolean isFemale() {
        return this.entityData.get(FEMALE);
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
    public boolean isWithinRangeOfOrigin() {
        return this.position().distanceTo(getOriginStay().getBottomCenter()) < ModConfig.<Integer>getValue("maxWanderingDistance").getValue();
    }

    @Override
    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }

    @Override
    public boolean isSleeping() {
        return this.sleeping;
    }

    public boolean canMove() {
        return !this.isSleeping() && !this.isAskedToStay() && !this.isOrderedToSit();
    }
}
