package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PetBowlBlockItem extends BlockItem {
    public PetBowlBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        BlockItemStateProperties blockStates = itemStack.get(DataComponents.BLOCK_STATE);
        if (blockStates != null) {
            Color color = blockStates.get(PetBowlBlock.COLOR);
            if (color == null) {
                color = Color.RED;
            }
            return Component.translatable(this.getDescriptionId(), Component.translatable(color.translationKey()));
        }
        return Component.translatable(this.getDescriptionId(), Component.translatable("text.undefined"));
    }
}
