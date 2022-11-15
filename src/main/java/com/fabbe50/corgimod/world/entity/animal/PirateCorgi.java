package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PirateCorgi extends Corgi {
    private static final EntityDataAccessor<Boolean> HAS_TREASURE = SynchedEntityData.defineId(PirateCorgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> TREASURE_POS = SynchedEntityData.defineId(PirateCorgi.class, EntityDataSerializers.BLOCK_POS);
    private int treasureCooldown;

    public PirateCorgi(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_TREASURE, false);
        this.entityData.define(TREASURE_POS, BlockPos.ZERO);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("treasureX", this.getTreasurePos().getX());
        compoundTag.putInt("treasureY", this.getTreasurePos().getY());
        compoundTag.putInt("treasureZ", this.getTreasurePos().getZ());
        compoundTag.putInt("treasureCooldown", this.getTreasureCooldown());
        compoundTag.putBoolean("hasTreasure", this.hasTreasure());
        super.addAdditionalSaveData(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        this.setTreasurePos(new BlockPos(compoundTag.getInt("treasureX"), compoundTag.getInt("treasureY"), compoundTag.getInt("treasureZ")));
        this.treasureCooldown = compoundTag.getInt("treasureCooldown");
        this.setHasTreasure(compoundTag.getBoolean("hasTreasure"));
        super.readAdditionalSaveData(compoundTag);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem().equals(Items.GOLD_INGOT) && this.getTreasureCooldown() <= 0) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (this.getLevel() instanceof ServerLevel serverLevel) {
                this.setTreasurePos(serverLevel.findNearestMapStructure(StructureTags.ON_TREASURE_MAPS, this.getOnPos(), 100, true));
                this.treasureCooldown = Utils.ticksFromSecond(600);
                this.setHasTreasure(true);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    private boolean hasVisited;
    @Override
    public void tick() {
        super.tick();
        if (this.treasureCooldown > 0)
            this.treasureCooldown--;

        if (CorgiMod.config.general.corgiParticleEffects && this.hasTreasure()) {
            BlockPos temp = this.calculateTreasurePointer();
            boolean close = this.isOwnerCloseToTreasure();
            if (close && !this.hasVisited) {
                this.hasVisited = true;
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.MASTER, 0.8f, 1, false);
            }
            if (!close && this.hasVisited) {
                this.setHasTreasure(false);
                this.hasVisited = false;
            }
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, temp.getX() + random.nextDouble() - 0.5D, temp.getY() + random.nextDouble(), temp.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
        }
    }

    public boolean isOwnerCloseToTreasure() {
        if (this.isTame() && this.getOwner() != null) {
            BlockPos pos = this.getOwner().getOnPos();
            double x = pos.getX();
            double z = pos.getZ();
            double dx = this.getTreasurePos().getX() - x;
            double dz = this.getTreasurePos().getZ() - z;
            double dp = Math.sqrt(dx * dx + dz * dz);
            return dp < 5;
        }
        return false;
    }

    public BlockPos calculateTreasurePointer() {
        if (this.getOwner() instanceof Player player) {
            double tx = getTreasurePos().getX();
            int y = getTreasurePos().getY();
            double tz = getTreasurePos().getZ();
            double dx = tx - player.getX();
            double dz = tz - player.getZ();
            double ds = Math.sqrt(dx * dx + dz * dz);
            double ty = player.getY();
            if (ds > 12) {
                return new BlockPos(player.getX() + dx / ds * 12.0D, ty + 1, player.getZ() + dz / ds * 12.0D);
            } else {
                return new BlockPos(tx, y, tz);
            }
        }
        return BlockPos.ZERO;
    }

    public void setTreasurePos(BlockPos pos) {
        this.entityData.set(TREASURE_POS, pos);
    }

    public BlockPos getTreasurePos() {
        return this.entityData.get(TREASURE_POS);
    }

    public void setHasTreasure(boolean hasTreasure) {
        this.entityData.set(HAS_TREASURE, hasTreasure);
    }

    public boolean hasTreasure() {
        return this.entityData.get(HAS_TREASURE);
    }

    public int getTreasureCooldown() {
        return treasureCooldown;
    }
}
