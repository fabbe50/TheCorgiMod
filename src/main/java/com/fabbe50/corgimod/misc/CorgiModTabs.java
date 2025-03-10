package com.fabbe50.corgimod.misc;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CorgiModTabs {
    public static final DeferredRegister<CreativeModeTab> DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CorgiMod.MODID);

    public static final RegistryObject<CreativeModeTab> CORGI_TAB = DEFERRED_REGISTER.register("creative_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + CorgiMod.MODID + ".creative_tab"))
            .icon(() -> new ItemStack(Items.BONE))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ItemRegistry.OAK_DOG_DOOR.get());
                output.accept(ItemRegistry.SPRUCE_DOG_DOOR.get());
                output.accept(ItemRegistry.BIRCH_DOG_DOOR.get());
                output.accept(ItemRegistry.JUNGLE_DOG_DOOR.get());
                output.accept(ItemRegistry.ACACIA_DOG_DOOR.get());
                output.accept(ItemRegistry.DARK_OAK_DOG_DOOR.get());
                output.accept(ItemRegistry.MANGROVE_DOG_DOOR.get());
                output.accept(ItemRegistry.CRIMSON_DOG_DOOR.get());
                output.accept(ItemRegistry.WARPED_DOG_DOOR.get());

                output.accept(ItemRegistry.URANIUM.get());
                output.accept(ItemRegistry.SUNGLASSES.get());

                output.accept(ItemRegistry.SPAWN_EGG_RANDOM_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_NORMAL_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_ANTI_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_BODYGUARD_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_BUSINESS_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_CREEPER_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_HERO_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_LOVE_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_MELON_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_PIRATE_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_RADIOACTIVE_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_SKELETON_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_SPY_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_SUNGLASSES_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_ZOMBIE_CORGI.get());
                output.accept(ItemRegistry.SPAWN_EGG_ENDER_CORGI.get());

                if (CorgiMod.config.general.enableWorkInProgressFeatures) {
                    output.accept(ItemRegistry.SPAWN_EGG_FABBE50_CORGI.get());
                    output.accept(ItemRegistry.SPAWN_EGG_FARMER_CORGI.get());
                    output.accept(ItemRegistry.SPAWN_EGG_NERD_CORGI.get());
                }
            })
            .build()
    );
}
