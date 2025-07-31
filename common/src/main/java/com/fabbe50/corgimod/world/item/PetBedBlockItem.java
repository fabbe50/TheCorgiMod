package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PetBedBlockItem extends BlockItem {
    public PetBedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        BlockItemStateProperties blockStates = itemStack.get(DataComponents.BLOCK_STATE);
        if (blockStates != null) {
            WoodType woodType = blockStates.get(PetBedBlock.WOOD_TYPE);
            if (woodType == null) {
                woodType = WoodType.OAK;
            }
            Color wool_color = blockStates.get(PetBedBlock.WOOL_COLOR);
            if (wool_color == null) {
                wool_color = Color.WHITE;
            }
            return Component.translatable(this.getDescriptionId(), Component.translatable(woodType.translationKey()), Component.translatable(wool_color.translationKey()));
        }
        return Component.translatable(this.getDescriptionId(), Component.translatable("text.undefined"), Component.translatable("text.undefined"));
    }
}
