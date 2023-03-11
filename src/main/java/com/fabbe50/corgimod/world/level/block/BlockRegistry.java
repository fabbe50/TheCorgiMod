package com.fabbe50.corgimod.world.level.block;

import com.fabbe50.corgimod.CorgiMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, CorgiMod.MODID);

    public static final RegistryObject<Block> ACACIA_DOG_DOOR = DEFERRED_REGISTER.register("acacia_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> BIRCH_DOG_DOOR = DEFERRED_REGISTER.register("birch_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> CRIMSON_DOG_DOOR = DEFERRED_REGISTER.register("crimson_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> DARK_OAK_DOG_DOOR = DEFERRED_REGISTER.register("dark_oak_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> JUNGLE_DOG_DOOR = DEFERRED_REGISTER.register("jungle_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> MANGROVE_DOG_DOOR = DEFERRED_REGISTER.register("mangrove_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> OAK_DOG_DOOR = DEFERRED_REGISTER.register("oak_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> SPRUCE_DOG_DOOR = DEFERRED_REGISTER.register("spruce_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> WARPED_DOG_DOOR = DEFERRED_REGISTER.register("warped_dog_door", () -> new DogDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
}
