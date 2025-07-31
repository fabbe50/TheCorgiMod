package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SunglassesCorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public InteractionResult onInteractWith(Corgi entity, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (!entity.level().isClientSide) {
            ServerLevel level = (ServerLevel) entity.level();
            if (entity.isTame()) {
                if (item.equals(Items.FLINT_AND_STEEL)){
                    if (!player.getAbilities().instabuild) {
                        itemStack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    }
                    level.setWeatherParameters(24000, 0, false, false);
                    return InteractionResult.SUCCESS;
                } else if (item.equals(Items.WATER_BUCKET)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                        player.addItem(new ItemStack(Items.BUCKET));
                    }
                    level.setWeatherParameters(0, 6000, true, false);
                    return InteractionResult.SUCCESS;
                } else if (item.equals(ModRegistries.URANIUM.get())) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setWeatherParameters(0, 6000, true, true);
                    return InteractionResult.SUCCESS;
                } else if (item.equals(Items.LAPIS_LAZULI)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setDayTime(13000);
                    return InteractionResult.SUCCESS;
                } else if (item.equals(Items.GLOWSTONE_DUST)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setDayTime(1000);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.onInteractWith(entity, player, hand);
    }

    @Override
    public boolean isItemOfInterest(Corgi entity, ItemStack stack) {
        if (entity.isTame() && (stack.is(Items.WATER_BUCKET) || stack.is(Items.FLINT_AND_STEEL) || stack.is(ModRegistries.URANIUM.get()) || stack.is(Items.LAPIS_LAZULI) || stack.is(Items.GLOWSTONE_DUST))) {
            return true;
        }
        return super.isItemOfInterest(entity, stack);
    }
}
