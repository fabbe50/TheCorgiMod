package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.block.util.ContainerUtils;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class FarmerDepositItemsGoal extends DepositItemsGoal<Corgi> {
    public FarmerDepositItemsGoal(Corgi entity, double d, int i, int j) {
        super(entity, d, i, j);
    }

    @Override
    public void depositItems(Container inventory, Container container) {
        Set<Item> seenItems = new HashSet<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(ModTags.FARMER_SEEDS)) {
                if (!seenItems.contains(stack.getItem())) {
                    seenItems.add(stack.getItem());
                    continue;
                }
            }
            if (stack != null && !stack.isEmpty()) {
                int rest = ContainerUtils.insertItem(container, stack);
                stack.setCount(rest);
                inventory.setItem(i, stack);
            }
        }
    }
}
