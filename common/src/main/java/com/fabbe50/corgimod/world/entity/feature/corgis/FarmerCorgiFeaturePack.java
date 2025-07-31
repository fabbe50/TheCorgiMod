package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.entity.ai.abilities.FarmerDepositItemsGoal;
import com.fabbe50.corgimod.world.entity.ai.abilities.FarmerGoal;
import com.fabbe50.corgimod.world.entity.ai.abilities.FarmerPlantSeedGoal;
import com.fabbe50.corgimod.world.entity.ai.abilities.PickupItemsGoal;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FarmerCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public void registerGoals(Corgi entity, GoalSelector goalSelector) {
        super.registerGoals(entity, goalSelector);
        goalSelector.addGoal(10, new PickupItemsGoal<>(entity, 0.8, 16, 3, itemEntity -> itemEntity.getItem().is(ModTags.FARMER_CAN_PICKUP)));
        goalSelector.addGoal(11, new FarmerGoal<>(entity, 0.8, 16, 3));
        goalSelector.addGoal(11, new FarmerPlantSeedGoal<>(entity, 0.8, 16, 3));
        goalSelector.addGoal(12, new FarmerDepositItemsGoal(entity, 0.8, 16, 3));
    }

    @Override
    public void onTick(Corgi entity) {
        super.onTick(entity);
        if (entity.level() instanceof ServerLevel level && entity.isTame()) {
            if (!entity.isHungry()) {
                RandomSource random = entity.getRandom();
                if (random.nextInt(40) == 0) {
                    int bounds = ModConfig.<Integer>getValue("farmerCorgiGrowthBoostRadius").getValue();
                    BlockPos.betweenClosedStream(entity.getBoundingBox().inflate(bounds, 3, bounds)).filter(pos -> {
                        BlockState state = level.getBlockState(pos);
                        return state.getBlock() instanceof BushBlock || state.is(BlockTags.CROPS);
                    }).forEach(pos -> {
                        BlockState state = level.getBlockState(pos);
                        if (state.isRandomlyTicking() && random.nextInt(32) == 0) {
                            state.randomTick(level, pos, random);
                            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, random.nextDouble(), random.nextDouble(), random.nextDouble(), 0.3);
                        }
                    });
                }
            }
        }
    }

    @Override
    public HurtType onHurt(Corgi entity, DamageSource source, float f) {
        if (source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.CACTUS)) {
            return HurtType.NO_HURT;
        }
        return super.onHurt(entity, source, f);
    }

    @Override
    public void onTame(Corgi entity, GoalSelector goalSelector) {
        super.onTame(entity, goalSelector);
        entity.setCanPickUpLoot(true);
    }

    @Override
    public boolean hasInventory(Corgi corgi) {
        return corgi.getInventory() != null;
    }

    @Override
    public SimpleContainer getInventory(Corgi corgi) {
        if (corgi.getInventory() == null) {
            corgi.createInventory();
        }
        return corgi.getInventory();
    }

    private ChestMenu getMenu(int id, Corgi corgi, Player player) {
        return ChestMenu.threeRows(id, player.getInventory(), corgi.getInventory());
    }

    private MenuProvider buildMenu(int id, Corgi corgi, Player player) {
        return new SimpleMenuProvider((i, inventory, player1) -> getMenu(id, corgi, player1), corgi.getName());
    }

    @Override
    public void onItemPickup(Corgi entity, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.is(ModTags.FARMER_CAN_PICKUP)) {
            SimpleContainer inventory = this.getInventory(entity);
            if (inventory.canAddItem(stack)) {
                inventory.addItem(stack);
                inventory.setChanged();
                itemEntity.discard();
            }
        }
    }

    @Override
    public InteractionResult onInteractWith(Corgi entity, Player player, InteractionHand hand) {
        if (player.getMainHandItem().is(Items.CHEST)) {
            buildMenu(entity.getId(), entity, player).createMenu(entity.getId(), player.getInventory(), player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void addAdditionalSaveData(Corgi entity, CompoundTag tag) {
        super.addAdditionalSaveData(entity, tag);
        ContainerHelper.saveAllItems(tag, getInventory(entity).getItems(), entity.level().registryAccess());
    }

    @Override
    public void readAdditionalSaveData(Corgi entity, CompoundTag tag) {
        super.readAdditionalSaveData(entity, tag);

        getInventory(entity);
        if (hasInventory(entity)) {
            NonNullList<ItemStack> itemStacks = NonNullList.createWithCapacity(27);
            ContainerHelper.loadAllItems(tag, itemStacks, entity.level().registryAccess());
            for (int i = 0; i < itemStacks.size(); i++) {
                ItemStack stack = itemStacks.get(i);
                if (stack == null) {
                    stack = ItemStack.EMPTY;
                }
                getInventory(entity).setItem(i, stack);
            }
            if (entity.isTame()) {
                entity.setCanPickUpLoot(true);
            }
        }
    }
}
