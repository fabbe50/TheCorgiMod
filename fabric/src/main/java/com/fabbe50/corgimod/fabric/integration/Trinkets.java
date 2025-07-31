package com.fabbe50.corgimod.fabric.integration;

import com.fabbe50.corgimod.registries.ModRegistries;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;

import java.util.NoSuchElementException;

public class Trinkets {
    public static boolean isPlayerWearingSunglasses(Player player) {
        try {
            return TrinketsApi.getTrinketComponent(player).get().isEquipped(ModRegistries.SUNGLASSES.get());
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }
}
