package com.fabbe50.corgimod.world.entity.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public interface IBaby {
    ResourceLocation SPEED_MODIFIER_BABY_ID = ResourceLocation.withDefaultNamespace("baby");
    AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_ID, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    boolean isBaby();

    void setBaby(boolean isBaby);

    default SpawnGroupData finalizeBabySpawnChance(ServerLevelAccessor level, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomSource = level.getRandom();
        if (spawnGroupData == null) {
            spawnGroupData = new BabySpawnGroupData(getBabySpawnOdds(randomSource));
        }
        if (spawnGroupData instanceof BabySpawnGroupData(boolean isBaby)) {
            if (isBaby) {
                this.setBaby(true);
            }
        }
        return spawnGroupData;
    }

    default void saveBabyState(CompoundTag tag) {
        tag.putBoolean("IsBaby", this.isBaby());
    }

    default void readBabyState(CompoundTag tag) {
        this.setBaby(tag.getBoolean("IsBaby"));
    }

    default boolean getBabySpawnOdds(RandomSource randomSource) {
        return randomSource.nextFloat() < 0.05F;
    }

    record BabySpawnGroupData(boolean isBaby) implements SpawnGroupData {
    }
}
