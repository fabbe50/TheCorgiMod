package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.misc.CorgiSpawnEggGroup;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CorgiMod.MODID);

    public static final RegistryObject<Item> URANIUM = DEFERRED_REGISTER.register("uranium", () -> new ItemUranium(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));


    static {
        initSpawnEggs();
    }

    private static void initSpawnEggs() {
        DEFERRED_REGISTER.register("spawn_egg_corgi_normal", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_NORMAL, 0xE0BC8D, 0x89644E, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_anti", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_ANTI, 0x1F4372, 0x769BB1, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_bodyguard", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_BODYGUARD, 0x171717, 0xd4d4d4, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_creeper", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_CREEPER, 0x57af49, 0x138c10, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_love", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_LOVE, 0xEF8FDC, 0xAB4D9D, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_radioactive", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_RADIOACTIVE, 0x7ef927, 0x50a800, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));

        if (CorgiMod.config.general.enableWorkInProgressFeatures) {
            DEFERRED_REGISTER.register("spawn_egg_corgi_fabbe50", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_FABBE50, 0xd4d6e8, 0xffffcc, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_hero", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_HERO, 0xE0BC8D, 0xff0000, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_business", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_BUSINESS, 0x171717, 0xe6e6e6, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_melon", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_MELON, 0x55681a, 0xaeb541, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_nerd", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_NERD, 0xffffff, 0x000000, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_pirate", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_PIRATE, 0xddb471, 0x41392c, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_skeleton", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_SKELETON, 0xbebebe, 0x505050, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_spy", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_SPY, 0x000000, 0x000000, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_sunglasses", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_SUNGLASSES, 0xddb471, 0x000000, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
            DEFERRED_REGISTER.register("spawn_egg_corgi_zombie", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_ZOMBIE, 0x45673e, 0x224119, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        }
    }
}
