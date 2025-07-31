package com.fabbe50.corgimod.neoforge.integration.curios;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class SunglassesCurioItem extends CurioItem {
    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity != null) {
            if (livingEntity.hasEffect(MobEffects.BLINDNESS)) {
                livingEntity.removeEffect(MobEffects.BLINDNESS);
            }
        }
    }
}
