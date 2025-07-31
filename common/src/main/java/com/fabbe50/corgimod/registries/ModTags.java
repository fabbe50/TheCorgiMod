package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Mod Tags
    // - Block Tags
    public static final TagKey<Block> CORGIS_SPAWNABLE_ON = createBlockModTag("corgis_spawnable_on");
    public static final TagKey<Block> CORGI_BED_BLOCKS = createBlockModTag("corgi_beds");
    public static final TagKey<Block> CORGI_DOOR_BLOCKS = createBlockModTag("corgi_doors");
    public static final TagKey<Block> CORGI_BOWL_BLOCKS = createBlockModTag("corgi_bowls");
    public static final TagKey<Block> FARMER_BLOCKS_TO_BREAK = createBlockModTag("farmer_blocks_to_break");
    public static final TagKey<Block> FARMER_LEAVE_BOTTOM_BLOCK = createBlockModTag("farmer_leave_bottom_block");
    // - Item Tags
    public static final TagKey<Item> CORGI_FOOD = createItemModTag("corgi_food");
    public static final TagKey<Item> CORGI_TAMING_ITEMS = createItemModTag("corgi_taming_items");
    public static final TagKey<Item> ENDERMAN_SAFE_ITEMS = createItemModTag("enderman_safe_items");
    public static final TagKey<Item> CORGI_BED_ITEMS = createItemModTag("corgi_beds");
    public static final TagKey<Item> CORGI_DOOR_ITEMS = createItemModTag("corgi_doors");
    public static final TagKey<Item> CORGI_BOWL_ITEMS = createItemModTag("corgi_bowls");
    public static final TagKey<Item> FARMER_CAN_PICKUP = createItemModTag("farmer_can_pickup");
    public static final TagKey<Item> FARMER_SEEDS_BLACKLIST = createItemModTag("farmer_seeds_blacklist");
    // - Biome Tags
    public static final TagKey<Biome> CORGI_SPAWN_BIOMES = createBiomeModTag("corgi_spawn_biomes");


    // C Tags
    public static final TagKey<Item> GLASS = createItemCTag("glass_blocks");
    public static final TagKey<Item> FARMER_SEEDS = createItemCTag("seeds");
    public static final TagKey<Item> SPAWN_EGGS = createItemCTag("spawn_eggs");
    public static final TagKey<Block> FARMER_FARMLAND = createBlockCTag("farmland");


    // Creation Methods
    private static TagKey<Biome> createBiomeModTag(String name) {
        return TagKey.create(Registries.BIOME, TheCorgiMod.location(name));
    }

    private static TagKey<Item> createItemModTag(String name) {
        return TagKey.create(Registries.ITEM, TheCorgiMod.location(name));
    }

    private static TagKey<Item> createItemCTag(String name) {
        return TagKey.create(Registries.ITEM, TheCorgiMod.location("c", name));
    }

    private static TagKey<Block> createBlockModTag(String name) {
        return TagKey.create(Registries.BLOCK, TheCorgiMod.location(name));
    }

    private static TagKey<Block> createBlockCTag(String name) {
        return TagKey.create(Registries.BLOCK, TheCorgiMod.location("c", name));
    }
}
