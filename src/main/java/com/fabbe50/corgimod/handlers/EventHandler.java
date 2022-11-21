package com.fabbe50.corgimod.handlers;

import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    static List<Item> wip = new ArrayList<>();
    static List<Item> broken = new ArrayList<>();

    @SubscribeEvent
    public void toolTipEvent(ItemTooltipEvent event) {
        if (wip.contains(event.getItemStack().getItem()) || broken.contains(event.getItemStack().getItem())) {
            event.getToolTip().set(0, event.getToolTip().get(0).copy().append(" [WIP]"));
        }
        if (broken.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(Component.literal("This feature may be broken cause of early stages in development. Please do not use or report issues about this."));
        }
        if (event.getItemStack().is(ItemRegistry.SUNGLASSES.get())) {
            List<Component> tooltips = event.getToolTip();
            int i = 0;
            for (Component tooltip : tooltips) {
                if (tooltip.getString().equalsIgnoreCase("When on Head:")) {
                    event.getToolTip().add(i+1, Component.literal("+9001 Swag").withStyle(ChatFormatting.BLUE));
                    event.getToolTip().add(i+2, Component.literal("Safe Enderman Gaze").withStyle(ChatFormatting.BLUE));
                    break;
                }
                i++;
            }
        }
    }

    public static void addWip(Item item) {
        wip.add(item);
    }

    public static void addBroken(Item item) {
        wip.add(item);
        broken.add(item);
    }
}
