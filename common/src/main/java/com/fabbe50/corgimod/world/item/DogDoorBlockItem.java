package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.world.block.DogDoorBlock;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class DogDoorBlockItem extends BlockItem {
    public DogDoorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        BlockItemStateProperties blockStates = itemStack.get(DataComponents.BLOCK_STATE);
        if (blockStates != null) {
            WoodType woodType = blockStates.get(DogDoorBlock.WOOD_TYPE);
            if (woodType == null) {
                woodType = WoodType.OAK;
            }
            return Component.translatable(this.getDescriptionId(), Component.translatable(woodType.translationKey()));
        }
        return Component.translatable(this.getDescriptionId(), Component.translatable("text.undefined"), Component.translatable("text.undefined"));
    }
}
