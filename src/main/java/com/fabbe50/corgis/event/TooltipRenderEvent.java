package com.fabbe50.corgis.event;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class TooltipRenderEvent {
    private static final List<ResourceLocation> mobsMarkedForBeta = new ArrayList<>();

    @SubscribeEvent
    public void tooltipRender(ItemTooltipEvent event) {
        if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof SpawnEggItem) {
            EntityType<?> type = ((SpawnEggItem)event.getItemStack().getItem()).getType(event.getItemStack().getTag());
            if (type.getRegistryName() != null) {
                mobsMarkedForBeta.forEach(loc -> {
                    boolean flag = loc.toString().equals(type.getRegistryName().toString());
                    if (flag) {
                        event.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + "WIP, may not work as intended."));
                    }
                });
            }
        }
    }

    public static void addMob(ResourceLocation resourceLocation) {
        mobsMarkedForBeta.add(resourceLocation);
    }
}
