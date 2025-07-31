package com.fabbe50.corgimod.neoforge.integration.patchouli;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import vazkii.patchouli.api.PatchouliAPI;

public class Patchouli {
    public static void openBook(ServerPlayer player, ResourceLocation location) {
        System.out.println("Opening: " + location.toString());
        PatchouliAPI.get().openBookGUI(player, location);
    }
}
