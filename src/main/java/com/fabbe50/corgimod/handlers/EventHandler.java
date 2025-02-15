package com.fabbe50.corgimod.handlers;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.ExplosionEvent;
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

    @SubscribeEvent
    public void onExplode(ExplosionEvent event) {
        if (CorgiMod.config.general.allowUraniumTNTBoosting) {
            Level level = event.getLevel();
            Entity source = event.getExplosion().getExploder();
            if (source instanceof PrimedTnt tnt) {
                List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(tnt.blockPosition().offset(-1, -1, -1), tnt.blockPosition().offset(1, 1, 1)));
                for (ItemEntity item : items) {
                    if (item.getItem().is(ItemRegistry.URANIUM.get())) {
                        level.explode(null, tnt.getX(), tnt.getY(0.0625F), tnt.getZ(), 30, Level.ExplosionInteraction.TNT);
                        break;
                    }
                }
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
