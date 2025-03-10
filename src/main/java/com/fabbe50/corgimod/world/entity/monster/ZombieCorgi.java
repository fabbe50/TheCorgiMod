package com.fabbe50.corgimod.world.entity.monster;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ZombieCorgi extends Zombie {
    public ZombieCorgi(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45.0D).add(Attributes.MOVEMENT_SPEED, (double)0.33F).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.ARMOR, 1.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.85F;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getDimensions(this.getPose()).height * 0.28;
    }

    public float getTailAngle() {
        return ((float)Math.PI / 5F);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
            return Component.literal(Corgis.ZOMBIE.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);

        if (data instanceof ZombieGroupData zombieGroupData) {
            if (!zombieGroupData.isBaby) {
                if (zombieGroupData.canSpawnJockey) {
                    if (random.nextFloat() < ((float) CorgiMod.config.general.zombieCorgiJockeySpawnChance / 100)) {
                        Zombie zombie = EntityType.ZOMBIE.create(this.level());
                        if (zombie != null) {
                            zombie.setBaby(true);
                            zombie.moveTo(this.getX(), this.getY(), this.getZ());
                            zombie.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null, null);
                            zombie.startRiding(this);
                            level.addFreshEntity(zombie);
                        }
                    }
                }
            }
        }

        return data;
    }

    public static class ZombieEvents {
        @SubscribeEvent
        public static void summonAid(ZombieEvent.SummonAidEvent event) {
            if (event.getEntity() instanceof ZombieCorgi corgi) {
                int x = Mth.floor(corgi.getX());
                int y = Mth.floor(corgi.getY());
                int z = Mth.floor(corgi.getZ());
                ServerLevel serverLevel = (ServerLevel) corgi.level();
                LivingEntity attacker = event.getAttacker();
                if (attacker != null && corgi.level().getDifficulty() == Difficulty.HARD && (double)corgi.random.nextFloat() < Objects.requireNonNull(corgi.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)).getValue() && corgi.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                    ZombieCorgi zombieCorgi = EntityRegistry.CORGI_ZOMBIE.get().create(corgi.level());
                    if (zombieCorgi != null) {
                        for (int l = 0; l < 50; l++) {
                            int x1 = x + Mth.nextInt(corgi.random, 7, 40) + Mth.nextInt(corgi.random, -1, 1);
                            int y1 = y + Mth.nextInt(corgi.random, 7, 40) + Mth.nextInt(corgi.random, -1, 1);
                            int z1 = z + Mth.nextInt(corgi.random, 7, 40) + Mth.nextInt(corgi.random, -1, 1);
                            BlockPos pos = new BlockPos(x1, y1, z1);
                            EntityType<?> entityType = zombieCorgi.getType();
                            SpawnPlacements.Type spawnplacements$type = SpawnPlacements.getPlacementType(entityType);
                            if (NaturalSpawner.isSpawnPositionOk(spawnplacements$type, corgi.level(), pos, entityType) && SpawnPlacements.checkSpawnRules(entityType, serverLevel, MobSpawnType.REINFORCEMENT, pos, corgi.level().random)) {
                                zombieCorgi.setPos(x1, y1, z1);
                                if (!corgi.level().hasNearbyAlivePlayer(x1, y1, z1, 7) && corgi.level().isUnobstructed(zombieCorgi) && corgi.level().noCollision(zombieCorgi) && !corgi.level().containsAnyLiquid(zombieCorgi.getBoundingBox())) {
                                    zombieCorgi.setTarget(attacker);
                                    zombieCorgi.finalizeSpawn(serverLevel, corgi.level().getCurrentDifficultyAt(zombieCorgi.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                                    serverLevel.addFreshEntityWithPassengers(zombieCorgi);
                                    Objects.requireNonNull(corgi.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)).addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05f, AttributeModifier.Operation.ADDITION));
                                    Objects.requireNonNull(zombieCorgi.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)).addPermanentModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05f, AttributeModifier.Operation.ADDITION));
                                    break;
                                }
                            }
                        }
                    }
                }
                event.setResult(Event.Result.DENY);
            }
            event.setResult(Event.Result.DEFAULT);
        }
    }
}
