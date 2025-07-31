package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.ModRegistries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheCorgiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistrySupplier<Item> item : ModRegistries.CORGI_BEDS) {
            withExistingParent(item.getId().getPath(), TheCorgiMod.location("block/" + item.getId().getPath()));
        }
        for (RegistrySupplier<Item> item : ModRegistries.DOG_DOORS) {
            withExistingParent(item.getId().getPath(), TheCorgiMod.location("block/" + item.getId().getPath()))
                    .transforms()
                    .transform(ItemDisplayContext.GUI).rotation(30, 225, 0).translation(-3.25f, 1, 0).scale(0.625f, 0.625f, 0.625f).end();
        }
        for (RegistrySupplier<Item> item : ModRegistries.PET_BOWLS) {
            withExistingParent(item.getId().getPath(), TheCorgiMod.location("block/" + item.getId().getPath()));
        }

        basicItem(ModRegistries.SUNGLASSES.get());
        basicItem(ModRegistries.URANIUM.get());
        basicItem(ModRegistries.CORGI_SOUL.get());
        basicItem(ModRegistries.CORGI_CARRY_TOOL.get());

        spawnEggItem(ModRegistries.SPAWN_EGG_RANDOM_CORGI.get());
        for (RegistrySupplier<Item> item : ModRegistries.SPAWN_EGGS) {
            spawnEggItem(item.get());
        }
    }
}
