package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.registries.ModEntitySerializers;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class Corgi extends TamableAnimal implements NeutralMob, VariantHolder<Holder<CorgiVariant>>, ICorgi {
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);;
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.INT);;
    private static final EntityDataAccessor<Holder<CorgiVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Corgi.class, ModEntitySerializers.CORGI_VARIANT);
    private static final EntityDataAccessor<Boolean> ASKED_TO_STAY = SynchedEntityData.defineId(Corgi.class, EntityDataSerializers.BOOLEAN);
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
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @Override
    public void setVariant(Holder<CorgiVariant> object) {

    }

    @Override
    public Holder<CorgiVariant> getVariant() {
        return null;
    }
}
