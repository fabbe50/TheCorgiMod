package com.fabbe50.corgimod.world.entity.monster;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.world.entity.interfaces.IBaby;
import com.fabbe50.corgimod.world.entity.interfaces.corgi.IZombieCorgi;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ZombieCorgi extends Zombie implements IZombieCorgi, IBaby {
    private static final ResourceLocation REINFORCEMENT_CALLER_CHARGE_ID = ResourceLocation.withDefaultNamespace("reinforcement_caller_charge");
    private static final AttributeModifier ZOMBIE_REINFORCEMENT_CALLEE_CHARGE = new AttributeModifier(ResourceLocation.withDefaultNamespace("reinforcement_callee_charge"), -0.05F, AttributeModifier.Operation.ADD_VALUE);
    private static final EntityDimensions BABY_DIMENSIONS = EntityRegistry.ZOMBIE_CORGI.get().getDimensions().scale(0.7F).withEyeHeight(0.35F);

    public ZombieCorgi(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    protected ZombieCorgi(Level level) {
        this(EntityRegistry.ZOMBIE_CORGI.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45.0D).add(Attributes.MOVEMENT_SPEED, 0.33F).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.ARMOR, 1.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected void doUnderWaterConversion() {
        this.convertToZombieType(EntityRegistry.DROWNED_CORGI.get());
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1040, this.blockPosition(), 0);
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomSource = level.getRandom();
        float f = difficulty.getSpecialMultiplier();
        if (spawnGroupData == null) {
            spawnGroupData = new ZombieGroupData(getBabySpawnOdds(randomSource), true);
        }

        if (spawnGroupData instanceof ZombieGroupData zombieGroupData) {
            if (!zombieGroupData.isBaby) {
                if (zombieGroupData.canSpawnJockey) {
                    if (random.nextInt(ModConfig.<Integer>getValue("zombieCorgiJockeySpawnChance").getValue()) == 0) {
                        Zombie zombie = EntityType.ZOMBIE.create(this.level());
                        if (zombie != null) {
                            zombie.setBaby(true);
                            zombie.moveTo(this.getX(), this.getY(), this.getZ());
                            zombie.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null);
                            zombie.startRiding(this);
                            level.addFreshEntity(zombie);
                        }
                    }
                }
            } else {
                this.setBaby(true);
            }
        }

        this.handleAttributes(f);
        return spawnGroupData;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!super.hurt(damageSource, f)) {
            return false;
        } else if (!(this.level() instanceof ServerLevel serverLevel)) {
            return false;
        } else {
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && damageSource.getEntity() instanceof LivingEntity) {
                livingEntity = (LivingEntity)damageSource.getEntity();
            }

            if (livingEntity != null && this.level().getDifficulty() == Difficulty.HARD && (double)this.random.nextFloat() < this.getAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE) && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                int i = Mth.floor(this.getX());
                int j = Mth.floor(this.getY());
                int k = Mth.floor(this.getZ());
                ZombieCorgi zombie = new ZombieCorgi(this.level());

                for(int l = 0; l < 50; ++l) {
                    int m = i + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int n = j + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int o = k + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    BlockPos blockPos = new BlockPos(m, n, o);
                    EntityType<?> entityType = zombie.getType();
                    if (SpawnPlacements.isSpawnPositionOk(entityType, this.level(), blockPos) && SpawnPlacements.checkSpawnRules(entityType, serverLevel, MobSpawnType.REINFORCEMENT, blockPos, this.level().random)) {
                        zombie.setPos(m, n, o);
                        if (!this.level().hasNearbyAlivePlayer(m, n, o, 7.0F) && this.level().isUnobstructed(zombie) && this.level().noCollision(zombie) && !this.level().containsAnyLiquid(zombie.getBoundingBox())) {
                            zombie.setTarget(livingEntity);
                            zombie.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, null);
                            serverLevel.addFreshEntityWithPassengers(zombie);
                            AttributeInstance attributeInstance = this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
                            if (attributeInstance == null) {
                                break;
                            }
                            AttributeModifier attributeModifier = attributeInstance.getModifier(REINFORCEMENT_CALLER_CHARGE_ID);
                            double d = attributeModifier != null ? attributeModifier.amount() : (double)0.0F;
                            attributeInstance.removeModifier(REINFORCEMENT_CALLER_CHARGE_ID);
                            attributeInstance.addPermanentModifier(new AttributeModifier(REINFORCEMENT_CALLER_CHARGE_ID, d - 0.05, AttributeModifier.Operation.ADD_VALUE));
                            Objects.requireNonNull(zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)).addPermanentModifier(ZOMBIE_REINFORCEMENT_CALLEE_CHARGE);
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    protected @NotNull ItemStack getSkull() {
        return ItemStack.EMPTY;
    }
}
