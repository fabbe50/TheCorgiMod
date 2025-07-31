package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.world.entity.ai.abilities.StealMelonGoal;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MelonCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public void registerGoals(Corgi entity, GoalSelector goalSelector) {
        super.registerGoals(entity, goalSelector);
        goalSelector.addGoal(10, new StealMelonGoal<>(entity, 0.8, 12, 3));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.MELON) || stack.is(Items.MELON_SLICE) || stack.is(Items.GLISTERING_MELON_SLICE) || super.isFood(stack);
    }

    @Override
    public boolean isTamingItem(ItemStack stack) {
        return stack.is(Items.MELON) || stack.is(Items.MELON_SLICE) || stack.is(Items.GLISTERING_MELON_SLICE) || super.isTamingItem(stack);
    }
}