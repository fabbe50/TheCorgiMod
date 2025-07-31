package com.fabbe50.corgimod.world.block.util;

import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ContainerUtils {
    public static int insertItem(Container container, ItemStack stack) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.canPlaceItem(i, stack)) {
                ItemStack slotStack = container.getItem(i);
                if (ItemStack.isSameItem(slotStack, stack)) {
                    int addedStacks = slotStack.getCount() + stack.getCount();
                    int rest = addedStacks - 64;
                    if (rest > 0) {
                        slotStack.setCount(64);
                        container.setItem(i, slotStack);
                        stack.setCount(rest);
                    } else {
                        slotStack.setCount(addedStacks);
                        container.setItem(i, slotStack);
                        stack.setCount(0);
                        return 0;
                    }
                } else if (slotStack.isEmpty()) {
                    container.setItem(i, stack.copy());
                    stack.setCount(0);
                    return 0;
                }
            }
        }
        // Return the extra count if it doesn't fit in the receiving container.
        return stack.getCount();
    }

    public static ItemStack getRandomItemStack(Container container, TagKey<Item> filter, TagKey<Item> blacklist) {
        RandomSource random = RandomSource.create();
        ItemStack stack = ItemStack.EMPTY;
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack containerStack = container.getItem(i);
            if (containerStack == null || !containerStack.is(filter) || containerStack.is(blacklist)) {
                containerStack = ItemStack.EMPTY;
            }
            if (!containerStack.isEmpty()) {
                itemStacks.add(containerStack);
            }
        }
        if (!itemStacks.isEmpty()) {
            stack = itemStacks.get(random.nextInt(0, itemStacks.size()));
        }
        return stack;
    }
}
