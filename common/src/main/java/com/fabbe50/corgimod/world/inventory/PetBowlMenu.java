package com.fabbe50.corgimod.world.inventory;

import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.registries.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PetBowlMenu extends AbstractContainerMenu {
    private static final int SLOT_COUNT = 1;
    private final Container container;

    public PetBowlMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(SLOT_COUNT));
    }

    public PetBowlMenu(int i, Inventory inventory, Container container) {
        super(RendererRegistry.PET_BOWL_MENU_SUPPLIER.get(), i);
        checkContainerSize(container, SLOT_COUNT);
        this.container = container;
        container.startOpen(inventory.player);
        this.addSlot(new FoodSlot(container, 0, 80, 35));

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(int j = 0; j < 9; j++) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();
            if (i == 0) {
                if (!this.moveItemStackTo(stack, 1, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (FoodSlot.mayPlaceItem(itemStack)) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stack);
        }
        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    static class FoodSlot extends Slot {
        public FoodSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return mayPlaceItem(itemStack);
        }

        public static boolean mayPlaceItem(ItemStack itemStack) {
            return itemStack.is(ModTags.CORGI_FOOD);
        }
    }
}
