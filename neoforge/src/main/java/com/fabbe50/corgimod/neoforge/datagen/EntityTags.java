package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.EntityRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EntityTags extends EntityTypeTagsProvider {
    public EntityTags(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(EntityTypeTags.ARTHROPOD)
                .add(EntityRegistry.SPIDER_CORGI.get());

        tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(EntityRegistry.STRAY_CORGI.get());

        tag(EntityTypeTags.SKELETONS)
                .add(EntityRegistry.BOGGED_CORGI.get())
                .add(EntityRegistry.SKELETON_CORGI.get())
                .add(EntityRegistry.STRAY_CORGI.get())
                .add(EntityRegistry.WITHER_SKELETON_CORGI.get());

        tag(EntityTypeTags.ZOMBIES)
                .add(EntityRegistry.DROWNED_CORGI.get())
                .add(EntityRegistry.HUSK_CORGI.get())
                .add(EntityRegistry.ZOMBIE_CORGI.get());
    }
}
