package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
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
                        itemStack.shrink(1);
                    }
                    level.setWeatherParameters(24000, 0, false, false);
                } else if (item.equals(Items.WATER_BUCKET)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setWeatherParameters(0, 6000, true, false);
                } else if (item.equals(ItemRegistry.URANIUM.get())) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setWeatherParameters(0, 6000, true, true);
                } else if (item.equals(Items.LAPIS_LAZULI)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setDayTime(13000);
                } else if (item.equals(Items.GLOWSTONE_DUST)) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    level.setDayTime(1000);
                }
            }
        }
        return super.mobInteract(player, hand);
    }
}
