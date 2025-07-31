package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTags extends BiomeTagsProvider {
    public BiomeTags(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.CORGI_SPAWN_BIOMES)
                .addTag(net.minecraft.tags.BiomeTags.IS_FOREST)
                .addTag(net.minecraft.tags.BiomeTags.IS_SAVANNA)
                .addTag(net.minecraft.tags.BiomeTags.IS_HILL)
                .addTag(net.minecraft.tags.BiomeTags.IS_TAIGA)
                .add(Biomes.PLAINS);
    }
}
