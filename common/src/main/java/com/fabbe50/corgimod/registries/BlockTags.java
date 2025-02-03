package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTags {
    public static final TagKey<Block> CORGIS_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TheCorgiMod.MOD_ID, "corgis_spawnable_on"));
}
