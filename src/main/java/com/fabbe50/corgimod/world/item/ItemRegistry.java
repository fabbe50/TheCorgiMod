package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.misc.CorgiSpawnEggGroup;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CorgiMod.MODID);

    static {
        initSpawnEggs();
    }

    public static void initSpawnEggs() {
        DEFERRED_REGISTER.register("spawn_egg_corgi_normal", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_NORMAL, 0xE0BC8D, 0x89644E, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_anti", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_ANTI, 0x1F4372, 0x769BB1, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_bodyguard", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_BODYGUARD, 0x171717, 0xd4d4d4, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_creeper", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_CREEPER, 0x57af49, 0x138c10, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_love", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_LOVE, 0xEF8FDC, 0xAB4D9D, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        DEFERRED_REGISTER.register("spawn_egg_corgi_zombie", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_ZOMBIE, 0x45673e, 0x224119, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));


        if (CorgiMod.config.general.enableWorkInProgressFeatures) {
            DEFERRED_REGISTER.register("spawn_egg_corgi_business", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_BUSINESS, 0x171717, 0xe6e6e6, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
        }
    }
}
