package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.utils.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class ItemUranium extends Item {
    public ItemUranium(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return Utils.getTickTimeForSmeltingItem(12);
    }
}
