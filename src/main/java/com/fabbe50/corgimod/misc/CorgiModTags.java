package com.fabbe50.corgimod.misc;

import com.fabbe50.corgimod.CorgiMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CorgiModTags {
    public static final TagKey<Block> DOGGY_DOOR = BlockTags.create(new ResourceLocation(CorgiMod.MODID, "doggy_doors"));
}
