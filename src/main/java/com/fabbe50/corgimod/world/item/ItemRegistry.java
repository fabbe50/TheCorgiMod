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
        DEFERRED_REGISTER.register("spawn_egg_corgi_bodyguard", () -> new ForgeSpawnEggItem(EntityRegistry.CORGI_BODYGUARD, 0xE0BC8D, 0x000000, new Item.Properties().tab(CorgiSpawnEggGroup.INSTANCE)));
    }
}
