package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTags {
    public static final TagKey<Item> CORGI_FOOD = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, "corgi_food"));
    public static final TagKey<Item> CORGI_TAMING_ITEMS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, "corgi_taming_items"));
}
