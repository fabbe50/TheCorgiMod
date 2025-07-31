package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.fabbe50.corgimod.world.entity.animal.Corgi.*;

public class PirateCorgiFeaturePack extends BaseCorgiFeaturePack {
    private boolean hasVisitedTreasure;

    @Override
    public void addAdditionalSaveData(Corgi entity, CompoundTag tag) {
        BlockPos treasurePos = getTreasurePos(entity);
        tag.putInt("treasureX", treasurePos.getX());
        tag.putInt("treasureY", treasurePos.getY());
        tag.putInt("treasureZ", treasurePos.getZ());
        tag.putInt("treasureCooldown", getTreasureCooldown(entity));
        tag.putBoolean("hasTreasure", hasTreasure(entity));
        super.addAdditionalSaveData(entity, tag);
    }

    @Override
    public void readAdditionalSaveData(Corgi entity, CompoundTag tag) {
        setTreasurePos(entity, new BlockPos(tag.getInt("treasureX"), tag.getInt("treasureY"), tag.getInt("treasureZ")));
        setTreasureCooldown(entity, tag.getInt("treasureCooldown"));
        setHasTreasure(entity, tag.getBoolean("hasTreasure"));
        super.readAdditionalSaveData(entity, tag);
    }

    @Override
    public InteractionResult onInteractWith(Corgi entity, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.GOLD_INGOT) && getTreasureCooldown(entity) <= 0) {
            player.getItemInHand(hand).consume(1, entity);
            if (entity.level() instanceof ServerLevel level) {
                setTreasurePos(entity, level.findNearestMapStructure(StructureTags.ON_TREASURE_MAPS, entity.getOnPos(), 100, true));
                setTreasureCooldown(entity, Utilities.ticksFromSecond(600));
                setHasTreasure(entity, true);
                return InteractionResult.SUCCESS;
            }
        }
        return super.onInteractWith(entity, player, hand);
    }

    @Override
    public void onTick(Corgi entity) {
        super.onTick(entity);
        if (getTreasureCooldown(entity) > 0) {
            setTreasureCooldown(entity, getTreasureCooldown(entity) - 1);
        }
    }

    @Override
    public void onHandleParticles(Corgi entity, RandomSource random) {
        super.onHandleParticles(entity, random);
        if (hasTreasure(entity)) {
            BlockPos temp = calculateTreasurePointer(entity);
            boolean close = isOwnerCloseToTreasure(entity);
            if (close && !this.hasVisitedTreasure) {
                this.hasVisitedTreasure = true;
                entity.level().playLocalSound(entity.getOnPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.MASTER, 0.8f, 1, false);
            }
            if (!close && this.hasVisitedTreasure) {
                setHasTreasure(entity, false);
                hasVisitedTreasure = false;
            }
            entity.level().addParticle(ParticleTypes.HAPPY_VILLAGER, Utilities.getPartialPos(temp.getX()), Utilities.getPartialPosY(temp.getY()), Utilities.getPartialPos(temp.getZ()), 0, 0.1d, 0);
        }
    }

    @Override
    public boolean isItemOfInterest(Corgi entity, ItemStack stack) {
        if (entity.isTame() && stack.is(Items.GOLD_INGOT)) {
            return true;
        }
        return super.isItemOfInterest(entity, stack);
    }

    public boolean isOwnerCloseToTreasure(Corgi entity) {
        if (entity.isTame() && entity.getOwner() != null) {
            BlockPos pos = entity.getOwner().getOnPos();
            double x = pos.getX();
            double z = pos.getZ();
            BlockPos treasurePos = getTreasurePos(entity);
            double dx = treasurePos.getX() - x;
            double dz = treasurePos.getZ() - z;
            double dp = Math.sqrt(dx * dx + dz * dz);
            return dp < 5;
        }
        return false;
    }

    public BlockPos calculateTreasurePointer(Corgi entity) {
        if (entity.getOwner() instanceof Player player) {
            BlockPos treasurePos = getTreasurePos(entity);
            double tx = treasurePos.getX();
            int y = treasurePos.getY();
            double tz = treasurePos.getZ();
            double dx = tx - player.getX();
            double dz = tz - player.getZ();
            double ds = Math.sqrt(dx * dx + dz * dz);
            double ty = player.getY();
            if (ds > 12) {
                return new BlockPos((int)(player.getX() + dx / ds * 12.0D), (int)ty + 1, (int)(player.getZ() + dz / ds * 12.0D));
            } else {
                return new BlockPos((int) tx, y, (int) tz);
            }
        }
        return BlockPos.ZERO;
    }

    public void setTreasurePos(Corgi corgi, BlockPos pos) {
        setCustomData(corgi, TREASURE_POS, pos);
    }

    public BlockPos getTreasurePos(Corgi corgi) {
        return getCustomData(corgi, TREASURE_POS);
    }

    public void setHasTreasure(Corgi corgi, boolean hasTreasure) {
        setCustomData(corgi, HAS_TREASURE, hasTreasure);
    }

    public boolean hasTreasure(Corgi corgi) {
        return getCustomData(corgi, HAS_TREASURE);
    }

    public void setTreasureCooldown(Corgi corgi, int cooldown) {
        setCustomData(corgi, TREASURE_COOLDOWN, cooldown);
    }

    public int getTreasureCooldown(Corgi corgi) {
        return getCustomData(corgi, TREASURE_COOLDOWN);
    }
}
