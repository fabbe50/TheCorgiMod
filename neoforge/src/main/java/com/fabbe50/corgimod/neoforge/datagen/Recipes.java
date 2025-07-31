package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.registries.ModTags;
import com.fabbe50.corgimod.world.block.DogDoorBlock;
import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {
    public Recipes(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        for (RegistrySupplier<Item> itemSupplier : ModRegistries.DOG_DOORS) {
            Item item = itemSupplier.get();
            BlockItemStateProperties components = item.components().get(DataComponents.BLOCK_STATE);
            if (components != null) {
                WoodType woodType = components.get(DogDoorBlock.WOOD_TYPE);
                if (woodType != null) {
                    Item slab = getItemSlabFromWoodType(woodType);
                    ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                            .pattern("SSS")
                            .pattern("SGS")
                            .define('S', slab)
                            .define('G', ModTags.GLASS)
                            .group(TheCorgiMod.MOD_ID + "_dog_doors")
                            .unlockedBy("has_slab", has(slab))
                            .save(output);
                }
            }
        }
        for (RegistrySupplier<Item> itemSupplier : ModRegistries.CORGI_BEDS) {
            Item item = itemSupplier.get();
            BlockItemStateProperties components = item.components().get(DataComponents.BLOCK_STATE);
            if (components != null) {
                WoodType woodType = components.get(PetBedBlock.WOOD_TYPE);
                Color color = components.get(PetBedBlock.WOOL_COLOR);
                if (woodType != null && color != null) {
                    Item slab = getItemSlabFromWoodType(woodType);
                    Item wool = getItemWoolFromColor(color);
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item)
                            .pattern("W")
                            .pattern("S")
                            .define('W', wool)
                            .define('S', slab)
                            .group(TheCorgiMod.MOD_ID + "_pet_bed")
                            .unlockedBy("has_wool", has(wool))
                            .save(output);
                }
            }
        }
        for (RegistrySupplier<Item> itemSupplier : ModRegistries.PET_BOWLS) {
            Item item = itemSupplier.get();
            BlockItemStateProperties components = item.components().get(DataComponents.BLOCK_STATE);
            if (components != null) {
                Color color = components.get(PetBowlBlock.COLOR);
                if (color != null) {
                    Item dye = getDyeFromColor(color);
                    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item)
                            .pattern("IDI")
                            .pattern("III")
                            .define('I', Items.IRON_INGOT)
                            .define('D', dye)
                            .group(TheCorgiMod.MOD_ID + "pet_bowl")
                            .unlockedBy("has_iron", has(Items.IRON_INGOT))
                            .save(output);
                }
            }
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistries.SUNGLASSES.get())
                .pattern("n n")
                .pattern("n n")
                .pattern("gng")
                .define('n', Items.IRON_NUGGET)
                .define('g', Items.TINTED_GLASS)
                .group(TheCorgiMod.MOD_ID + "_sunglasses")
                .unlockedBy("has_tinted_glass", has(Items.TINTED_GLASS))
                .save(output);
    }

    private Item getItemSlabFromWoodType(WoodType woodType) {
        return switch (woodType) {
            case OAK -> Items.OAK_SLAB;
            case SPRUCE -> Items.SPRUCE_SLAB;
            case BIRCH -> Items.BIRCH_SLAB;
            case JUNGLE -> Items.JUNGLE_SLAB;
            case ACACIA -> Items.ACACIA_SLAB;
            case DARK_OAK -> Items.DARK_OAK_SLAB;
            case MANGROVE -> Items.MANGROVE_SLAB;
            case CHERRY -> Items.CHERRY_SLAB;
            case BAMBOO -> Items.BAMBOO_SLAB;
            case CRIMSON -> Items.CRIMSON_SLAB;
            case WARPED -> Items.WARPED_SLAB;
        };
    }

    private Item getItemWoolFromColor(Color color) {
        return switch (color) {
            case WHITE -> Items.WHITE_WOOL;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
            case GRAY -> Items.GRAY_WOOL;
            case BLACK -> Items.BLACK_WOOL;
            case BROWN -> Items.BROWN_WOOL;
            case RED -> Items.RED_WOOL;
            case ORANGE -> Items.ORANGE_WOOL;
            case YELLOW -> Items.YELLOW_WOOL;
            case LIME -> Items.LIME_WOOL;
            case GREEN -> Items.GREEN_WOOL;
            case CYAN -> Items.CYAN_WOOL;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
            case BLUE -> Items.BLUE_WOOL;
            case PURPLE -> Items.PURPLE_WOOL;
            case MAGENTA -> Items.MAGENTA_WOOL;
            case PINK -> Items.PINK_WOOL;
        };
    }

    private Item getDyeFromColor(Color color) {
        return switch (color) {
            case WHITE -> Items.WHITE_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case GRAY -> Items.GRAY_DYE;
            case BLACK -> Items.BLACK_DYE;
            case BROWN -> Items.BROWN_DYE;
            case RED -> Items.RED_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case GREEN -> Items.GREEN_DYE;
            case CYAN -> Items.CYAN_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case PINK -> Items.PINK_DYE;
        };
    }
}
