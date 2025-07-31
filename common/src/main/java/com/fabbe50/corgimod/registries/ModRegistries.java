package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.block.DogDoorBlock;
import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.entity.PetBowlBlockEntity;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.fabbe50.corgimod.world.inventory.PetBowlMenu;
import com.fabbe50.corgimod.world.item.*;
import com.google.common.base.Suppliers;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModRegistries {
    public static final Registrar<Block> BLOCKS = TheCorgiMod.MANAGER.get().get(Registries.BLOCK);
    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES = TheCorgiMod.MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static final Registrar<Item> ITEMS = TheCorgiMod.MANAGER.get().get(Registries.ITEM);
    public static final Registrar<Potion> POTIONS = TheCorgiMod.MANAGER.get().get(Registries.POTION);
    public static final List<RegistrySupplier<Block>> BLOCK_LIST = new ArrayList<>();
    public static final List<RegistrySupplier<Item>> SPAWN_EGGS = new ArrayList<>();

    //Blocks
    public static final RegistrySupplier<Block> DOG_DOOR = register(TheCorgiMod.location("dog_door"), () -> new DogDoorBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
    public static final RegistrySupplier<Block> CORGI_BED = register(TheCorgiMod.location("corgi_bed"), () -> new PetBedBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).mapColor(MapColor.WOOL)));
    public static final RegistrySupplier<Block> PET_BOWL = register(TheCorgiMod.location("pet_bowl"), () -> new PetBowlBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).mapColor(MapColor.WOOL)));

    //Block Entities
    public static final RegistrySupplier<BlockEntityType<PetBowlBlockEntity>> PET_BOWL_BLOCK_ENTITY = BLOCK_ENTITIES.register(TheCorgiMod.location("pet_bowl_block_entity"), Suppliers.memoize(() -> BlockEntityType.Builder.of(PetBowlBlockEntity::new, PET_BOWL.get()).build(null)));

    //Items
    public static final RegistrySupplier<Item> URANIUM                      = ITEMS.register(TheCorgiMod.location("uranium"), () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> SUNGLASSES                   = ITEMS.register(TheCorgiMod.location("sunglasses"), () -> new ItemSunglasses(ArmorRegistry.SUNGLASSES, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> CORGI_SOUL                   = ITEMS.register(TheCorgiMod.location("corgi_soul"), () -> new CorgiSoulItem(new Item.Properties()));
    public static final RegistrySupplier<Item> CORGI_CARRY_TOOL             = ITEMS.register(TheCorgiMod.location("corgi_carry_tool"), () -> new CorgiHolderItem(new Item.Properties()));

    //Block Items
    public static final List<RegistrySupplier<Item>> DOG_DOORS              = registerDogDoors();
    public static final List<RegistrySupplier<Item>> CORGI_BEDS             = registerPetBeds();
    public static final List<RegistrySupplier<Item>> PET_BOWLS              = registerPetBowls();

    //Spawn Eggs
    public static final RegistrySupplier<Item> SPAWN_EGG_RANDOM_CORGI       = ITEMS.register(TheCorgiMod.location("spawn_egg_corgi_random"), () -> new RandomVariantSpawnEgg<>(EntityRegistry.CORGI, new Item.Properties(), CorgiVariants.CORGI_VARIANTS_REGISTRY));

    public static final RegistrySupplier<Item> SPAWN_EGG_NORMAL_CORGI       = registerCorgiSpawnEgg("corgi_normal", 0xE0BC8D, 0x89644E, new Item.Properties(), CorgiVariants.NORMAL);
    public static final RegistrySupplier<Item> SPAWN_EGG_ANTI_CORGI         = registerCorgiSpawnEgg("corgi_anti", 0x1F4372, 0x769BB1, new Item.Properties(), CorgiVariants.ANTI);
    public static final RegistrySupplier<Item> SPAWN_EGG_BODYGUARD_CORGI    = registerCorgiSpawnEgg("corgi_bodyguard", 0x171717, 0xd4d4d4, new Item.Properties(), CorgiVariants.BODYGUARD);
    public static final RegistrySupplier<Item> SPAWN_EGG_BUSINESS_CORGI     = registerCorgiSpawnEgg("corgi_business", 0x171717, 0xe6e6e6, new Item.Properties(), CorgiVariants.BUSINESS);
    public static final RegistrySupplier<Item> SPAWN_EGG_FABBE50_CORGI      = registerCorgiSpawnEgg("corgi_fabbe50", 0xd4d6e8, 0xffffcc, new Item.Properties(), CorgiVariants.FABBE50);
    public static final RegistrySupplier<Item> SPAWN_EGG_FARMER_CORGI       = registerCorgiSpawnEgg("corgi_farmer", 0xE0BC8D, 0x89644E, new Item.Properties(), CorgiVariants.FARMER);
    public static final RegistrySupplier<Item> SPAWN_EGG_HERO_CORGI         = registerCorgiSpawnEgg("corgi_hero", 0xE0BC8D, 0xff0000, new Item.Properties(), CorgiVariants.HERO);
    public static final RegistrySupplier<Item> SPAWN_EGG_LOVE_CORGI         = registerCorgiSpawnEgg("corgi_love", 0xEF8FDC, 0xAB4D9D, new Item.Properties(), CorgiVariants.LOVE);
    public static final RegistrySupplier<Item> SPAWN_EGG_MELON_CORGI        = registerCorgiSpawnEgg("corgi_melon", 0x55681a, 0xaeb541, new Item.Properties(), CorgiVariants.MELON);
    public static final RegistrySupplier<Item> SPAWN_EGG_NERD_CORGI         = registerCorgiSpawnEgg("corgi_nerd", 0xffffff, 0x000000, new Item.Properties(), CorgiVariants.NERD);
    public static final RegistrySupplier<Item> SPAWN_EGG_PIRATE_CORGI       = registerCorgiSpawnEgg("corgi_pirate", 0xddb471, 0x41392c, new Item.Properties(), CorgiVariants.PIRATE);
    public static final RegistrySupplier<Item> SPAWN_EGG_RADIOACTIVE_CORGI  = registerCorgiSpawnEgg("corgi_radioactive", 0x7ef927, 0x50a800, new Item.Properties(), CorgiVariants.RADIOACTIVE);
    public static final RegistrySupplier<Item> SPAWN_EGG_SPY_CORGI          = registerCorgiSpawnEgg("corgi_spy", 0x000000, 0x000000, new Item.Properties(), CorgiVariants.SPY);
    public static final RegistrySupplier<Item> SPAWN_EGG_SUNGLASSES_CORGI   = registerCorgiSpawnEgg("corgi_sunglasses", 0xddb471, 0x000000, new Item.Properties(), CorgiVariants.SUNGLASSES);

    public static final RegistrySupplier<Item> SPAWN_EGG_BOGGED_CORGI           = registerSpawnEgg("corgi_bogged", EntityRegistry.BOGGED_CORGI, 0x3b4519, 0xb2de71, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_CREEPER_CORGI          = registerSpawnEgg("corgi_creeper", EntityRegistry.CREEPER_CORGI, 0x57af49, 0x138c10, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_DROWNED_CORGI          = registerSpawnEgg("corgi_drowned", EntityRegistry.DROWNED_CORGI, 0x2d453d, 0x3bbfa9, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_ENDER_CORGI            = registerSpawnEgg("corgi_ender", EntityRegistry.ENDER_CORGI, 0x160f1a, 0x281830, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_HUSK_CORGI             = registerSpawnEgg("corgi_husk", EntityRegistry.HUSK_CORGI, 0x383627, 0x363113, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_SKELETON_CORGI         = registerSpawnEgg("corgi_skeleton", EntityRegistry.SKELETON_CORGI, 0xbebebe, 0x505050, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_SPIDER_CORGI           = registerSpawnEgg("corgi_spider", EntityRegistry.SPIDER_CORGI, 0x29251e, 0x870309, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_STRAY_CORGI            = registerSpawnEgg("corgi_stray", EntityRegistry.STRAY_CORGI, 0x80bfb5, 0x505050, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_WITHER_SKELETON_CORGI  = registerSpawnEgg("corgi_wither_skeleton", EntityRegistry.WITHER_SKELETON_CORGI, 0x262525, 0x121111, new Item.Properties());
    public static final RegistrySupplier<Item> SPAWN_EGG_ZOMBIE_CORGI           = registerSpawnEgg("corgi_zombie", EntityRegistry.ZOMBIE_CORGI, 0x45673e, 0x224119, new Item.Properties());

    public static final RegistrySupplier<Potion> HERO_ABILITIES = POTIONS.register(TheCorgiMod.location("hero_abilities_potion"), () ->
            new Potion(
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 5),
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 5),
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400),
                    new MobEffectInstance(MobEffects.REGENERATION, 400, 5),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 5)
            )
    );

    private static RegistrySupplier<Block> register(ResourceLocation location, Supplier<Block> blockSupplier) {
        RegistrySupplier<Block> block = BLOCKS.register(location, blockSupplier);
        BLOCK_LIST.add(block);
        return block;
    }

    private static List<RegistrySupplier<Item>> registerDogDoors() {
        List<RegistrySupplier<Item>> items = new ArrayList<>();
        for (WoodType woodType : WoodType.values()) {
            items.add(ITEMS.register(TheCorgiMod.location("dog_door_" + woodType.getSerializedName()), () -> new DogDoorBlockItem(DOG_DOOR.get(), new Item.Properties().component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(DogDoorBlock.WOOD_TYPE, woodType)))));
        }
        return items;
    }

    private static List<RegistrySupplier<Item>> registerPetBeds() {
        List<RegistrySupplier<Item>> items = new ArrayList<>();
        for (WoodType woodType : WoodType.values()) {
            for (Color wool : Color.values()) {
                items.add(ITEMS.register(TheCorgiMod.location("corgi_bed_" + woodType.getSerializedName() + "_" + wool.getSerializedName()), () -> new PetBedBlockItem(CORGI_BED.get(), new Item.Properties().stacksTo(1).component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(PetBedBlock.WOOD_TYPE, woodType).with(PetBedBlock.WOOL_COLOR, wool)))));
            }
        }
        return items;
    }

    private static List<RegistrySupplier<Item>> registerPetBowls() {
        List<RegistrySupplier<Item>> items = new ArrayList<>();
        for (Color color : Color.values()) {
            items.add(ITEMS.register(TheCorgiMod.location("pet_bowl_" + color.getSerializedName()), () -> new PetBowlBlockItem(PET_BOWL.get(), new Item.Properties().component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(PetBowlBlock.COLOR, color)))));
        }
        return items;
    }

    private static RegistrySupplier<Item> registerSpawnEgg(String registryName, RegistrySupplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor, Item.Properties properties) {
        RegistrySupplier<Item> item = ITEMS.register(TheCorgiMod.location("spawn_egg_" + registryName), () -> new ExtSpawnEggItem(entityType, backgroundColor, highlightColor, properties));
        SPAWN_EGGS.add(item);
        return item;
    }

    private static RegistrySupplier<Item> registerCorgiSpawnEgg(String registryName, int backgroundColor, int highlightColor, Item.Properties properties, ResourceKey<CorgiVariant> corgiVariant) {
        RegistrySupplier<Item> item = ITEMS.register(TheCorgiMod.location("spawn_egg_" + registryName), () -> new VariantSpawnEggItem<>(EntityRegistry.CORGI, backgroundColor, highlightColor, properties, CorgiVariants.CORGI_VARIANTS_REGISTRY, corgiVariant));
        SPAWN_EGGS.add(item);
        return item;
    }

    public static void init() {}
}
