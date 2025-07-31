package com.fabbe50.corgimod.world.block.entity;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.inventory.PetBowlMenu;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PetBowlBlockEntity extends RandomizableContainerBlockEntity {
    public static final int CONTAINER_SIZE = 1;
    private NonNullList<ItemStack> items;
    private boolean hasFood = false;

    public PetBowlBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    }

    public PetBowlBlockEntity(BlockPos pos, BlockState state) {
        this(ModRegistries.PET_BOWL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.pet_bowl");
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items, provider);
            this.updateBlockState();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items, provider);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.updateBlockState();
    }

    private void updateBlockState() {
        this.hasFood = !this.isEmpty();
        Level level = getLevel();
        if (level != null) {
            BlockPos pos = getBlockPos();
            BlockState state = level.getBlockState(pos);
            if (state.is(ModRegistries.PET_BOWL.get())) {
                state = state.setValue(PetBowlBlock.FOOD_IN_BOWL, this.hasFood);
                if (state.is(ModRegistries.PET_BOWL)) {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    level.removeBlockEntity(pos);
                    level.setBlockAndUpdate(pos, state);
                    level.setBlockEntity(blockEntity);
                }
            }
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new PetBowlMenu(i, inventory, this);
    }
}
