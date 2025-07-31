package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.DogDoorBlock;
import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class Models extends BlockStateProvider {
    public Models(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModRegistries.CORGI_BED.get()).forAllStates(state -> {
            ModelFile model;
            WoodType woodType = state.getValue(PetBedBlock.WOOD_TYPE);
            Color wool_color = state.getValue(PetBedBlock.WOOL_COLOR);
            model = models().withExistingParent(ModRegistries.CORGI_BED.getId().getPath() + "_" + woodType.getSerializedName() + "_" + wool_color.getSerializedName(), TheCorgiMod.location("block/corgi_bed_base"))
                    .texture("0", TheCorgiMod.location("minecraft", "block/" + wool_color.getSerializedName() + "_wool"))
                    .texture("1", TheCorgiMod.location("minecraft", "block/" + woodType.getSerializedName() + "_planks"))
                    .texture("particle", TheCorgiMod.location("minecraft", "block/" + wool_color.getSerializedName() + "_wool"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
        getVariantBuilder(ModRegistries.DOG_DOOR.get()).forAllStates(state -> {
            ModelFile model;
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            Boolean open = state.getValue(DogDoorBlock.OPEN);
            WoodType woodType = state.getValue(DogDoorBlock.WOOD_TYPE);
            if (open) {
                model = models().withExistingParent(ModRegistries.DOG_DOOR.getId().getPath() + "_" + woodType.getSerializedName() + "_open", TheCorgiMod.location("block/dog_door_open"))
                        .texture("4", TheCorgiMod.location("minecraft", "block/" + woodType.getSerializedName() + "_planks"))
                        .texture("particle", TheCorgiMod.location("minecraft", "block/" + woodType.getSerializedName() + "_planks"));
            } else {
                model = models().withExistingParent(ModRegistries.DOG_DOOR.getId().getPath() + "_" + woodType.getSerializedName(), TheCorgiMod.location("block/dog_door"))
                        .texture("4", TheCorgiMod.location("minecraft", "block/" + woodType.getSerializedName() + "_planks"))
                        .texture("particle", TheCorgiMod.location("minecraft", "block/" + woodType.getSerializedName() + "_planks"));
            }
            return ConfiguredModel.builder().modelFile(model).uvLock(true).rotationY(((int)direction.toYRot()) % 360).build();
        });
        getVariantBuilder(ModRegistries.PET_BOWL.get()).forAllStates(state -> {
            ModelFile model;
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            Color color = state.getValue(PetBowlBlock.COLOR);
            Boolean filled = state.getValue(PetBowlBlock.FOOD_IN_BOWL);
            if (filled) {
                model = models().withExistingParent(ModRegistries.PET_BOWL.getId().getPath() + "_" + color.getSerializedName() + "_full", TheCorgiMod.location("block/pet_bowl_full"))
                        .texture("1", TheCorgiMod.location("minecraft", "block/" + color.getSerializedName() + "_concrete"))
                        .texture("particle", TheCorgiMod.location("minecraft", "block/" + color.getSerializedName() + "_concrete"));
            } else {
                model = models().withExistingParent(ModRegistries.PET_BOWL.getId().getPath() + "_" + color.getSerializedName(), TheCorgiMod.location("block/pet_bowl"))
                        .texture("1", TheCorgiMod.location("minecraft", "block/" + color.getSerializedName() + "_concrete"))
                        .texture("particle", TheCorgiMod.location("minecraft", "block/" + color.getSerializedName() + "_concrete"));
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(((int)direction.toYRot()) % 360).build();
        });
    }
}
