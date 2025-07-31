package com.fabbe50.corgimod.world.entity.ai.overrides;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("DataFlowIssue")
public class CustomRelaxOnOwnerGoal extends Goal {
    private final Corgi corgi;
    @Nullable
    private Player ownerPlayer;
    @Nullable
    private BlockPos goalPos;
    private int onBedTicks;

    public CustomRelaxOnOwnerGoal(Corgi corgi) {
        this.corgi = corgi;
    }

    public boolean canUse() {
        if (!this.corgi.isTame()) {
            return false;
        } else if (this.corgi.isOrderedToSit()) {
            return false;
        } else {
            net.minecraft.world.entity.LivingEntity livingEntity = this.corgi.getOwner();
            if (livingEntity instanceof Player) {
                this.ownerPlayer = (Player)livingEntity;
                if (!livingEntity.isSleeping()) {
                    return false;
                }

                if (this.corgi.distanceToSqr(this.ownerPlayer) > (double)100.0F) {
                    return false;
                }

                BlockPos blockPos = this.ownerPlayer.blockPosition();
                BlockState blockState = this.corgi.level().getBlockState(blockPos);
                if (blockState.is(BlockTags.BEDS)) {
                    this.goalPos = blockState.getOptionalValue(BedBlock.FACING).map((direction) -> blockPos.relative(direction.getOpposite())).orElseGet(() -> new BlockPos(blockPos));
                    return this.spaceIsVacant();
                }
            }

            return false;
        }
    }

    private boolean spaceIsVacant() {
        for(Corgi corgi : this.corgi.level().getEntitiesOfClass(Corgi.class, (new AABB(this.goalPos)).inflate((double)2.0F))) {
            if (corgi != this.corgi && (corgi.isLying() || corgi.isRelaxStateOne())) {
                return false;
            }
        }
        return true;
    }

    public boolean canContinueToUse() {
        return this.corgi.isTame() && !this.corgi.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && this.spaceIsVacant();
    }

    public void start() {
        if (this.goalPos != null) {
            this.corgi.setInSittingPose(false);
            this.corgi.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
        }

    }

    public void stop() {
        this.corgi.setLying(false);
        float f = this.corgi.level().getTimeOfDay(1.0F);
        if (this.ownerPlayer.getSleepTimer() >= 100 && (double)f > 0.77 && (double)f < 0.8 && (double)this.corgi.level().getRandom().nextFloat() < 0.7) {
            this.giveMorningGift();
        }

        this.onBedTicks = 0;
        this.corgi.setRelaxStateOne(false);
        this.corgi.getNavigation().stop();
    }

    private void giveMorningGift() {
        RandomSource randomSource = this.corgi.getRandom();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        mutableBlockPos.set(this.corgi.isLeashed() ? this.corgi.getLeashHolder().blockPosition() : this.corgi.blockPosition());
        this.corgi.randomTeleport(mutableBlockPos.getX() + randomSource.nextInt(11) - 5, mutableBlockPos.getY() + randomSource.nextInt(5) - 2, mutableBlockPos.getZ() + randomSource.nextInt(11) - 5, false);
        mutableBlockPos.set(this.corgi.blockPosition());
        LootTable lootTable = this.corgi.level().getServer().reloadableRegistries().getLootTable(BuiltInLootTables.CAT_MORNING_GIFT);
        LootParams lootParams = (new LootParams.Builder((ServerLevel)this.corgi.level())).withParameter(LootContextParams.ORIGIN, this.corgi.position()).withParameter(LootContextParams.THIS_ENTITY, this.corgi).create(LootContextParamSets.GIFT);

        for(ItemStack itemStack : lootTable.getRandomItems(lootParams)) {
            this.corgi.level().addFreshEntity(new ItemEntity(this.corgi.level(), (double)mutableBlockPos.getX() - (double) Mth.sin(this.corgi.yBodyRot * ((float)Math.PI / 180F)), (double)mutableBlockPos.getY(), (double)mutableBlockPos.getZ() + (double)Mth.cos(this.corgi.yBodyRot * ((float)Math.PI / 180F)), itemStack));
        }

    }

    public void tick() {
        if (this.ownerPlayer != null && this.goalPos != null) {
            this.corgi.setInSittingPose(false);
            this.corgi.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
            if (this.corgi.distanceToSqr(this.ownerPlayer) < (double)2.5F) {
                ++this.onBedTicks;
                if (this.onBedTicks > this.adjustedTickDelay(16)) {
                    this.corgi.setLying(true);
                    this.corgi.setRelaxStateOne(false);
                } else {
                    this.corgi.lookAt(this.ownerPlayer, 45.0F, 45.0F);
                    this.corgi.setRelaxStateOne(true);
                }
            } else {
                this.corgi.setLying(false);
            }
        }

    }
}
