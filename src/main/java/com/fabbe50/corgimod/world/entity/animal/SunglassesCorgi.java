package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SunglassesCorgi extends Corgi {
    public SunglassesCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (!this.level.isClientSide) {
            ServerLevel level = (ServerLevel) this.level;
            if (this.isTame()) {
                if (item.equals(Items.FLINT_AND_STEEL)){
                    if (!player.getAbilities().instabuild) {
                        itemStack.hurtAndBreak(1, player, (player1) -> {
                            player1.broadcastBreakEvent(hand);
                        });
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
                } else if (item.equals(ItemRegistry.URANIUM.get())) {
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
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isItemOfInterest(ItemStack stack) {
        if (this.isTame() && (stack.is(Items.WATER_BUCKET) || stack.is(Items.FLINT_AND_STEEL) || stack.is(ItemRegistry.URANIUM.get()) || stack.is(Items.LAPIS_LAZULI) || stack.is(Items.GLOWSTONE_DUST))) {
            return true;
        }

        return super.isItemOfInterest(stack);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
            return Component.literal(Corgis.SUNGLASSES.getFormattedName());
        return super.getDisplayName();
    }
}
