package com.fabbe50.corgimod;

import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

public class Platform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityDataSerializer<Holder<CorgiVariant>> getCorgiVariantSerializer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void openPatchouliBook(ServerPlayer player, ResourceLocation location) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isWearingSunglassesTrinketOrCurio(Player player) {
        throw new AssertionError();
    }
}
