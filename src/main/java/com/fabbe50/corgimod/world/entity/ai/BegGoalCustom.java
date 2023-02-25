package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BegGoalCustom extends BegGoal {
    Corgi corgi;

    public BegGoalCustom(Corgi corgi, float lookDistance) {
        super(corgi, lookDistance);
        this.corgi = corgi;
    }

    @Override
    public boolean playerHoldingInteresting(@NotNull Player player) {
        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            return corgi.isItemOfInterest(stack);
        }
        return false;
    }
}
