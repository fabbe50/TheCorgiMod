package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.registries.ModTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        IntrinsicTagAppender<Item> corgiDoorItems = tag(ModTags.CORGI_DOOR_ITEMS);
        for (RegistrySupplier<Item> item : ModRegistries.DOG_DOORS) {
            corgiDoorItems.add(item.get());
        }
        IntrinsicTagAppender<Item> corgiBedItems = tag(ModTags.CORGI_BED_ITEMS);
        for (RegistrySupplier<Item> item : ModRegistries.CORGI_BEDS) {
            corgiBedItems.add(item.get());
        }
        IntrinsicTagAppender<Item> spawnEggs = tag(ModTags.SPAWN_EGGS);
        for (RegistrySupplier<Item> item : ModRegistries.SPAWN_EGGS) {
            spawnEggs.add(item.get());
        }
        IntrinsicTagAppender<Item> corgiBowlItems = tag(ModTags.CORGI_BOWL_ITEMS);
        for (RegistrySupplier<Item> item : ModRegistries.PET_BOWLS) {
            corgiBowlItems.add(item.get());
        }

        tag(ModTags.CORGI_FOOD)
                .addTag(net.minecraft.tags.ItemTags.MEAT);

        tag(ModTags.CORGI_TAMING_ITEMS)
                .add(Items.BONE);

        tag(ModTags.ENDERMAN_SAFE_ITEMS)
                .add(ModRegistries.SUNGLASSES.get())
                .add(Items.CARVED_PUMPKIN);

        tag(ModTags.FARMER_CAN_PICKUP)
                .add(Items.WHEAT_SEEDS)
                .add(Items.WHEAT)
                .add(Items.BEETROOT)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.NETHER_WART)
                .add(Items.POTATO)
                .add(Items.POISONOUS_POTATO)
                .add(Items.CARROT)
                .add(Items.SWEET_BERRIES)
                .add(Items.COCOA_BEANS)
                .add(Items.MELON_SLICE)
                .add(Items.PUMPKIN)
                .add(Items.SUGAR_CANE)
                .add(Items.CACTUS)
                .add(Items.BAMBOO);

        tag(ModTags.FARMER_SEEDS)
                .add(Items.WHEAT_SEEDS)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.CARROT)
                .add(Items.POTATO)
                .add(Items.NETHER_WART);

        tag(net.minecraft.tags.ItemTags.HEAD_ARMOR)
                .add(ModRegistries.SUNGLASSES.get());
    }
}
