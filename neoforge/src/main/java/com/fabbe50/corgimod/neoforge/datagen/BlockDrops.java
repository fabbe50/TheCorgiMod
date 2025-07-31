package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.DogDoorBlock;
import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BlockDrops extends VanillaBlockLoot {
    public BlockDrops(HolderLookup.Provider arg) {
        super(arg);
    }

    @Override
    protected void generate() {
        add(ModRegistries.DOG_DOOR.get(), (arg) -> {
            LootTable.Builder lootTableBuilder = LootTable.lootTable();
            for (RegistrySupplier<Item> itemSupplier : ModRegistries.DOG_DOORS) {
                Item item = itemSupplier.get();
                BlockItemStateProperties blockItemStateProperties = item.components().get(DataComponents.BLOCK_STATE);
                if (blockItemStateProperties != null) {
                    WoodType woodType = blockItemStateProperties.get(DogDoorBlock.WOOD_TYPE);
                    if (woodType != null) {
                        lootTableBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(this.applyExplosionDecay(arg, LootItem.lootTableItem(item)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(arg).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DogDoorBlock.WOOD_TYPE, woodType)))));
                    }
                }
            }
            return lootTableBuilder;
        });
        add(ModRegistries.CORGI_BED.get(), (arg) -> {
            LootTable.Builder lootTableBuilder = LootTable.lootTable();
            for (RegistrySupplier<Item> itemSupplier : ModRegistries.CORGI_BEDS) {
                Item item = itemSupplier.get();
                BlockItemStateProperties blockItemStateProperties = item.components().get(DataComponents.BLOCK_STATE);
                if (blockItemStateProperties != null) {
                    WoodType woodType = blockItemStateProperties.get(PetBedBlock.WOOD_TYPE);
                    Color color = blockItemStateProperties.get(PetBedBlock.WOOL_COLOR);
                    if (woodType != null && color != null) {
                        lootTableBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(this.applyExplosionDecay(arg, LootItem.lootTableItem(item)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(arg).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PetBedBlock.WOOD_TYPE, woodType).hasProperty(PetBedBlock.WOOL_COLOR, color)))));
                    }
                }
            }
            return lootTableBuilder;
        });
        add(ModRegistries.PET_BOWL.get(), (arg) -> {
            LootTable.Builder lootTableBuilder = LootTable.lootTable();
            for (RegistrySupplier<Item> itemSupplier : ModRegistries.PET_BOWLS) {
                Item item = itemSupplier.get();
                BlockItemStateProperties blockItemStateProperties = item.components().get(DataComponents.BLOCK_STATE);
                if (blockItemStateProperties != null) {
                    Color color = blockItemStateProperties.get(PetBowlBlock.COLOR);
                    if (color != null) {
                        lootTableBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(this.applyExplosionDecay(arg, LootItem.lootTableItem(item)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(arg).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PetBowlBlock.COLOR, color)))));
                    }
                }
            }
            return lootTableBuilder;
        });
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModRegistries.BLOCK_LIST.stream().map(Supplier::get).toList();
    }
}
