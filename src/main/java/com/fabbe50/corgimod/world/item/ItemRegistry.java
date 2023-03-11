package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Armors;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CorgiMod.MODID);

    //Items
    public static final RegistryObject<Item> URANIUM = DEFERRED_REGISTER.register("uranium", () -> new ItemUranium(new Item.Properties()));
    public static final RegistryObject<Item> SUNGLASSES = DEFERRED_REGISTER.register("sunglasses", () -> new ItemSunglasses(Armors.SUNGLASSES, EquipmentSlot.HEAD, new Item.Properties()));


    //Spawn Eggs
    public static final RegistryObject<Item> SPAWN_EGG_NORMAL_CORGI = registerSpawnEgg("spawn_egg_corgi_normal", EntityRegistry.CORGI_NORMAL, 0xE0BC8D, 0x89644E, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_ANTI_CORGI = registerSpawnEgg("spawn_egg_corgi_anti", EntityRegistry.CORGI_ANTI, 0x1F4372, 0x769BB1, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_BODYGUARD_CORGI = registerSpawnEgg("spawn_egg_corgi_bodyguard", EntityRegistry.CORGI_BODYGUARD, 0x171717, 0xd4d4d4, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_BUSINESS_CORGI = registerSpawnEgg("spawn_egg_corgi_business", EntityRegistry.CORGI_BUSINESS, 0x171717, 0xe6e6e6, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_CREEPER_CORGI = registerSpawnEgg("spawn_egg_corgi_creeper", EntityRegistry.CORGI_CREEPER, 0x57af49, 0x138c10, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_HERO_CORGI = registerSpawnEgg("spawn_egg_corgi_hero", EntityRegistry.CORGI_HERO, 0xE0BC8D, 0xff0000, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_LOVE_CORGI = registerSpawnEgg("spawn_egg_corgi_love", EntityRegistry.CORGI_LOVE, 0xEF8FDC, 0xAB4D9D, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_MELON_CORGI = registerSpawnEgg("spawn_egg_corgi_melon", EntityRegistry.CORGI_MELON, 0x55681a, 0xaeb541, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_PIRATE_CORGI = registerSpawnEgg("spawn_egg_corgi_pirate", EntityRegistry.CORGI_PIRATE, 0xddb471, 0x41392c, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_RADIOACTIVE_CORGI = registerSpawnEgg("spawn_egg_corgi_radioactive", EntityRegistry.CORGI_RADIOACTIVE, 0x7ef927, 0x50a800, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_SKELETON_CORGI = registerSpawnEgg("spawn_egg_corgi_skeleton", EntityRegistry.CORGI_SKELETON, 0xbebebe, 0x505050, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_SUNGLASSES_CORGI = registerSpawnEgg("spawn_egg_corgi_sunglasses", EntityRegistry.CORGI_SUNGLASSES, 0xddb471, 0x000000, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_ZOMBIE_CORGI = registerSpawnEgg("spawn_egg_corgi_zombie", EntityRegistry.CORGI_ZOMBIE, 0x45673e, 0x224119, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_FABBE50_CORGI = registerSpawnEgg("spawn_egg_corgi_fabbe50", EntityRegistry.CORGI_FABBE50, 0xd4d6e8, 0xffffcc, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_FARMER_CORGI = registerSpawnEgg("spawn_egg_corgi_farmer", EntityRegistry.CORGI_FARMER, 0xE0BC8D, 0x89644E, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_NERD_CORGI = registerSpawnEgg("spawn_egg_corgi_nerd", EntityRegistry.CORGI_NERD, 0xffffff, 0x000000, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_SPY_CORGI = registerSpawnEgg("spawn_egg_corgi_spy", EntityRegistry.CORGI_SPY, 0x000000, 0x000000, new Item.Properties());
    public static final RegistryObject<Item> SPAWN_EGG_RANDOM_CORGI = DEFERRED_REGISTER.register("spawn_egg_corgi_random", () -> new ItemRandomSpawnEgg(EntityRegistry.CORGI_NORMAL, Corgis.getNonHostileCorgiTypeRegistryObjects(), new Item.Properties()));

    private static RegistryObject<Item> registerSpawnEgg(String registryName, RegistryObject<? extends EntityType<? extends Mob>> entityObject, int backgroundColor, int highlightColor, Item.Properties properties) {
        return DEFERRED_REGISTER.register(registryName, () -> new ForgeSpawnEggItem(entityObject, backgroundColor, highlightColor, properties));
    }
}
