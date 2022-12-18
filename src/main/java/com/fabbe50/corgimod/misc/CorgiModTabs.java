package com.fabbe50.corgimod.misc;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class CorgiModTabs {
    @SubscribeEvent
    public void registerCreativeTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(CorgiMod.MODID, "spawneggs"), builder ->
            builder.title(Component.translatable("item_group." + CorgiMod.MODID + ".spawneggs"))
                    .icon(() -> new ItemStack(Items.BONE))
                    .displayItems((enabledFlags, populator, hasPermissions) -> {
                        populator.accept(ItemRegistry.SPAWN_EGG_NORMAL_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_ANTI_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_BODYGUARD_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_BUSINESS_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_CREEPER_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_HERO_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_LOVE_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_MELON_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_PIRATE_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_RADIOACTIVE_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_SKELETON_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_SUNGLASSES_CORGI.get());
                        populator.accept(ItemRegistry.SPAWN_EGG_ZOMBIE_CORGI.get());

                        if (CorgiMod.config.general.enableWorkInProgressFeatures) {
                            populator.accept(ItemRegistry.SPAWN_EGG_FABBE50_CORGI.get());
                            populator.accept(ItemRegistry.SPAWN_EGG_FARMER_CORGI.get());
                            populator.accept(ItemRegistry.SPAWN_EGG_NERD_CORGI.get());
                            populator.accept(ItemRegistry.SPAWN_EGG_SPY_CORGI.get());
                        }
                    })
        );
    }

    @SubscribeEvent
    public void addToCreativeTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemRegistry.URANIUM.get());
        }
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ItemRegistry.SUNGLASSES.get());
        }
    }
}
