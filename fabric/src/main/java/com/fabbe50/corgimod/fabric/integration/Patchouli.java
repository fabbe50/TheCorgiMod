package com.fabbe50.corgimod.fabric.integration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import vazkii.patchouli.api.PatchouliAPI;

public class Patchouli {
    public static void openBook(ServerPlayer player, ResourceLocation location) {
        PatchouliAPI.get().openBookGUI(player, location);
    }
}
