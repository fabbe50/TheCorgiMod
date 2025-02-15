package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.handlers.NameHandler;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AntiCorgi extends Cat {
    public AntiCorgi(EntityType<? extends Cat> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        if (!this.hasCustomName()) {
            if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES)) {
                System.out.println("Corgi does not have a name. Assigning default...");
                this.setCustomName(Component.literal(Corgis.NORMAL.getFormattedName()));
            } else if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.RANDOM_NAMES)) {
                System.out.println("Corgi does not have a name. Assigning random...");
                this.setCustomName(Component.literal(NameHandler.getRandomName(random.nextBoolean())));
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }

    @Override
    public Cat getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        AntiCorgi corgi = EntityRegistry.CORGI_ANTI.get().create(level);
        if (corgi != null && this.isTame()) {
            corgi.setOwnerUUID(this.getOwnerUUID());
            corgi.setTame(true);
        }
        return corgi;
    }

    public static boolean checkCorgiSpawnRules(EntityType<? extends AntiCorgi> corgi, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof AntiCorgi corgi)) {
            return false;
        } else {
            if (!corgi.isTame()) {
                return false;
            } else if (corgi.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && corgi.isInLove();
            }
        }
    }
}
