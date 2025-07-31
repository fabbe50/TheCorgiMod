package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.Platform;
import com.fabbe50.corgimod.registries.ModRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CorgiGuideBookItem extends Item {
    public CorgiGuideBookItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (player instanceof ServerPlayer serverPlayer && Platform.isModLoaded("patchouli")) {
            System.out.println("Attempting to open guide book.");
            Platform.openPatchouliBook(serverPlayer, ModRegistries.ITEMS.getId(this));
        } else {
            return super.use(level, player, interactionHand);
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        if (Platform.isModLoaded("patchouli")) {
            list.add(Component.translatable("patchouli.gui.lexicon.edition_str", "1st").withStyle(ChatFormatting.GRAY));
        } else {
            list.add(Component.translatable("thecorgimod.patchouli.install").withStyle(ChatFormatting.GRAY));
        }
    }
}
