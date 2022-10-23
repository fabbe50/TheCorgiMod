package com.fabbe50.corgimod.misc;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CorgiSpawnEggGroup extends CreativeModeTab {
    public static final CorgiSpawnEggGroup INSTANCE = new CorgiSpawnEggGroup();

    public CorgiSpawnEggGroup() {
        super(CorgiMod.MODID + "_spawneggs");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.BONE);
    }
}
