package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CorgiSoulItem extends CorgiHolderItem {
    public CorgiSoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public Corgi getCorgi(Level level, ItemStack stack) {
        Corgi corgi = super.getCorgi(level, stack);
        if (corgi != null) {
            corgi.setHealth(corgi.getMaxHealth());
            corgi.setOrderedToSit(false);
            corgi.setAskedToStay(false);
        }
        return corgi;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return InteractionResult.CONSUME;
    }
}
