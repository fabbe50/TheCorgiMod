package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.registries.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE)
                .add(ModRegistries.CORGI_BED.get())
                .add(ModRegistries.DOG_DOOR.get());

        tag(ModTags.CORGI_BED_BLOCKS).add(ModRegistries.CORGI_BED.get());
        tag(ModTags.CORGI_DOOR_BLOCKS).add(ModRegistries.DOG_DOOR.get());
        tag(ModTags.CORGI_BOWL_BLOCKS).add(ModRegistries.PET_BOWL.get());

        tag(ModTags.CORGIS_SPAWNABLE_ON)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.SNOW)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.PODZOL);

        tag(ModTags.FARMER_BLOCKS_TO_BREAK)
                .add(Blocks.PUMPKIN)
                .add(Blocks.MELON)
                .add(Blocks.SUGAR_CANE)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(ModTags.FARMER_LEAVE_BOTTOM_BLOCK)
                .add(Blocks.SUGAR_CANE)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(ModTags.FARMER_FARMLAND)
                .add(Blocks.FARMLAND);
    }
}
