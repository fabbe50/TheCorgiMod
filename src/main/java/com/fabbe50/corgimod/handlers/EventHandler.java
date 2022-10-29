package com.fabbe50.corgimod.handlers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    static List<Item> wip = new ArrayList<>();
    static List<Item> broken = new ArrayList<>();

    @SubscribeEvent
    public static void toolTipEvent(ItemTooltipEvent event) {
        if (wip.contains(event.getItemStack().getItem()) || broken.contains(event.getItemStack().getItem())) {
            event.getToolTip().set(0, event.getToolTip().get(0).copy().append(" [WIP]"));
        }
        if (broken.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(Component.literal("This feature may be broken cause of early stages in development. Please do not use or report issues about this."));
        }
    }
}
