package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.TheCorgiMod;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ArmorRegistry {
    public static final Registrar<ArmorMaterial> ARMOR_MATERIALS = TheCorgiMod.MANAGER.get().get(Registries.ARMOR_MATERIAL);

    public static final RegistrySupplier<ArmorMaterial> SUNGLASSES = ARMOR_MATERIALS.register(TheCorgiMod.location("sunglasses"),
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class),
            (enumMap) -> enumMap.put(ArmorItem.Type.HELMET, 1)),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(Items.TINTED_GLASS),
            List.of(
                    new ArmorMaterial.Layer(TheCorgiMod.location("sunglasses"), "", false)
            ),
            0.0F, // Toughness
            0.0F // Knockback Resistance
    ));

    public static void init() {}
}
