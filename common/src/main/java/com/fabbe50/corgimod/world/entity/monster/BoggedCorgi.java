package com.fabbe50.corgimod.world.entity.monster;

import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.world.entity.interfaces.IBaby;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.ISkeletonCorgi;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoggedCorgi extends Bogged implements ISkeletonCorgi, IBaby {
    private static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(BoggedCorgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions BABY_DIMENSIONS = EntityRegistry.BOGGED_CORGI.get().getDimensions().scale(0.7F).withEyeHeight(0.35F);

    public BoggedCorgi(EntityType<? extends Bogged> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BABY, false);
    }

    @Override
    public boolean isBaby() {
        return this.getEntityData().get(IS_BABY);
    }

    @Override
    public void setBaby(boolean bl) {
        this.getEntityData().set(IS_BABY, bl);
        if (this.level() != null && !this.level().isClientSide) {
            AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(SPEED_MODIFIER_BABY_ID);
                if (bl) {
                    attributeInstance.addTransientModifier(SPEED_MODIFIER_BABY);
                }
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (IS_BABY.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficultyInstance, mobSpawnType, spawnGroupData);
        spawnGroupData = finalizeBabySpawnChance(level, spawnGroupData);
        return spawnGroupData;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        saveBabyState(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        readBabyState(compoundTag);
    }
}
