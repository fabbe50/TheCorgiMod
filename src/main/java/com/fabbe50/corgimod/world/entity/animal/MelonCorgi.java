package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.data.LootTables;
import com.fabbe50.corgimod.world.entity.ai.StealMelonGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

public class MelonCorgi extends Corgi {
    private static final EntityDataAccessor<Integer> BARTER_TIME = SynchedEntityData.defineId(MelonCorgi.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_SEED = SynchedEntityData.defineId(MelonCorgi.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SEED_TIME = SynchedEntityData.defineId(MelonCorgi.class, EntityDataSerializers.INT);

    public MelonCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BARTER_TIME, this.random.nextInt(6000) + 6000);
        this.entityData.define(HAS_SEED, false);
        this.entityData.define(SEED_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("barter_time", this.getBarterTime());
        compoundTag.putBoolean("has_seed", this.getHasSeed());
        compoundTag.putInt("seed_time", this.getSeedTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBarterTime(compoundTag.getInt("barter_time"));
        this.setHasSeed(compoundTag.getBoolean("has_seed"));
        this.setSeedTime(compoundTag.getInt("seed_time"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new StealMelonGoal(this, 0.8, 20));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getLevel().getGameTime() % 20 == 0) {
            int barterTime = this.getBarterTime();
            if (barterTime > 0) {
                this.setBarterTime(barterTime - 20);
            }
            int seedTime = this.getSeedTime();
            if (seedTime > 0 && this.getHasSeed()) {
                this.setSeedTime(seedTime - 1);
            } else if (this.getHasSeed()) {
                int amount = this.random.nextInt(5);
                for (int i = 0; i < amount; i++) {
                    this.getLevel().addFreshEntity(new ItemEntity(this.getLevel(), this.position().x, this.position().y, this.position().z, new ItemStack(Items.MELON), (this.random.nextDouble() / 2) - 0.25, 0.1, (this.random.nextDouble() / 2) - 0.25));
                }
                this.setHasSeed(false);
            }
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem().equals(Items.MELON_SLICE)) {
            if (!this.isAngry() && !this.isTame()) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }
                return InteractionResult.SUCCESS;
            } else {
                if (this.getBarterTime() <= 0) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    int amount = this.random.nextInt(5);
                    for (int i = 0; i < amount; i++) {
                        this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + this.random.nextDouble() - 0.5D, this.getY() + this.random.nextDouble(), this.getZ() + this.random.nextDouble() - 0.5D, 0, 0.1D, 0);
                    }
                    MinecraftServer server = this.getLevel().getServer();
                    if (server != null) {
                        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                        mutableBlockPos.set(this.blockPosition());
                        LootTable lootTable = server.getLootTables().get(LootTables.MELON_CORGI_GIFT);
                        LootContext.Builder builder = (new LootContext.Builder((ServerLevel) this.getLevel())).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.getRandom());
                        for (ItemStack itemStack : lootTable.getRandomItems(builder.create(LootContextParamSets.GIFT))) {
                            this.getLevel().addFreshEntity(new ItemEntity(this.getLevel(), mutableBlockPos.getX() - Mth.sin(this.yBodyRot * ((float) Math.PI / 180F)), mutableBlockPos.getY(), mutableBlockPos.getZ() + Mth.cos(this.yBodyRot * ((float) Math.PI / 180F)), itemStack));
                        }
                        this.resetBarterTime();
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        } else if (stack.getItem().equals(Items.MELON_SEEDS) && !this.getHasSeed() && this.isTame()) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            int amount = this.random.nextInt(5);
            for (int i = 0; i < amount; i++) {
                this.level.addParticle(ParticleTypes.HEART, this.getX() + this.random.nextDouble() - 0.5D, this.getY() + this.random.nextDouble(), this.getZ() + this.random.nextDouble() - 0.5D, 0, 0.1D, 0);
            }
            this.setHasSeed(true);
            this.setSeedTime(60);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.MELON.getFormattedName());
        return super.getDisplayName();
    }

    public void setBarterTime(int barterTime) {
        this.entityData.set(BARTER_TIME, barterTime);
    }

    public void resetBarterTime() {
        this.entityData.set(BARTER_TIME, this.random.nextInt(6000) + 6000);
    }

    public int getBarterTime() {
        return this.entityData.get(BARTER_TIME);
    }

    public void setHasSeed(boolean hasSeed) {
        this.entityData.set(HAS_SEED, hasSeed);
    }

    public boolean getHasSeed() {
        return this.entityData.get(HAS_SEED);
    }

    public void setSeedTime(int seedTime) {
        this.entityData.set(SEED_TIME, seedTime);
    }

    public int getSeedTime() {
        return this.entityData.get(SEED_TIME);
    }
}