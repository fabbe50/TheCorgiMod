package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreativeTabRegistry {
    public static final Registrar<CreativeModeTab> TABS = TheCorgiMod.MANAGER.get().get(Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> THE_CORGI_MOD_TAB = TABS.register(TheCorgiMod.location("corgi_tab"), () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 4)
            .title(Component.translatable("item_group." + TheCorgiMod.MOD_ID + ".creative_tab"))
            .icon(() -> new ItemStack(Items.BONE))
            .displayItems((itemDisplayParameters, output) -> {
                for (RegistrySupplier<Item> item : ModRegistries.DOG_DOORS) {
                    output.accept(item.get());
                }
                for (RegistrySupplier<Item> item : ModRegistries.CORGI_BEDS) {
                    output.accept(item.get());
                }
                for (RegistrySupplier<Item> item : ModRegistries.PET_BOWLS) {
                    output.accept(item.get());
                }

                output.accept(ModRegistries.URANIUM.get());
                output.accept(ModRegistries.SUNGLASSES.get());
                output.accept(ModRegistries.CORGI_CARRY_TOOL.get());

                output.accept(ModRegistries.SPAWN_EGG_RANDOM_CORGI.get().getDefaultInstance());

                output.accept(ModRegistries.SPAWN_EGG_NORMAL_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_ANTI_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_BODYGUARD_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_BUSINESS_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_FABBE50_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_FARMER_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_HERO_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_LOVE_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_MELON_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_NERD_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_PIRATE_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_RADIOACTIVE_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_SPY_CORGI.get().getDefaultInstance());
                output.accept(ModRegistries.SPAWN_EGG_SUNGLASSES_CORGI.get().getDefaultInstance());

                output.accept(ModRegistries.SPAWN_EGG_BOGGED_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_CREEPER_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_DROWNED_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_ENDER_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_HUSK_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_SKELETON_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_SPIDER_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_STRAY_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_WITHER_SKELETON_CORGI.get());
                output.accept(ModRegistries.SPAWN_EGG_ZOMBIE_CORGI.get());
            })
            .build()
    );

    public static void init() {}
}
